package com.hmdp.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.constant.RedisConstants;
import com.hmdp.constant.SystemConstants;
import com.hmdp.dto.Result;
import com.hmdp.dto.ScrollResult;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.Blog;
import com.hmdp.entity.Follow;
import com.hmdp.entity.User;
import com.hmdp.mapper.BlogMapper;
import com.hmdp.service.IBlogService;
import com.hmdp.service.IFollowService;
import com.hmdp.service.IUserService;
import com.hmdp.utils.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {
    @Resource
    private IUserService userService;

    @Autowired
    private IFollowService followService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加笔记
     * @param blog
     * @return
     */
    @Override
    public Result saveBlog(Blog blog) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        blog.setUserId(user.getId());

        // 保存探店博文
        boolean saveResult = this.save(blog);
        if (!saveResult) {
            return Result.fail("保存失败");
        }

        // 查询粉丝列表
        List<Follow> followUserIdList = followService.query()
                .eq("follow_user_id", user.getId()).list();
        if (followUserIdList == null || followUserIdList.isEmpty()) {
            return Result.ok(blog.getId());
        }
        // 推送博文给粉丝
        ZSetOperations<String, String> operations = stringRedisTemplate.opsForZSet();
        for (Follow follow : followUserIdList) {
            Long fansUserId = follow.getUserId();
            String key = RedisConstants.FEED_KEY + fansUserId;
            Long blogId = blog.getId();
            Double score = Double.longBitsToDouble(System.currentTimeMillis());

            operations.add(key, blogId.toString(), score);
            stringRedisTemplate.expire(key, RedisConstants.FEED_TTL, TimeUnit.MINUTES);
        }

        // 返回id
        return Result.ok(blog.getId());
    }

    /**
     * 笔记点赞
     * @param id
     * @return
     */
    @Override
    public Result likeBlog(Long id) {
        ZSetOperations<String, String> operations = stringRedisTemplate.opsForZSet();

        // 当前登录用户id
        Long userId = UserHolder.getUser().getId();
        // SortedSet的key
        String key = RedisConstants.BLOG_LIKED_KEY + id;
        Double score = operations.score(key, userId.toString());

        // 判断用户是否已点赞
        // 用户已点赞
        // 已点赞列表删除该用户
        // 数据库点赞数-1
        if (score != null) {
            Long removeNum = operations.remove(key, userId.toString());
            if (removeNum != null && removeNum > 0) {
                boolean cancelLikeNumResult = this.update()
                        .setSql("liked = liked - 1").eq("id", id).update();
                return  cancelLikeNumResult ? Result.ok("取消成功") : Result.fail("取消失败");
            }
        }

        // 用户未点赞
        // 已点赞列表添加该用户
        // 数据库点赞数+1
        else {
            Double currentScore = Double.longBitsToDouble(System.currentTimeMillis());
            Boolean addResult = operations.add(key, userId.toString(), currentScore);
            if (addResult != null && addResult) {
                boolean addLikeNumResult = this.update()
                        .setSql("liked = liked + 1").eq("id", id).update();
                return addLikeNumResult ? Result.ok("点赞成功") : Result.fail("点赞失败");
            }
        }

        return Result.fail("操作失败");
    }

    /**
     * 获取该笔记的点赞排名榜（前5名）
     * @param id
     * @return
     */
    @Override
    public Result     /**
     * 获取该笔记的点赞排名榜（前5名）
     * @param id
     * @return
     */queryBlogLike(Long id) {
        ZSetOperations<String, String> operations = stringRedisTemplate.opsForZSet();

        // SortedSet的key
        String key = RedisConstants.BLOG_LIKED_KEY + id;

        // 用户编号（根据点赞时间排序）
        Set<String> rangeList = operations.range(key, 0, 4);
        if (rangeList == null || rangeList.isEmpty()) {
            return Result.ok(Collections.EMPTY_LIST);
        }
        List<Long> idList = rangeList.stream().map(Long::valueOf).collect(Collectors.toList());

        // 根据用户编号返回用户信息
        // 还要按照前面的id顺序返回用户信息
        String idListStr = StringUtils.join(idList, ",");
        List<UserDTO> userDTOList = userService.query()
                .in("id", idList)
                // 根据idList的id顺序返回用户信息列表
                .last("ORDER BY FIELD(id," + idListStr + ")").list()
                .stream().map(user -> {
                    UserDTO userDTO = new UserDTO();
                    BeanUtils.copyProperties(user, userDTO);
                    return userDTO;
                }).collect(Collectors.toList());

        return Result.ok(userDTOList);
    }

    /**
     * 根据用户id查询笔记（分页查询）
     * @param current
     * @return
     */
    @Override
    public Result queryBlogByUserId(Long userId, Integer current) {
        // 根据用户查询
        Page<Blog> page = this.query()
                .eq("user_id", userId).page(new Page<>(current, SystemConstants.DEFAULT_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        if (records == null || records.isEmpty()) {
            return Result.ok(Collections.EMPTY_LIST);
        }

        // 查询该blog的用户
        // 记录登录是否已点赞
        records.forEach(blog ->{
            this.queryBlogUser(blog);
            this.isLikeBlog(blog);
        });

        return Result.ok(records);
    }

    /**
     * 分页查询关注博主的笔记
     * @param offset 偏移量
     * @param lastId 时间戳
     * @return
     */
    @Override
    public Result queryBlogByFollow(Long offset, Long lastId) {
        Long userId = UserHolder.getUser().getId();

        ZSetOperations<String, String> operations = stringRedisTemplate.opsForZSet();
        String key = RedisConstants.FEED_KEY + userId;
        // 系统投喂的笔记编号
        // value: 笔记编号
        // score: 时间戳
        Set<ZSetOperations.TypedTuple<String>> tuples =
                operations.reverseRangeByScoreWithScores(key, 0, Double.longBitsToDouble(lastId), offset, 2);
        if (tuples == null || tuples.isEmpty()) {
            ScrollResult empty = new ScrollResult();
            empty.setList(Collections.EMPTY_LIST);
            empty.setOffset(Math.toIntExact(offset));
            empty.setMinTime(lastId);

            return Result.ok(empty);
        }

        // 记录新的offset和lastId
        Integer newoffset = Math.toIntExact(offset);
        Double newLastId = Double.longBitsToDouble(lastId);
        // 记录博客id
        List<Long> blogIdList = new ArrayList<>();
        for (ZSetOperations.TypedTuple<String> tuple : tuples) {
            String value = tuple.getValue();
            // 添加博客id
            blogIdList.add(Long.valueOf(value));

            Double score = tuple.getScore();
            if (newLastId.equals(score)) {
                newoffset++;
            } else {
                newLastId = score;
                newoffset = 1;
            }
        }

        // 还要按照前面的id顺序返回博客
        String idListStr = StringUtils.join(blogIdList, ",");
        // 根据笔记编号返回笔记
        List<Blog> blogList = this.query()
                .in("id", blogIdList)
                // 根据idList的id顺序返回博客
                .last("ORDER BY FIELD(id," + idListStr + ")").list();

        // 查询该blog的用户
        // 记录登录是否已点赞
        blogList.forEach(blog ->{
            this.queryBlogUser(blog);
            this.isLikeBlog(blog);
        });

        // 返回结果
        ScrollResult scrollResult = new ScrollResult();
        scrollResult.setList(blogList);
        scrollResult.setOffset(newoffset);
        scrollResult.setMinTime(Double.doubleToLongBits(newLastId));

        return Result.ok(scrollResult);
    }

    /**
     * 查询热门笔记（分页查询）
     * @param current
     * @return
     */
    @Override
    public Result queryHotBlog(Integer current) {
        // 根据用户查询
        Page<Blog> page = this.query()
                .orderByDesc("liked")
                .page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        // 查询该blog的用户
        // 记录登录是否已点赞
        records.forEach(blog ->{
            this.queryBlogUser(blog);
            this.isLikeBlog(blog);
        });
        return Result.ok(records);
    }


    /**
     * 根据笔记id查询笔记
     * @param id
     * @return
     */
    @Override
    public Result queryBlogById(Long id) {
        // 查询blog
        Blog blog = this.getById(id);
        if (blog == null) {
            return Result.fail("笔记不存在");
        }
        // 查询该blog的用户
        this.queryBlogUser(blog);
        // 登录用户是否已点赞
        this.isLikeBlog(blog);

        return Result.ok(blog);
    }

    /**
     * 查询该blog的用户
     * @param blog
     */
    private void queryBlogUser(Blog blog) {
        Long userId = blog.getUserId();
        User user = userService.getById(userId);
        blog.setName(user.getNickName());
        blog.setIcon(user.getIcon());
    }

    /**
     * 用户是否已点赞
     * @param blog
     */
    private void isLikeBlog(Blog blog) {
        // 获取当前登录用户
        UserDTO user = UserHolder.getUser();
        if (user == null) {
            blog.setIsLike(false);
            return;
        }

        String key = RedisConstants.BLOG_LIKED_KEY + blog.getId();

        Double score = stringRedisTemplate.opsForZSet().score(key, user.getId().toString());;
        blog.setIsLike(score != null);
    }
}

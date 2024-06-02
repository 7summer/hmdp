package com.hmdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmdp.dto.Result;
import com.hmdp.entity.Blog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IBlogService extends IService<Blog> {
    /**
     * 添加笔记
     * @param blog
     * @return
     */
    Result saveBlog(Blog blog);
    /**
     * 笔记点赞
     * @param id
     * @return
     */
    Result likeBlog(Long id);
    /**
     * 获取该笔记的点赞排名榜（前5名）
     * @param id
     * @return
     */
    Result queryBlogLike(Long id);
    /**
     * 根据用户id查询笔记（分页查询）
     * @param current
     * @return
     */
    Result queryBlogByUserId(Long userId, Integer current);
    /**
     * 分页查询关注博主的笔记
     * @param offset 偏移量
     * @param lastId 时间戳
     * @return
     */
    Result queryBlogByFollow(Long offset, Long lastId);
    /**
     * 查询热门笔记（分页查询）
     * @param current
     * @return
     */
    Result queryHotBlog(Integer current);
    /**
     * 根据笔记id查询笔记
     * @param id
     * @return
     */
    Result queryBlogById(Long id);
}

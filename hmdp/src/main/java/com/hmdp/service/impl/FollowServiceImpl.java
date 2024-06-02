package com.hmdp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.Follow;
import com.hmdp.mapper.FollowMapper;
import com.hmdp.service.IFollowService;
import com.hmdp.service.IUserService;
import com.hmdp.utils.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {
    @Autowired
    private IUserService userService;

    /**
     * 关注或取关博主
     * 当isFollow为true时，关注博主
     * 当isFolow为false时，取关博主
     * @param followUserId
     * @param isFollow
     * @return
     */
    @Override
    public Result follow(Long followUserId, boolean isFollow) {
        // 当前登录用户id
        Long userId = UserHolder.getUser().getId();
        if (userId.equals(followUserId)) {
            return Result.fail("不能自己关注自己");
        }

        Follow follow = this.query()
                .eq("user_id", userId).eq("follow_user_id", followUserId).one();

        // 关注博主
        if (isFollow) {
            // 用户已关注博主
            if (follow != null) {
                return Result.fail("已关注博主");
            }

            follow = new Follow();
            follow.setUserId(userId);
            follow.setFollowUserId(followUserId);

            this.save(follow);
        }
        // 取关博主
        else {
            // 用户未关注博主
            if (follow == null) {
                return Result.fail("取关博主失败");
            }

            this.removeById(follow);
        }

        return Result.ok();
    }

    /**
     * 是否已关注博主
     * 已关注博主，返回true
     * 未关注博主，返回false
     * @param forrowUserId
     * @return
     */
    @Override
    public Result isFollow(Long forrowUserId) {
        // 当前登录用户id
        Long userId = UserHolder.getUser().getId();

        Follow follow = this.query()
                .eq("user_id", userId).eq("follow_user_id", forrowUserId).one();

        return Result.ok(follow != null);
    }

    /**
     * 共同关注
     * @param forrowUserId
     * @return
     */
    @Override
    public Result commonFollow(Long forrowUserId) {
        // 当前登录用户id
        Long userId = UserHolder.getUser().getId();

        // 当前用户的关注列表
        List<Follow> userFollowList = this.query()
                .eq("user_id", userId).list();
        if (userFollowList == null || userFollowList.isEmpty()) {
            return Result.ok(Collections.EMPTY_LIST);
        }

        // 关注用户的关注列表
        List<Follow> followUserFollowList = this.query()
                .eq("user_id", forrowUserId).list();
        if (followUserFollowList == null || followUserFollowList.isEmpty()) {
            return Result.ok(Collections.EMPTY_LIST);
        }
        // 关注列表里的每一个关注用户id
        Set<Long> followUserFollowIdSet = followUserFollowList.stream()
                .map(follow -> follow.getFollowUserId()).collect(Collectors.toSet());


        // 共同关注的用户id列表
        List<Long> commonUserIdList = new ArrayList<>();
        for (Follow follow : userFollowList) {
            if (followUserFollowIdSet.contains(follow.getFollowUserId())) {
                commonUserIdList.add(follow.getFollowUserId());
            }
        }
        if (commonUserIdList.isEmpty()) {
            return Result.ok(Collections.EMPTY_LIST);
        }

        // 根据commonUserIdList返回用户信息列表
        List<UserDTO> userDTOList = userService.listByIds(commonUserIdList)
                .stream()
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    BeanUtils.copyProperties(user, userDTO);

                    return userDTO;
                }).collect(Collectors.toList());
        return Result.ok(userDTOList);
    }
}

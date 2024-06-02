package com.hmdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmdp.dto.Result;
import com.hmdp.entity.Follow;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IFollowService extends IService<Follow> {
    /**
     * 关注或取关博主
     * 当isFollow为true时，关注博主
     * 当isFolow为false时，取关博主
     * @param forrowUserId
     * @param isFollow
     * @return
     */
    Result follow(Long forrowUserId, boolean isFollow);
    /**
     * 是否已关注博主
     * 已关注博主，返回true
     * 未关注博主，返回false
     * @param forrowUserId
     * @return
     */
    Result isFollow(Long forrowUserId);
    /**
     * 共同关注
     * @param forrowUserId
     * @return
     */
    Result commonFollow(Long forrowUserId);
}

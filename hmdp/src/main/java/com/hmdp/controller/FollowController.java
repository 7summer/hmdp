package com.hmdp.controller;


import com.hmdp.dto.Result;
import com.hmdp.service.IFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private IFollowService iFollowService;

    /**
     * 关注或取关博主
     * 当isFollow为true时，关注博主
     * 当isFolow为false时，取关博主
     * @param forrowUserId 博主id
     * @param isFollow
     * @return
     */
    @PutMapping("/{id}/{isFollow}")
    public Result follow(@PathVariable("id") Long forrowUserId, @PathVariable boolean isFollow) {
        return iFollowService.follow(forrowUserId, isFollow);
    }

    /**
     * 是否已关注博主
     * 已关注博主，返回true
     * 未关注博主，返回false
     * @param forrowUserId
     * @return
     */
    @GetMapping("/or/not/{id}")
    public Result isFollow(@PathVariable("id") Long forrowUserId) {
        return iFollowService.isFollow(forrowUserId);
    }

    /**
     * 共同关注
     * @param forrowUserId
     * @return
     */
    @GetMapping("/common/{id}")
    public Result commonFollow(@PathVariable("id") Long forrowUserId) {
        return iFollowService.commonFollow(forrowUserId);
    }
}

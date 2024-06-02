package com.hmdp.controller;


import com.hmdp.dto.Result;
import com.hmdp.entity.Blog;
import com.hmdp.service.IBlogService;
import com.hmdp.utils.UserHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private IBlogService blogService;

    /**
     * 添加笔记
     * @param blog
     * @return
     */
    @PostMapping
    public Result saveBlog(@RequestBody Blog blog) {
        return blogService.saveBlog(blog);
    }

    /**
     * 笔记点赞
     * @param id
     * @return
     */
    @PutMapping("/like/{id}")
    public Result likeBlog(@PathVariable("id") Long id) {
        return blogService.likeBlog(id);
    }

    /**
     * 获取该笔记的点赞排名榜（前5名）
     * @param id
     * @return
     */
    @GetMapping("/likes/{id}")
    public Result queryBlogLike(@PathVariable("id") Long id) {
        return blogService.queryBlogLike(id);
    }


    /**
     * 查询我的笔记
     * @param current
     * @return
     */
    @GetMapping("/of/me")
    public Result queryMyBlog(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        Long userId = UserHolder.getUser().getId();

        return blogService.queryBlogByUserId(userId, current);
    }

    /**
     * 查询指定用户的笔记
     * @param userId
     * @param current
     * @return
     */
    @GetMapping("/of/user")
    public Result queryBlogByUserId(@RequestParam("id") Long userId, @RequestParam(value = "current", defaultValue = "1") Integer current) {
        return blogService.queryBlogByUserId(userId, current);
    }

    /**
     * 分页查询关注博主的笔记
     * @param offset 偏移量
     * @param lastId 时间戳
     * @return
     */
    @GetMapping("/of/follow")
    public Result queryBlogByFollow(@RequestParam(defaultValue = "0") Long offset, @RequestParam Long lastId) {
        return blogService.queryBlogByFollow(offset, lastId);
    }

    /**
     * 查询热门笔记
     * @param current
     * @return
     */
    @GetMapping("/hot")
    public Result queryHotBlog(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        return blogService.queryHotBlog(current);
    }

    /**
     * 根据笔记id查询笔记
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result queryBlogById(@PathVariable(value = "id") Long id) {
        return blogService.queryBlogById(id);
    }
}

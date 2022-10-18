package com.ll.comibird.Week_Mission.app.post.controller;

import com.ll.comibird.Week_Mission.app.post.entity.Post;
import com.ll.comibird.Week_Mission.app.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String postList(Model model) {
        List<Post> posts = postService.findAllByOrderByIdDesc();

        model.addAttribute("posts", posts);

        return "post/list";
    }
}

package com.ll.comibird.Week_Mission.app.post.controller;

import com.ll.comibird.Week_Mission.app.post.entity.Post;
import com.ll.comibird.Week_Mission.app.post.form.PostForm;
import com.ll.comibird.Week_Mission.app.post.service.PostService;
import com.ll.comibird.Week_Mission.app.security.dto.MemberContext;
import com.ll.comibird.Week_Mission.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartRequest;

import javax.validation.Valid;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWrite() {
        return "post/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@AuthenticationPrincipal MemberContext memberContext, @Valid PostForm postForm) {

        Post post = postService.write(memberContext.getMember(), postForm.getSubject(), postForm.getContent(), postForm.getHashTagContents());

        return "redirect:/post/list?msg=" + Util.url.encode(post.getId()+"번 개시물이 작성되었습니다.");
    }
}

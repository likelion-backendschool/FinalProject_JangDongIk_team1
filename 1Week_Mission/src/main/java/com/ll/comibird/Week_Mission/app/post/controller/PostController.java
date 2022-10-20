package com.ll.comibird.Week_Mission.app.post.controller;

import com.ll.comibird.Week_Mission.app.member.entity.Member;
import com.ll.comibird.Week_Mission.app.post.entity.Post;
import com.ll.comibird.Week_Mission.app.post.form.PostForm;
import com.ll.comibird.Week_Mission.app.post.service.PostService;
import com.ll.comibird.Week_Mission.app.security.dto.MemberContext;
import com.ll.comibird.Week_Mission.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String showPostList(Model model) {
        List<Post> posts = postService.findAllByOrderByIdDesc();
        model.addAttribute("posts", posts);

        return "post/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showPostWrite() {
        return "post/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String postWrite(@AuthenticationPrincipal MemberContext memberContext, @Valid PostForm postForm) {
        Post post = postService.write(memberContext.getMember(), postForm.getSubject(), postForm.getContent(), postForm.getPostHashTagContents());

        return "redirect:/post/list?msg=" + Util.url.encode(post.getId() + "번 게시물이 작성되었습니다.");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String postDetail(@AuthenticationPrincipal MemberContext memberContext, @PathVariable Long id, Model model) {
        Post post = postService.findByPostId(id).get();
        Member member = memberContext.getMember();

        if (!postService.canAccess(member, post)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("post", post);

        return "post/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/delete")
    public String delete(@AuthenticationPrincipal MemberContext memberContext, @PathVariable Long id) {
        Post post = postService.findByPostId(id).get();
        Member member = memberContext.getMember();

        if (!postService.canAccess(member, post)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        postService.delete(post);

        return "redirect:/post/list?msg=" + Util.url.encode(post.getId() + "번 게시물이 삭제되었습니다.");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String showModify(@AuthenticationPrincipal MemberContext memberContext, @PathVariable long id, Model model) {
        Post post = postService.findByPostId(id).get();
        Member member = memberContext.getMember();

        if (!postService.canAccess(member, post)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("post", post);

        return "post/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String modify(@AuthenticationPrincipal MemberContext memberContext, @PathVariable long id, @Valid PostForm postForm) {
        Post post = postService.findByPostId(id).get();
        Member member = memberContext.getMember();

        if (!postService.canAccess(member, post)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        postService.modify(post, postForm.getSubject(), postForm.getContent(), postForm.getPostHashTagContents());

        return "redirect:/post/list?msg=" + Util.url.encode(post.getId() + "번 게시물이 수정되었습니다.");
    }
}

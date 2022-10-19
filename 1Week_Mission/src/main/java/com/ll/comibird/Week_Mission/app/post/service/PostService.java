package com.ll.comibird.Week_Mission.app.post.service;

import com.ll.comibird.Week_Mission.app.member.entity.Member;
import com.ll.comibird.Week_Mission.app.post.entity.Post;
import com.ll.comibird.Week_Mission.app.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostHashTagService postHashTagService;

    public List<Post> findAllByOrderByIdDesc() {
        return postRepository.findAllByOrderByIdDesc();
    }

    public Post write(Member member, String subject, String content, String postHashTagContents) {
        Post post = Post.builder()
                .author(member)
                .subject(subject)
                .content(content)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        postRepository.save(post);
        postHashTagService.applyHashTags(post, postHashTagContents);
        return post;
    }

    public Optional<Post> findByPostId(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post;
    }

    public boolean canAccess(Member member, Post post) {
        return member.getId().equals(post.getAuthor().getId());
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public void modify(Post post, String subject, String content, String postHashTagContents) {
        post.setSubject(subject);
        post.setContent(content);
        postHashTagService.applyHashTags(post, postHashTagContents);

        postRepository.save(post);
    }
}

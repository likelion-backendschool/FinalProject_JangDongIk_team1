package com.ll.comibird.Week_Mission.app.post.service;

import com.ll.comibird.Week_Mission.app.member.entity.Member;
import com.ll.comibird.Week_Mission.app.post.entity.Post;
import com.ll.comibird.Week_Mission.app.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostHashTagService postHashTagService;
    public List<Post> findAllByOrderByIdDesc() {
        return postRepository.findAllByOrderByIdDesc();
    }

    public Post write(Member member, String subject, String content, String hashTagContents) {
        Post post = Post.builder()
                .author(member)
                .subject(subject)
                .content(content)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        postRepository.save(post);
        postHashTagService.applyHashTags(post, hashTagContents);
        return post;
    }
}

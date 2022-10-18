package com.ll.comibird.Week_Mission.app.post.service;

import com.ll.comibird.Week_Mission.app.post.entity.Post;
import com.ll.comibird.Week_Mission.app.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public List<Post> findAllByOrderByIdDesc() {
        return postRepository.findAllByOrderByIdDesc();
    }
}

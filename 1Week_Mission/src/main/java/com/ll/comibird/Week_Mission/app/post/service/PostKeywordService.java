package com.ll.comibird.Week_Mission.app.post.service;

import com.ll.comibird.Week_Mission.app.post.entity.PostKeyword;
import com.ll.comibird.Week_Mission.app.post.repository.PostKeywordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostKeywordService {
    private final PostKeywordRepository postKeyWordRepository;
    public PostKeyword save(String keywordContent) {
        Optional<PostKeyword> optKeyword = postKeyWordRepository.findByContent(keywordContent);

        if ( optKeyword.isPresent() ) {
            return optKeyword.get();
        }

        PostKeyword postKeyword = PostKeyword
                .builder()
                .content(keywordContent)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        postKeyWordRepository.save(postKeyword);

        return postKeyword;
    }
}

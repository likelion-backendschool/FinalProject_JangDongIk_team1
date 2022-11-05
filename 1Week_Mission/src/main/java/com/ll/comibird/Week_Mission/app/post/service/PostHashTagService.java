package com.ll.comibird.Week_Mission.app.post.service;

import com.ll.comibird.Week_Mission.app.post.entity.Post;
import com.ll.comibird.Week_Mission.app.post.entity.PostHashTag;
import com.ll.comibird.Week_Mission.app.post.entity.PostKeyword;
import com.ll.comibird.Week_Mission.app.post.repository.PostHashTagRepository;
import javassist.compiler.ast.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostHashTagService {
    private final PostKeywordService postKeywordService;
    private final PostHashTagRepository postHashTagRepository;

    public void applyHashTags(Post post, String postHashTagContents) {
        List<PostHashTag> oldPostHashTags = getPostHashTags(post);

        List<String> keywordContents = Arrays.stream(postHashTagContents.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        List<PostHashTag> needToDelete = new ArrayList<>();

        for (PostHashTag oldPostHashTag : oldPostHashTags) {
            boolean contains = keywordContents.stream().anyMatch(s -> s.equals(oldPostHashTag.getPostKeyword().getContent()));

            if (contains == false) {
                needToDelete.add(oldPostHashTag);
            }
        }

        needToDelete.forEach(postHashTag -> {
            postHashTagRepository.delete(postHashTag);
        });

        keywordContents.forEach(keywordContent -> {
            savePostHashTag(post, keywordContent);
        });
    }

    private PostHashTag savePostHashTag(Post post, String keywordContent) {
        PostKeyword postkeyword = postKeywordService.save(keywordContent);

        Optional<PostHashTag> opHashTag = postHashTagRepository.findByPostIdAndPostKeywordId(post.getId(), postkeyword.getId());

        if (opHashTag.isPresent()) {
            return opHashTag.get();
        }

        PostHashTag postHashTag = PostHashTag.builder()
                .post(post)
                .postKeyword(postkeyword)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        postHashTagRepository.save(postHashTag);

        return postHashTag;
    }

    public List<PostHashTag> getPostHashTags(Post post) {
        return postHashTagRepository.findAllByPostId(post.getId());
    }
}

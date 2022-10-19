package com.ll.comibird.Week_Mission.app.post.repository;

import com.ll.comibird.Week_Mission.app.post.entity.Post;
import com.ll.comibird.Week_Mission.app.post.entity.PostHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostHashTagRepository extends JpaRepository<PostHashTag, Long> {
    Optional<PostHashTag> findByPostIdAndPostKeywordId(Long articleId, Long keywordId);

    List<PostHashTag> findAllByPostId(Long postId);
}

package com.ll.exam.mutBook.app.postKeyword.repository;

import com.ll.exam.mutBook.app.postKeyword.entity.PostKeyword;

import java.util.List;

public interface PostKeywordRepositoryCustom {
    List<PostKeyword> getQslAllByAuthorId(Long authorId);
}

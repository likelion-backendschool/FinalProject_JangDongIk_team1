package com.ll.exam.mutBook.app.myBook.repository;

import com.ll.exam.mutBook.app.myBook.entity.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {
    void deleteByProductIdAndOwnerId(long productId, long ownerId);

    List<MyBook> findAllByOwnerId(long ownerId);
}

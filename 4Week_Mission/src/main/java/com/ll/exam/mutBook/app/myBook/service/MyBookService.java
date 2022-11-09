package com.ll.exam.mutBook.app.myBook.service;

import com.ll.exam.mutBook.app.base.dto.RsData;
import com.ll.exam.mutBook.app.myBook.entity.MyBook;
import com.ll.exam.mutBook.app.myBook.repository.MyBookRepository;
import com.ll.exam.mutBook.app.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyBookService {
    private final MyBookRepository myBookRepository;

    @Transactional
    public RsData add(Order order) {
        order.getOrderItems()
                .stream()
                .map(orderItem -> MyBook.builder()
                        .owner(order.getBuyer())
                        .orderItem(orderItem)
                        .product(orderItem.getProduct())
                        .build())
                .forEach(myBookRepository::save);

        return RsData.of("S-1", "나의 책장에 추가되었습니다.");
    }

    @Transactional
    public RsData remove(Order order) {
        order.getOrderItems()
                .stream()
                .forEach(orderItem -> myBookRepository.deleteByProductIdAndOwnerId(orderItem.getProduct().getId(), order.getBuyer().getId()));

        return RsData.of("S-1", "나의 책장에서 제거되었습니다.");
    }

    public List<MyBook> findAll(Long id) {
        return myBookRepository.findAllByOwnerId(id);
    }

    public List<MyBook> findAllByOwnerId(Long id) {
        List<MyBook> myBooks = myBookRepository.findAllByOwnerId(id);
        if(myBooks==null){
            return null;
        }
        return myBooks;
    }
}

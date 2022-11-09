package com.ll.exam.mutBook.app.myBook.controller;

import com.ll.exam.mutBook.app.base.dto.RsData;
import com.ll.exam.mutBook.app.myBook.entity.MyBook;
import com.ll.exam.mutBook.app.myBook.service.MyBookService;
import com.ll.exam.mutBook.app.security.dto.MemberContext;
import com.ll.exam.mutBook.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/myBooks")
@RequiredArgsConstructor
public class ApiV1MyBookController {
    private final MyBookService myBookService;

    @GetMapping("")
    public ResponseEntity<RsData> list(@AuthenticationPrincipal MemberContext memberContext) {
        List<MyBook> myBooks = myBookService.findAllByOwnerId(memberContext.getMember().getId());
        return Ut.spring.responseEntityOf(
                RsData.successOf(
                        Ut.mapOf(
                                "myBooks", myBooks
                        )
                )
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<RsData> detail(@PathVariable Long id) {
        MyBook myBook = myBookService.findById(id).orElse(null);

        if (myBook == null) {
            return Ut.spring.responseEntityOf(
                    RsData.of(
                            "F-1",
                            "해당 도서는 존재하지 않습니다."
                    )
            );
        }

        return Ut.spring.responseEntityOf(
                RsData.successOf(
                        Ut.mapOf(
                                "myBook", myBook
                        )
                )
        );
    }
}

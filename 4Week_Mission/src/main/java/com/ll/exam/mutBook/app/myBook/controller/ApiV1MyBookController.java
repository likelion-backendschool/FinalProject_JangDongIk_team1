package com.ll.exam.mutBook.app.myBook.controller;

import com.ll.exam.mutBook.app.base.dto.RsData;
import com.ll.exam.mutBook.app.member.entity.Member;
import com.ll.exam.mutBook.app.myBook.entity.MyBook;
import com.ll.exam.mutBook.app.myBook.service.MyBookService;
import com.ll.exam.mutBook.app.security.dto.MemberContext;
import com.ll.exam.mutBook.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
}

package com.ll.comibird.Week_Mission.app.member.service;

import com.ll.comibird.Week_Mission.app.member.dto.MailDto;
import com.ll.comibird.Week_Mission.app.member.entity.Member;
import com.ll.comibird.Week_Mission.app.member.exception.AlreadyJoinException;
import com.ll.comibird.Week_Mission.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public Member join(String username, String password, String email) {
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new AlreadyJoinException();
        }

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .authLevel(1)
                .build();

        memberRepository.save(member);

        return member;
    }

    public Member join(String username, String password, String email, String nickname) {
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new AlreadyJoinException();
        }

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .nickname(nickname)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .authLevel(3)
                .build();

        memberRepository.save(member);

        return member;
    }
}

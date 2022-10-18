package com.ll.comibird.Week_Mission.app.member.controller;

import com.ll.comibird.Week_Mission.app.member.entity.Member;
import com.ll.comibird.Week_Mission.app.member.form.FindUsernameForm;
import com.ll.comibird.Week_Mission.app.member.form.JoinForm;
import com.ll.comibird.Week_Mission.app.member.repository.MemberRepository;
import com.ll.comibird.Week_Mission.app.member.service.MailService;
import com.ll.comibird.Week_Mission.app.member.service.MemberService;
import com.ll.comibird.Week_Mission.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final MailService mailService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin(HttpServletRequest request) {
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/member/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }
        return "member/login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinForm joinForm) {
        if (joinForm.getNickname() == null)
            memberService.join(joinForm.getUsername(), joinForm.getPassword(), joinForm.getEmail());
        else
            memberService.join(joinForm.getUsername(), joinForm.getPassword(), joinForm.getEmail(), joinForm.getNickname());

        /* 수정 필요
        MailDto mailDto = new MailDto();
        mailDto.setAddress(joinForm.getEmail());
        mailDto.setTitle("환영합니다 Ebook입니다");
        mailDto.setMessage(joinForm.getUsername() + "님 환영합니다");
        mailService.mailSend(mailDto);
        */
        return "redirect:/member/login?msg=" + Util.url.encode("회원가입이 완료되었습니다.");
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/findUsername")
    public String findUsername() {
        return "member/findUsername";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/findUsername")
    public String findUsername(@Valid FindUsernameForm findUsernameForm) {
        if (memberRepository.findByEmail(findUsernameForm.getEmail()) == null) {
            return "redirect:/member/findUsername?msg=" + Util.url.encode("아이디를 찾을 수 없습니다.");
        }
        Member member = memberRepository.findByEmail(findUsernameForm.getEmail());
        return "redirect:/member/login?msg=" + Util.url.encode("아이디는 " + member.getUsername() + "입니다.");
    }
}

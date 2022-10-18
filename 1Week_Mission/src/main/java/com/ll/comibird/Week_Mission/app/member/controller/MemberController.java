package com.ll.comibird.Week_Mission.app.member.controller;

import com.ll.comibird.Week_Mission.app.member.entity.Member;
import com.ll.comibird.Week_Mission.app.member.form.FindUsernameForm;
import com.ll.comibird.Week_Mission.app.member.form.JoinForm;
import com.ll.comibird.Week_Mission.app.member.form.ModifyForm;
import com.ll.comibird.Week_Mission.app.member.form.ModifyPasswordForm;
import com.ll.comibird.Week_Mission.app.member.repository.MemberRepository;
import com.ll.comibird.Week_Mission.app.member.service.MailService;
import com.ll.comibird.Week_Mission.app.member.service.MemberService;
import com.ll.comibird.Week_Mission.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String modify(Principal principal, @Valid ModifyForm modifyForm) {
        Member member = memberService.findByUsername(principal.getName());
        modifyForm.setEmail(member.getEmail());
        modifyForm.setNickname(member.getNickname());
        return "member/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modify(Principal principal, @Valid ModifyForm modifyForm, BindingResult bindingResult) {
        memberService.modify(memberService.findByUsername(principal.getName()), modifyForm.getEmail(), modifyForm.getNickname());
        return "redirect:/member/modify?msg=" + Util.url.encode("회원정보 변경이 완료되었습니다.");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modifyPassword")
    public String modifyPassword(@Valid ModifyPasswordForm modifyPasswordForm) {
        return "member/modifyPassword";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modifyPassword")
    public String modifyPassword(Principal principal, @Valid ModifyPasswordForm modifyPasswordForm, BindingResult bindingResult) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(modifyPasswordForm.getOldPassword(), (memberService.findByUsername(principal.getName()).getPassword()))) {
            return "redirect:/member/modifyPassword?msg=" + Util.url.encode("기존 비밀번호가 일치하지 않습니다.");
        }

        if (!modifyPasswordForm.getPassword().equals(modifyPasswordForm.getPasswordConfirm())) {
            return "redirect:/member/modifyPassword?msg=" + Util.url.encode("새 비밀번호가 일치하지 않습니다.");
        }

        memberService.modifyPassword(memberService.findByUsername(principal.getName()), modifyPasswordForm.getPassword());
        return "redirect:/member/modifyPassword?msg=" + Util.url.encode("비밀번호 변경이 완료되었습니다.");
    }
}
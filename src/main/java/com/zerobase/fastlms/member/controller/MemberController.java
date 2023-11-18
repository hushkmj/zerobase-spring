package com.zerobase.fastlms.member.controller;

import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MemberController {

  private final MemberService memberService;

  @GetMapping("member/login")
  public String login() {
    return "member/login";
  }

  @GetMapping("member/register")
  public String register() {
    System.out.println("GetMapping");
    return "member/register";
  }

  @PostMapping("member/register")
  public String registerSubmit(
      HttpServletRequest request
      , HttpServletResponse response
      , Model model
      , MemberInput parameter
  ) {
    boolean result = memberService.register(parameter);
    model.addAttribute("result", result);

    return "member/register_complete";
  }

  @GetMapping("/member/email-auth")
  public String emailAuth(
      HttpServletRequest request
      , HttpServletResponse response
      , Model model
  ) {

    String uuid = request.getParameter("id");
    System.out.println(uuid);

    boolean result = memberService.emailAuth(uuid);
    model.addAttribute("result", result);

    return "member/email_auth";
  }

  @GetMapping("/member/info")
  public String memberInfo() {
    return "member/info";
  }


}

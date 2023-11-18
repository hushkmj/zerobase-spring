package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.components.MailComponents;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;
  private final MailComponents mailComponents;

  @Override
  public boolean register(MemberInput parameter) {

    Optional<Member> optionalMember = memberRepository.findById(parameter.getEmail());
    if (optionalMember.isPresent()) {
      return false;
    }

    String uuid = UUID.randomUUID().toString();

    Member member = Member.builder()
        .email(parameter.getEmail())
        .userName(parameter.getUserName())
        .phone(parameter.getPhone())
        .password(parameter.getPassword())
        .regDt(LocalDateTime.now())
        .emailAuthYn(false)
        .emailAuthKey(uuid)
        .build();

//    Member member = new Member();
//    member.setEmail(parameter.getEmail());
//    member.setUserName(parameter.getUserName());
//    member.setPhone(parameter.getPhone());
//    member.setPassword(parameter.getPassword());
//    member.setRegDt(LocalDateTime.now());
//    member.setEmailAuthYn(false);
//    member.setEmailAuthKey(uuid);

    memberRepository.save(member);

    String email = parameter.getEmail();
    String subject = "fastlms 사이트 가입을 축하드립니다.";
    String text = "<p>fastlms 사이트 가입을 축하드립니다.</p>"
        + "<p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
        + "<div>"
        + "<a target='_blank' href='http://localhost:8080/member/email-auth?id=" + uuid
        + "'>가입완료</a>"
        + "</div>";

    mailComponents.sendMail(email, subject, text);
    return true;
  }

  @Override
  public boolean emailAuth(String uuid) {

    Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
    if (optionalMember.isEmpty()) {
      return false;
    }

    Member member = optionalMember.get();
    member.setEmailAuthYn(true);
    member.setEmailAuthDt(LocalDateTime.now());
    memberRepository.save(member);

    return true;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Member> optionalMember = memberRepository.findById(username);
    if (!optionalMember.isPresent()) {
      throw new UsernameNotFoundException("회원정보가 존재하지 않습니다.");
    }

    Member member = optionalMember.get();

    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    return new User(member.getEmail(), member.getPassword(), grantedAuthorities);
  }
}

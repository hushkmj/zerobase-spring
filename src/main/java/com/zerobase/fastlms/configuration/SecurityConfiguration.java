package com.zerobase.fastlms.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final MemberService memberService;

  @Bean
  protected PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  UserAuthenticationFailureHandler getFailureHandler() {
    return new UserAuthenticationFailureHandler();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeHttpRequests(
            (authz) -> authz.anyRequest().authenticated()
        )
        .httpBasic(withDefaults());

    httpSecurity.formLogin(form -> form
        .loginPage("/member/login")
        .failureHandler(null)
        .permitAll()
    );

    httpSecurity.userDetailsService(memberService);

    return httpSecurity.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(
        "/"
        , "member/register"
        , "/member/email-auth"
    );
  }


}

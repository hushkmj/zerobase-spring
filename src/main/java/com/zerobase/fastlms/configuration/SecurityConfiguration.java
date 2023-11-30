package com.zerobase.fastlms.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  private final MemberService memberService;

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserAuthenticationFailureHandler getFailureHandler() {
    return new UserAuthenticationFailureHandler();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers(
                  "/"
                  , "/member/register"
                  , "/member/login"
                  , "/member/email-auth"
                  , "/member/find-password"
                  , "/member/reset/password"
              )
              .permitAll();
          auth.anyRequest().authenticated();
        })
        .formLogin(formLogin -> {
          formLogin.loginPage("/member/login");
          formLogin.failureHandler(getFailureHandler());
          formLogin.permitAll();
        })
        .logout(logout -> {
          logout.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"));
          logout.logoutSuccessUrl("/");
          logout.invalidateHttpSession(true);
        })
        .build();
  }

  @Bean //사용자 권한을 주는 메서드
  public AuthenticationManager authenticationManager(AuthenticationConfiguration auth)
      throws Exception {
    return auth.getAuthenticationManager();
  }

//  @Bean
//  public WebSecurityCustomizer webSecurityCustomizer() {
//    return (web) -> web.ignoring().requestMatchers("/member/reset/password");
//  }

}

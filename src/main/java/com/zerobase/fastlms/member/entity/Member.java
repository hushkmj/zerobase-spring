package com.zerobase.fastlms.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Member {

  @Id
  private String email;

  private String userName;
  private String phone;
  private String password;
  private LocalDateTime regDt;

  private boolean emailAuthYn;
  private String emailAuthKey;
  private LocalDateTime emailAuthDt;

}

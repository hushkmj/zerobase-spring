package com.zerobase.fastlms.member.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
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
  private String userId;

  private String userName;
  private String phone;
  private String password;
  private LocalDateTime regDt;

  private boolean emailAuthYn;
  private String emailAuthKey;
  private LocalDateTime emailAuthDt;

  private String resetPasswordKey;
  private LocalDateTime resetPasswordLimitDt;

  private boolean adminYn;

}

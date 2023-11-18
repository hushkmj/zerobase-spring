package com.zerobase.fastlms.main.controller;

import com.zerobase.fastlms.components.MailComponents;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequiredArgsConstructor
public class MainController {

  private final MailComponents mailComponents;

  @RequestMapping("/")
  public String index() {
    return "index";
  }
}

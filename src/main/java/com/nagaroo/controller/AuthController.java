package com.nagaroo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping; 
@Controller
public class AuthController {
   @GetMapping("/loginInvalid")
   public String loginInvalid() { return "loginInvalid"; }

   @GetMapping("/loginExpire")
   public String loginExpire() { return "loginExpire"; }

}


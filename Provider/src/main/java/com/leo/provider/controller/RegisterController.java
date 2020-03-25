package com.leo.provider.controller;

import com.leo.common.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/25
 * @version: 1.0
 */
@Controller
public class RegisterController
{
    @PostMapping("/register")
    public String register(User user){
        return "redirect:http://provider/loginPage?username="+user.getUsername();
    }

    @GetMapping("/loginPage")
    @ResponseBody
    public String loginPage(String username){
        return "login"+username;
    }
}

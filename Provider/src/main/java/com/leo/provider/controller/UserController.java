package com.leo.provider.controller;

import com.leo.common.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/26
 * @version: 1.0
 */
@RestController
public class UserController
{
    @GetMapping("/user/{ids}")
    public List<User> getUserByIds(@PathVariable String ids){
        System.out.println(ids);
        String[] split = ids.split(",");
        ArrayList<User> list = new ArrayList<>();
        for (String s : split)
        {
            User user = new User();
            user.setId(Integer.parseInt(s));
            list.add(user);
        }
        return list;
    }
}

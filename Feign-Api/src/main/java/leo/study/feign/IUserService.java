package leo.study.feign;

import com.leo.common.User;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/3/27
 * @version: 1.0
 */
//@RestController
public interface IUserService
{
    @GetMapping("/hello")
    String hello();//定义一个方法名

    @GetMapping("/hello2")
    String hello2(@RequestParam("name") String name);

    @PostMapping("/user2")
    User addUser2(@RequestBody User user);

    @DeleteMapping("/deleteUser2/{id}")
    void deleteUser2(@PathVariable("id") Integer id);

    @GetMapping("/user3")
    void getUserByName(@RequestHeader("name") String name) throws UnsupportedEncodingException;
}

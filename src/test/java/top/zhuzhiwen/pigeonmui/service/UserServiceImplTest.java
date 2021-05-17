package top.zhuzhiwen.pigeonmui.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.zhuzhiwen.pigeonmui.model.User;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void add() {

        User user = new User();
        user.setNickname("总裁");
        user.setUsername("Ronaldo");
        user.setPassword("123");
        user.setStatus(false);
        userService.add(user);

        user = new User();
        user.setNickname("煤老板");
        user.setUsername("Messi");
        user.setPassword("123");
        user.setStatus(false);
        userService.add(user);

        user = new User();
        user.setNickname("开心");
        user.setUsername("Coolwen");
        user.setPassword("123");
        user.setStatus(true);
        userService.add(user);

    }
}
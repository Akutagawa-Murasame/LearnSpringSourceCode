package org.mura.springframework.test.bean;

import org.mura.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/7 11:26
 */
@Component("userService")
public class UserService implements IUserService {
    private String token;

    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "akutagawa murasame，2968439893，湖北";
    }

    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "注册用户：" + userName + " success！";
    }

    @Override
    public String toString() {
        return "UserService#token = { " + token + " }";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

package org.mura.springframework.test.bean;

import org.mura.springframework.beans.factory.annotation.Autowired;
import org.mura.springframework.beans.factory.annotation.Qualifier;
import org.mura.springframework.beans.factory.annotation.Value;
import org.mura.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Akutagawa Murasame
 */
@Component("userService")
public class UserService implements IUserService {
    @Value("${token}")
    private String token;

    @Autowired
//    @Qualifier("userDao")
    private UserDao userDao;

    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return userDao.queryUserName("00001") + "，" + token;
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

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}

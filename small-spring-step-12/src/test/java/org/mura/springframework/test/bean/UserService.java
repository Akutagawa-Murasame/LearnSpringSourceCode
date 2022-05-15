package org.mura.springframework.test.bean;

import java.util.Random;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/7 11:26
 */
public class UserService implements IUserService {
    @Override
    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Akutagawa Murasame";
    }

    @Override
    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "register " + userName;
    }
}

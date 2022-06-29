package org.mura.springframework.test.bean;

import org.mura.springframework.beans.factory.annotation.Autowired;
import org.mura.springframework.beans.factory.annotation.Qualifier;
import org.mura.springframework.beans.factory.annotation.Value;
import org.mura.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Akutagawa Murasame
 */
public class UserService implements IUserService {
    private String token;

    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "mura，100001，武汉，" + token;
    }
}


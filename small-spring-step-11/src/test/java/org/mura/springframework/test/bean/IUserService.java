package org.mura.springframework.test.bean;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/7 11:26
 */
public interface IUserService {
    String queryUserInfo();

    String register(String userName);
}

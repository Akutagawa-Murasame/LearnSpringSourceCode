package org.mura.springframework.test.bean;

/**
 * @author Akutagawa Murasame
 * @date 2022/4/25 21:36
 *
 * 用IUserDao取代原来的UserDao来实现一个代理的效果
 */
public interface IUserDao {
    String queryUserName(String uId);
}

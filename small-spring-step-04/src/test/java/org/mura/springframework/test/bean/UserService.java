package org.mura.springframework.test.bean;

/**
 * @author Akutagawa Murasame
 * 这里简单定义了一个 UserService 对象，方便我们后续对 Spring 容器测试
 */
public class UserService {
    private String name;

    public UserService() {}

    public UserService(String name) {
        this.name = name;
    }

    public void queryUserInfo() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "UserService{" +
                "name='" + name + '\'' +
                '}';
    }
}

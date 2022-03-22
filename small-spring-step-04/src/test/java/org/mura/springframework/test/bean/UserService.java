package org.mura.springframework.test.bean;

/**
 * @author Akutagawa Murasame
 * 这里简单定义了一个 UserService 对象，方便我们后续对 Spring 容器测试
 */
public class UserService {
    private String uId;

//    用来体现属性依赖
    private UserDao userDao;

    public void queryUserInfo() {
        System.out.println("查询用户信息：" + userDao.queryUserName(uId));
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
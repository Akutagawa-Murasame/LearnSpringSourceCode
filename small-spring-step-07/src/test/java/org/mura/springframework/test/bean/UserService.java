package org.mura.springframework.test.bean;

import org.mura.springframework.beans.factory.DisposableBean;
import org.mura.springframework.beans.factory.InitializingBean;

/**
 * @author Akutagawa Murasame
 *
 * 测试实现了初始化和销毁接口
 */
public class UserService implements InitializingBean, DisposableBean {
    private String uId;
    private String company;
    private String location;
    private UserDao userDao;

    @Override
    public void destroy() {
        System.out.println("执行：UserService.destroy");
    }
    @Override
    public void afterPropertiesSet() {
        System.out.println("执行：UserService.afterPropertiesSet");
    }


    public String queryUserInfo() {
        return userDao.queryUserName(uId) + "," + company + "," + location;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
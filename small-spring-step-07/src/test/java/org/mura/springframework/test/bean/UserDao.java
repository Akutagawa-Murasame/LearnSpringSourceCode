package org.mura.springframework.test.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/22 20:18
 *
 * 测试没实现初始化和销毁接口，但是在xml中定义了
 */
public class UserDao {
    private static final Map<String, String> hashMap = new HashMap<>();

    public void initDataMethod() {
        System.out.println("执行：init-method");

        hashMap.put("10001", "小傅哥");
        hashMap.put("10002", "3y");
        hashMap.put("10003", "akutagawa murasame");
    }

    public void destroyDataMethod(){
        System.out.println("执行：destroy-method");
        hashMap.clear();
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }
}
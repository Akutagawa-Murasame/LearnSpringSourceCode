package org.mura.springframework.test.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/22 20:18
 * 模拟数据库
 */
public class UserDao {
    private static final Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "小傅哥");
        hashMap.put("10002", "3y");
        hashMap.put("10003", "akutagawa murasame");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }
}
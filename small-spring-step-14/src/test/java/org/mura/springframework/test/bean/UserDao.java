package org.mura.springframework.test.bean;

import org.mura.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("00001", "Akutagawa Murasame，湖北，武汉");
        hashMap.put("00002", "3y，广东，广州");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }
}

package org.mura.springframework.utils;

/**
 * @author Akutagawa Murasame
 * @date 2022/6/16 19:30
 *
 * 解析字符串接口
 */
public interface StringValueResolver {
    String resolveStringValue(String strVal);
}
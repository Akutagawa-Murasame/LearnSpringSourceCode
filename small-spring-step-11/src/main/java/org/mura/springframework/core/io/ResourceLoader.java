package org.mura.springframework.core.io;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/24 21:13
 */
public interface ResourceLoader {
    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);
}

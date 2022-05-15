package org.mura.springframework.core.io;

import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/24 21:13
 *
 * 通过网络IO获取配置文件
 */
public class UrlResource implements Resource {
    private final URL url;

    public UrlResource(URL url) {
        Assert.notNull(url, "URL must not be null");

        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection urlConnection = this.url.openConnection();

        try {
            return urlConnection.getInputStream();
        } catch (IOException e) {
//            Http长连接会消耗资源，主动关闭
            if (urlConnection instanceof HttpURLConnection) {
                ((HttpURLConnection) urlConnection).disconnect();
            }

            throw e;
        }
    }
}

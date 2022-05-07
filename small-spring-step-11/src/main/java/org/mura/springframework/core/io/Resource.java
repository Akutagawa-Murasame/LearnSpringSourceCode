package org.mura.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/24 21:13
 *
 * 配置文件可能来自很多地方，网络和磁盘
 * Java的IO把他们都当作流来看待，十分方便
 */
public interface Resource {
    InputStream getInputStream() throws IOException;
}

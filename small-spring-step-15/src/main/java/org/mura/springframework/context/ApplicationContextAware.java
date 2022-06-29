package org.mura.springframework.context;

import org.mura.springframework.beans.factory.Aware;

/**
 * @author Akutagawa Murasame
 * @date 2022/4/20 18:45
 *
 * 实现此接口以感知到所属的 ApplicationContext
 */
public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext);
}

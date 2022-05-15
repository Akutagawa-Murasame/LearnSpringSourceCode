package org.mura.springframework.beans;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/22 16:49
 * 属性值
 */
public class PropertyValue {
    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}

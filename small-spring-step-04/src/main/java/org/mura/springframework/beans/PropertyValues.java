package org.mura.springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/22 16:50
 * 属性集和
 * 这两个类的作用就是创建出一个用于传递类中属性信息的类，因为属性可能会有很
 * 多，所以还需要定义一个集合包装下。并且添加到BeanDefinition类中
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }

    public PropertyValue[] getPropertyValues() {
//        new一个空数组是为了指定泛型，详见阿里巴巴Java开发手册
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : this.propertyValueList) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }

        return null;
    }
}

package org.mura.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.PropertyValue;
import org.mura.springframework.beans.factory.config.BeanDefinition;
import org.mura.springframework.beans.factory.config.BeanReference;
import org.mura.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.mura.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.mura.springframework.core.io.Resource;
import org.mura.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/24 21:08
 * <p>
 * 这个类设计真的妙
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        super(beanDefinitionRegistry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry, ResourceLoader resourceLoader) {
        super(beanDefinitionRegistry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
//            使用try语句进行资源使用完自动释放
            try (InputStream inputStream = resource.getInputStream()) {
                doLoadBeanDefinitions(inputStream);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, BeansException {
//        Dom解析，有点忘了

//        hutool真是好
//        将inputStream变成xml结构
        Document document = XmlUtil.readXML(inputStream);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0, n = childNodes.getLength(); i < n; ++i) {
//            不是Element
            if (!((childNodes.item(i)) instanceof Element)) {
                continue;
            }

//            不是bean定义
            if (!"bean".equals(childNodes.item(i).getNodeName())) {
                continue;
            }

//             解析标签
            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");

//             获取 Class，方便获取类中的属性名称
            Class<?> clazz = Class.forName(className);

//             优先级 id > name
//            hutool
            String beanName = StrUtil.isNotEmpty(id) ? id : name;

//            spring里面，没有定义bean的id和name，bean的id就是类名小写，这里也一样
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

//            之前这个if是放在for循环后面的，我不明白为什么以前的实现要在把一切数据都准备好了之后再判断是否存在这个beanName😂
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }

//            定义 Bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);

//            读取属性并填充
            for (int j = 0, m = bean.getChildNodes().getLength(); j < m; ++j) {
//                不是Element
                if (!(bean.getChildNodes().item(j) instanceof Element)) {
                    continue;
                }

//                不是bean属性
                if (!"property".equals(bean.getChildNodes().item(j).getNodeName())) {
                    continue;
                }

//                解析标签：property
                Element property = (Element) bean.getChildNodes().item(j);
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");

//                一般引用属性才有ref标签，所以这里可能为空字符串
                String attrRef = property.getAttribute("ref");

//                获取属性值：引入对象、值对象
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;

//                创建属性信息
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }

//                注册 BeanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }
}

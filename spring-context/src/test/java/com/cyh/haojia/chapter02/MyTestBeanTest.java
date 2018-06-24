package com.cyh.haojia.chapter02;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class MyTestBeanTest {

    public static void main(String[] args) {
        ClassPathResource resource = new ClassPathResource("com/cyh/haojia/chapter02/beanFactoryTest.xml");
        MyBeanFactory beanFactory = new MyBeanFactory(resource);
        MyTestBean bean = (MyTestBean) beanFactory.getBean("myTestBean");
        System.out.println(bean.getTestStr());
    }


    /**
     * 自己写的BeanFactory
     * 和 XmlBeanFactory 差不多，其内部实现也非常简单
     */
    static class MyBeanFactory extends DefaultListableBeanFactory {
        private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);

        public MyBeanFactory(Resource resource) {
            super(null);
            this.reader.loadBeanDefinitions(resource);
        }
    }

}

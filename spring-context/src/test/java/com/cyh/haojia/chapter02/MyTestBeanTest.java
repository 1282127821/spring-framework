package com.cyh.haojia.chapter02;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class MyTestBeanTest {

    public static void main(String[] args) {
        XmlBeanFactory beanFactory =
                new XmlBeanFactory(new ClassPathResource("com/cyh/haojia/chapter02/beanFactoryTest.xml"));
        MyTestBean bean = (MyTestBean) beanFactory.getBean("myTestBean");
        System.out.println(bean.getTestStr());
    }


}

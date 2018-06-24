package com.cyh.haojia.chapter05;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class CarFactoryBeanTest {


    public static void main(String[] args) {
        Resource resource = new ClassPathResource("com/cyh/haojia/chapter05/carFactoryBeanTest.xml");
        XmlBeanFactory beanFactory = new XmlBeanFactory(resource);
        System.out.println(beanFactory.getBean("car").getClass().getName());
        System.out.println(beanFactory.getBean("&car").getClass().getName());
    }

}

package com.cyh;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2018/3/5 0005.
 */
public class PersonTest {


    @Test
    public void testClassPath() {
        ApplicationContext context = new ClassPathXmlApplicationContext("com/cyh/person.xml");
        Object person = context.getBean("person");
        System.out.println("person.getClass().getName(): " + person.getClass().getName());
    }



}

package com.cyh.haojia.chapter05;

import org.junit.Test;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CyclicDependencyTest {

    @Test(expected = BeanCurrentlyInCreationException.class)
    public void testCyclicDependencyByConstructor() throws Throwable {
        try {
            new ClassPathXmlApplicationContext("com/cyh/haojia/chapter05/cyclicDependency.xml");
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause().getCause().getCause();
            throw cause;
        }
    }

    @Test(expected = BeanCurrentlyInCreationException.class)
    public void testCyclicDependencyBySetterAndPrototype() throws Throwable {
        try {
            ClassPathXmlApplicationContext context =
                    new ClassPathXmlApplicationContext("com/cyh/haojia/chapter05/cyclicDependency2.xml");
            System.out.println(context.getBean("testA"));
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause().getCause().getCause();
            throw cause;
        }
    }


}

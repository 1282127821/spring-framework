package com.cyh.ioc.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationIocTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationIocTest.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AnnotationConfig.class);
        LOGGER.info("===================容器初始化完成=====================");
        Student student = context.getBean(Student.class);
        LOGGER.info("====student: {}", student);
    }
}

package com.cyh.bean.life.cycle.ioc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Student {

    private static final Logger LOGGER = LoggerFactory.getLogger(Student.class);

    public Student() {
        LOGGER.info("Student() constructor");
    }
}

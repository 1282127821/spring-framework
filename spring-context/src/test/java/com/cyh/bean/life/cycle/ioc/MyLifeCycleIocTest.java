package com.cyh.bean.life.cycle.ioc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyLifeCycleIocTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyLifeCycleIocTest.class);

    /**
     * 提示：运行前设置 log4j.logger.org.springframework=INFO 不然会有太多日志
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyLifeCycleIocConfig.class);
        LOGGER.info("=============容器初始化完成===========");
        context.close();
    }

}

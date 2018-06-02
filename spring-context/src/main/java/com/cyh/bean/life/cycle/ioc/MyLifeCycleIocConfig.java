package com.cyh.bean.life.cycle.ioc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@ComponentScan("com.cyh.bean.life.cycle.ioc")
public class MyLifeCycleIocConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyLifeCycleIocConfig.class);

    @Bean
    public Student student() {
        return new Student();
    }

    @EventListener(classes = {ApplicationEvent.class})
    public void myListener(ApplicationEvent event) {
        LOGGER.info("Received an event: {}", event);
    }
}

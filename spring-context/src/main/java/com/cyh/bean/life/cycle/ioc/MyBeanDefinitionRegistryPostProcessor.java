package com.cyh.bean.life.cycle.ioc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Notice: BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBeanDefinitionRegistryPostProcessor.class);

    /**
     * Bean定义注册中心的后置处理器
     *
     * BeanDefinitionRegistry: Bean定义信息注册中心，
     * 以后 BeanFactory 就是按照 BeanDefinitionRegistry 里面保存的每一个bean定义信息创建bean实例；
     * 可以在此添加自定义Bean
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        int count = registry.getBeanDefinitionCount();
        LOGGER.info("Current bean count: {}", count);
        String beanName = "anotherStudent";
        AbstractBeanDefinition beanDefinition =
                BeanDefinitionBuilder.rootBeanDefinition(Student.class).getBeanDefinition();
        registry.registerBeanDefinition(beanName, beanDefinition);
        LOGGER.info("Added one customerized bean. beanName: {}, beanDefinition: {}", beanName, beanDefinition);
    }

    /**
     * Bean工厂的后置处理器
     *
     * Because of: BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        int beanDefinitionCount = beanFactory.getBeanDefinitionCount();
        int beanPostProcessorCount = beanFactory.getBeanPostProcessorCount();
        int singletonCount = beanFactory.getSingletonCount();
        LOGGER.info("beanDefinitionCount: {}, beanPostProcessorCount: {}, singletonCount: {}", beanDefinitionCount,
                beanPostProcessorCount, singletonCount);

        StringBuilder content = new StringBuilder();
        int count = beanFactory.getBeanDefinitionCount();
        content.append("当前BeanFactory中有 " + count + " 个Bean，它们分别是：").append("\n");
        String[] names = beanFactory.getBeanDefinitionNames();
        for (String name : names) {
            content.append("  ").append(name).append(" --> ").append(beanFactory.getBeanDefinition(name)).append("\n");
        }
        LOGGER.info(content.toString());
    }
}

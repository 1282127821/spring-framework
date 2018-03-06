package com.cyh.bean.life.cycle;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

public class MyInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    /**
     * Apply this BeanPostProcessor before the target bean gets instantiated.
     * 在构造函数之前调用
     */
    @Override
    public Object postProcessBeforeInstantiation(Class beanClass, String beanName) throws BeansException {
        System.out.println("MyInstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation beanClass: "
                + beanClass + ", beanName: " + beanName);
        return null;
    }

    /**
     * Perform operations after the bean has been instantiated, via a constructor or factory method,
     * but before Spring property population (from explicit properties or autowiring) occurs.
     * 在构造函数之后调用
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("MyInstantiationAwareBeanPostProcessor.postProcessAfterInstantiation bean: " + bean
                + ", beanName: " + beanName);
        return true;
    }

    /**
     * Post-process the given property values before the factory applies them to the given bean.
     * 在属性设置之前调用
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean,
                                                    String beanName) throws BeansException {

        System.out.println("MyInstantiationAwareBeanPostProcessor.postProcessPropertyValues PropertyValues: " + pvs
                + ", brand: " + pvs.getPropertyValue("brand").getValue() + ", maxSpeed: "
                + pvs.getPropertyValue("maxSpeed").getValue());
        return pvs;
    }

    /**
     * Apply this BeanPostProcessor to the given new bean instance
     * before any bean initialization callbacks (like InitializingBean's afterPropertiesSet or a custom init-method).
     * The bean will already be populated with property values.
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyInstantiationAwareBeanPostProcessor.postProcessBeforeInitialization bean: " + bean
                + ", beanName: " + beanName);
        return bean;
    }

    /**
     * Apply this BeanPostProcessor to the given new bean instance
     * after any bean initialization callbacks (like InitializingBean's afterPropertiesSet or a custom init-method).
     * The bean will already be populated with property values.
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyInstantiationAwareBeanPostProcessor.postProcessAfterInitialization bean: " + bean
                + ", beanName: " + beanName);
        return bean;
    }
}

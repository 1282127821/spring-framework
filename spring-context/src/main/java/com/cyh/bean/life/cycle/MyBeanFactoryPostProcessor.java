package com.cyh.bean.life.cycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory bf) throws BeansException {
        BeanDefinition bd = bf.getBeanDefinition("car");
        MutablePropertyValues propertyValues = bd.getPropertyValues();
        PropertyValue brand = propertyValues.getPropertyValue("brand");
        propertyValues.addPropertyValue("brand", "奇瑞QQ");
        System.out.println(
                "MyBeanFactoryPostProcessor.postProcessBeanFactory()，将brand的值由'" + brand.getValue() + "'设置为'奇瑞QQ'");

        PropertyValue maxSpeed = propertyValues.getPropertyValue("maxSpeed");
        propertyValues.addPropertyValue("maxSpeed", "80");
        System.out.println(
                "MyBeanFactoryPostProcessor.postProcessBeanFactory()，将maxSpeed的值由'" + maxSpeed.getValue() + "'设置为'80'");
    }

}

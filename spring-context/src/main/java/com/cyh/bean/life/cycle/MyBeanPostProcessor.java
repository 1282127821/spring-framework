package com.cyh.bean.life.cycle;

import com.cyh.bean.life.cycle.Car;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     * Apply this BeanPostProcessor to the given new bean instance
     * before any bean initialization callbacks (like InitializingBean's afterPropertiesSet or a custom init-method).
     * The bean will already be populated with property values.
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("car".equals(beanName)) {
            Car car = (Car) bean;
            if (car.getColor() == null) {
                System.out.println("调用MyBeanPostProcessor.postProcessBeforeInitialization()，color为空，设置为默认黑色。");
                car.setColor("黑色");
            }
        }
        return bean;
    }

    /**
     * Apply this BeanPostProcessor to the given new bean instance
     * after any bean initialization callbacks (like InitializingBean's afterPropertiesSet or a custom init-method).
     * The bean will already be populated with property values.
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("car".equals(beanName)) {
            Car car = (Car) bean;
            if (car.getMaxSpeed() >= 200) {
                System.out.println("调用MyBeanPostProcessor.postProcessAfterInitialization()，将maxSpeed调整为200。");
                car.setMaxSpeed(200);
            }
        }
        return bean;
    }
}

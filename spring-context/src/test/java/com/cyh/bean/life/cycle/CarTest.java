package com.cyh.bean.life.cycle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by CYH on 2018/3/6.
 */
public class CarTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("com/cyh/bean/life/cycle/car.xml");
        Car car = (Car) ctx.getBean("car");
        car.introduce();
    }
}

package com.cyh.haojia.chapter05;

import org.springframework.beans.factory.FactoryBean;

public class CarFactoryBean implements FactoryBean<Car> {

    private String carInfo;

    @Override
    public Car getObject() throws Exception {
        String[] split = carInfo.split(",");
        Car car = new Car();
        car.setBrand(split[0]);
        car.setMaxSpeed(Integer.valueOf(split[1]));
        car.setPrice(Double.valueOf(split[2]));
        return car;
    }

    @Override
    public Class<?> getObjectType() {
        return Car.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }
}

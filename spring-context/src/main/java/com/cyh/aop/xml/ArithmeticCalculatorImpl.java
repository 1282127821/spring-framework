package com.cyh.aop.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArithmeticCalculatorImpl implements ArithmeticCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArithmeticCalculatorImpl.class);

    @Override
    public int add(int i, int j) {
        int result = i + j;
        LOGGER.debug("The result of {} + {} = {}", i, j, result);
        return result;
    }

    @Override
    public int sub(int i, int j) {
        int result = i - j;
        LOGGER.debug("The result of {} - {} = {}", i, j, result);
        return result;
    }

    @Override
    public int mul(int i, int j) {
        int result = i * j;
        LOGGER.debug("The result of {} * {} = {}", i, j, result);
        return result;
    }

    @Override
    public int div(int i, int j) {
        int result = i / j;
        LOGGER.debug("The result of {} / {} = {}", i, j, result);
        return result;
    }
}

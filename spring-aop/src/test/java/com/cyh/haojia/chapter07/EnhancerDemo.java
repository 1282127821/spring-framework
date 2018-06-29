package com.cyh.haojia.chapter07;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class EnhancerDemo {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(EnhancerDemo.class);
        enhancer.setCallback(new MethodInterceptorImpl());

        EnhancerDemo demo = (EnhancerDemo) enhancer.create();
        demo.test();
        System.err.println(demo);
    }

    /**
     * 会无限递归，造成SOF
     * 不知道是为什么????
     */

    public void test() {
        System.err.println("test()...");
    }

    private static class MethodInterceptorImpl implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.err.println("Before invoke... " + method.getName());
            Object result = proxy.invoke(o, args);
            System.err.println("After invoke... " + method.getName());
            return result;
        }
    }

}

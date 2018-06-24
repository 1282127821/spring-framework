package com.cyh.haojia.chapter05;

public class TestB {

    private TestC testC;

    public TestB(TestC testC) {
        this.testC = testC;
    }

    public TestC getTestC() {
        return testC;
    }

    public void setTestC(TestC testC) {
        this.testC = testC;
    }

    public void b() {
        testC.c();
    }
}

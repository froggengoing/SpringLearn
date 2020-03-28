package com.froggengo.springlearn.start;

import org.springframework.boot.SpringBootExceptionReporter;

public class MySpringBootExceptionReporter implements SpringBootExceptionReporter {
    @Override
    public boolean reportException(Throwable failure) {
        System.out.println("测试SpringBootExceptionReporter的方法");
        return false;
    }
}

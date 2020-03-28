package com.froggengo.springlearn.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class MySpringApplicationRunListener implements SpringApplicationRunListener {

    private final SpringApplication application;
    private final String[] args;
    private final SimpleApplicationEventMulticaster initialMulticaster;

    public MySpringApplicationRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
        this.initialMulticaster = new SimpleApplicationEventMulticaster();
        Iterator var3 = application.getListeners().iterator();

        while(var3.hasNext()) {
            ApplicationListener<?> listener = (ApplicationListener)var3.next();
            this.initialMulticaster.addApplicationListener(listener);
        }

    }

    public void starting() {
        System.out.println("测试SpringApplicationRunListener的方法starting");
    }

    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("测试SpringApplicationRunListener的方法environmentPrepared");
        Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
        systemEnvironment.forEach((k,v)-> System.out.println(""+k+v));
    }

    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("测试SpringApplicationRunlistener的contextPrepared方法");
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(n-> System.out.println(n));

    }

    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("测试SpringApplicationRunlistener的 contextLoaded 方法");
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(n-> System.out.println(n));
    }

    public void started(ConfigurableApplicationContext context) {
        System.out.println("测试SpringApplicationRunlistener的 started 方法");
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(n-> System.out.println(n));
    }

    public void running(ConfigurableApplicationContext context) {
        System.out.println("测试SpringApplicationRunlistener的 running 方法");
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(n-> System.out.println(n));
    }

    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println(exception);
        System.out.println("测试SpringApplicationRunlistener的 failed 方法");
    }

}

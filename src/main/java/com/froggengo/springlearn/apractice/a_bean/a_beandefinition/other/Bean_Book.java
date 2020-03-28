package com.froggengo.springlearn.apractice.a_bean.a_beandefinition.other;


import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

/*@Component
@Lazy
@Description("测试 AnnotationConfigUtils")
@DependsOn({"com.froggengo.springlearn.SpringLearnMain"})
@Role(BeanDefinition.ROLE_APPLICATION)
@Scope("TARGET_CLASS")//指定范围及代理模式ScopedProxyMode 、ConfigurableBeanFactory.SCOPE_PROTOTYPE*/
public class Bean_Book {

    public int money;
    private String name;


    public Bean_Book(){

    }

    public Bean_Book(String name, String auth) {
        this.name = name;
        this.auth = auth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public int sale(){
        return 200;
    }

    private String auth;

    @Override
    public String toString() {
        return "Book{" +
                "money=" + money +
                ", name='" + name + '\'' +
                ", auth='" + auth + '\'' +
                '}';
    }
}


package com.froggengo.springlearn.apractice.a_bean.a_beandefinition.other.testConfiguration;

import com.froggengo.springlearn.apractice.a_bean.a_beandefinition.other.Bean_Book;
import com.froggengo.springlearn.apractice.a_bean.a_beandefinition.other.Bean_User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
//@Configuration
public class myConfiguration {


    @Bean
    public Bean_Book getBean_Book(){
        System.out.println("创建一个Book");
        return new Bean_Book();
    }
    @Bean(name="user1")
    public Bean_User getBean_User(){
        Bean_User bean_user = new Bean_User();
        bean_user.setBean_book(getBean_Book());
        return bean_user;
    }

    public class componentBean{

    }
    public  void something(){

    }
/*    @Bean(name="user2")
    public Bean_User getBean_User(Bean_Book bean_book){
        Bean_User bean_user = new Bean_User();
        bean_user.setBean_book(bean_book);
        return bean_user;
    }*/

}

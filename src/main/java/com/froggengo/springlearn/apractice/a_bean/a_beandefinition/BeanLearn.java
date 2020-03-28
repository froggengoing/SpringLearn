package com.froggengo.springlearn.apractice.a_bean.a_beandefinition;

import com.froggengo.springlearn.apractice.a_bean.a_beandefinition.other.Bean_User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;


public class BeanLearn {


    /**
     * 定义在@Component中的Bean不进过cglib代理，所以每次都生成新的对象
     * 定义在@Configuration中的Bean金cgliib代理，每次返回统一对象
     */

    @Test
    public void DifBetweenConfigurationAndComent(){
        //BeanUtils.
       //BeanDefinition
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scan("com.froggengo.springlearn.apractice.a_bean.a_beandefinition.other.testConfiguration");
        applicationContext.refresh();
        Bean_User user1 = (Bean_User)applicationContext.getBean("user1");
        Object getBean_book = applicationContext.getBean("getBean_Book");
        Object getBean_book1 = applicationContext.getBean("getBean_Book");
        if (getBean_book ==user1.getBean_book()){
            System.out.println("相同");
        }else{
            System.out.println("不相同");
        }
    }
}

package com.froggengo.springlearn.apractice.a_bean.a_beandefinition;

import com.froggengo.springlearn.apractice.a_bean.a_beandefinition.other.Bean_User;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValue;

import java.util.Arrays;

public class A_BeanWapperLearn {

    /**
     * <A>Spring对于注册自定义PropertyEditor:https://blog.csdn.net/hawk7/article/details/83698013</A>
     */
    @Test
    public  void test(){
        //BeanUtils
        /*        import org.apache.commons.beanutils.BeanUtils;
        import org.apache.commons.beanutils.PropertyUtils;*/
        Bean_User bean_user = new Bean_User();
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean_user);
        beanWrapper.setPropertyValue("name","froggengo");
        PropertyValue propertyValue = new PropertyValue("name","froggengo2");
        beanWrapper.setPropertyValue(propertyValue);
        System.out.println(ReflectionToStringBuilder.toString(bean_user));
    }

    @Test
    public void testNestableProperty(){
        Bean_User bean_user = Bean_User.getDefaultBean_user();
        System.out.println("修改前："+ReflectionToStringBuilder.toString(bean_user));
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean_user);
        beanWrapper.setPropertyValue("bean_book.name","嵌套属性");
        beanWrapper.setPropertyValue("list[0].name","list嵌套属性");
        System.out.println("修改后："+ReflectionToStringBuilder.toString(bean_user));
    }
}

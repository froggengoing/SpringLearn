package com.froggengo.springlearn.apractice.a_bean.a_beandefinition.a_javabeanspackage;
import com.froggengo.springlearn.apractice.a_bean.a_beandefinition.other.Bean_Book;
import com.froggengo.springlearn.apractice.a_bean.a_beandefinition.other.Bean_BookEditor;
import com.froggengo.springlearn.apractice.a_bean.a_beandefinition.other.Bean_User;
import com.sun.org.apache.xerces.internal.impl.PropertyManager;
import net.sf.cglib.core.Local;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.junit.Test ;

import java.beans.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

public class JdkBeansPackageLearn {


    @Test
    public void printBean(){
        Bean_User bean_user = Bean_User.getDefaultBean_user();
        //--打印
        String path="F:\\dev\\SpringLearn\\src\\main\\java\\com\\froggengo\\springlearn\\apractice\\a_bean\\a_beandefinition\\a_javabeanspackage\\user.xml";
        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
             XMLEncoder xmlEncoder = new XMLEncoder(fileOutputStream);){
            xmlEncoder.writeObject(bean_user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //读取xml的bean
        try (FileInputStream fileInputStream = new FileInputStream(path);
            XMLDecoder xmlDecoder = new XMLDecoder(fileInputStream);){
            Bean_User bean_user1 = (Bean_User) xmlDecoder.readObject();//从当前流中读取下一个object，类似iterator
            System.out.println("jdk读取xml中的bean"+bean_user1.toString());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ReflectionToStringBuilder可以输出Object的属性名和值
     */
    @Test
    public void testIntrospector() {
        BeanInfo beanInfo=null;
        try {
            beanInfo = Introspector.getBeanInfo(Bean_User.class);
            //打印方法的descriptors
            MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
            Arrays.stream(methodDescriptors).forEach(n->{
                if(n.getParameterDescriptors() !=null){
                    Arrays.stream(n.getParameterDescriptors()).forEach(des->{
                        System.out.println(ReflectionToStringBuilder.toString(des));
                    });
                }else{
                    System.out.println(n+"参数描述为空");
                }
            });
            //打印属性的desciptors
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            Arrays.stream(propertyDescriptors).forEach(
                    n-> {
                        System.out.println(ReflectionToStringBuilder.toString(n));
                        System.out.println("  属性读方法"+ReflectionToStringBuilder.toString(n.getReadMethod()));
                        System.out.println("  属性写方法"+ReflectionToStringBuilder.toString(n.getWriteMethod()));
                        System.out.println("  属性编辑类"+ReflectionToStringBuilder.toString(n.getPropertyEditorClass()));
                    }
            );
            //打印BeanDescriptor
            BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
            System.out.println(ReflectionToStringBuilder.toString(beanDescriptor));
            //打印
            System.out.println(beanInfo.getDefaultPropertyIndex());
            /*EventSetDescriptor描述给定Java bean触发的一组事件。
            给定的事件组都作为方法调用在单个事件侦听器接口上传递，并且事件侦听器对象可以通过对事件源提供的注册方法的调用进行注册。*/
            //打印EventSetDescriptor
            EventSetDescriptor[] eventSetDescriptors = beanInfo.getEventSetDescriptors();
            if(eventSetDescriptors.length>0){
                Arrays.stream(eventSetDescriptors).forEach(n->
                        System.out.println("EventSetDescriptor:"+ReflectionToStringBuilder.toString(n)));
            }else {
                System.out.println("EventSetDescriptor为空");
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        //通过BeanInfo修改属性值
        Bean_User bean_user = Bean_User.getDefaultBean_user();
        System.out.println("修改前："+bean_user);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Arrays.stream(propertyDescriptors).forEach(descriptor->{
            if(descriptor.getName().equals("password")){//注意basename和name区别
                System.out.println("修改password");
                Method writeMethod = descriptor.getWriteMethod();
                try {
                    //PropertyEditor editor = PropertyEditorManager.findEditor(descriptor.getPropertyType());/
                    writeMethod.invoke(bean_user,"99999");
                    //PropertyEditor editor = PropertyEditorManager.findEditor(descriptor.getPropertyType());/
                    //PropertyEditorManager
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }


            }
            if(descriptor.getName().equals("age")){
                System.out.println("修改 age");
                Method writeMethod = descriptor.getWriteMethod();
                //注意只有基础类型比如int、long、boolean、string等，没有date
                PropertyEditor editor = PropertyEditorManager.findEditor(descriptor.getPropertyType());
                editor.setAsText("55");
                try {
                    writeMethod.invoke(bean_user,editor.getValue());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
            //自定义PropertyEditor
            if(descriptor.getName().equals("bean_book")){
                System.out.println("修改 bean_book");
                Method writeMethod = descriptor.getWriteMethod();
                //注意只有基础类型比如int、long、boolean、string等，没有date
                PropertyEditor editor = new Bean_BookEditor();
                editor.setAsText("55,234b");
                try {
                    writeMethod.invoke(bean_user,editor.getValue());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        });

        System.out.println("修改后："+bean_user);
        /** <a>https://blog.csdn.net/shenchaohao12321/article/details/80295371</a>
         * JavaBean规范提供了一个管理默认属性编辑器的管理器：PropertyEditorManager，
           该管理器内保存着一些常见类型的属性编辑器，如果某个JavaBean的常见类型属性没有通过BeanInfo显式指定属性编辑器，
            IDE将自动使用PropertyEditorManager中注册的对应默认属性编辑器。
            Java为PropertyEditor提供了一个方便的实现类：PropertyEditorSupport。
         */
    }


    @Test
    public void testPropertyEditor(){

    }
}

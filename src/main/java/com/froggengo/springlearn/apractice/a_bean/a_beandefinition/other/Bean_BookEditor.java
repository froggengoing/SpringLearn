package com.froggengo.springlearn.apractice.a_bean.a_beandefinition.other;


import java.beans.PropertyEditorSupport;

public class Bean_BookEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text){
        if(text ==null || text.indexOf(",")==-1){
            throw new IllegalArgumentException("输入值出错");
        }
        String[] split = text.split(",");
        Bean_Book bean_book = new Bean_Book();
        bean_book.setName(split[0]);
        bean_book.setAuth(split[1]);
        setValue(bean_book);
        System.out.println("转换"+bean_book);
    }

}

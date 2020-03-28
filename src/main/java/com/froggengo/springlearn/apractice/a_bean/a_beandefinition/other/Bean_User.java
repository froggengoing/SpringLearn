package com.froggengo.springlearn.apractice.a_bean.a_beandefinition.other;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Bean_User implements Serializable {
    private static final long serialVersionUID = 5076428480402933567L;
    private String id;
    private String name;
    private String password;
    private int age;
    private Integer score;
    private Date birth;
    private List<Bean_Book> list;
    private Bean_Book bean_book;
    //没有setter和getter的方法
    private String noSetterParam;
    public Bean_Book getBean_book() {
        return bean_book;
    }

    public void setBean_book(Bean_Book bean_book) {
        this.bean_book = bean_book;
    }



    public List<Bean_Book> getList() {
        return list;
    }

    public void setList(List<Bean_Book> list) {
        this.list = list;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String saleSomething(String product){
        String str="I will sale "+product;
        System.out.println(str);
        return str;
    }

    @Override
    public String toString() {
        return "Bean_User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", score=" + score +
                ", birth=" + birth +
                ", list=" + list +
                ", bean_book=" + bean_book +
                '}';
    }
    public static Bean_User getDefaultBean_user(){
        ArrayList<Bean_Book> bean_bookList = new ArrayList<>();
        Bean_Book bean_book = new Bean_Book();
        bean_book.setAuth("beanbook");
        bean_book.setName("helloworld");
        bean_bookList.add(bean_book);
        Bean_Book bean_book2 = new Bean_Book();
        bean_book2.setAuth("beanbook2");
        bean_book2.setName("helloworld2");
        //-----------
        Bean_User bean_user = new Bean_User();
        bean_user.setAge(20);
        bean_user.setBirth(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        bean_user.setId("froggengo");
        bean_user.setList(bean_bookList);
        bean_user.setName("froggenname");
        bean_user.setPassword("123456");
        bean_user.setScore(100);
        bean_user.setBean_book(bean_book2);
        return bean_user;
    }
}
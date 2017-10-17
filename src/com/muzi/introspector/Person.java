package com.muzi.introspector;

/**
 * Created by muzi on 2017/10/14.
 * 这个类就是javabean
 * 1.一个get或set方法就是一个bean属性
 * 2.get和set操作同一个属性时只能算是一个bean属性
 * 3.一个类中还默认存在一个getClass() 方法，也算一个bean属相
 */
public class Person {

    private String name;
    private int age;
    private String password;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                '}';
    }
}

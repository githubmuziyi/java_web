package com.muzi.introspector;

import com.muzi.beanutils.Person;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by muzi on 2017/10/15.
 * 使用内省api操作bean的属性
 */
public class PersonTest {


    /**
     * 获取bean的所有属性
     * @throws IntrospectionException
     */
    @Test
    public void test1() throws IntrospectionException {
        //去除Object类的bean属性
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd:
             descriptors) {
            System.out.println(pd.getName());
        }
    }

    /**
     * 操作bean属性
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    public void test2() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Person person = new Person();
        PropertyDescriptor pd = new PropertyDescriptor("age", Person.class);
        //得到属性的写方法，为属性赋值  setAge()
        Method method = pd.getWriteMethod();
        method.invoke(person, 45);
        System.out.println(person);
        //获取属性值
        Method method1 = pd.getReadMethod();
        System.out.println(method1.invoke(person, null));
    }

    /**
     * 获取挡墙操作的属性的类型
     * @throws IntrospectionException
     */
    @Test
    public void test3() throws IntrospectionException {
        PropertyDescriptor pd = new PropertyDescriptor("age", Person.class);
        System.out.println(pd.getPropertyType());
    }
}

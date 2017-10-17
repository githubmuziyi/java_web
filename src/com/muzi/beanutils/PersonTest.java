package com.muzi.beanutils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by muzi on 2017/10/16.
 * 使用beanutils操作bean的属性 （推荐）
 */
public class PersonTest {

    @Test
    public void test1() throws InvocationTargetException, IllegalAccessException {
        Person person = new Person();
        BeanUtils.setProperty(person, "name", "muzi");
        System.out.println(person);
    }

    /**
     * beanUtils只支持基本类型转换，复杂类型需要自己注册转换类
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    public void test2() throws InvocationTargetException, IllegalAccessException {
        Person person = new Person();
        String name = "muzi";
        String password = "1243";
        String age = "34";
        String birthday = "1992-08-12";
        //为了让日期赋到bean的birthday属性上，我们需要给beanUtils注册一个日期转换器
        ConvertUtils.register(new Converter() {
            @Override
            public <T> T convert(Class<T> type, Object value) {
                if (value == null) {
                    return null;
                }
                if (!(value instanceof String)) {
                    throw new ConversionException("格式异常");
                }
                if (((String) value).trim().equals("")) {
                    return null;
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return (T) simpleDateFormat.parse((String) value);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }, Date.class);
        /**
         * beanUtils自己实现了一些转换类，常用的一般都已经存在了
         */
        ConvertUtils.register(new DateLocaleConverter(), Date.class);
        BeanUtils.setProperty(person, "name", name);
        BeanUtils.setProperty(person, "password", password);
        BeanUtils.setProperty(person, "age", age); //支持8中数据类型的自动转换
        BeanUtils.setProperty(person, "birthday", birthday);
        System.out.println(person);
    }

    /**
     * 用集合中的值，填充bean的属性
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    public void test3() throws InvocationTargetException, IllegalAccessException {
        Map map = new HashMap<>();
        map.put("name", "wacai");
        map.put("password", "123456");
        map.put("age", "12");
        map.put("birthday", "2017-12-23");
        ConvertUtils.register(new DateLocaleConverter(), Date.class);
        Person person = new Person();
        BeanUtils.populate(person, map);
        System.out.println(person);
    }
}

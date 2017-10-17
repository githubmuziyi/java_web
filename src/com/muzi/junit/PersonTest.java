package com.muzi.junit;

import org.junit.*;

/**
 * Created by muzi on 2017/10/14.
 * JUnit 测试
 */
public class PersonTest {

    private Person person;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("beforeclass");
    }

    @Before
    public void before() {
        person = new Person();
        System.out.println(person);
    }

    @Test
    public void testRun() {
        person.run();
        //断言
        Assert.assertEquals("show", person.show());
    }

    @Test
    public void testEat() {
        person.eat();
        Assert.assertTrue(person.bool());
    }

    @After
    public void after() {
        person = null;
        System.out.println(person);
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("afterclass");
    }
}

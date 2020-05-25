package pers.li.utils;

import org.junit.Test;

import java.lang.reflect.Method;

public class ReflectionUtilsTest {

    @Test
    public void newInstance() {
        TestClass testClass = ReflectionUtils.newInstance(TestClass.class);
        testClass.b();
    }

    @Test
    public void getPublicMethods() {
        Method[] publicMethods = ReflectionUtils.getPublicMethods(TestClass.class);
        System.err.println(publicMethods.length);
        System.err.println(publicMethods[0].getName());
    }

    @Test
    public void invoke() {
        Method[] publicMethods = ReflectionUtils.getPublicMethods(TestClass.class);
        TestClass testClass = ReflectionUtils.newInstance(TestClass.class);
        Object invoke = ReflectionUtils.invoke(testClass,publicMethods[0]);
        System.err.println(invoke);
    }
}

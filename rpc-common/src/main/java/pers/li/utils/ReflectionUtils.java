package pers.li.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * 反射工具类
 */
public class ReflectionUtils {

    /**
     * 根据class创建对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    /**
     * 根据class获取所有公共方法
     *
     * @param clazz
     * @return
     */
    public static Method[] getPublicMethods(Class clazz) {
        //获取当前类的所有方法，含private
        Method[] declaredMethods = clazz.getDeclaredMethods();
        ArrayList<Method> methodArrayList = new ArrayList<Method>();
        for (int i = 0; i < declaredMethods.length; i++) {
            Method method = declaredMethods[i];
            if (Modifier.isPublic(method.getModifiers())) {
                methodArrayList.add(method);
            }
        }
        return methodArrayList.toArray(new Method[0]);
    }

    /**
     * 反射方法调用
     * @param object
     * @param method
     * @param args
     * @return
     */
    public static Object invoke(Object object, Method method, Object... args) {
        try {
            //当方法为静态方法时，object为null，因为静态方法存在于类上，而非随对象产生
            return method.invoke(object, args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
}

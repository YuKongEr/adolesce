package com.yukong.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author yukong
 * @date 2019-03-04 10:26
 */
public class Reflection {


    private String name;

    protected Integer age;

    public String sex;


    public void say() {
        System.out.println("hi");
    }

    private void say(String name){
        System.out.println("hi ! i am " + name);
    }

    void go(){
        System.out.println("go!");
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 获取类字节码对象

        // 1 通过类名获取
        Class clazz1 = Reflection.class;

        // 2 通过类对象获取
        Reflection reflection= new Reflection();
        Class clazz2 = reflection.getClass();

        // 3 通过类名获取
        Class clazz3 = Class.forName("com.yukong.reflection.Reflection");

        // 获取类名
        System.out.println("class1 name = " + clazz1.getName());
        System.out.println("class2 name = " + clazz2.getName());
        System.out.println("class3 name = " + clazz3.getName());

        // 获取构造函数
        Constructor constructor = clazz1.getConstructor();
        Reflection r = (Reflection) constructor.newInstance();
        System.out.println(r);

        // 获取 field

        Field[] fields = clazz1.getDeclaredFields();
        for (Field field : fields ) {
            System.out.println(field.getModifiers() + " " + field.getType().getName() + " " + field.getName());
        }
    }


}

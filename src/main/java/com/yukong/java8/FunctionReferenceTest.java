package com.yukong.java8;

/**
 * @author yukong
 * @date 2019-06-11 20:58
 */
public class FunctionReferenceTest {

    public static void main(String[] args) {
        int num;
        String str;
        // 获取构造方法引用
        Convert<String, Integer> stringIntegerConvert = Integer::new;
        num = stringIntegerConvert.convert("12");
        System.out.println("num = " + num);
        // 获取静态方法引用
        Convert<Integer, String> integerStringConvert = String::valueOf;
        str = integerStringConvert.convert(121212);
        System.out.println("str = " + str);
    }


    /**
     * 定义函数接口 代表类型转换
     * @param <T>
     * @param <P>
     */
    @FunctionalInterface
    public static interface Convert<T,P>{

        /**
         * 定义接口从 T类转P类
         * @param t
         * @return
         */
        P convert(T t);
    }
}

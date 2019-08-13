package com.yukong.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author yukong
 * @date 2019-06-11 21:06
 */
public class InnerFunctionTest {

    public static void main(String[] args) {

        // 1、Predicates 断言 test方法是接受一个传入类型,返回一个布尔值.此方法应用于判断.
        Predicate<String> isNull = Objects::isNull;
        System.out.println(isNull.test("adsa"));
        // 取反
        System.out.println(isNull.negate().test("ada"));
        // 两个Predicate取and操作
        Predicate<String> isNotNull = Objects::nonNull;
        System.out.println(isNull.and(isNotNull).test(null));
        // 两个Predicate取or操作
        System.out.println(isNull.or(isNotNull).test(null));


        Predicate<Integer> graterThanSix = e -> e > 6;
        List<Integer> list = new ArrayList<>();

        // stream的filter方法的接收一个Predicates对象
        list.stream().filter(graterThanSix);


        // 2、Functions 将Function对象应用到输入的参数上，然后返回计算结果。

        Function<Integer, Integer> increment = i -> ++i;

        System.out.println(increment.apply(2));

        Function<Integer, String> convertToString = e -> "num" + e;
        // 将两个Function整合，并返回一个能够执行两个Function对象功能的Function对象。

        // 先执行自己的apply然后执行参数的apply
        System.out.println(increment.andThen(convertToString).apply(2));


        // stream的map方法的接收一个Functions对象
        list.stream().map(convertToString);

        // 3、 Consumer Consumer 接口表示要对单个输入参数执行的操作。
        Consumer<Integer> stringConsumer = str -> System.out.println("consumer: " + str);
        stringConsumer.accept(1212);

        // stream的forEach方法接收一个Consumer对象
        list.stream().forEach(stringConsumer);

        // 4、 Supplier 接口产生给定泛型类型的结果。 与 Function 接口不同，Supplier 接口不接受参数。
        Supplier<Object> objectSupplier = Object::new;
        Object obj = objectSupplier.get();



    }



}

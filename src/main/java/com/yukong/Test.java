package com.yukong;

/**
 * @author yukong
 * @date 2019-07-21 11:02
 */
public class Test {


    public static void main(String[] args) {
        String ab2 = "ab";

        String ab1 = new String("ab");
        String ab6 = new String("ab");


        System.out.println(ab1.intern() == ab2 && ab1.intern() == ab6.intern());

        String ab3 = "a" + "b";
        String a1 = "a";
        String b1 = "b";
        String ab4 = a1 + b1;


        System.out.println(ab1 == ab2); // t

        System.out.println(ab1 == ab3); // t

        System.out.println(ab2 == ab3); // t

        System.out.println(ab2 == ab4); // f
    }
}

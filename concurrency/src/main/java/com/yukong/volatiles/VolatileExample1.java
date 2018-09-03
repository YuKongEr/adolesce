package com.yukong.volatiles;

/**
 * @author yukong
 * @date 2018/9/3
 * @description  如果一个场景存在对volatile变量的读写场景，在读线程B读一个volatile变量后
 * ，写线程A在写这个volatile变量前所有的所见的共享变量的值都将会立即变得对读线程B可见。
 */
public class VolatileExample1 {

    /**
     * 共享变量 name
     */
    private static String name = "init";


    /**
     * 共享变量 flag
     */
    private  static boolean flag = false;


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000000; i++) {
            Thread threadA = new Thread(() -> {
                name = "yukong";
                flag = true;
            });
            Thread threadB = new Thread(() -> {
                if (flag) {
                    System.out.println("flag = " + flag + " name = " +name);
                };
            });
            threadA.start();
            threadB.start();
            threadA.join();
            threadB.join();
            flag = false;
            name = "";
        }
    }
}

package com.yukong.concurrency.call;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yukong
 * @date 2019-08-06 21:14
 */
public class GuavaFutureDemo {
    public static final int SLEEP_GAP = 500;


    public static String getCurThreadName()
    {
        return Thread.currentThread().getName();
    }

    static class HotWarterJob implements Callable<Boolean> //①
    {

        @Override
        public Boolean call() throws Exception //②
        {

            try
            {
                System.out.println("洗好水壶");
                System.out.println("灌上凉水");
                System.out.println("放在火上");

                //线程睡眠一段时间，代表烧水中
                Thread.sleep(SLEEP_GAP);
                System.out.println("水开了");

            } catch (InterruptedException e)
            {
                System.out.println(" 发生异常被中断.");
                return false;
            }
            System.out.println(" 运行结束.");

            return true;
        }
    }

    static class WashJob implements Callable<Boolean>
    {

        @Override
        public Boolean call() throws Exception
        {


            try
            {
                System.out.println("洗茶壶");
                System.out.println("洗茶杯");
                System.out.println("拿茶叶");
                //线程睡眠一段时间，代表清洗中
                Thread.sleep(SLEEP_GAP);
                System.out.println("洗完了");

            } catch (InterruptedException e)
            {
                System.out.println(" 清洗工作 发生异常被中断.");
                return false;
            }
            System.out.println(" 清洗工作  运行结束.");
            return true;
        }

    }

    static boolean warterOk = false;
    static boolean cupOk = false;


    public synchronized static void drinkTea()
    {
        if (warterOk && cupOk)
        {
            System.out.println("泡茶喝");
        }
        else if (!warterOk)
        {
            System.out.println("烧水失败，没有茶喝了");
        }
        else if (!cupOk)
        {
            System.out.println("杯子洗不了，没有茶喝了");
        }

    }

    public static void main(String args[])
    {
        Thread.currentThread().setName("主线程");

        Callable<Boolean> hJob = new HotWarterJob();//③

        Callable<Boolean> wJob = new WashJob();//③

        //java 线程池
        ExecutorService jPool =
                Executors.newFixedThreadPool(10);

        //guava 线程池
        ListeningExecutorService gPool =
                MoreExecutors.listeningDecorator(jPool);


        ListenableFuture<Boolean> hFuture = gPool.submit(hJob);

        Futures.addCallback(hFuture, new FutureCallback<Boolean>()
        {
            @Override
            public void onSuccess(Boolean r)
            {
                if (r)
                {
                    warterOk = true;
                    drinkTea();
                }
                else
                {

                    System.out.println("烧水失败，没有茶喝了");

                }

            }

            @Override
            public void onFailure(Throwable t)
            {
                System.out.println("烧水失败，没有茶喝了");
            }
        });


        ListenableFuture<Boolean> wFuture = gPool.submit(wJob);

        Futures.addCallback(wFuture, new FutureCallback<Boolean>()
        {
            @Override
            public void onSuccess(Boolean r)
            {
                if (r)
                {
                    cupOk = true;
                    drinkTea();
                }
                else
                {

                    System.out.println("清洗失败，没有茶喝了");

                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                System.out.println("杯子洗不了，没有茶喝了");
            }
        });

        try
        {

            System.out.println("读书中......");
            Thread.sleep(100000);

        } catch (InterruptedException e)
        {
            System.out.println(getCurThreadName() + "发生异常被中断.");
        }
        System.out.println(getCurThreadName() + " 运行结束.");

        gPool.shutdown();

    }
}

package com.coder.util.thread;


import java.util.concurrent.BlockingQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//线程池，用来维护执行任务的线程
//进程是分配任务的最小单位、线程是执行具体任务的最小单位
public class ThreadPool {
    Log log = LogFactory.getLog(ThreadPool.class);

    private static ThreadPool instance;//单例模式

    //任务队列，任务需要实现Runnable接口，该队列的作用是:
    //如果有多个任务同时到达，可以缓存到队列，防止任务丢失。
    BlockingQueue<Runnable> taskQueue;


    ExecutorService service;//线程池服务


    private ThreadPool() {
        super();
    }
    private ThreadPool(BlockingQueue<Runnable> taskQueue, ExecutorService service) {
        super();
        this.taskQueue = taskQueue;
        this.service = service;
    }

    //获得单例
    public static synchronized ThreadPool getInstance(){
        if(instance==null){
            //生成一个空队列，队列具有泛型Runnable，只有实现了Runnable接口的线程才能放入这个队列
            BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

            //获得CPU的个数
            int cpuCounts = Runtime.getRuntime().availableProcessors();

            int corePoolSize = cpuCounts;//核心线程个数等于CPU的个数

            int maximumPoolSize = cpuCounts * 2;//最大线程数量等于CPU数量的二倍

            //超过核心线程个数的线程,最长存活30秒
            //比如：cpu是4核的，则核心线程数4个，由于任务比较多生成了6个线程
            //则第五个和第六个线程，在任务完成后，其最长存活期是30秒
            int keepAliveTime = 30;

            TimeUnit unit = TimeUnit.SECONDS;//指定上面keepAliveTime的单位是秒

            //handler是人物执行失败后的处理策略，
            //这里使用ThreadPoolExecutor的内部类CallerRunsPolicy来定义策略，
            //表示：失败后重新执行
            ThreadPoolExecutor.CallerRunsPolicy handler =
                    new ThreadPoolExecutor.CallerRunsPolicy();

            //ThreadPoolExecutor是java自己完成的线程池框架
            //声明一个线程池，该线程池用来执行taskQueue队列里的多线程，并且该线程池的核心线程是4个
            //最大线程数8个，多余线程最长存活时间30秒，失败处理策略是失败后重新执行
            ExecutorService service = new ThreadPoolExecutor
                    (corePoolSize, maximumPoolSize, keepAliveTime, unit, taskQueue, handler);

            instance = new ThreadPool(taskQueue,service);
        }
        return instance;
    }

    //异步添加任务，只是将任务添加到了线程池service中的taskQueue里。
    public void addTask(Runnable task){
        log.info("向线程池中添加任务");
        service.submit(task);//submit只是提交任务到线程池。
    }

    public void close() throws InterruptedException{
        log.info("开始关闭线程池...");
        //应用停止的时候，需要将线程池安全关闭
        //首先判断taskQuene中是否还有未完成的任务队列
        if(taskQueue!=null && taskQueue.size() > 0){
            log.info("目前仍有：("+taskQueue.size()+")个任务未完成...");
            log.info("===延缓几秒钟时间，让未完成的任务完成===");
            Thread.sleep(5000);
        }
        log.info("执行关闭流程...");
        if(service!=null){
            log.info("...正在停止线程池服务");
            service.shutdown();//调用线程池服务关闭的方法，不保证立即关闭
            log.info("线程池服务停止完毕...");
        }
        taskQueue = null;//清空任务队列
        log.info("关闭线程池End...");
    }
}

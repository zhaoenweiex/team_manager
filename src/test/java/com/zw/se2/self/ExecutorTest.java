package com.zw.se2.self;

import org.junit.Before;
import org.junit.Test;
import org.omg.SendingContext.RunTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

public class ExecutorTest {
    public static final Logger logger = LoggerFactory.getLogger(ExecutorTest.class);
    private ExecutorService es = new ThreadPoolExecutor(4, 32,
            10, TimeUnit.SECONDS, new SynchronousQueue<>(), r -> {
        Thread thread = new Thread(r);
        thread.setName("my " + thread.getName());
        return thread;
    });

    @Test
    public void cmdTest() throws InterruptedException {
        Long ago = System.currentTimeMillis();
        for (int i = 0; i < 60; i++) {
            ThreadPoolExecutor tpe = (ThreadPoolExecutor) es;
            logger.info("第{}次循环", i);
            logger.info("getCompletedTaskCount={}", tpe.getCompletedTaskCount());
            logger.info("getActiveCount={}", tpe.getActiveCount());
            logger.info("getPoolSize={}", tpe.getPoolSize());
            logger.info("getQueue={}", tpe.getQueue().size());
            es.submit(() -> {
                Process processor = null;
                try {
                    processor = new ProcessBuilder("cmd \"ping www.baidu.com -t\"").start();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {
                    processor.waitFor(5,TimeUnit.SECONDS);
                    logger.info("{}完成", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
            Thread.sleep(1000L);
        }
        Thread.sleep(600000L);
    }
}

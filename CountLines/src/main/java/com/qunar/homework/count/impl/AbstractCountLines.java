package com.qunar.homework.count.impl;

import com.qunar.homework.count.CountLines;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.*;

/**
 * Created by zhangzhi on 16-1-5.
 * 计算java源文件有效行数的实现类
 */
public abstract class AbstractCountLines implements CountLines {

    //依据主机配置决定的线程数量
    protected int threadCount;

    //缓存并发任务的队列
    protected Queue<String> taskQueue;

    /**
     * 抽象类构造器
     *
     * @param taskQueue   缓存并发任务的队列
     * @param threadCount 依据主机配置决定的线程数量
     */
    public AbstractCountLines(Queue<String> taskQueue, int threadCount) {
        this.taskQueue = taskQueue;
        this.threadCount = threadCount;
    }

    @Override
    public Map<String, Integer> countValidLinesForMultipleFiles(String... filePathes) {
        addTask(filePathes);
        return runTask();
    }

    @Override
    public int countValidLinesForSingleFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile() && isTargetFile(file.getName())) {
            return 1;
        } else
            return -1;
    }

    /**
     * 将所有待计算的java源文件路径入队(非目标文件将被过滤掉)
     *
     * @param filePathes 源文件(或目录)的路径
     */
    protected void addTask(String... filePathes) {

    }

    /**
     * 判断该文件是否是目标文件
     *
     * @param fileName 给定的文件名
     * @return 是否是目标文件
     */
    protected abstract boolean isTargetFile(String fileName);

    /**
     * 从队列中取目标源文件路径并计算有效行数
     *
     * @return 执行的结果(源文件路径到有效行数的映射集)
     */
    protected Map<String, Integer> runTask() {
        //用于同步线程池
        CountDownLatch latch = new CountDownLatch(threadCount);
        //执行任务的线程池
        ExecutorService countPool = Executors.newFixedThreadPool(threadCount);
        //执行结果的存放地,保证线程安全的动态代理
        Map<String, Integer> report = Collections.synchronizedMap(new TreeMap<>());

        for (int i = 0; i < threadCount; i++) {
            countPool.submit(() -> {
                for (; ; ) {
                    String path = taskQueue.poll();
                    if (path == null)
                        break;
                    count(path, report);
                }
                latch.countDown();
            });
        }
        countPool.shutdown();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return report;
    }

    /**
     * 计算一个源文件的有效行数的具体实现
     *
     * @param path   源文件的路径
     * @param report 计算的结果写入的数据结构
     */
    protected abstract void count(String path, Map<String, Integer> report);

    /**
     * 根据给定的路径名获取文件内容
     *
     * @param path 路径名
     * @return 路径名所在文件的内容(若发生异常返回null)
     */
    protected String getContent(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            StringBuilder sb = new StringBuilder();
            FileChannel fc = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                buffer.clear();
                if (fc.read(buffer) == -1)
                    break;
                buffer.flip();
                sb.append(new String(buffer.array(), "UTF-8"));
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }

}

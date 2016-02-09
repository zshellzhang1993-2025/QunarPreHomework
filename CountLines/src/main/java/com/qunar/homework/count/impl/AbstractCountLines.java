package com.qunar.homework.count.impl;

import com.qunar.homework.count.CountLines;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.*;

/**
 * Created by zhangzhi on 16-1-25.
 * 不针对具体语言的计算源文件有效行数的抽象类
 */
public abstract class AbstractCountLines implements CountLines {

    //异常跟踪日志
    private Logger exceptionLogger = LogManager.getLogger("exceptionLogger");
    //实时显示执行情况
    private Logger contentLogger = LogManager.getLogger("contentLogger");

    //依据主机配置决定的线程数量
    protected int threadCount;

    //缓存并发任务的队列
    protected Queue<String> taskQueue;

    /**
     * 抽象类默认构造器
     * 默认使用并发队列
     * 默认能开辟的最佳线程数是4
     */
    public AbstractCountLines() {
        this.taskQueue = new ConcurrentLinkedQueue<>();
        this.threadCount = 4;
    }

    /**
     * 抽象类自定义构造器
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
        if (isSingleTargetFile(filePath))
            return count(filePath);
        else
            return -1;
    }

    /**
     * 判断给定的文件路径是否构成一个有效的目标源文件
     *
     * @param filePath 给定的路径
     * @return 是否是有效的目标文件
     */
    protected boolean isSingleTargetFile(String filePath) {
        return Files.exists(Paths.get(filePath))
                && !Files.isDirectory(Paths.get(filePath))
                && isTargetFile(filePath);
    }

    /**
     * 将所有待计算的java源文件路径入队(非目标文件将被过滤掉)
     *
     * @param filePathes 源文件(或目录)的路径
     */
    protected void addTask(String... filePathes) {
        for (String filePath : filePathes) {
            try {
                if (Files.exists(Paths.get(filePath))) {
                    if (Files.isDirectory(Paths.get(filePath))) {
                        Files.walkFileTree(Paths.get(filePath),
                                new SimpleFileVisitor<Path>() {
                                    //访问文件时的判断操作
                                    @Override
                                    public FileVisitResult visitFile
                                    (Path path, BasicFileAttributes attrs) throws IOException {
                                        if (isTargetFile(path.toFile().getName()))
                                            taskQueue.offer(path.toString());
                                        return FileVisitResult.CONTINUE;
                                    }
                                });
                    } else if (isTargetFile(filePath))
                        taskQueue.offer(filePath);
                }
            } catch (IOException e) {
                exceptionLogger.debug("源文件名入队时遇到错误:", e);
            }
        }
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
                    String filePath = taskQueue.poll();
                    if (filePath == null)
                        break;
                    int countLines = count(filePath);
                    report.put(filePath, countLines);
                    contentLogger.info(filePath + " : " + countLines);
                }
                latch.countDown();
            });
        }
        countPool.shutdown();

        try {
            latch.await();
        } catch (InterruptedException e) {
            exceptionLogger.error("产生意外的线程中断:", e);
        }
        return report;
    }

    /**
     * 计算一个源文件的有效行数的具体实现
     *
     * @param filePath 源文件的路径
     * @return 有效的行数
     */
    protected abstract int count(String filePath);

    /**
     * 根据给定的路径名获取文件内容
     *
     * @param filePath 路径名
     * @return 路径名所在文件的内容(若发生异常返回null)
     */
    protected String getContent(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             FileChannel fisChannel = fis.getChannel()) {
            StringBuilder content = new StringBuilder();
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            while (true) {
                buffer.clear();
                if (fisChannel.read(buffer) == -1)
                    break;
                buffer.flip();
                content.append(new String(buffer.array(), "UTF-8"));
            }
            return content.toString();
        } catch (IOException e) {
            exceptionLogger.error(filePath, e);
            return null;
        }
    }

}

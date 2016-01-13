package com.qunar.homework.count.impl;

import com.qunar.homework.count.CountLines;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * Created by zhangzhi on 16-1-5.
 * 计算java源文件有效行数的实现类
 */
public class JavaCountLines implements CountLines {

    //缓存并发任务的阻塞队列
    protected BlockingQueue<String> taskQueue;

    //执行并发任务的线程池
    protected ExecutorService executePool;

    //存放执行的结果(映射集:源文件路径->有效行数)
    protected Map<String, Integer> report;


    public JavaCountLines(BlockingQueue<String> taskQueue, ExecutorService executePool) {

    }

    @Override
    public Map<String, Integer> countValidLines(String... filePath) {
        return report;
    }

    /**
     * @param filePath 源文件路径
     */
    protected void addTask(String filePath) {

    }

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

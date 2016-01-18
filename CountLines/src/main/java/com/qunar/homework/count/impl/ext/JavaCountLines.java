package com.qunar.homework.count.impl.ext;

import com.qunar.homework.count.impl.AbstractCountLines;

import java.util.Queue;

/**
 * Created by zhangzhi on 16-1-14.
 * 针对java源文件的计算有效行数的实现类
 */
public class JavaCountLines extends AbstractCountLines {

    /**
     * 默认构造器
     */
    public JavaCountLines() {
        super();
    }

    /**
     * 自定义构造器
     *
     * @param taskQueue   缓存并发任务的队列
     * @param threadCount 依据主机配置决定的线程数量
     */
    public JavaCountLines(Queue<String> taskQueue, int threadCount) {
        super(taskQueue, threadCount);
    }

    @Override
    protected boolean isTargetFile(String fileName) {
        return fileName.endsWith(".java");
    }

    @Override
    protected int count(String path) {
        String content = getContent(path);
        return 0;
    }
}

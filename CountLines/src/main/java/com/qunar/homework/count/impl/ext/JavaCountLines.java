package com.qunar.homework.count.impl.ext;

import com.qunar.homework.count.impl.AbstractCountLines;

import java.util.Queue;

/**
 * Created by zhangzhi on 16-1-25.
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
        //记录有效的行数
        int linesCount = 0;
        //记录当前扫描的是第几个字符
        int currentCharacter = 0;
        //源文件转成的字符串
        String content = getContent(path);
        if (content == null)
            return -1;
        while (currentCharacter < content.length()) {

        }
        return linesCount;
    }

    protected void judgeInvalid(int linesCount) {

    }
}

package com.qunar.homework.count.impl;

import com.qunar.homework.count.impl.ext.JavaCountLines;
import org.junit.Test;

/**
 * Created by zhangzhi on 16-2-3.
 * 测试计算有效行数抽象类的各方法
 */
public class AbstractCountLinesTest {

    @Test
    public void testGetContent() {
        AbstractCountLines countLines = new JavaCountLines();
        StringBuilder content = countLines.getContent
                ("./src/main/java/com/qunar/homework/MainProc.java");
        System.out.println(content);
    }

    @Test
    public void testAddTask() {
        AbstractCountLines countLines = new JavaCountLines();
        String[] filePathes = new String[2];
        filePathes[0] = "/home/zhangzhi/Documents/src/cn/edu/njnu/tidypage";
        //filePathes[1] = "/home/zhangzhi/Documents/src/cn/edu/njnu/infoextract";
        filePathes[1] = "/home/zhangzhi/Documents/src/cn/edu/njnu/ExtractModule.java";
        filePathes[2] = "/home/zhangzhi/Documents/src/cn/edu/njnu/domain";
        countLines.addTask(filePathes);
        System.out.println(countLines.taskQueue);
    }

}

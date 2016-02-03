package com.qunar.homework.count.impl;

import com.qunar.homework.count.impl.ext.JavaCountLines;
import org.junit.Test;

/**
 * Created by zhangzhi on 16-2-3.
 * 测试计算有效行数的抽象类各方法
 */
public class TestAbstractCountLines {

    @Test
    public void testGetContent() {
        AbstractCountLines countLines = new JavaCountLines();
        String content = countLines.getContent
                ("./src/main/java/com/qunar/homework/MainProc.java");
        System.out.println(content);
    }

}

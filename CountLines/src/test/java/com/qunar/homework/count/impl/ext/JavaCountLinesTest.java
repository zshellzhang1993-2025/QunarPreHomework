package com.qunar.homework.count.impl.ext;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhangzhi on 16-2-4.
 * 测试基于java的计算源代码行数的实现类
 */
public class JavaCountLinesTest {

    @Test
    public void testIsInvalidLine() {
        JavaCountLines countLines = new JavaCountLines();
        String content = "//test\n   int i = 0;  \n//test";
        Assert.assertEquals(false, countLines.isValidLine(content, 22, false));
    }

    @Test
    public void testProcessSingleLineComment() {
        JavaCountLines countLines = new JavaCountLines();
        String content = "//test single line comment\n";
        Assert.assertEquals(27, countLines.processSingleLineComment(content, 0));
    }

    @Test
    public void testCount() {

    }

}

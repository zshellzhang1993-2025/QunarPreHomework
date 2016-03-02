package com.qunar.homework.count.impl;

import com.qunar.homework.count.impl.ext.JavaCountLines;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by zhangzhi on 16-2-3.
 * 使用多数据集单独测试AbstractCountLines.isSingleTargetFile(String filePath);
 */
@RunWith(Parameterized.class)
public class IsSingleTargetFileTest {

    //期望输出值
    private boolean isSingleTargetFile;

    //待判断路径
    private String filePath;

    public IsSingleTargetFileTest(boolean isSingleTargetFile, String filePath) {
        this.isSingleTargetFile = isSingleTargetFile;
        this.filePath = filePath;
    }

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {true, "./src/main/java/com/qunar/homework/MainProc.java"},
                {false, "./src/main/java/com/qunar"},
                {false, "./pom.xml"}});
    }

    @Test
    public void testIsSingleTargetFile() {
        Assert.assertEquals(isSingleTargetFile,
                new JavaCountLines().isSingleTargetFile(filePath));
    }

}

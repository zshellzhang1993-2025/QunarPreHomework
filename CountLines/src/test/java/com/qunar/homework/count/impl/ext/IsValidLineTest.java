package com.qunar.homework.count.impl.ext;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by zhangzhi on 16-2-9.
 * 测试isValidLine(String content, int currentCharacter);
 */
@RunWith(Parameterized.class)
public class IsValidLineTest {

    //期望输出值
    private boolean isValid;

    //待判断内容
    private String content;

    //当前字符位置
    int currentCharacter;

    //是否在注释里
    boolean inComment;

    public IsValidLineTest(boolean isValid, String content,
                           int currentCharacter, boolean inComment) {
        this.isValid = isValid;
        this.content = content;
        this.currentCharacter = currentCharacter;
        this.inComment = inComment;
    }

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {false, "\n/*test*//*test*/", 16, false},
                {false, "/*test*//*test*/", 15, false},
                {false, "//pom.xml", 8, true},
                {true, "\n/*test*/int i = 0;/*test*/", 26, true},
                {true, "int i = 0;//pom.xml", 18, true},
        });
    }

    @Test
    public void testIsValidLine() {
        Assert.assertEquals(isValid,
                new JavaCountLines().isValidLine(content, currentCharacter, inComment));
    }

}

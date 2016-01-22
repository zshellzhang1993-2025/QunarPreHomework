package com.qunar.homework;

import com.qunar.homework.count.CountLines;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Created by zhangzhi on 16-1-4.
 * 程序入口
 */
public class MainProc {

    /**
     * 程序的入口
     *
     * @param filePathes 待统计的文件或目录(若输入为目录路径则统计目录下所有java源文件)
     */
    public static void main(String... filePathes) {

        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        CountLines countLines = (CountLines) applicationContext.getBean("countlines");

        Map<String, Integer> result = countLines.countValidLinesForMultipleFiles(filePathes);
        System.out.println(result);

    }

}

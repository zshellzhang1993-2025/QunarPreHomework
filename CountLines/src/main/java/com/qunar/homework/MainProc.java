package com.qunar.homework;

import com.qunar.homework.count.CountLines;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import java.util.Map;

/**
 * Created by zhangzhi on 16-1-24.
 * 程序入口
 */
public class MainProc {

    /**
     * 程序的入口
     *
     * @param filePathes 待统计的文件或目录(若输入为目录路径则统计目录下所有java源文件)
     */
    public static void main(String... filePathes) {

        //注入实例
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        CountLines countLines = (CountLines) applicationContext.getBean("countlines");

        //记录结果的日志
        Logger reportLogger = LogManager.getLogger(MainProc.class);

        //如果没有输入则结束程序
        if (filePathes.length < 1) {
            reportLogger.info("没有输入!");
            System.exit(0);
        } else if (filePathes.length == 1) {
            int linesCount = countLines.countValidLinesForSingleFile(filePathes[0]);
            //如果结果不是-1,说明是单个源文件,否则可能是单个目录,继续下面的过程
            if (linesCount != -1) {
                reportLogger.info(filePathes[0] + " : " + linesCount);
                System.exit(0);
            }
        }
        //针对多源文件或目录
        StringBuilder content = new StringBuilder();
        for (String directory : filePathes)
            content.append(directory);
        reportLogger.info(content);
        /*Map<String, Integer> result =*/   //方法返回的map可以作为其他模块的输入
        countLines.countValidLinesForMultipleFiles(filePathes);
    }

}

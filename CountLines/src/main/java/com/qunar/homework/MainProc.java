package com.qunar.homework;

import com.qunar.homework.count.CountLines;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

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

        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        CountLines countLines = (CountLines) applicationContext.getBean("countlines");

        Logger logger = Logger.getLogger(MainProc.class);

        //如果没有输入则结束程序
        if (filePathes.length < 1) {
            logger.info("没有可统计的源文件!");
            System.exit(0);
        } else if (filePathes.length == 1) {
            int linesCount = countLines.countValidLinesForSingleFile(filePathes[0]);
            //如果结果不是-1,说明是单个源文件,否则可能是单个目录,继续下面的过程
            if (linesCount != -1) {
                logger.info(filePathes[0] + " : " + linesCount);
                System.exit(0);
            }
        }
        //针对多源文件或目录
        Map<String, Integer> result = countLines.countValidLinesForMultipleFiles(filePathes);
        for (String file : result.keySet()) {
            logger.info(file + " : " + result.get(file));
        }

    }

}

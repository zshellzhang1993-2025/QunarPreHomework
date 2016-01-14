package com.qunar.homework.count;

import java.util.Map;

/**
 * Created by zhangzhi on 16-1-5.
 * 定义计算有效行数的行为规范
 * caution:
 * * countValidLinesForMultipleFiles(String... filePathes)可以计算单个源文件的有效行数
 * * 所返回的map将只有一个Entry;
 * * countValidLinesForSingleFile(String filePath)不能计算多于一个的源文件有效行数
 * * 若参数不合法将返回-1
 */
public interface CountLines {

    /**
     * 计算给定目录下的源文件(或直接给出源文件)的有效行数
     *
     * @param filePathes 目标目录(源文件)的路径
     * @return 源文件路径到其有效行数的映射集
     */
    Map<String, Integer> countValidLinesForMultipleFiles(String... filePathes);

    /**
     * 计算给定的单个源文件的有效行数
     *
     * @param filePath 源文件的路径
     * @return 给定源文件的有效行数
     */
    int countValidLinesForSingleFile(String filePath);
}

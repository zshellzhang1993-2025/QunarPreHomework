package com.qunar.homework.count;

/**
 * Created by zhangzhi on 16-1-5.
 * 定义计算有效行数的行为规范
 */
public interface CountLines {

    /**
     * 计算给定文本内容的有效行数
     *
     * @param filePathes 目标源文件的路径
     * @return 该文本的有效行数
     */
    int countValidLines(String... filePathes);

}

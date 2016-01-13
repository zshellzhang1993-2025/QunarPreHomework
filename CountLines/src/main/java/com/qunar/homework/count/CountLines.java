package com.qunar.homework.count;

import java.util.Map;

/**
 * Created by zhangzhi on 16-1-5.
 * 定义计算有效行数的行为规范
 */
public interface CountLines {

    /**
     * 计算给定文本内容的有效行数
     *
     * @param filePathes 目标源文件(或目录)的路径
     * @return 所有
     */
    Map<String, Integer> countValidLines(String... filePathes);

}

package com.qunar.homework.count.impl.ext;

import com.qunar.homework.count.impl.AbstractCountLines;

import java.util.Queue;

/**
 * Created by zhangzhi on 16-1-25.
 * 针对java源文件的计算有效行数的实现类
 */
public class JavaCountLines extends AbstractCountLines {

    /**
     * 默认构造器
     */
    public JavaCountLines() {
        super();
    }

    /**
     * 自定义构造器
     *
     * @param taskQueue   缓存并发任务的队列
     * @param threadCount 依据主机配置决定的线程数量
     */
    public JavaCountLines(Queue<String> taskQueue, int threadCount) {
        super(taskQueue, threadCount);
    }

    @Override
    protected boolean isTargetFile(String fileName) {
        return fileName.endsWith(".java");
    }

    @Override
    protected int count(String path) {
        //记录有效的行数
        int linesCount = 0;
        //记录当前字符位置
        int currentCharacter = 0;
        //源文件转成的字符串
        String content = getContent(path);
        if (content == null)
            return -1;
        while (currentCharacter < content.length()) {
            if (content.charAt(currentCharacter) == '\n') {
                //如果不是无效行就增加有效行数
                if (!isBlankLine(content, currentCharacter++))
                    linesCount++;
            } else if (content.charAt(currentCharacter) == '/') {
                //如果下一个字符是'/'或'*'则进入注释处理
                if (content.charAt(currentCharacter + 1) == '/' ||
                        content.charAt(currentCharacter + 1) == '*') {
                    //判断注释前是否有有效字符,如果有则增加有效行数
                    if (!isBlankLine(content, currentCharacter))
                        linesCount++;

                    //处理"//"风格的注释
                    if (content.charAt(currentCharacter + 1) == '/') {
                        currentCharacter = processSingleLineComment(content, currentCharacter);
                    }
                    //处理"/**/"风格的注释
                    else if (content.charAt(currentCharacter + 1) == '*') {
                        currentCharacter = processMutipleLineComment(content, currentCharacter);
                    }

                    //判断注释后是否有有效字符,如果有则增加有效行数
                    if (!isBlankLine(content, currentCharacter))
                        linesCount++;
                }
                //如果下一个字符不是'/'或'*'则按有效字符处理
                else
                    currentCharacter++;
            }
        }
        return linesCount;
    }

    /**
     * 判断一行是否是无效的行(仅由'/n','/t'和' '组成或注释的头尾不含有效字符)
     *
     * @param content          源代码的内容
     * @param currentCharacter 当前的字符位置
     * @return 是否是一个无效的行
     */
    protected boolean isBlankLine(String content, int currentCharacter) {
        //如果第一个字符是'\n'或'/'那这就是一个空行
        if (currentCharacter == 0)
            return true;
        else {
            while (currentCharacter > 0 && content.charAt(currentCharacter - 1) != '\n') {
                if (content.charAt(currentCharacter) != ' ' ||
                        content.charAt(currentCharacter) != '\t') {
                    return (content.charAt(currentCharacter) == '/' &&
                            content.charAt(currentCharacter - 1) == '*');
                } else
                    currentCharacter--;
            }
            return true;
        }
    }

    /**
     * 处理//风格的注释行,返回注释后的下一个字符位置
     *
     * @param content          源代码的内容
     * @param currentCharacter 当前的字符位置
     * @return 注释后的下一个字符位置
     */
    protected int processSingleLineComment(String content, int currentCharacter) {

    }

    /**
     * 处理/ * * /风格的注释行,返回注释后的下一个字符位置
     *
     * @param content          源代码的内容
     * @param currentCharacter 当前的字符位置
     * @return 注释后的下一个字符位置
     */
    protected int processMutipleLineComment(String content, int currentCharacter) {

    }

}



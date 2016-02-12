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
                if (isValidLine(content, currentCharacter, false))
                    linesCount++;
                currentCharacter++;
            } else if (content.charAt(currentCharacter) == '/') {
                //如果下一个字符是'/'或'*'则进入注释处理
                if (content.charAt(currentCharacter + 1) == '/' ||
                        content.charAt(currentCharacter + 1) == '*') {

                    //处理"//"风格的注释
                    if (content.charAt(currentCharacter + 1) == '/') {
                        currentCharacter = processSingleLineComment(content, currentCharacter);
                        //检测该行初有无有效字符
                        if (isValidLine(content, currentCharacter, true))
                            linesCount++;
                        currentCharacter++;
                    }

                    //处理"/**/"风格的注释
                    else if (content.charAt(currentCharacter + 1) == '*') {
                        currentCharacter = currentCharacter + 2;
                        while (currentCharacter < content.length()) {
                            while (currentCharacter < content.length() &&
                                    content.charAt(currentCharacter) != '*') {
                                //遇到换行符就扫描该行是否有效
                                if (content.charAt(currentCharacter) == '\n' &&
                                        isValidLine(content, currentCharacter, true))
                                    linesCount++;
                                currentCharacter++;
                            }
                            if (currentCharacter == content.length())
                                return currentCharacter;
                            //如果已经匹配上/**/
                            if (content.charAt(currentCharacter + 1) == '/') {
                                currentCharacter = currentCharacter + 2;
                                break;
                            } else
                                currentCharacter++;
                        }
                    }

                }
                //如果下一个字符不是'/'或'*'则按有效字符处理
                else
                    currentCharacter++;
            } else {
                if (currentCharacter == content.length() - 1 &&
                        content.charAt(currentCharacter) != '\n' &&
                        isValidLine(content, currentCharacter, false))
                    linesCount++;
                currentCharacter++;
            }
        }
        return linesCount;
    }

    /**
     * 判断一行是否是有效的行
     *
     * @param content          源代码的内容
     * @param currentCharacter 当前的字符位置
     * @param inComment        当前字符是否在注释里面
     * @return 是否是一个有效的行
     */
    protected boolean isValidLine(String content, int currentCharacter, boolean inComment) {
        //如果第一个字符是'\n'那这就是一个无效的行
        if (currentCharacter == 0)
            return false;
        else {
            if (inComment) {
                if (content.charAt(currentCharacter) == '\n')
                    currentCharacter--;
                while (currentCharacter > 1 && content.charAt(currentCharacter) != '\n') {
                    if (content.charAt(currentCharacter) == '*' &&
                            content.charAt(currentCharacter - 1) == '/' ||
                            content.charAt(currentCharacter) == '/' &&
                                    content.charAt(currentCharacter - 1) == '/') {
                        currentCharacter = currentCharacter - 2;
                        //如果在注释外面有有效字符则判断该行为有效行
                        while (currentCharacter >= 0 &&
                                content.charAt(currentCharacter) != '\n') {
                            //如果从一个注释出来又进了另一个注释
                            if (currentCharacter > 1 &&
                                    content.charAt(currentCharacter) == '/' &&
                                    content.charAt(currentCharacter - 1) == '*')
                                return isValidLine(content, currentCharacter - 2, true);
                            else if (content.charAt(currentCharacter) != ' ' &&
                                    content.charAt(currentCharacter) != '\t')
                                return true;
                            else
                                currentCharacter--;
                        }
                        return false;
                    } else
                        currentCharacter--;
                }
                return false;
            } else {
                if (content.charAt(currentCharacter) == '\n')
                    currentCharacter--;
                while (currentCharacter > 1 && content.charAt(currentCharacter) != '\n') {
                    if (content.charAt(currentCharacter) == '/' &&
                            content.charAt(currentCharacter - 1) == '*') {
                        currentCharacter = currentCharacter - 2;
                        return (content.charAt(currentCharacter) != '\n') &&
                                isValidLine(content, currentCharacter, true);
                    } else if (content.charAt(currentCharacter) != ' ' &&
                            content.charAt(currentCharacter) != '\t' &&
                            content.charAt(currentCharacter) != '\n')
                        return true;
                    else
                        currentCharacter--;
                }
                return false;
            }
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
        while (currentCharacter < content.length() &&
                content.charAt(currentCharacter) != '\n')
            currentCharacter++;
        return currentCharacter;
    }

}

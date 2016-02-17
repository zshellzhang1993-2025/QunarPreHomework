package com.qunar.homework.crawler;

import com.qunar.homework.parser.Parser;

import javax.annotation.Resource;

/**
 * Created by zhangzhi on 16-2-17.
 * 定义获取页面内容的行为规范
 */
public abstract class Crawler {

    @Resource
    protected Parser pageParser;

    public abstract void retriveDataFromPages();

    protected String downloadPage(String url) {
        return null;
    }

}

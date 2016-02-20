package com.qunar.homework.contentprovider;

import com.qunar.homework.extractor.Extractor;

/**
 * Created by zhangzhi on 16-2-17.
 * 定义获取页面内容的抽象类
 */
public abstract class ContentProvider {

    protected Extractor extractor;

    public void setExtractor(Extractor extractor) {
        this.extractor = extractor;
    }

    /**
     * 加载配置文件爬取网页并持久化从网页中抽取的内容
     * 过程如下:
     * 1.下载从配置文件给出的目标网页
     * 2.从网页内容中抽取目标数据集
     * 3.持久化目标数据集
     */
    public abstract void retriveDataFromPages();

    /**
     * 根据给定的url访问网页并获取页面内容
     *
     * @param url 给定的url
     * @return 页面的内容
     */
    protected String downloadPage(String url) {
        return null;
    }

}

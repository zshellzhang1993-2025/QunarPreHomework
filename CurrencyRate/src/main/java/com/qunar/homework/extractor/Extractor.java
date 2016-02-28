package com.qunar.homework.extractor;

import com.qunar.homework.ParameterHelper;
import com.qunar.homework.domain.Extractable;
import org.jsoup.nodes.Element;

/**
 * Created by zhangzhi on 16-2-17.
 * 定义分析页面抽取内容的抽象类
 */
public abstract class Extractor {

    //参数获取工具
    protected ParameterHelper parameterHelper;

    public void setParameterHelper(ParameterHelper parameterHelper) {
        this.parameterHelper = parameterHelper;
    }

    /**
     * 从获取的网页内容中抽取需要的信息
     *
     * @param element 网页内容(表现为一棵DOM树)
     * @return 抽取出来的数据集合
     */
    public abstract Extractable extractDataFormContent(Element element);

    /**
     * 从获取的网页内容中抽取下一级的URLs
     *
     * @param element 网页内容(表现为一棵DOM树)
     * @return 抽取出来的URLs
     */
    public abstract String[] extractUrlsFromContent(Element element);

}

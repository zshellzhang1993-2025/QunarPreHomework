package com.qunar.homework.extractor;

import com.qunar.homework.domain.Extractable;
import org.jsoup.nodes.Element;

/**
 * Created by zhangzhi on 16-2-17.
 * 定义分析页面抽取内容的抽象类
 */
public abstract class Extractor {


    public abstract Extractable extractDataFormContent(Element element);

}

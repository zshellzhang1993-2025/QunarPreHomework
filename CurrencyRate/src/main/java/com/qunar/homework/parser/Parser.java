package com.qunar.homework.parser;

import com.qunar.homework.domain.Extractable;
import org.jsoup.nodes.Element;

/**
 * Created by zhangzhi on 16-2-17.
 * 定义分析页面抽取内容的行为规范
 */
public interface Parser {

    Extractable extractDataFormContent(Element element);

}

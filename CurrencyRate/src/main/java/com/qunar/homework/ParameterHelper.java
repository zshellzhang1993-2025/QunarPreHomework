package com.qunar.homework;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhi on 16-2-20.
 * 用于提取配置文件里的参数信息
 */
public class ParameterHelper {

    Element infoRoot;

    /**
     * 构造器:初始化配置文件的根节点
     */
    public ParameterHelper() {
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(new File(
                    ParameterHelper.class.getResource("/config.xml").getPath()));
            infoRoot = doc.getRootElement();
        } catch (DocumentException e) {
            //slf4j
            System.exit(1);
        }
    }

    /**
     * 根据标签名获得单个参数值
     * 使用说明:不定长数组从前往后为路径所在的标签名,用户需依次输入配置文件中目标标签
     * 所在路径上的标签名
     *
     * @param tags 标签名数组
     * @return 标签对应的值
     */
    public String getSingleParameter(String... tags) {
        //如果用户没有给出标签
        if (tags.length == 0) {
            //slf4j
            return null;
        }
        Element target = infoRoot;
        //如果只有一个标签
        if (tags.length == 1)
            return target.element(tags[0]).getText().replaceAll("\n", "").trim();
        //如果有多个标签,先定位到倒数第二个,如果中间有不符合的标签及时终止
        for (int i = 0; i < tags.length - 1; i++) {
            target = target.element(tags[i]);
            if (target == null || target.nodeCount() == 0) {
                //slf4j
                return null;
            }
        }
        //定位到最后一个目标标签后获取其内容
        target = target.element(tags[tags.length - 1]);
        if (target != null && target.nodeCount() == 0)
            return target.getText().replaceAll("\n", "").trim();
        else
            return null;
    }

    /**
     * 根据标签名获得标签下多个批量参数值
     * 使用说明:不定长数组从前往后为路径所在的标签名,用户需依次输入配置文件中目标标签
     * 所在路径上的标签名,最后一个标签下应含有所需要的多个批量标签
     *
     * @param tags 标签名(不定长数组,用前往后为路径所在的标签名)
     * @return 标签下多个参数值的list
     */
    public List<String> getMultiParameters(String... tags) {
        //如果用户没有给出标签
        if (tags.length == 0) {
            //slf4j
            return null;
        }
        Element target = infoRoot;
        List<Element> elements;
        List<String> result = new ArrayList<>();
        //如果标签只有一个且其子标签大于等于1
        if (tags.length == 1 && target.nodeCount() >= 1) {
            elements = target.elements();
            elements.forEach(element ->
                    result.add(element.getText().replaceAll("\n", "").trim()));
            return result;
        }
        //如果有多个标签,先定位到倒数第二个,如果中间有不符合的标签及时终止
        for (int i = 0; i < tags.length - 1; i++) {
            target = target.element(tags[i]);
            if (target == null || target.nodeCount() == 0) {
                //slf4j
                return null;
            }
        }
        //定位到最后一个目标标签后获取其内容
        target = target.element(tags[tags.length - 1]);
        if (target != null && target.nodeCount() == 0) {
            elements = target.elements();
            elements.forEach(element ->
                    result.add(element.getText().replaceAll("\n", "").trim()));
            return result;
        } else
            return null;
    }

}

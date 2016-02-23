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
     *
     * @param tags 标签名
     * @return 标签对应的值
     */
    public String getSingleParameter(String... tags) {
        for (int i = 0; i < tags.length; i++) {

        }
        return infoRoot.element(tags).getText().replaceAll("\n", "").trim();
    }

    public List<String> getMultiParameters(String name) {
        List<Element> elements = infoRoot.element(name).elements();
        List<String> result = new ArrayList<>();

        elements.forEach(element ->
                result.add(element.getText().replaceAll("\n", "").trim()));
        return result;
    }

}

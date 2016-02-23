package com.qunar.homework.extractor.ext;

import com.qunar.homework.domain.Extractable;
import com.qunar.homework.extractor.Extractor;
import org.jsoup.nodes.Element;

/**
 * Created by zhangzhi on 16-2-18.
 * 适用于货币汇率抽取内容的工具类
 */
public class CurrencyRateExtractor extends Extractor {

    @Override
    public Extractable extractDataFormContent(Element element) {
        return null;
    }

}

package com.qunar.homework.contentprovider.ext;

import com.qunar.homework.contentprovider.ContentProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * Created by zhangzhi on 16-2-18.
 * 每日货币汇率的数据源提供者
 */
public class CurrencyRateProvider extends ContentProvider {

    @Override
    public void retriveDataFromPages() {
        String seedPage = parameterHelper.getSingleParameter("seed");
        String pageContent = downloadPage(seedPage);
        Element element = Jsoup.parse(pageContent).body();
        String[] contentPage = extractor.extractUrlsFromContent(element);
    }

}

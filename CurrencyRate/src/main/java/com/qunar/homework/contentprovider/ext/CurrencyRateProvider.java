package com.qunar.homework.contentprovider.ext;

import com.qunar.homework.contentprovider.ContentProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangzhi on 16-2-18.
 * 每日货币汇率的数据源提供者
 */
public class CurrencyRateProvider extends ContentProvider {

    //计数总共要抓取多少条记录
    protected int count;

    public void setCount(int count) {
        this.count = count;
    }

    //适合开辟的子线程数量
    protected int threadCount;

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public void retriveDataFromPages() {
        String pageContent = downloadPage(seedPage);
        Element element = Jsoup.parse(pageContent).body();

        //从种子网页中获得具体的数据目标数据所在的网页
        String[] contentPage = extractor.extractUrlsFromContent(element);

        //以下为从目标网页中抽取数据集
        AtomicInteger countNumber = new AtomicInteger(count);
        ExecutorService extractService = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            extractService.submit(() -> {
                int exceptNum;
                int updateNum;
                for (; ; ) {
                    if ((exceptNum = countNumber.get()) != 0) {
                        updateNum = exceptNum - 1;
                        //如果
                        if (countNumber.compareAndSet(exceptNum, updateNum)) {
                        }
                        //extractor.extractDataFormContent();
                    }
                }
            });
        }
    }

}

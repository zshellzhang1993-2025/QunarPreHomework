package com.qunar.homework.contentprovider;

import com.qunar.homework.ParameterHelper;
import com.qunar.homework.extractor.Extractor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by zhangzhi on 16-2-17.
 * 定义获取页面内容的抽象类
 */
public abstract class ContentProvider {

    ParameterHelper parameterHelper;

    public void setParameterHelper(ParameterHelper helper) {
        this.parameterHelper = helper;
    }

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
        CloseableHttpClient httpClient = null;
        HttpGet method = null;
        try {
            httpClient = HttpClients.createDefault();
            method = new HttpGet(url);
            HttpResponse response = httpClient.execute(method);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            //slf4j log
            return null;
        } finally {
            if (method != null)
                method.releaseConnection();
            try {
                if (httpClient != null)
                    httpClient.close();
            } catch (IOException e) {
                //slf4j
            }
        }
    }

}

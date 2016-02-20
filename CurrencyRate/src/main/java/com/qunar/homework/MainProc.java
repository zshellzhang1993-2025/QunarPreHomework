package com.qunar.homework;

import com.qunar.homework.contentprovider.ContentProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhangzhi on 16-2-17.
 * 程序入口
 */
public class MainProc {

    public static void main(String[] args) {

        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        //内容提供者
        ContentProvider contentProvider =
                (ContentProvider) applicationContext.getBean("contentProvider");

        contentProvider.retriveDataFromPages();

    }

}

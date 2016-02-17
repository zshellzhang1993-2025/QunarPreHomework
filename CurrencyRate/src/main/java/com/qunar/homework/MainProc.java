package com.qunar.homework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhangzhi on 16-2-17.
 * 主方法
 */
public class MainProc {

    public static void main(String[] args) {

        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");


    }

}

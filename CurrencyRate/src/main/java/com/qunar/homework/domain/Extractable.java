package com.qunar.homework.domain;

/**
 * Created by zhangzhi on 16-2-17.
 * 定义从页面抽取出来的数据的集合：可以被遍历
 */
public interface Extractable<T> extends Iterable<T> {
}

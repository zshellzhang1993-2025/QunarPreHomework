package com.qunar.homework.dao;

import com.qunar.homework.domain.Extractable;

/**
 * Created by zhangzhi on 16-2-18.
 * 定义存储数据的基本行为规范
 */
public interface SaveDao {

    /**
     * 存储数据的行为
     *
     * @param extractable 待存储的实体
     * @return 是否成功存储
     */
    boolean save(Extractable<String> extractable);

}

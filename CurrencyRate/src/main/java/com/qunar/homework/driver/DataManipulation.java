package com.qunar.homework.driver;

import com.qunar.homework.domain.Extractable;

import java.util.List;

/**
 * Created by zhangzhi on 16-2-28.
 * 定义数据操纵的基本行为规范
 */
public abstract class DataManipulation {

    //数据源
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //基本表映射
    protected String table;

    public void setTable(String table) {
        this.table = table;
    }

    /**
     * 基本行为之向某个单元设置/插入值
     *
     * @param primaryKey 主键
     * @param key        待设置/插入的目标字段/键
     * @param value      待设置/插入的值
     * @return 是否操作成功
     */
    public abstract boolean set(Object primaryKey, String key, Object value);

    /**
     * 基本行为之删除一条记录
     *
     * @param primaryKey 主键
     * @return 是否操作成功
     */
    public abstract boolean delete(Object primaryKey);

    /**
     * 基本行为之查找
     *
     * @param sql 待执行的sql语句
     * @return 查找的结果集
     */
    public abstract List<Extractable> find(String sql);

}

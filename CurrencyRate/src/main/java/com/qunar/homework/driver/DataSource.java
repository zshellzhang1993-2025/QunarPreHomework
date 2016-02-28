package com.qunar.homework.driver;

/**
 * Created by zhangzhi on 16-2-27.
 * 定义数据源的基本行为规范
 */
public abstract class DataSource {

    //数据源地址
    protected String dataSourcePath;

    public void setDataSourcePath(String dataSourcePath) {
        this.dataSourcePath = dataSourcePath;
    }

    /**
     * 加载并初始化数据源
     */
    public abstract void initDataSource();


}

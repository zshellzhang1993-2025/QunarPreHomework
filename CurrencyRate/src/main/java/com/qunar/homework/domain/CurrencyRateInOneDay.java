package com.qunar.homework.domain;

import org.joda.time.DateTime;

/**
 * Created by zhangzhi on 16-2-16.
 * 领域模型：某日期的当日汇率
 */
public class CurrencyRateInOneDay {

    //日期
    private DateTime dateTime;

    //对美元汇率
    private String USD;

    //对欧元汇率
    private String EUR;

    //对港币汇率
    private String HKD;

    public CurrencyRateInOneDay(DateTime dateTime, String USD, String EUR, String HKD) {
        this.dateTime = dateTime;
        this.USD = USD;
        this.EUR = EUR;
        this.HKD = HKD;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public String getUSD() {
        return USD;
    }

    public String getEUR() {
        return EUR;
    }

    public String getHKD() {
        return HKD;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setUSD(String USD) {
        this.USD = USD;
    }

    public void setEUR(String EUR) {
        this.EUR = EUR;
    }

    public void setHKD(String HKD) {
        this.HKD = HKD;
    }

}

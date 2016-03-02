package com.qunar.homework.domain.impl;

import com.qunar.homework.domain.Extractable;

import java.util.Iterator;

/**
 * Created by zhangzhi on 16-2-16.
 * 领域模型：某日期下的当日汇率
 */
public class CurrencyRateRecord implements Extractable<String> {

    //日期
    private String date;

    //对美元汇率
    private String USD;

    //对欧元汇率
    private String EUR;

    //对港币汇率
    private String HKD;

    public CurrencyRateRecord(String date, String USD, String EUR, String HKD) {
        this.date = date;
        this.USD = USD;
        this.EUR = EUR;
        this.HKD = HKD;
    }

    @Override
    public Iterator<String> iterator() {
        return new CurrencyIterator();
    }

    private class CurrencyIterator implements Iterator<String> {

        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < 4;
        }

        @Override
        public String next() {
            int current = cursor;
            cursor++;
            switch (current) {
                case 0:
                    return date;
                case 1:
                    return USD;
                case 2:
                    return EUR;
                case 3:
                    return HKD;
                default:
                    return null;
            }
        }
    }

}

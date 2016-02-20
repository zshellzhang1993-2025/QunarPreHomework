package com.qunar.homework.dao.exceldao;

import com.qunar.homework.dao.SaveDao;
import com.qunar.homework.domain.Extractable;

/**
 * Created by zhangzhi on 16-2-20.
 * 适用于以excel作为存储方式的业务
 */
public class ExcelSaveDaoImpl implements SaveDao {

    @Override
    public boolean save(Extractable<String> extractable) {
        return false;
    }

}

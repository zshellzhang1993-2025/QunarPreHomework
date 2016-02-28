package com.qunar.homework.dao.exceldao;

import com.qunar.homework.dao.SaveDao;
import com.qunar.homework.domain.Extractable;
import jxl.Workbook;
import jxl.write.WritableSheet;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by zhangzhi on 16-2-20.
 * 适用于以excel作为数据存储方式的业务
 */
public class ExcelSaveDaoImpl implements SaveDao, InitializingBean {

    //excel文件的路径
    protected String excelFilePath;

    public void setExcelFilePath(String excelFilePath) {
        this.excelFilePath = excelFilePath;
    }

    //excel文件的存储实体
    protected WritableSheet savable;

    /**
     * 初始化excel文件
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //如果文件不存在则创建
        if (Files.exists(Paths.get(excelFilePath)))
            savable = Workbook.createWorkbook(
                    new File(excelFilePath)).createSheet(
                    Paths.get(excelFilePath).getFileName().toString(), 0);
            //否则直接打开文件
        else
            savable = (WritableSheet) Workbook.getWorkbook(new File(excelFilePath)).getSheet(0);
    }

    @Override
    public boolean save(Extractable extractable) {
        return false;
    }

}

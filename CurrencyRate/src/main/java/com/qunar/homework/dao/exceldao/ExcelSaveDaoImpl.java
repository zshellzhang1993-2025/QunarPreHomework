package com.qunar.homework.dao.exceldao;

import com.qunar.homework.dao.SaveDao;
import com.qunar.homework.domain.Extractable;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by zhangzhi on 16-2-20.
 * 适用于以excel作为数据存储方式的业务
 */
public class ExcelSaveDaoImpl implements SaveDao, InitializingBean {

    //错误日志
    protected Logger errorLogger = LogManager.getLogger(ExcelSaveDaoImpl.class);

    //excel文件的路径
    protected String excelFilePath;

    public void setExcelFilePath(String excelFilePath) {
        this.excelFilePath = excelFilePath;
    }

    //excel文件的存储实体
    protected WritableWorkbook workbook;

    //excel文件里的基本表
    protected WritableSheet writableSheet;

    /**
     * 初始化excel文件
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //如果文件不存在则创建
        if (Files.exists(Paths.get(excelFilePath))) {
            workbook = Workbook.createWorkbook(
                    new File(excelFilePath));
            writableSheet = workbook.createSheet(
                    Paths.get(excelFilePath).getFileName().toString(), 0);
            writableSheet.addCell(new Label(0, 0, "日期"));
            writableSheet.addCell(new Label(0, 1, "美元"));
            writableSheet.addCell(new Label(0, 2, "欧元"));
            writableSheet.addCell(new Label(0, 0, "港元"));
        }
    }

    @Override
    public boolean save(Extractable<String> extractable) {
        try {
            Label label;
            int row = writableSheet.getRows();
            int column = 0;
            for (String item : extractable) {
                label = new Label(row, column++, item);
                writableSheet.addCell(label);
            }
            workbook.write();
            return true;
        } catch (WriteException e) {
            //slf4j
            return false;
        } catch (IOException e) {
            //slf4j
            return false;
        }
    }

}

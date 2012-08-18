package com.hanghang.codestore.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * EXCEL的工作表<br>
 * company 深圳市康索特软件有限公司<br>
 * copyright Copyright (c) 2010<br>
 * version 3.0.0.0<br>
 * @author 韦斯多
 */
public class Sheet
{
    /**
     * EXCEL
     */
    private Excel excel;

    /**
     * 工作表
     */
    private HSSFSheet sheet;

    /**
     * 构造方法
     * @param excel EXCEL
     * @param sheet 工作表
     */
    public Sheet(Excel excel, HSSFSheet sheet)
    {
        this.excel = excel;
        this.sheet = sheet;
    }

    /**
     * 获得工作簿
     * @return 工作簿
     */
    HSSFSheet getHSSFSheet()
    {
        return sheet;
    }

    /**
     * 获得EXCEL
     * @return EXCEL
     */
    public Excel getExcel()
    {
        return excel;
    }

    /**
     * 获得被使用的第一行被包含的行数
     * @return 被使用的第一行被包含的行数
     */
    public int getFirstRowNumber()
    {
        return sheet.getFirstRowNum();
    }

    /**
     * 获得被使用的最后一行被包含的行数
     * @return 被使用的最后一行被包含的行数
     */
    public int getLastRowNumber()
    {
        return sheet.getLastRowNum() + 1;
    }

    /**
     * 获得所有被使用的总行数
     * @return 行数
     */
    public int getPhysicalRowNumber()
    {
        return sheet.getPhysicalNumberOfRows();
    }

    /**
     * 获得行
     * @param rowIndex 行定位（从0开始）
     * @return 行
     */
    public Row getRow(int rowIndex)
    {
        HSSFRow row = sheet.getRow(rowIndex);
        return row == null ? null : new Row(this, sheet.getRow(rowIndex));
    }
}

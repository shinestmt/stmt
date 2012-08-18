package com.hanghang.codestore.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * EXCEL的行<br>
 * company 深圳市康索特软件有限公司<br>
 * copyright Copyright (c) 2010<br>
 * version 3.0.0.0<br>
 * @author 韦斯多
 */
public class Row
{
    /**
     * 工作表
     */
    private Sheet sheet;

    /**
     * 行
     */
    private HSSFRow row;

    /**
     * 构造方法
     * @param sheet 工作表
     * @param row 行
     */
    public Row(Sheet sheet, HSSFRow row)
    {
        this.sheet = sheet;
        this.row = row;
    }

    /**
     * 获得行
     * @return 行
     */
    HSSFRow getHSSFRow()
    {
        return row;
    }

    /**
     * 获得工作表
     * @return 工作表
     */
    public Sheet getSheet()
    {
        return sheet;
    }

    /**
     * 获得被使用的第一个单元格被包含的单元格数
     * @return 被使用的第一个单元格被包含的单元格数
     */
    public int getFirstCellNumber()
    {
        return row.getFirstCellNum();
    }

    /**
     * 获得被使用的最后一个单元格被包含的单元格数
     * @return 被使用的最后一个单元格被包含的单元格数
     */
    public int getLastCellNumber()
    {
        return row.getLastCellNum();
    }

    /**
     * 获得被使用的单元格数量
     * @return 被使用的单元格数量
     */
    public int getPhysicalCellNumber()
    {
        return row.getPhysicalNumberOfCells();
    }

    /**
     * 获得单元格
     * @param cellIndex 单元格定位（从0开始）
     * @return 单元格
     */
    public Cell getCell(int cellIndex)
    {
        HSSFCell cell = row.getCell(cellIndex);
        return cell == null ? null : new Cell(this, row.getCell(cellIndex));
    }
}

package com.hanghang.codestore.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * EXCEL����<br>
 * company �����п�����������޹�˾<br>
 * copyright Copyright (c) 2010<br>
 * version 3.0.0.0<br>
 * @author Τ˹��
 */
public class Row
{
    /**
     * ������
     */
    private Sheet sheet;

    /**
     * ��
     */
    private HSSFRow row;

    /**
     * ���췽��
     * @param sheet ������
     * @param row ��
     */
    public Row(Sheet sheet, HSSFRow row)
    {
        this.sheet = sheet;
        this.row = row;
    }

    /**
     * �����
     * @return ��
     */
    HSSFRow getHSSFRow()
    {
        return row;
    }

    /**
     * ��ù�����
     * @return ������
     */
    public Sheet getSheet()
    {
        return sheet;
    }

    /**
     * ��ñ�ʹ�õĵ�һ����Ԫ�񱻰����ĵ�Ԫ����
     * @return ��ʹ�õĵ�һ����Ԫ�񱻰����ĵ�Ԫ����
     */
    public int getFirstCellNumber()
    {
        return row.getFirstCellNum();
    }

    /**
     * ��ñ�ʹ�õ����һ����Ԫ�񱻰����ĵ�Ԫ����
     * @return ��ʹ�õ����һ����Ԫ�񱻰����ĵ�Ԫ����
     */
    public int getLastCellNumber()
    {
        return row.getLastCellNum();
    }

    /**
     * ��ñ�ʹ�õĵ�Ԫ������
     * @return ��ʹ�õĵ�Ԫ������
     */
    public int getPhysicalCellNumber()
    {
        return row.getPhysicalNumberOfCells();
    }

    /**
     * ��õ�Ԫ��
     * @param cellIndex ��Ԫ��λ����0��ʼ��
     * @return ��Ԫ��
     */
    public Cell getCell(int cellIndex)
    {
        HSSFCell cell = row.getCell(cellIndex);
        return cell == null ? null : new Cell(this, row.getCell(cellIndex));
    }
}

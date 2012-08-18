package com.hanghang.codestore.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * EXCEL�Ĺ�����<br>
 * company �����п�����������޹�˾<br>
 * copyright Copyright (c) 2010<br>
 * version 3.0.0.0<br>
 * @author Τ˹��
 */
public class Sheet
{
    /**
     * EXCEL
     */
    private Excel excel;

    /**
     * ������
     */
    private HSSFSheet sheet;

    /**
     * ���췽��
     * @param excel EXCEL
     * @param sheet ������
     */
    public Sheet(Excel excel, HSSFSheet sheet)
    {
        this.excel = excel;
        this.sheet = sheet;
    }

    /**
     * ��ù�����
     * @return ������
     */
    HSSFSheet getHSSFSheet()
    {
        return sheet;
    }

    /**
     * ���EXCEL
     * @return EXCEL
     */
    public Excel getExcel()
    {
        return excel;
    }

    /**
     * ��ñ�ʹ�õĵ�һ�б�����������
     * @return ��ʹ�õĵ�һ�б�����������
     */
    public int getFirstRowNumber()
    {
        return sheet.getFirstRowNum();
    }

    /**
     * ��ñ�ʹ�õ����һ�б�����������
     * @return ��ʹ�õ����һ�б�����������
     */
    public int getLastRowNumber()
    {
        return sheet.getLastRowNum() + 1;
    }

    /**
     * ������б�ʹ�õ�������
     * @return ����
     */
    public int getPhysicalRowNumber()
    {
        return sheet.getPhysicalNumberOfRows();
    }

    /**
     * �����
     * @param rowIndex �ж�λ����0��ʼ��
     * @return ��
     */
    public Row getRow(int rowIndex)
    {
        HSSFRow row = sheet.getRow(rowIndex);
        return row == null ? null : new Row(this, sheet.getRow(rowIndex));
    }
}

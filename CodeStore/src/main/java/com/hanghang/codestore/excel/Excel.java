package com.hanghang.codestore.excel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel������װ��<br>
 * Company�������п�����������޹�˾<br>
 * copyright Copyright (c) 2010<br>
 * version 3.0.0.0<br>
 * @author Τ˹��
 */
public class Excel
{
    /**
     * ͨ�ø�ʽ������
     */
    private static final short FORMAT_GENERAL = 0;

    /**
     * ���θ�ʽ������
     */
    private static final short FORMAT_INT = 1;

    /**
     * ��λС���ĸ�������ʽ������
     */
    private static final short FORMAT_FLOAT2 = 2;

    /**
     * ��λС���İٷֱȸ�ʽ������
     */
    private static final short FORMAT_PERCENT2 = 10;

    /**
     * ������
     */
    private HSSFWorkbook workbook;

    /**
     * ������
     */
    private HSSFSheet sheet;

    /**
     * ������
     */
    private int totalCols;

    /**
     * Ĭ�Ϲ��췽��
     */
    public Excel()
    {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("sheet");
    }
    
    /**
     * �����п�
     * @param columnWidth �п���Ϣ
     */
    public void setColumnWidth(int[] columnWidth){
    	for(int i=0; i<columnWidth.length; i++){
    		if(columnWidth[i]!=0){
    			if(sheet.getColumnWidth((short)i)!=(columnWidth[i]*37.5)){
        			sheet.setColumnWidth((short)i, (short)(columnWidth[i]*37.5));
        		}
    		}
    	}
    }

    /**
     * ������������һ��EXCEL��
     * @param in ������
     * @throws IOException
     */
    public Excel(InputStream in) throws IOException
    {
        workbook = new HSSFWorkbook(in);
    }

    /**
     * ��ù�����
     * @return ������
     */
    HSSFWorkbook getHSSFWorkbook()
    {
        return workbook;
    }

    /**
     * �������ֳ����Զ�������Ԫ����
     */
    private void autoSizeColumns()
    {
        for (int i = 0; i < totalCols; i++)
        {
            sheet.autoSizeColumn((short) i, true);
        }
    }

    /**
     * ��ù��������
     * @return ���������
     */
    public int getSheetNumber()
    {
        return workbook.getNumberOfSheets();
    }

    /**
     * ��ù�����
     * @param sheetIndex ������λ����0��ʼ��
     * @return ������
     */
    public Sheet getSheet(int sheetIndex)
    {
        return new Sheet(this, workbook.getSheetAt(sheetIndex));
    }

    /**
     * ��ù�����
     * @param sheetName ����������
     * @return ������
     */
    public Sheet getSheet(String sheetName)
    {
        return new Sheet(this, workbook.getSheet(sheetName));
    }

    /**
     * ��ù���������
     * @param sheetIndex ������λ����0��ʼ��
     * @return ����������
     */
    public String getSheetName(int sheetIndex)
    {
        return workbook.getSheetName(sheetIndex);
    }
}

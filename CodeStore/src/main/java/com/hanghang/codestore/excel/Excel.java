package com.hanghang.codestore.excel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel操作封装类<br>
 * Company：深圳市康索特软件有限公司<br>
 * copyright Copyright (c) 2010<br>
 * version 3.0.0.0<br>
 * @author 韦斯多
 */
public class Excel
{
    /**
     * 通用格式化参数
     */
    private static final short FORMAT_GENERAL = 0;

    /**
     * 整形格式化参数
     */
    private static final short FORMAT_INT = 1;

    /**
     * 两位小数的浮点数格式化参数
     */
    private static final short FORMAT_FLOAT2 = 2;

    /**
     * 两位小数的百分比格式化参数
     */
    private static final short FORMAT_PERCENT2 = 10;

    /**
     * 工作薄
     */
    private HSSFWorkbook workbook;

    /**
     * 工作表
     */
    private HSSFSheet sheet;

    /**
     * 总列数
     */
    private int totalCols;

    /**
     * 默认构造方法
     */
    public Excel()
    {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("sheet");
    }
    
    /**
     * 设置列宽
     * @param columnWidth 列宽信息
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
     * 从输入流构造一个EXCEL类
     * @param in 输入流
     * @throws IOException
     */
    public Excel(InputStream in) throws IOException
    {
        workbook = new HSSFWorkbook(in);
    }

    /**
     * 获得工作簿
     * @return 工作簿
     */
    HSSFWorkbook getHSSFWorkbook()
    {
        return workbook;
    }

    /**
     * 根据文字长度自动调整单元格宽度
     */
    private void autoSizeColumns()
    {
        for (int i = 0; i < totalCols; i++)
        {
            sheet.autoSizeColumn((short) i, true);
        }
    }

    /**
     * 获得工作表个数
     * @return 工作表个数
     */
    public int getSheetNumber()
    {
        return workbook.getNumberOfSheets();
    }

    /**
     * 获得工作表
     * @param sheetIndex 工作表定位（从0开始）
     * @return 工作表
     */
    public Sheet getSheet(int sheetIndex)
    {
        return new Sheet(this, workbook.getSheetAt(sheetIndex));
    }

    /**
     * 获得工作表
     * @param sheetName 工作表名称
     * @return 工作表
     */
    public Sheet getSheet(String sheetName)
    {
        return new Sheet(this, workbook.getSheet(sheetName));
    }

    /**
     * 获得工作表名称
     * @param sheetIndex 工作表定位（从0开始）
     * @return 工作表名称
     */
    public String getSheetName(int sheetIndex)
    {
        return workbook.getSheetName(sheetIndex);
    }
}

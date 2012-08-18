package com.hanghang.codestore.excel;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.Region;

/**
 * EXCEL的单元格<br>
 */
public class Cell {
	/**
	 * 行
	 */
	private Row row;

	/**
	 * 单元格
	 */
	private HSSFCell cell;

	/**
	 * 构造方法
	 * 
	 * @param row
	 *            行
	 * @param cell
	 *            单元格
	 */
	public Cell(Row row, HSSFCell cell) {
		this.row = row;
		this.cell = cell;
	}
	

	/**
	 * 获得单元格
	 * 
	 * @return 单元格
	 */
	HSSFCell getHSSFCell() {
		return cell;
	}

	/**
	 * 获得行
	 * 
	 * @return 行
	 */
	public Row getRow() {
		return row;
	}

	/**
	 * 获取String值
	 * 
	 * @return String值
	 */
	public String getStringValue() {
		String value = null;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			value = cell.getRichStringCellValue().getString();
			break;
		case 2:
			value = "0";
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			long ls = (long) cell.getNumericCellValue();
			// value = String.valueOf(cell.getNumericCellValue());
			value = String.valueOf(ls);
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			value = "";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		default:
			throw new NumberFormatException("不能获得String值");
		}
		return value;
	}

	/**
	 * 获取int值
	 * 
	 * @return int值
	 */
	public int getIntValue() {
		int value = 0;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			value = Integer.parseInt(cell.getRichStringCellValue().getString());
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			value = (int) cell.getNumericCellValue();
			break;
		default:
			throw new NumberFormatException("不能获得int值");
		}
		return value;
	}

	/**
	 * 获取long值
	 * 
	 * @return long值
	 */
	public long getLongValue() {
		long value = 0;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			value = Long.parseLong(cell.getRichStringCellValue().getString());
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			value = (long) cell.getNumericCellValue();
			break;
		default:
			throw new NumberFormatException("不能获得long值");
		}
		return value;
	}

	/**
	 * 获取float值
	 * 
	 * @return float值
	 */
	public float getFloatValue() {
		float value = 0;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			value = Float.parseFloat(cell.getRichStringCellValue().getString());
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			value = (float) cell.getNumericCellValue();
			break;
		default:
			throw new NumberFormatException("不能获得float值");
		}
		return value;
	}

	/**
	 * 获取double值
	 * 
	 * @return double值
	 */
	public double getDoubleValue() {
		double value = 0;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			value = Double.parseDouble(cell.getRichStringCellValue()
					.getString());
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			value = cell.getNumericCellValue();
			break;
		default:
			throw new NumberFormatException("不能获得double值");
		}
		return value;
	}

	/**
	 * 当前单元格是否是被合并
	 * 
	 * @return true，该单元格被合并
	 */
	public boolean isMarged() {
		HSSFSheet sheet = getRow().getSheet().getHSSFSheet();
		int row = getRow().getHSSFRow().getRowNum();
		int col = cell.getCellNum();
		for (int i = 0, ii = sheet.getNumMergedRegions(); i < ii; i++) {
			Region r = sheet.getMergedRegionAt(i);
			if (r.contains(row, (short) col)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取cell单元格数据为日期格式的数据
	 * 
	 * @return 日期格式的数据
	 */
	public Date getDateCellValue() {
		return cell.getDateCellValue();
	}
}

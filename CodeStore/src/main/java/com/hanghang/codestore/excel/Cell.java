package com.hanghang.codestore.excel;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.Region;

/**
 * EXCEL�ĵ�Ԫ��<br>
 */
public class Cell {
	/**
	 * ��
	 */
	private Row row;

	/**
	 * ��Ԫ��
	 */
	private HSSFCell cell;

	/**
	 * ���췽��
	 * 
	 * @param row
	 *            ��
	 * @param cell
	 *            ��Ԫ��
	 */
	public Cell(Row row, HSSFCell cell) {
		this.row = row;
		this.cell = cell;
	}
	

	/**
	 * ��õ�Ԫ��
	 * 
	 * @return ��Ԫ��
	 */
	HSSFCell getHSSFCell() {
		return cell;
	}

	/**
	 * �����
	 * 
	 * @return ��
	 */
	public Row getRow() {
		return row;
	}

	/**
	 * ��ȡStringֵ
	 * 
	 * @return Stringֵ
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
			throw new NumberFormatException("���ܻ��Stringֵ");
		}
		return value;
	}

	/**
	 * ��ȡintֵ
	 * 
	 * @return intֵ
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
			throw new NumberFormatException("���ܻ��intֵ");
		}
		return value;
	}

	/**
	 * ��ȡlongֵ
	 * 
	 * @return longֵ
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
			throw new NumberFormatException("���ܻ��longֵ");
		}
		return value;
	}

	/**
	 * ��ȡfloatֵ
	 * 
	 * @return floatֵ
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
			throw new NumberFormatException("���ܻ��floatֵ");
		}
		return value;
	}

	/**
	 * ��ȡdoubleֵ
	 * 
	 * @return doubleֵ
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
			throw new NumberFormatException("���ܻ��doubleֵ");
		}
		return value;
	}

	/**
	 * ��ǰ��Ԫ���Ƿ��Ǳ��ϲ�
	 * 
	 * @return true���õ�Ԫ�񱻺ϲ�
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
	 * ��ȡcell��Ԫ������Ϊ���ڸ�ʽ������
	 * 
	 * @return ���ڸ�ʽ������
	 */
	public Date getDateCellValue() {
		return cell.getDateCellValue();
	}
}

package com.hanghang.codestore.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.nutz.json.Json;

public class DataUtil extends TestCase {
		
	/**
	 * 将数组第一列作为Key, 第二列至末尾的数组作为Value
	 * @param arrayList
	 * @return MultiMap(String, List<String[]>)
	 */
	public static Map<String, List<String[]>> castStringListMap(List<String[]> arrayList){
		MultiMap map = new MultiValueMap();
		for (String[] array : arrayList) {
			map.put(array[0], Arrays.copyOfRange(array, 1, array.length));
		}
		return map;
	}
	
	/**
	 * 
	 * @param arrayList
	 * @param transData
	 * @return
	 */
	public static List<String[]> transArrayList(List<String[]> arrayList, String... transData) {
		List<String[]> rsList = new ArrayList<String[]>();
		if(arrayList == null || arrayList.isEmpty()){
			return rsList;
		}
		if(transData == null || transData.length == 0){
			return rsList;
		}
		
		//得到Map(String, List<String[]>)
		Map<String, List<String[]>> strListMap = castStringListMap(arrayList);
		Map<String, Map<String, List<String[]>>> strListMapMap = new HashMap<String, Map<String,List<String[]>>>();
		for (Map.Entry<String, List<String[]>> entry : strListMap.entrySet()) {
			strListMapMap.put(entry.getKey(), castStringListMap(entry.getValue()));
		}
		
		/*
		 * 计算目标子项的长度
		 */
		int srcItemLen = arrayList.get(0).length; //源列表项的长度
		int subItemLen = srcItemLen - 2; //分组后子项的长度: 源列表长度剔除前两列
		int destItemLen = subItemLen * transData.length + 1; //目标项长度: 分组后子项的长度, 乘以分组项数目
		
		/**
		 * 组合旋转结果
		 */
		String[] transItem = null;
		Map<String, List<String[]>> itemMap = null;
		for (Map.Entry<String, Map<String, List<String[]>>> entry : strListMapMap.entrySet()) {
			transItem = new String[destItemLen];
			transItem[0] = entry.getKey();
			
			itemMap = entry.getValue();
			
			for (int i = 0; i < transData.length; i++) {
				if(itemMap.get(transData[i]) == null || itemMap.get(transData[i]).isEmpty()){
					continue;
				}
				System.arraycopy(itemMap.get(transData[i]).get(0), 0, transItem, i*subItemLen+1, subItemLen);
				
			}
			rsList.add(transItem);
		}
		return rsList;

	}
	
	
	/**
	 *
源数据: 
湖北	收入	15000	16000		dataList.add(new String[]{'湖北', '收入', '15000'});
湖北	支出	11000	12000		dataList.add(new String[]{'湖北', '支出', '11000'});
湖北	人数	200	210		dataList.add(new String[]{'湖北', '人数', '200'});
湖南	收入	16000	17000		dataList.add(new String[]{'湖南', '收入', '16000'});
湖南	支出	15000	16000		dataList.add(new String[]{'湖南', '支出', '15000'});
湖南	人数	150	160		dataList.add(new String[]{'湖南', '人数', '150'});

转换结果: 
湖北	15000	16000	11000	12000	200	210
湖南	16000	17000	15000	16000	150	160
	 *
	 */
	public static List<String[]> extendList(List<String[]> dataList, String... extendData) throws Exception {
		if(dataList == null || dataList.isEmpty()){
			Lang.handException("Empty List : dataList");
		}
		if(extendData == null || extendData.length == 0){
			Lang.handException("Empty Array : extendData");
		}
		
		//转换为Map格式: 第一列为key, 相同key的第二列及以后的数组组成List列表
		Map<String, List<String[]>> stringListMap = toStringListMap(dataList);
		
		List<String[]> rsList = new ArrayList<String[]>();
		int length = getLength(dataList);
		int itemLength = length - 2; 
		length = 1 + extendData.length * itemLength;
		String[] row = null;
		for (Map.Entry<String, List<String[]>> entry : stringListMap.entrySet()) {
			row = new String[length];
			row[0] = entry.getKey();
			List<String[]> valueList = entry.getValue();
			for (int i = 0, j = extendData.length; i < j; i++) {
				for (String[] item : valueList) {
					if(extendData[i].equals(item[0])){
						System.arraycopy(item, 1, row, i*itemLength+1, item.length-1);
					}
				}
			}
			rsList.add(row);
		}
		return rsList;
	}
	
	private static int getLength(List<String[]> dataList){
		if(dataList == null || dataList.isEmpty()){
			return -1;
		}
		return dataList.get(0).length;
	}
	
	private static Map<String, List<String[]>> toStringListMap(List<String[]> dataList) throws Exception{
		MultiMap rsMap = new MultiValueMap();
		for (String[] item : dataList) {
			rsMap.put(item[0], Arrays.copyOfRange(item, 1, item.length));
		}
		if(rsMap.size() == 0){
			return rsMap;
		}
		
		
		
		int length = getLength(dataList);
		
		Map<String, List<String[]>> stringListMap = new HashMap<String, List<String[]>>();
		List<String[]> arrayList = null;
		for (String[] item : dataList) {
			if(stringListMap.containsKey(item[0])){
				arrayList = stringListMap.get(item[0]);
				arrayList.add(Arrays.copyOfRange(item, 1, length));
			}else{
				arrayList = new ArrayList<String[]>();
				arrayList.add(Arrays.copyOfRange(item, 1, length));
				stringListMap.put(item[0], arrayList);
			}
		}
		return stringListMap;
	}
	
	public static void testTrans(List<String[]> arrayList) throws Exception {
		for (int i = 0; i < 100000; i++) {
			transArrayList(arrayList, "收入", "支出", "人数");
		}
	}
	
	public static void testExtend(List<String[]> arrayList) throws Exception {
		for (int i = 0; i < 100000; i++) {
			extendList(arrayList, "收入", "支出", "人数");
		}
	}
	
	public static void main(String[] args) {
		List<String[]> dataList = new ArrayList<String[]>();
		dataList.add(new String[]{"湖北", "收入", "15000", "16000"});
		dataList.add(new String[]{"湖北", "支出", "11000", "12000"});
		dataList.add(new String[]{"湖北", "人数", "200", "210"});
		dataList.add(new String[]{"湖南", "收入", "16000", "17000"});
		dataList.add(new String[]{"湖南", "支出", "15000", "16000"});
		dataList.add(new String[]{"湖南", "人数", "150", "160"});
		
		try {
			long start, end;
			
			
			
			start = System.currentTimeMillis();
			testTrans(dataList);
			end = System.currentTimeMillis();
			System.out.println(end - start);
			
			
			start = System.currentTimeMillis();
			testExtend(dataList);
			end = System.currentTimeMillis();
			System.out.println(end - start);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

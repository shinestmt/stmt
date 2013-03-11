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
	 * �������һ����ΪKey, �ڶ�����ĩβ��������ΪValue
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
		
		//�õ�Map(String, List<String[]>)
		Map<String, List<String[]>> strListMap = castStringListMap(arrayList);
		Map<String, Map<String, List<String[]>>> strListMapMap = new HashMap<String, Map<String,List<String[]>>>();
		for (Map.Entry<String, List<String[]>> entry : strListMap.entrySet()) {
			strListMapMap.put(entry.getKey(), castStringListMap(entry.getValue()));
		}
		
		/*
		 * ����Ŀ������ĳ���
		 */
		int srcItemLen = arrayList.get(0).length; //Դ�б���ĳ���
		int subItemLen = srcItemLen - 2; //���������ĳ���: Դ�б����޳�ǰ����
		int destItemLen = subItemLen * transData.length + 1; //Ŀ�����: ���������ĳ���, ���Է�������Ŀ
		
		/**
		 * �����ת���
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
Դ����: 
����	����	15000	16000		dataList.add(new String[]{'����', '����', '15000'});
����	֧��	11000	12000		dataList.add(new String[]{'����', '֧��', '11000'});
����	����	200	210		dataList.add(new String[]{'����', '����', '200'});
����	����	16000	17000		dataList.add(new String[]{'����', '����', '16000'});
����	֧��	15000	16000		dataList.add(new String[]{'����', '֧��', '15000'});
����	����	150	160		dataList.add(new String[]{'����', '����', '150'});

ת�����: 
����	15000	16000	11000	12000	200	210
����	16000	17000	15000	16000	150	160
	 *
	 */
	public static List<String[]> extendList(List<String[]> dataList, String... extendData) throws Exception {
		if(dataList == null || dataList.isEmpty()){
			Lang.handException("Empty List : dataList");
		}
		if(extendData == null || extendData.length == 0){
			Lang.handException("Empty Array : extendData");
		}
		
		//ת��ΪMap��ʽ: ��һ��Ϊkey, ��ͬkey�ĵڶ��м��Ժ���������List�б�
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
			transArrayList(arrayList, "����", "֧��", "����");
		}
	}
	
	public static void testExtend(List<String[]> arrayList) throws Exception {
		for (int i = 0; i < 100000; i++) {
			extendList(arrayList, "����", "֧��", "����");
		}
	}
	
	public static void main(String[] args) {
		List<String[]> dataList = new ArrayList<String[]>();
		dataList.add(new String[]{"����", "����", "15000", "16000"});
		dataList.add(new String[]{"����", "֧��", "11000", "12000"});
		dataList.add(new String[]{"����", "����", "200", "210"});
		dataList.add(new String[]{"����", "����", "16000", "17000"});
		dataList.add(new String[]{"����", "֧��", "15000", "16000"});
		dataList.add(new String[]{"����", "����", "150", "160"});
		
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

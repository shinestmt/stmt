package com.hanghang.codestore.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.nutz.json.Json;

import com.hanghang.codestore.util.print.Printer;

public class DataUtil {
	
	public static void testname() throws Exception {
		MultiMap map = new MultiHashMap();
		map.put("a", new String[]{"123", "1"});
		map.put("a", new String[]{"456", "4"});
		System.out.println(Json.toJson(map.get("a")));
		
		
		
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
		MultiMap rsMap = new MultiHashMap();
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
		System.out.println(Json.toJson(stringListMap));
		return stringListMap;
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
			List<String[]> rsList = extendList(dataList, "����", "֧��", "����");
			Printer.print(rsList);
			
			String[] array = rsList.get(0);
			String[] newArray = Arrays.copyOfRange(array, 1, array.length-2);
			System.out.println(Arrays.toString(newArray));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

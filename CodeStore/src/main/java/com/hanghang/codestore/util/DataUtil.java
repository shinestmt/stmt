package com.hanghang.codestore.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.json.Json;

import com.hanghang.codestore.util.print.Printer;

public class DataUtil {
	
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
		int length = getLength(dataList);
		
		Map<String, List<String[]>> stringListMap = new HashMap<String, List<String[]>>();
		List<String[]> arrayList = null;
		for (String[] item : dataList) {
			if(stringListMap.containsKey(item[0])){
				arrayList = stringListMap.get(item[0]);
				arrayList.add(Lang.getArray(item, 1, length));
			}else{
				arrayList = new ArrayList<String[]>();
				arrayList.add(Lang.getArray(item, 1, length));
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

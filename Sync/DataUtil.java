package com.zjcy.point.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtil {
	
	public static Map<String, List<String[]>> transArray(String[][] array, String[] transData) {
		if(array == null || array[0].length == 0){
			return null;
		}
		
		int itemLen = array[0].length-2;
		List<String[]> list = null;
		String[] item = null;
		Map<String, List<String[]>> map = new HashMap<String, List<String[]>>();
		for (int i=0; i<array.length; i++) {
			if(map.containsKey(array[i])){
				list = map.get(array[i]);
			} else {
				list = new ArrayList<String[]>();
			}
			item = new String[itemLen];
			System.arraycopy(array[i], 2, item, 0, item.length);
			list.add(item);
		}
		return map;
	}

}

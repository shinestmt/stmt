package com.hanghang.codestore.algo.sort.impl.exchange;

import com.hanghang.codestore.algo.sort.Sortable;

/**
 * 改进冒泡<br>
 * 记录已排好序的部分, 下次直接跳过
 */
public class Bubble2Sort implements Sortable {

	@Override
	public int[] sort(int[] array) {

		int index = array.length;
		boolean isExchange = true;
		
		while (index >= 0 && isExchange) {
			isExchange = false;
			for (int i = 0; i <= index-2; i++) {
				if(array[i] > array[i+1]){
					int tmp = array[i];
					array[i] = array[i+1];
					array[i+1] = tmp;
					isExchange = true;
				}
			}
			index--;
		}
		return array;
	}

}

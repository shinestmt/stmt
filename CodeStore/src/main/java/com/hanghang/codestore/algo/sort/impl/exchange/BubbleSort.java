package com.hanghang.codestore.algo.sort.impl.exchange;

import com.hanghang.codestore.algo.sort.Sortable;

/**
 * Ã°ÅİÅÅĞò
 */
public class BubbleSort implements Sortable {

	@Override
	public int[] sort(int[] array) {
		int index = array.length;
		
		while (index >= 0) {
			for (int i = 0; i <= index-2; i++) {
				if(array[i] > array[i+1]){
					int tmp = array[i];
					array[i] = array[i+1];
					array[i+1] = tmp;
				}
			}
			index--;
		}
		return array;
	}

}

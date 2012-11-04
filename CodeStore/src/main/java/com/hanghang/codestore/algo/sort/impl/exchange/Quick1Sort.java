package com.hanghang.codestore.algo.sort.impl.exchange;

import com.hanghang.codestore.algo.sort.Sortable;

/**
 * ¿ìËÙÅÅÐò£¨quick sort£©Ëã·¨
 * 
 */
public class Quick1Sort implements Sortable {

	@Override
	public int[] sort(int[] array) {
		sort(array, 1, 3);
		return null;
	}
	
	private void sort(int[] array, int low, int high) {
		if(low >= high){
			return;
		}
		int pivot = array.length / 2;
		
		
	}

}

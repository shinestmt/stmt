package com.hanghang.codestore.algo.sort.impl.exchange;

import com.hanghang.codestore.algo.sort.Sortable;

/**
 * 鸡尾酒排序（cocktai sort）算法
 */
public class CocktailSort implements Sortable {

	@Override
	public int[] sort(int[] array) {
		
		int start = 0, end = array.length-1;
		boolean flag = true;
		
		while (flag) {
			flag = false;
			
			/*
			 * 从小到大
			 */
			for (int i = start; i < end; i++) {
				if(array[i] > array[i+1]){
					swap(array, i, i+1);
					flag = true;
				}
			}
			start++;
			
			/*
			 * 从大到小
			 */
			for (int i = end; i > start-1; i--) {
				if(array[i-1] > array[i]){
					swap(array, i-1, i);
					flag = true;
				}
			}
			end--;
			
		}
		return array;
	}

	private void swap(int[] array, int i, int j){
		if(array[i] > array[j]){
			int tmp = array[i];
			array[i] = array[j];
			array[j] = tmp;
		}
	}
	
}

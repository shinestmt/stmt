package com.hanghang.codestore.algo.sort.impl.exchange;

import com.hanghang.codestore.algo.sort.Sortable;

/**
 * ��β������cocktai sort���㷨
 */
public class CocktailSort implements Sortable {

	@Override
	public int[] sort(int[] array) {
		
		int start = 0, end = array.length-1;
		boolean flag = true;
		
		while (flag) {
			flag = false;
			
			/*
			 * ��С����
			 */
			for (int i = start; i < end; i++) {
				if(array[i] > array[i+1]){
					swap(array, i, i+1);
					flag = true;
				}
			}
			start++;
			
			/*
			 * �Ӵ�С
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

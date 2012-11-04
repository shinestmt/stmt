package com.hanghang.codestore.algo.sort.impl.exchange;

import com.hanghang.codestore.algo.sort.Sortable;

/**
 * 改进冒泡<br>
 * 排序过程中交替改变扫描方向，可改进不对称性
 */
public class Bubble3Sort implements Sortable {

	int count = 0;
	
	@Override
	public int[] sort(int[] arr) {

		int high = arr.length - 1, low = 0, j;
		int temp;
		int index = low;
		while (high > low) {
			for (j = low; j < high; j++) {
				if (arr[j] > arr[j + 1]) {
					count++;
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
					index = j;
				}
			}
			high = index;
			for (j = high; j > low; j--) {
				if (arr[j] > arr[j - 1]) {
					count++;
					temp = arr[j];
					arr[j] = arr[j - 1];
					arr[j - 1] = temp;
					index = j;
				}
			}
			low = index;
		}
		System.out.println(count);
		return arr;
	}

}

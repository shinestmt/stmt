package com.hanghang.codestore.algo.sort.impl.exchange;

import com.hanghang.codestore.algo.sort.Sortable;
import com.hanghang.codestore.util.print.Printer;

/**
 * 快速排序（quick sort）算法
 * 
 */
public class Quick1Sort implements Sortable {

	@Override
	public int[] sort(int[] array) {
		sort(array, 0, array.length - 1);
		return array;
	}

	private void sort(int[] array, int low, int high) {
		if (low >= high) {
			return;
		}
		int mid = partition(array, low, high);
		// 对枢纽关键字左边的分区进行分区
		sort(array, low, mid - 1);
		// 对枢纽关键字右边的分区进行分区
		sort(array, mid + 1, high);

	}

	/**
	 * 挖坑填数，即具体实现分区的方法，每次的结果是将数组分为比枢纽关键字小的在左边，比枢纽关键字大的在右边
	 * 
	 * @param array 要排序的数组
	 * @param low
	 * @param high
	 * @return 执行完分区后low的坐标值，用于下次递归的时候分区用
	 */
	public int partition(int array[], int low, int high) {
		// 将数组中第一个元素作为枢纽关键字，这个关键字将在本次分区过程中不变
		int pivotKey = array[low];
//		System.out.println(pivotKey);
		int i = low, j = high;

		if (low < high) {
			while (i < j) {
				// 从后向前扫描，如果array[j]>=pivotKey，则下表j向前移动
				while (i < j && array[j] >= pivotKey) {
					j--;
				}
				// array[j]<pivotKey，则将array[j]挖出来填入array[i]，即刚才被pivotKey挖走的地方
				if (i < j) {
					array[i] = array[j];
					i++;
				}

				// 如果array[i]<=pivotKey，则下表i向后移动
				while (i < j && array[i] <= pivotKey) {
					i++;
				}
				// arry[i]>pivotKey，将array[i]挖出来填入刚才被挖的array[j]
				if (i < j) {
					array[j] = array[i];
					j--;
				}
			}
			// 如果到最后i=j的时候，也就是扫描完整个数组，则将枢纽关键字填入剩下的那个被挖的坑array[i]
			array[i] = pivotKey;

		}
		// 打印每次分区后的结果
//		Printer.print(array);
		// 将这个分区结束时的坐标i返回，用于下次执行时当做前分区的尾坐标，当做后分区的头坐标
		return i;
	}

}

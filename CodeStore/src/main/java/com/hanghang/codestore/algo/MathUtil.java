package com.hanghang.codestore.algo;

import java.util.Arrays;

import org.junit.Test;

import com.hanghang.codestore.util.print.Printer;

public class MathUtil {

	/**
	 * 最大公约数
	 * @param a
	 * @param b
	 * @return
	 */
	public int gcd(int a, int b) {
		int max = Math.max(a, b);
		int min = Math.min(a, b);
		int tmp = max % min;
		return tmp==0 ? min : gcd(min, tmp);
	}
	
	/**
	 * 最小公倍数
	 * @param a
	 * @param b
	 * @return
	 */
	public int lcm(int a, int b) {
		return a * b / gcd(a, b);
	}
	
	/**
	 * 指定范围的随机数(包含起始,不包含结尾)
	 * @param min 最小值
	 * @param max 最大值
	 * @return 
	 */
	public int random(int min, int max) {
		double random = Math.random();
		double result = random * (max-min) + min;
		return (int)Math.floor(result);
	}
	
	/**
	* 二分查找算法
	* @param srcArray 有序数组
	* @param des 查找元素
	* @return des的数组下标，没找到返回-1
	*/
	public int binarySearch(int[] srcArray, int des) {
		int low = 0;
		int high = srcArray.length - 1;
		while (low <= high) {
			int middle = (low + high) / 2;
			if (des == srcArray[middle]) {
				return middle;
			} else if (des < srcArray[middle]) {
				high = middle - 1;
			} else {
				low = middle + 1;
			}
		}
		return -1;
	}
	
	@Test
	public void testGCD() throws Exception {
		System.out.println(gcd(15, 12));
	}
	
	@Test
	public void testLCM() throws Exception {
		System.out.println(lcm(15, 12));
	}
	
	@Test
	public void testRandom() throws Exception {
		int[] counts = {0, 0, 0, 0, 0};
		int count = 200000;
		for (int i = 0; i < count; i++) {
			int random = random(95, 100);
			switch (random) {
			case 95:
				counts[0]++;
				break;
			case 96:
				counts[1]++;
				break;
			case 97:
				counts[2]++;
				break;
			case 98:
				counts[3]++;
				break;
			case 99:
				counts[4]++;
				break;
			}
		}
		for (int i : counts) {
			System.out.println(i + " ---- " + i*1d/count*100);
		}
	}
	
	@Test
	public void testBinary() throws Exception {
		int[] test = {1,6,3,6,2,9,3,5};
		Arrays.sort(test);
		Printer.print(test);
		System.out.println(binarySearch(test, 5));
	}
	
}

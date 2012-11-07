package com.hanghang.codestore.algo.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.hanghang.codestore.algo.sort.impl.exchange.Bubble3Sort;
import com.hanghang.codestore.algo.sort.impl.exchange.BubbleSort;
import com.hanghang.codestore.algo.sort.impl.exchange.Bubble2Sort;
import com.hanghang.codestore.algo.sort.impl.exchange.CocktailSort;
import com.hanghang.codestore.algo.sort.impl.exchange.Quick1Sort;
import com.hanghang.codestore.util.JsonUtil;
import com.hanghang.codestore.util.Stopwatch;
import com.hanghang.codestore.util.print.Printer;

import junit.framework.TestCase;

public class SortTest extends TestCase {

	Sortable bean = null;
	
//	int[] array = {8, 7, 6, 5, 4, 3, 2, 9};
	int[] array = {9, 5, 12, 1, 14, 10};
	
	private int[] randomArray(int length, int start, int end){
		Random random = new Random();
		
		int[] array = new int[length];
		for (int i = 0; i < length; i++) {
			array[i] = random.nextInt(end-start+1)+start;
		}
		
		return array;
	}
	
	private List<int[]> randomArrayList(int size, int length, int start, int end){
		List<int[]> arrayList = new ArrayList<int[]>();
		
		for (int i = 0; i < size; i++) {
			arrayList.add(randomArray(length, start, end));
		}
		
		return arrayList;
	}
	
	public void testBubbleSort() throws Exception {
		bean = new BubbleSort();
		Printer.print(array);
		array = bean.sort(array);
		Printer.print(array);
	}
	
	public void testBubble2Sort() throws Exception {
		bean = new Bubble2Sort();
		Printer.print(array);
		array = bean.sort(array);
		Printer.print(array);
	}
	
	public void testBubble3Sort() throws Exception {
		bean = new Bubble3Sort();
		Printer.print(array);
		array = bean.sort(array);
		Printer.print(array);
	}
	
	public void testCocktailSort() throws Exception {
		bean = new CocktailSort();
		Printer.print(array);
		array = bean.sort(array);
		Printer.print(array);
	}
	
	public void testQuick1Sort() throws Exception {
		bean = new Quick1Sort();
		Printer.print(array);
		array = bean.sort(array);
		Printer.print(array);
	}
	
	
	public void testRandom() throws Exception {
		
		Stopwatch sw = Stopwatch.create(true);
		
		List<int[]> arrayList = randomArrayList(10, 200, 1, 1000);
		
		test(new BubbleSort(), arrayList, sw);
		test(new Bubble2Sort(), arrayList, sw);
		test(new Bubble3Sort(), arrayList, sw);
		test(new CocktailSort(), arrayList, sw);
		test(new Quick1Sort(), arrayList, sw);
		
	}
	
	private void test(Sortable bean, List<int[]> arrayList, Stopwatch sw) throws InterruptedException{
		sw.start();
		for (int[] is : arrayList) {
			bean.sort(is);
		}
		sw.stop();
		System.out.printf("%s\t%s\n", bean.getClass().getSimpleName(), sw.getDuration());
	}
}

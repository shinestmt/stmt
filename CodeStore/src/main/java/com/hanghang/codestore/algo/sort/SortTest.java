package com.hanghang.codestore.algo.sort;

import com.hanghang.codestore.algo.sort.impl.exchange.Bubble3Sort;
import com.hanghang.codestore.algo.sort.impl.exchange.BubbleSort;
import com.hanghang.codestore.algo.sort.impl.exchange.Bubble2Sort;
import com.hanghang.codestore.algo.sort.impl.exchange.CocktailSort;
import com.hanghang.codestore.util.print.Printer;

import junit.framework.TestCase;

public class SortTest extends TestCase {

	Sortable bean = null;
	
	int[] array = {8, 7, 6, 5, 4, 3, 2, 9};
	
	
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
	
}

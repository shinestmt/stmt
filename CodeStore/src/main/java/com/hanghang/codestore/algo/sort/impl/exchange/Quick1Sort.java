package com.hanghang.codestore.algo.sort.impl.exchange;

import com.hanghang.codestore.algo.sort.Sortable;
import com.hanghang.codestore.util.print.Printer;

/**
 * ��������quick sort���㷨
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
		// ����Ŧ�ؼ�����ߵķ������з���
		sort(array, low, mid - 1);
		// ����Ŧ�ؼ����ұߵķ������з���
		sort(array, mid + 1, high);

	}

	/**
	 * �ڿ�������������ʵ�ַ����ķ�����ÿ�εĽ���ǽ������Ϊ����Ŧ�ؼ���С������ߣ�����Ŧ�ؼ��ִ�����ұ�
	 * 
	 * @param array Ҫ���������
	 * @param low
	 * @param high
	 * @return ִ���������low������ֵ�������´εݹ��ʱ�������
	 */
	public int partition(int array[], int low, int high) {
		// �������е�һ��Ԫ����Ϊ��Ŧ�ؼ��֣�����ؼ��ֽ��ڱ��η��������в���
		int pivotKey = array[low];
//		System.out.println(pivotKey);
		int i = low, j = high;

		if (low < high) {
			while (i < j) {
				// �Ӻ���ǰɨ�裬���array[j]>=pivotKey�����±�j��ǰ�ƶ�
				while (i < j && array[j] >= pivotKey) {
					j--;
				}
				// array[j]<pivotKey����array[j]�ڳ�������array[i]�����ղű�pivotKey���ߵĵط�
				if (i < j) {
					array[i] = array[j];
					i++;
				}

				// ���array[i]<=pivotKey�����±�i����ƶ�
				while (i < j && array[i] <= pivotKey) {
					i++;
				}
				// arry[i]>pivotKey����array[i]�ڳ�������ղű��ڵ�array[j]
				if (i < j) {
					array[j] = array[i];
					j--;
				}
			}
			// ��������i=j��ʱ��Ҳ����ɨ�����������飬����Ŧ�ؼ�������ʣ�µ��Ǹ����ڵĿ�array[i]
			array[i] = pivotKey;

		}
		// ��ӡÿ�η�����Ľ��
//		Printer.print(array);
		// �������������ʱ������i���أ������´�ִ��ʱ����ǰ������β���꣬�����������ͷ����
		return i;
	}

}

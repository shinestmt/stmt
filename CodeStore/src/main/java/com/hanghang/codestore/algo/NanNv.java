package com.hanghang.codestore.algo;

import java.util.Random;

public class NanNv {

	public static void main(String[] args) {
		Random r = new Random();
		int t = 0;// ������Ů���
		int nan = 0;// �к���
		int nv = 0;// Ů����

		for (int i = 0; i < 100000; i++) {// i�Ǽ�ͥ��
			do {
				t = r.nextInt(2);
				if (t == 0) {
					nv++;// t=0��ʾ��Ů��
				} else {
					nan++;// t=1��ʾ���к�
				}
			} while (t == 0);// t=0��Ů�����������
		}
		System.out.println("Ů��������" + nv);
		System.out.println("�к�������" + nan);
		System.out.println("Ů�����к�����ֵ��" + nv / (float) nan);
	}

}

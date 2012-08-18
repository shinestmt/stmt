package com.hanghang.codestore.algo;

import java.util.Random;

public class NanNv {

	public static void main(String[] args) {
		Random r = new Random();
		int t = 0;// 生男生女标记
		int nan = 0;// 男孩数
		int nv = 0;// 女孩数

		for (int i = 0; i < 100000; i++) {// i是家庭数
			do {
				t = r.nextInt(2);
				if (t == 0) {
					nv++;// t=0表示是女孩
				} else {
					nan++;// t=1表示是男孩
				}
			} while (t == 0);// t=0生女孩后继续再生
		}
		System.out.println("女孩个数：" + nv);
		System.out.println("男孩个数：" + nan);
		System.out.println("女孩与男孩数比值：" + nv / (float) nan);
	}

}

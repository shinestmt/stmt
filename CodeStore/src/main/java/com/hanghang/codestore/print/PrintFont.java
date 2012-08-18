package com.hanghang.codestore.print;

import java.awt.Font;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;

public class PrintFont {

	public static void main(String[] args) {
		try {
			Font font = new Font("����", Font.PLAIN, 30);
			AffineTransform at = new AffineTransform();
			FontRenderContext frc = new FontRenderContext(at, true, true);
			GlyphVector gv = font.createGlyphVector(frc, "����"); // Ҫ��ʾ������
			Shape shape = gv.getOutline(5, 30);
			int weith = 200;
			int height = 40;
			boolean[][] view = new boolean[weith][height];
			for (int i = 0; i < weith; i++) {
				for (int j = 0; j < height; j++) {
					if (shape.contains(i, j)) {
						view[i][j] = true;
					} else {
						view[i][j] = false;
					}
				}
			}

			for (int j = 0; j < height; j++) {
				for (int i = 0; i < weith; i++) {
					if (view[i][j]) {
						System.out.print("@");// �滻����ϲ����ͼ��
					} else {
						System.out.print("*");
					}
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
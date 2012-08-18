package com.hanghang.codestore.jtpl;

import java.io.File;
import java.net.URL;

public class Test {
	
	public static void main(String[] args) throws Exception {
		
		URL url = Test.class.getResource("/sample1.jtpl");
		System.out.println(url);
		Template tmpl = new Template(url.getPath());
		tmpl.assign("TITLE", "����").assign("MESSAGE", "��Ϣ");
		System.out.println(tmpl.out());
		
		
	}

}

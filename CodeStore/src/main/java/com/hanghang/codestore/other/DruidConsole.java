package com.hanghang.codestore.other;

import java.io.Console;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class DruidConsole {

	private Scanner console;

	private PrintStream out;

	private DruidConsole() {
		console = new Scanner(System.in).useDelimiter("\n");
		out = System.out;
	}

	public void run() throws Exception {
		for (;;) {
			String line = console.next();
			if ("exit".equals(line)) {
				break;
			}

			if ("ls".equals(line)) {
				ls(line);
			}

			if ("desc".equals(line)) {
				desc(line);
			}

			if (line.startsWith("show ")) {
				show(line);
			}
		}
	}

	public void show(String line) throws Exception {
		String[] items = line.split(" ");

		long interval = 0;
		{
			String tmp = items[2];
			interval = Long.parseLong(tmp);
		}

		String[] attributes = new String[] { "CreateCount", "DestroyCount",
				"ActivePeak", "ActiveCount", "PoolingCount", "CommitCount",
				"RollbackCount" };
		String[] titles = attributes;

		for (int i = 0; i < titles.length; ++i) {
			if (i != 0) {
				out.print("\t\t");
			}
			out.print(titles[i]);
		}
		out.println();
		Thread.sleep(interval);
	}

	public void ls(String line) throws Exception {
		out.print("line Content");
	}

	public void desc(String line) throws Exception {
		String arg = line.substring("desc ".length());
		int index = Integer.parseInt(arg);
		out.print("arg is: " + index);
	}

	public static void main(String[] args) throws Exception {
		DruidConsole console = new DruidConsole();
		console.run();
		
		

	}
}
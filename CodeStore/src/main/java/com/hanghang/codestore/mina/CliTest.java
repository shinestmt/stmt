package com.hanghang.codestore.mina;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Parser;
import org.apache.commons.cli.PosixParser;
import org.junit.Test;

import com.hanghang.codestore.mina.cmd.Commander;
import com.hanghang.codestore.mina.cmd.impl.StatCommand;

public class CliTest {

	public static void main1(String[] args) {

		String str = "ls -l";
		int index = str.indexOf(' ');
		if (index < 0) {
			index = str.length();
		}
		System.out.println(index);
		System.out.println(str.substring(0, index));
		Commander cmd = new StatCommand();

		Options options = new Options();
		options.addOption("ls", "list", true, "返回列表");
		options.addOption("help", "", false, "帮助信息");

		try {
			Parser parse = new PosixParser();
			CommandLine cmdLine = parse.parse(options, new String[] { "l" });
			System.out.println(cmdLine.hasOption("-l"));

			HelpFormatter format = new HelpFormatter();
			format.printHelp("ls", options, true);

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		
		System.out.println(Arrays.toString(args));
		
		Options options = new Options();
		options.addOption("t", false, "display current time");// 参数不可用
		options.addOption("c", true, "country code");// 参数可用

		try {
			CommandLineParser parser = new GnuParser();
			CommandLine cmd = parser.parse(options, args);

			if (cmd.hasOption("c")) {
				String countryCode = cmd.getOptionValue("c");
				System.out.println(countryCode);
			}

			if (cmd.hasOption("t")) {
				String countryCode = cmd.getOptionValue("t");
				System.out.println(countryCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

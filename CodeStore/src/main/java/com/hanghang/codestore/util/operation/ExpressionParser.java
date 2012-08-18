package com.hanghang.codestore.util.operation;

import java.util.HashMap;
import java.util.Map;

public class ExpressionParser {
	
	// 4种标记类型
	/** 标记为空或者结束符 */
	public static final int NONE_TOKEN = 0;

	/** 标记为分隔符 */
	public static final int DELIMITER_TOKEN = 1;

	/** 标记为变量 */
	public static final int VARIABLE_TOKEN = 2;

	/** 标记为数字 */
	public static final int NUMBER_TOKEN = 3;

	// 4种错误类型
	/** 语法错误 */
	public static final int SYNTAX_ERROR = 0;

	/** 括号未结束错误 */
	public static final int UNBALPARENS_ERROR = 1;

	/** 表达式为空错误 */
	public static final int NOEXP_ERROR = 2;

	/** 除数为0错误 */
	public static final int DIVBYZERO_ERROR = 3;

	/** 针对4种错误类型定义的4个错误提示 */
	public static final String[] ERROR_MESSAGES = { 
		"Syntax Error", 
		"Unbalanced Parentheses", 
		"No Expression Present", 
		"Division by Zero" 
	};

	/** 表达式的结束标记 */
	public static final String EOE = "\0";

	/** 表达式字符串 */
	private String exp; 
	/** 解析器当前指针在表达式中的位置 */
	private int expIndex; 
	/** 解析器当前处理的标记 */
	private String token; 
	/** 解析器当前处理的标记类型 */
	private int tokenType; 
	/** 变量数组 */
//	private double[] vars = new double[26]; 
	
	private Map<String, Double> vars = new HashMap<String, Double>();

	/**
	 * 表达式解析器
	 */
	public ExpressionParser() {
	}
	
	/**
	 * 带有变量的表达式解析器
	 * @param context 变量数组,例如: <br>
	 * [ PI=3.14159, v=2.99792*10^8 ]
	 * @throws Exception
	 */
	public ExpressionParser(String... vars) throws Exception {
		String[] temp;
		for (int i = 0; i < vars.length; i++) {
			temp = vars[i].split("=");
			this.vars.put(temp[0].trim(), Double.parseDouble(this.evaluate(temp[1])));
		}
	}
	
	/**
	 * 带有变量的表达式解析器
	 * @param context 变量内容(多个参数用逗号[,]相隔),例如: <br>
	 * PI=3.14159,V=2.99792*10^8
	 * @throws Exception
	 */
	public ExpressionParser(String varContext) throws Exception {
		this(varContext.split(","));
	}

	/**
	 * 解析一个表达式，返回表达式的值
	 */
	public String evaluate(String expStr) throws Exception {
		double result;
		this.exp = expStr;
		this.expIndex = 0;
		
		// 获取第一个标记
		this.getToken();
		if (this.token.equals(EOE)) {
			// 没有表达式异常
			this.handleError(NOEXP_ERROR);
		}

		result = this.parseAddOrSub();
		// 处理完赋值语句，应该就是表达式结束符，如果不是，则返回异常
		if (!this.token.equals(EOE)) {
			this.handleError(SYNTAX_ERROR);
		}
		return String.valueOf(result);
	}

	/**
	 * 计算加减法表达式
	 */
	private double parseAddOrSub() throws Exception {
		char op; // 运算符
		double result; // 结果
		double partialResult; // 子表达式的结果

		result = this.parseMulOrDiv(); // 用乘除法计算当前表达式的值
		// 如果当前标记的第一个字母是加减号，则继续进行加减运算
		while ((op = this.token.charAt(0)) == '+' || op == '-') {
			this.getToken(); // 取下一个标记
			// 用乘除法计算当前子表达式的值
			partialResult = this.parseMulOrDiv();
			switch (op) {
			case '-':
				// 如果是减法，则用已处理的子表达式的值减去当前子表达式的值
				result = result - partialResult;
				break;
			case '+':
				// 如果是加法，用已处理的子表达式的值加上当前子表达式的值
				result = result + partialResult;
				break;
			}
		}
		return result;
	}

	/**
	 * 计算乘除法表达式，包括取模运算
	 */
	private double parseMulOrDiv() throws Exception {
		char op; // 运算符
		double result; // 结果
		double partialResult; // 子表达式结果
		// 用指数运算计算当前子表达式的值
		result = this.parseExponent();
		// 如果当前标记的第一个字母是乘、除或者取模运算，则继续进行乘除法运算
		while ((op = this.token.charAt(0)) == '*' || op == '/' || op == '%') {
			this.getToken(); // 取下一标记
			// 用指数运算计算当前子表达式的值
			partialResult = this.parseExponent();
			switch (op) {
			case '*':
				// 如果是乘法，则用已处理子表达式的值乘以当前子表达式的值
				result = result * partialResult;
				break;
			case '/':
				// 如果是除法，判断当前字表达式的值是否为0，如果为0，则抛出被0除异常
				if (partialResult == 0.0) {
					this.handleError(DIVBYZERO_ERROR);
				}
				// 除数不为0，则进行除法运算
				result = result / partialResult;
				break;
			case '%':
				// 如果是取模运算，也要判断当前子表达式的值是否为0
				if (partialResult == 0.0) {
					this.handleError(DIVBYZERO_ERROR);
				}
				result = result % partialResult;
				break;
			}
		}
		return result;
	}

	/**
	 * 计算指数表达式
	 */
	private double parseExponent() throws Exception {
		double result; // 结果
		double partialResult; // 子表达式的值
		double ex; // 指数的底数
		int t; // 指数的幂

		// 用一元运算计算当前子表达式的值（底数）
		result = this.parseUnaryOperator();
		// 如果当前标记为“^”,则为指数运算
		if (this.token.equals("^")) {
			// 获取下一标记，即获得指数的幂
			this.getToken();
			partialResult = this.parseExponent();
			ex = result;
			if (partialResult == 0.0) {
				// 如果指数的幂为0，则指数的值为1
				result = 1.0;
			} else {
				// 否则，指数的值为个数为指数幂的底数相乘的结果
				for (t = (int) partialResult - 1; t > 0; t--) {
					result = result * ex;
				}
			}
		}
		return result;
	}

	/**
	 * 计算一元运算，＋，－，表示正数和负数
	 */
	private double parseUnaryOperator() throws Exception {
		double result; // 结果
		String op; // 运算符
		op = "";
		// 如果当前标记类型为分隔符，而且分隔符的值等于+或者-
		if ((this.tokenType == DELIMITER_TOKEN) && this.token.equals("+")
				|| this.token.equals("-")) {
			op = this.token;
			this.getToken();
		}
		// 用括号运算计算当前子表达式的值
		result = this.parseBracket();
		if (op.equals("-")) {
			// 如果运算符为-，则表示负数，将子表达式的值变为负数
			result = -result;
		}
		return result;
	}

	/**
	 * 计算括号运算
	 */
	private double parseBracket() throws Exception {
		double result; // 结果
		// 如果当前标记为左括号，则表示是一个括号运算
		if (this.token.equals("(")) {
			this.getToken(); // 取下一标记
			result = this.parseAddOrSub(); // 用加减法运算计算子表达式的值
			// 如果当前标记不等于右括号，抛出括号不匹配异常
			if (!this.token.equals(")")) {
				this.handleError(UNBALPARENS_ERROR);
			}
			this.getToken(); // 否则取下一个标记
		} else {
			// 如果不是左括号，表示不是一个括号运算，则用原子元素运算计算子表达式值
			result = this.parseAtomElement();
		}
		return result;
	}

	/**
	 * 计算原子元素运算，包括变量和数字
	 */
	private double parseAtomElement() throws Exception {
		double result = 0.0; // 结果

		switch (this.tokenType) {
		case NUMBER_TOKEN:
			// 如果当前标记类型为数字
			try {
				// 将数字的字符串转换成数字值
				result = Double.parseDouble(this.token);
			} catch (NumberFormatException exc) {
				this.handleError(SYNTAX_ERROR);
			}
			this.getToken(); // 取下一个标记
			break;
		case VARIABLE_TOKEN:
			// 如果当前标记类型是变量，则取变量的值
			result = this.findVar(token);
			this.getToken();
			break;
		default:
			this.handleError(SYNTAX_ERROR);
			break;
		}
		return result;
	}

	/**
	 * 根据变量名获取变量的值，如果变量名长度大于1，则只取变量的第一个字符
	 */
	private double findVar(String vname) throws Exception {
		//从实例变量数组vars中取出该变量的值
		if(vars.containsKey(vname)){
			return vars.get(vname);
		}
		throw new Exception(String.format("Var [%s] not define", vname));
	}
	
	/**
	 * 处理异常情况
	 */
	private void handleError(int errorType) throws Exception {
		// 遇到异常情况时，根据错误类型，取得异常提示信息，将提示信息封装在异常中抛出
		throw new Exception(ERROR_MESSAGES[errorType]);
	}

	/**
	 * 获取下一个标记
	 */
	private void getToken() {
		// 设置初始值
		this.token = "";
		this.tokenType = NONE_TOKEN;

		// 检查表达式是否结束，如果解析器当前指针已经到达了字符串长度，
		// 则表明表达式已经结束，置当前标记的值为EOE
		if (this.expIndex == this.exp.length()) {
			this.token = EOE;
			return;
		}

		// 跳过表达式中的空白符
		while (this.expIndex < this.exp.length()
				&& Character.isWhitespace(this.exp.charAt(this.expIndex))) {
			++this.expIndex;
		}

		// 再次检查表达式是否结束
		if (this.expIndex == this.exp.length()) {
			this.token = EOE;
			return;
		}

		// 取得解析器当前指针指向的字符
		char currentChar = this.exp.charAt(this.expIndex);
		// 如果当前字符是一个分隔符，则认为这是一个分隔符标记
		// 给当前标记和标记类型赋值，并将指针后移
		if (isDelim(currentChar)) {
			this.token += currentChar;
			this.expIndex++;
			this.tokenType = DELIMITER_TOKEN;
		} else if (Character.isLetter(currentChar)) {
			// 如果当前字符是一个字母，则认为是一个变量标记
			// 将解析器指针往后移，知道遇到一个分隔符，之间的字符都是变量的组成部分
			while (!isDelim(currentChar)) {
				this.token += currentChar;
				this.expIndex++;
				if (this.expIndex >= this.exp.length()) {
					break;
				} else {
					currentChar = this.exp.charAt(this.expIndex);
				}
			}
			this.tokenType = VARIABLE_TOKEN; // 设置标记类型为变量
		} else if (Character.isDigit(currentChar)) {
			// 如果当前字符是一个数字，则认为当前标记的类型为数字
			// 将解析器指针后移，知道遇到一个分隔符，之间的字符都是该数字的组成部分
			while (!isDelim(currentChar)) {
				this.token += currentChar;
				this.expIndex++;
				if (this.expIndex >= this.exp.length()) {
					break;
				} else {
					currentChar = this.exp.charAt(this.expIndex);
				}
			}
			this.tokenType = NUMBER_TOKEN; // 设置标记类型为数字
		} else {
			// 无法识别的字符，则认为表达式结束
			this.token = EOE;
			return;
		}
	}

	/**
	 * 判断一个字符是否为分隔符 表达式中的字符包括：<br>
	 * 加[+]、减[-]、乘[*]、除[/]、取模[%]、指数[^]、赋值[＝]、左括号[(]、右括号[)]
	 */
	private boolean isDelim(char c) {
		return ("+-*/%^=()".indexOf(c) != -1);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ExpressionParser parser = new ExpressionParser();
		String exp = "4 * (3 + 5)";
		System.out.println(exp+" = " + parser.evaluate(exp));
	}
}

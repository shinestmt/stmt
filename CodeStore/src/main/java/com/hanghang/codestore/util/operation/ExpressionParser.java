package com.hanghang.codestore.util.operation;

import java.util.HashMap;
import java.util.Map;

public class ExpressionParser {
	
	// 4�ֱ������
	/** ���Ϊ�ջ��߽����� */
	public static final int NONE_TOKEN = 0;

	/** ���Ϊ�ָ��� */
	public static final int DELIMITER_TOKEN = 1;

	/** ���Ϊ���� */
	public static final int VARIABLE_TOKEN = 2;

	/** ���Ϊ���� */
	public static final int NUMBER_TOKEN = 3;

	// 4�ִ�������
	/** �﷨���� */
	public static final int SYNTAX_ERROR = 0;

	/** ����δ�������� */
	public static final int UNBALPARENS_ERROR = 1;

	/** ���ʽΪ�մ��� */
	public static final int NOEXP_ERROR = 2;

	/** ����Ϊ0���� */
	public static final int DIVBYZERO_ERROR = 3;

	/** ���4�ִ������Ͷ����4��������ʾ */
	public static final String[] ERROR_MESSAGES = { 
		"Syntax Error", 
		"Unbalanced Parentheses", 
		"No Expression Present", 
		"Division by Zero" 
	};

	/** ���ʽ�Ľ������ */
	public static final String EOE = "\0";

	/** ���ʽ�ַ��� */
	private String exp; 
	/** ��������ǰָ���ڱ��ʽ�е�λ�� */
	private int expIndex; 
	/** ��������ǰ����ı�� */
	private String token; 
	/** ��������ǰ����ı������ */
	private int tokenType; 
	/** �������� */
//	private double[] vars = new double[26]; 
	
	private Map<String, Double> vars = new HashMap<String, Double>();

	/**
	 * ���ʽ������
	 */
	public ExpressionParser() {
	}
	
	/**
	 * ���б����ı��ʽ������
	 * @param context ��������,����: <br>
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
	 * ���б����ı��ʽ������
	 * @param context ��������(��������ö���[,]���),����: <br>
	 * PI=3.14159,V=2.99792*10^8
	 * @throws Exception
	 */
	public ExpressionParser(String varContext) throws Exception {
		this(varContext.split(","));
	}

	/**
	 * ����һ�����ʽ�����ر��ʽ��ֵ
	 */
	public String evaluate(String expStr) throws Exception {
		double result;
		this.exp = expStr;
		this.expIndex = 0;
		
		// ��ȡ��һ�����
		this.getToken();
		if (this.token.equals(EOE)) {
			// û�б��ʽ�쳣
			this.handleError(NOEXP_ERROR);
		}

		result = this.parseAddOrSub();
		// �����긳ֵ��䣬Ӧ�þ��Ǳ��ʽ��������������ǣ��򷵻��쳣
		if (!this.token.equals(EOE)) {
			this.handleError(SYNTAX_ERROR);
		}
		return String.valueOf(result);
	}

	/**
	 * ����Ӽ������ʽ
	 */
	private double parseAddOrSub() throws Exception {
		char op; // �����
		double result; // ���
		double partialResult; // �ӱ��ʽ�Ľ��

		result = this.parseMulOrDiv(); // �ó˳������㵱ǰ���ʽ��ֵ
		// �����ǰ��ǵĵ�һ����ĸ�ǼӼ��ţ���������мӼ�����
		while ((op = this.token.charAt(0)) == '+' || op == '-') {
			this.getToken(); // ȡ��һ�����
			// �ó˳������㵱ǰ�ӱ��ʽ��ֵ
			partialResult = this.parseMulOrDiv();
			switch (op) {
			case '-':
				// ����Ǽ����������Ѵ�����ӱ��ʽ��ֵ��ȥ��ǰ�ӱ��ʽ��ֵ
				result = result - partialResult;
				break;
			case '+':
				// ����Ǽӷ������Ѵ�����ӱ��ʽ��ֵ���ϵ�ǰ�ӱ��ʽ��ֵ
				result = result + partialResult;
				break;
			}
		}
		return result;
	}

	/**
	 * ����˳������ʽ������ȡģ����
	 */
	private double parseMulOrDiv() throws Exception {
		char op; // �����
		double result; // ���
		double partialResult; // �ӱ��ʽ���
		// ��ָ��������㵱ǰ�ӱ��ʽ��ֵ
		result = this.parseExponent();
		// �����ǰ��ǵĵ�һ����ĸ�ǳˡ�������ȡģ���㣬��������г˳�������
		while ((op = this.token.charAt(0)) == '*' || op == '/' || op == '%') {
			this.getToken(); // ȡ��һ���
			// ��ָ��������㵱ǰ�ӱ��ʽ��ֵ
			partialResult = this.parseExponent();
			switch (op) {
			case '*':
				// ����ǳ˷��������Ѵ����ӱ��ʽ��ֵ���Ե�ǰ�ӱ��ʽ��ֵ
				result = result * partialResult;
				break;
			case '/':
				// ����ǳ������жϵ�ǰ�ֱ��ʽ��ֵ�Ƿ�Ϊ0�����Ϊ0�����׳���0���쳣
				if (partialResult == 0.0) {
					this.handleError(DIVBYZERO_ERROR);
				}
				// ������Ϊ0������г�������
				result = result / partialResult;
				break;
			case '%':
				// �����ȡģ���㣬ҲҪ�жϵ�ǰ�ӱ��ʽ��ֵ�Ƿ�Ϊ0
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
	 * ����ָ�����ʽ
	 */
	private double parseExponent() throws Exception {
		double result; // ���
		double partialResult; // �ӱ��ʽ��ֵ
		double ex; // ָ���ĵ���
		int t; // ָ������

		// ��һԪ������㵱ǰ�ӱ��ʽ��ֵ��������
		result = this.parseUnaryOperator();
		// �����ǰ���Ϊ��^��,��Ϊָ������
		if (this.token.equals("^")) {
			// ��ȡ��һ��ǣ������ָ������
			this.getToken();
			partialResult = this.parseExponent();
			ex = result;
			if (partialResult == 0.0) {
				// ���ָ������Ϊ0����ָ����ֵΪ1
				result = 1.0;
			} else {
				// ����ָ����ֵΪ����Ϊָ���ݵĵ�����˵Ľ��
				for (t = (int) partialResult - 1; t > 0; t--) {
					result = result * ex;
				}
			}
		}
		return result;
	}

	/**
	 * ����һԪ���㣬����������ʾ�����͸���
	 */
	private double parseUnaryOperator() throws Exception {
		double result; // ���
		String op; // �����
		op = "";
		// �����ǰ�������Ϊ�ָ��������ҷָ�����ֵ����+����-
		if ((this.tokenType == DELIMITER_TOKEN) && this.token.equals("+")
				|| this.token.equals("-")) {
			op = this.token;
			this.getToken();
		}
		// ������������㵱ǰ�ӱ��ʽ��ֵ
		result = this.parseBracket();
		if (op.equals("-")) {
			// ��������Ϊ-�����ʾ���������ӱ��ʽ��ֵ��Ϊ����
			result = -result;
		}
		return result;
	}

	/**
	 * ������������
	 */
	private double parseBracket() throws Exception {
		double result; // ���
		// �����ǰ���Ϊ�����ţ����ʾ��һ����������
		if (this.token.equals("(")) {
			this.getToken(); // ȡ��һ���
			result = this.parseAddOrSub(); // �üӼ�����������ӱ��ʽ��ֵ
			// �����ǰ��ǲ����������ţ��׳����Ų�ƥ���쳣
			if (!this.token.equals(")")) {
				this.handleError(UNBALPARENS_ERROR);
			}
			this.getToken(); // ����ȡ��һ�����
		} else {
			// ������������ţ���ʾ����һ���������㣬����ԭ��Ԫ����������ӱ��ʽֵ
			result = this.parseAtomElement();
		}
		return result;
	}

	/**
	 * ����ԭ��Ԫ�����㣬��������������
	 */
	private double parseAtomElement() throws Exception {
		double result = 0.0; // ���

		switch (this.tokenType) {
		case NUMBER_TOKEN:
			// �����ǰ�������Ϊ����
			try {
				// �����ֵ��ַ���ת��������ֵ
				result = Double.parseDouble(this.token);
			} catch (NumberFormatException exc) {
				this.handleError(SYNTAX_ERROR);
			}
			this.getToken(); // ȡ��һ�����
			break;
		case VARIABLE_TOKEN:
			// �����ǰ��������Ǳ�������ȡ������ֵ
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
	 * ���ݱ�������ȡ������ֵ��������������ȴ���1����ֻȡ�����ĵ�һ���ַ�
	 */
	private double findVar(String vname) throws Exception {
		//��ʵ����������vars��ȡ���ñ�����ֵ
		if(vars.containsKey(vname)){
			return vars.get(vname);
		}
		throw new Exception(String.format("Var [%s] not define", vname));
	}
	
	/**
	 * �����쳣���
	 */
	private void handleError(int errorType) throws Exception {
		// �����쳣���ʱ�����ݴ������ͣ�ȡ���쳣��ʾ��Ϣ������ʾ��Ϣ��װ���쳣���׳�
		throw new Exception(ERROR_MESSAGES[errorType]);
	}

	/**
	 * ��ȡ��һ�����
	 */
	private void getToken() {
		// ���ó�ʼֵ
		this.token = "";
		this.tokenType = NONE_TOKEN;

		// �����ʽ�Ƿ�����������������ǰָ���Ѿ��������ַ������ȣ�
		// ��������ʽ�Ѿ��������õ�ǰ��ǵ�ֵΪEOE
		if (this.expIndex == this.exp.length()) {
			this.token = EOE;
			return;
		}

		// �������ʽ�еĿհ׷�
		while (this.expIndex < this.exp.length()
				&& Character.isWhitespace(this.exp.charAt(this.expIndex))) {
			++this.expIndex;
		}

		// �ٴμ����ʽ�Ƿ����
		if (this.expIndex == this.exp.length()) {
			this.token = EOE;
			return;
		}

		// ȡ�ý�������ǰָ��ָ����ַ�
		char currentChar = this.exp.charAt(this.expIndex);
		// �����ǰ�ַ���һ���ָ���������Ϊ����һ���ָ������
		// ����ǰ��Ǻͱ�����͸�ֵ������ָ�����
		if (isDelim(currentChar)) {
			this.token += currentChar;
			this.expIndex++;
			this.tokenType = DELIMITER_TOKEN;
		} else if (Character.isLetter(currentChar)) {
			// �����ǰ�ַ���һ����ĸ������Ϊ��һ���������
			// ��������ָ�������ƣ�֪������һ���ָ�����֮����ַ����Ǳ�������ɲ���
			while (!isDelim(currentChar)) {
				this.token += currentChar;
				this.expIndex++;
				if (this.expIndex >= this.exp.length()) {
					break;
				} else {
					currentChar = this.exp.charAt(this.expIndex);
				}
			}
			this.tokenType = VARIABLE_TOKEN; // ���ñ������Ϊ����
		} else if (Character.isDigit(currentChar)) {
			// �����ǰ�ַ���һ�����֣�����Ϊ��ǰ��ǵ�����Ϊ����
			// ��������ָ����ƣ�֪������һ���ָ�����֮����ַ����Ǹ����ֵ���ɲ���
			while (!isDelim(currentChar)) {
				this.token += currentChar;
				this.expIndex++;
				if (this.expIndex >= this.exp.length()) {
					break;
				} else {
					currentChar = this.exp.charAt(this.expIndex);
				}
			}
			this.tokenType = NUMBER_TOKEN; // ���ñ������Ϊ����
		} else {
			// �޷�ʶ����ַ�������Ϊ���ʽ����
			this.token = EOE;
			return;
		}
	}

	/**
	 * �ж�һ���ַ��Ƿ�Ϊ�ָ��� ���ʽ�е��ַ�������<br>
	 * ��[+]����[-]����[*]����[/]��ȡģ[%]��ָ��[^]����ֵ[��]��������[(]��������[)]
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

package com.hanghang.codestore.util;

import java.util.Formatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	private final static String EMPTY_STRING = "";
	private final static String[] EMPTY_STRING_ARRAY = {};
	
	/**
	 * ������ռλ���ı��ʽ�Ͳ������и�ʽ��<br>
     * @param exp ���ʽ
     * @param args ��������
     * @return ��ʽ��֮����ַ���
     * ����:<br>
     * exp="({0}+{1})*{2}" <br>
     * args=["3", "7", "4"] <br>
     * result="(3+7)*4"<br>
     * @throws Exception
	 */
	public static String format(String exp, String... args) throws Exception {
		StringBuffer result = new StringBuffer(exp);
		boolean started = false;
		int start = -1, end = -1, index = -1;
		int i = 0;
		while (i < result.length()) {
			char c = result.charAt(i);
			if(c == '{'){
				start = i;
				started = true;	//ƥ��ռλ����ʼ
			} else if(c == '}') {
				if(started){
					end = i;
					try {
						index = Integer.parseInt(result.substring(start+1, end));
						result.replace(start, end+1, args[index]);
						i = start+args[index].length();
						started = false;	//ƥ��ռλ������
						continue;
					} catch (NumberFormatException e) {
						throw Lang.handException("�Ƿ�ռλ��{%s}", result.substring(start+1, end));
					} catch (ArrayIndexOutOfBoundsException e) {
						throw Lang.handException(e, "���ʽ%sȱ�ٲ���{%s}", exp, result.substring(start+1, end));
					}
				}
			}
			i++;
		}
		return result.toString();
	}
	
	public static String nvl(String exp, String replace){
		return exp==null ? replace : exp;
	}
	
	public static String evl(String exp, String replace){
		return exp.equals("") ? replace : exp;
	}
	
//	 ==========================================================================
    // ���������� 
    //
    // ע�����ڴ�Сд���е�isEquals��������ʹ��ObjectUtil.isEquals��
    // ==========================================================================

    /**
     * ȡ���ַ����ĳ��ȡ�
     * 
     * @param str Ҫȡ���ȵ��ַ���
     * @return ����ַ���Ϊ<code>null</code>���򷵻�<code>0</code>�����򷵻��ַ����ĳ��ȡ�
     */
    public static int getLength(String str) {
        return str == null ? 0 : str.length();
    }

    /**
     * �Ƚ������ַ�������Сд�����У���
     * 
     * <pre>
     * StringUtil.equalsIgnoreCase(null, null)   = true
     * StringUtil.equalsIgnoreCase(null, "abc")  = false
     * StringUtil.equalsIgnoreCase("abc", null)  = false
     * StringUtil.equalsIgnoreCase("abc", "abc") = true
     * StringUtil.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     * 
     * @param str1 Ҫ�Ƚϵ��ַ���1
     * @param str2 Ҫ�Ƚϵ��ַ���2
     * @return ��������ַ�����ͬ�����߶���<code>null</code>���򷵻�<code>true</code>
     */
    public static boolean isEqualsIgnoreCase(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equalsIgnoreCase(str2);
    }

    // ==========================================================================
    // �пպ����� 
    //  
    // ���·��������ж�һ���ַ����Ƿ�Ϊ�� 
    // 1. null 
    // 2. empty - "" 
    // 3. blank - "ȫ���ǿհ�" - �հ���Character.isWhitespace�����塣 
    // ==========================================================================

    /**
     * ����ַ����Ƿ�Ϊ<code>null</code>����ַ���<code>""</code>��
     * 
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("bob")     = false
     * StringUtil.isEmpty("  bob  ") = false
     * </pre>
     * 
     * @param str Ҫ�����ַ���
     * @return ���Ϊ��, �򷵻�<code>true</code>
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * ����ַ����Ƿ��ǿհף�<code>null</code>�����ַ���<code>""</code>��ֻ�пհ��ַ���
     * 
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     * 
     * @param str Ҫ�����ַ���
     * @return ���Ϊ�հ�, �򷵻�<code>true</code>
     */
    public static boolean isBlank(String str) {
        int length;

        if (str == null || (length = str.length()) == 0) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    // ==========================================================================
    // Ĭ��ֵ������ 
    //  
    // ���ַ���Ϊempty��blankʱ�����ַ���ת����ָ����Ĭ���ַ�����
    // ע���ж��ַ���Ϊnullʱ�����ø�ͨ�õ�ObjectUtil.defaultIfNull��
    // ==========================================================================

    /**
     * ����ַ�����<code>null</code>����ַ���<code>""</code>���򷵻�ָ��Ĭ���ַ��������򷵻��ַ�������
     * 
     * <pre>
     * StringUtil.defaultIfEmpty(null, "default")  = "default"
     * StringUtil.defaultIfEmpty("", "default")    = "default"
     * StringUtil.defaultIfEmpty("  ", "default")  = "  "
     * StringUtil.defaultIfEmpty("bat", "default") = "bat"
     * </pre>
     * 
     * @param str Ҫת�����ַ���
     * @param defaultStr Ĭ���ַ���
     * @return �ַ��������ָ����Ĭ���ַ���
     */
    public static String defaultIfEmpty(String str, String defaultStr) {
        return str == null || str.length() == 0 ? defaultStr : str;
    }

    /**
     * ����ַ�����<code>null</code>����ַ���<code>""</code>���򷵻�ָ��Ĭ���ַ��������򷵻��ַ�������
     * 
     * <pre>
     * StringUtil.defaultIfBlank(null, "default")  = "default"
     * StringUtil.defaultIfBlank("", "default")    = "default"
     * StringUtil.defaultIfBlank("  ", "default")  = "default"
     * StringUtil.defaultIfBlank("bat", "default") = "bat"
     * </pre>
     * 
     * @param str Ҫת�����ַ���
     * @param defaultStr Ĭ���ַ���
     * @return �ַ��������ָ����Ĭ���ַ���
     */
    public static String defaultIfBlank(String str, String defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    // ==========================================================================
    // ȥ�հ׵ĺ����� 
    //  
    // ���·���������ȥһ���ִ���β�Ŀհס� 
    // ==========================================================================

    /**
     * ��ȥ�ַ���ͷβ���Ŀհף�����ַ�����<code>null</code>����Ȼ����<code>null</code>��
     * 
     * <pre>
     * StringUtil.trim(null)          = null
     * StringUtil.trim("")            = ""
     * StringUtil.trim("     ")       = ""
     * StringUtil.trim("abc")         = "abc"
     * StringUtil.trim("    abc    ") = "abc"
     * </pre>
     * 
     * @param str Ҫ������ַ���
     * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * ��ȥ�ַ���ͷβ���Ŀհף��������ַ����ǿ��ַ���<code>""</code>���򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.trimToNull(null)          = null
     * StringUtil.trimToNull("")            = null
     * StringUtil.trimToNull("     ")       = null
     * StringUtil.trimToNull("abc")         = "abc"
     * StringUtil.trimToNull("    abc    ") = "abc"
     * </pre>
     * 
     * @param str Ҫ������ַ���
     * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>�����ַ���Ϊ<code>""</code>���򷵻�
     *         <code>null</code>
     */
    public static String trimToNull(String str) {
        if (str == null) {
            return null;
        }

        String result = str.trim();

        if (result == null || result.length() == 0) {
            return null;
        }

        return result;
    }

    /**
     * ��ȥ�ַ���ͷβ���Ŀհף�����ַ�����<code>null</code>���򷵻ؿ��ַ���<code>""</code>��
     * 
     * <pre>
     * StringUtil.trimToEmpty(null)          = ""
     * StringUtil.trimToEmpty("")            = ""
     * StringUtil.trimToEmpty("     ")       = ""
     * StringUtil.trimToEmpty("abc")         = "abc"
     * StringUtil.trimToEmpty("    abc    ") = "abc"
     * </pre>
     * 
     * @param str Ҫ������ַ���
     * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>�����ַ���Ϊ<code>""</code>���򷵻�
     *         <code>null</code>
     */
    public static String trimToEmpty(String str) {
        if (str == null) {
            return EMPTY_STRING;
        }

        return str.trim();
    }

    /**
     * ��ȥ�ַ���ͷβ����ָ���ַ�������ַ�����<code>null</code>����Ȼ����<code>null</code>��
     * 
     * <pre>
     * StringUtil.trim(null, *)          = null
     * StringUtil.trim("", *)            = ""
     * StringUtil.trim("abc", null)      = "abc"
     * StringUtil.trim("  abc", null)    = "abc"
     * StringUtil.trim("abc  ", null)    = "abc"
     * StringUtil.trim(" abc ", null)    = "abc"
     * StringUtil.trim("  abcyx", "xyz") = "  abc"
     * </pre>
     * 
     * @param str Ҫ������ַ���
     * @param stripChars Ҫ��ȥ���ַ������Ϊ<code>null</code>��ʾ��ȥ�հ��ַ�
     * @return ��ȥָ���ַ���ĵ��ַ��������ԭ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String trim(String str, String stripChars) {
        return trim(str, stripChars, 0);
    }

    /**
     * ��ȥ�ַ���ͷ���Ŀհף�����ַ�����<code>null</code>���򷵻�<code>null</code>��
     * <p>
     * ע�⣬��<code>String.trim</code>��ͬ���˷���ʹ��<code>Character.isWhitespace</code>
     * ���ж��հף� ������Գ�ȥӢ���ַ���֮��������հף������Ŀո�
     * 
     * <pre>
     * StringUtil.trimStart(null)         = null
     * StringUtil.trimStart("")           = ""
     * StringUtil.trimStart("abc")        = "abc"
     * StringUtil.trimStart("  abc")      = "abc"
     * StringUtil.trimStart("abc  ")      = "abc  "
     * StringUtil.trimStart(" abc ")      = "abc "
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫ������ַ���
     * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>�����ַ���Ϊ<code>""</code>���򷵻�
     *         <code>null</code>
     */
    public static String trimStart(String str) {
        return trim(str, null, -1);
    }

    /**
     * ��ȥ�ַ���ͷ����ָ���ַ�������ַ�����<code>null</code>����Ȼ����<code>null</code>��
     * 
     * <pre>
     * StringUtil.trimStart(null, *)          = null
     * StringUtil.trimStart("", *)            = ""
     * StringUtil.trimStart("abc", "")        = "abc"
     * StringUtil.trimStart("abc", null)      = "abc"
     * StringUtil.trimStart("  abc", null)    = "abc"
     * StringUtil.trimStart("abc  ", null)    = "abc  "
     * StringUtil.trimStart(" abc ", null)    = "abc "
     * StringUtil.trimStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     * 
     * @param str Ҫ������ַ���
     * @param stripChars Ҫ��ȥ���ַ������Ϊ<code>null</code>��ʾ��ȥ�հ��ַ�
     * @return ��ȥָ���ַ���ĵ��ַ��������ԭ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String trimStart(String str, String stripChars) {
        return trim(str, stripChars, -1);
    }

    /**
     * ��ȥ�ַ���β���Ŀհף�����ַ�����<code>null</code>���򷵻�<code>null</code>��
     * <p>
     * ע�⣬��<code>String.trim</code>��ͬ���˷���ʹ��<code>Character.isWhitespace</code>
     * ���ж��հף� ������Գ�ȥӢ���ַ���֮��������հף������Ŀո�
     * 
     * <pre>
     * StringUtil.trimEnd(null)       = null
     * StringUtil.trimEnd("")         = ""
     * StringUtil.trimEnd("abc")      = "abc"
     * StringUtil.trimEnd("  abc")    = "  abc"
     * StringUtil.trimEnd("abc  ")    = "abc"
     * StringUtil.trimEnd(" abc ")    = " abc"
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫ������ַ���
     * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>�����ַ���Ϊ<code>""</code>���򷵻�
     *         <code>null</code>
     */
    public static String trimEnd(String str) {
        return trim(str, null, 1);
    }

    /**
     * ��ȥ�ַ���β����ָ���ַ�������ַ�����<code>null</code>����Ȼ����<code>null</code>��
     * 
     * <pre>
     * StringUtil.trimEnd(null, *)          = null
     * StringUtil.trimEnd("", *)            = ""
     * StringUtil.trimEnd("abc", "")        = "abc"
     * StringUtil.trimEnd("abc", null)      = "abc"
     * StringUtil.trimEnd("  abc", null)    = "  abc"
     * StringUtil.trimEnd("abc  ", null)    = "abc"
     * StringUtil.trimEnd(" abc ", null)    = " abc"
     * StringUtil.trimEnd("  abcyx", "xyz") = "  abc"
     * </pre>
     * 
     * @param str Ҫ������ַ���
     * @param stripChars Ҫ��ȥ���ַ������Ϊ<code>null</code>��ʾ��ȥ�հ��ַ�
     * @return ��ȥָ���ַ���ĵ��ַ��������ԭ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String trimEnd(String str, String stripChars) {
        return trim(str, stripChars, 1);
    }

    /**
     * ��ȥ�ַ���ͷβ���Ŀհף��������ַ����ǿ��ַ���<code>""</code>���򷵻�<code>null</code>��
     * <p>
     * ע�⣬��<code>String.trim</code>��ͬ���˷���ʹ��<code>Character.isWhitespace</code>
     * ���ж��հף� ������Գ�ȥӢ���ַ���֮��������հף������Ŀո�
     * 
     * <pre>
     * StringUtil.trim(null, *)          = null
     * StringUtil.trim("", *)            = null
     * StringUtil.trim("abc", null)      = "abc"
     * StringUtil.trim("  abc", null)    = "abc"
     * StringUtil.trim("abc  ", null)    = "abc"
     * StringUtil.trim(" abc ", null)    = "abc"
     * StringUtil.trim("  abcyx", "xyz") = "  abc"
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫ������ַ���
     * @param stripChars Ҫ��ȥ���ַ������Ϊ<code>null</code>��ʾ��ȥ�հ��ַ�
     * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>�����ַ���Ϊ<code>""</code>���򷵻�
     *         <code>null</code>
     */
    public static String trimToNull(String str, String stripChars) {
        String result = trim(str, stripChars);

        if (result == null || result.length() == 0) {
            return null;
        }

        return result;
    }

    /**
     * ��ȥ�ַ���ͷβ���Ŀհף�����ַ�����<code>null</code>���򷵻ؿ��ַ���<code>""</code>��
     * <p>
     * ע�⣬��<code>String.trim</code>��ͬ���˷���ʹ��<code>Character.isWhitespace</code>
     * ���ж��հף� ������Գ�ȥӢ���ַ���֮��������հף������Ŀո�
     * 
     * <pre>
     * StringUtil.trim(null, *)          = ""
     * StringUtil.trim("", *)            = ""
     * StringUtil.trim("abc", null)      = "abc"
     * StringUtil.trim("  abc", null)    = "abc"
     * StringUtil.trim("abc  ", null)    = "abc"
     * StringUtil.trim(" abc ", null)    = "abc"
     * StringUtil.trim("  abcyx", "xyz") = "  abc"
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫ������ַ���
     * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>�����ַ���Ϊ<code>""</code>���򷵻�
     *         <code>null</code>
     */
    public static String trimToEmpty(String str, String stripChars) {
        String result = trim(str, stripChars);

        if (result == null) {
            return EMPTY_STRING;
        }

        return result;
    }

    /**
     * ��ȥ�ַ���ͷβ����ָ���ַ�������ַ�����<code>null</code>����Ȼ����<code>null</code>��
     * 
     * <pre>
     * StringUtil.trim(null, *)          = null
     * StringUtil.trim("", *)            = ""
     * StringUtil.trim("abc", null)      = "abc"
     * StringUtil.trim("  abc", null)    = "abc"
     * StringUtil.trim("abc  ", null)    = "abc"
     * StringUtil.trim(" abc ", null)    = "abc"
     * StringUtil.trim("  abcyx", "xyz") = "  abc"
     * </pre>
     * 
     * @param str Ҫ������ַ���
     * @param stripChars Ҫ��ȥ���ַ������Ϊ<code>null</code>��ʾ��ȥ�հ��ַ�
     * @param mode <code>-1</code>��ʾtrimStart��<code>0</code>��ʾtrimȫ����
     *            <code>1</code>��ʾtrimEnd
     * @return ��ȥָ���ַ���ĵ��ַ��������ԭ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    private static String trim(String str, String stripChars, int mode) {
        if (str == null) {
            return null;
        }

        int length = str.length();
        int start = 0;
        int end = length;

        // ɨ���ַ���ͷ��
        if (mode <= 0) {
            if (stripChars == null) {
                while (start < end && Character.isWhitespace(str.charAt(start))) {
                    start++;
                }
            } else if (stripChars.length() == 0) {
                return str;
            } else {
                while (start < end && stripChars.indexOf(str.charAt(start)) != -1) {
                    start++;
                }
            }
        }

        // ɨ���ַ���β��
        if (mode >= 0) {
            if (stripChars == null) {
                while (start < end && Character.isWhitespace(str.charAt(end - 1))) {
                    end--;
                }
            } else if (stripChars.length() == 0) {
                return str;
            } else {
                while (start < end && stripChars.indexOf(str.charAt(end - 1)) != -1) {
                    end--;
                }
            }
        }

        if (start > 0 || end < length) {
            return str.substring(start, end);
        }

        return str;
    }

    // ==========================================================================
    // ��Сдת���� 
    // ==========================================================================

    /**
     * ���ַ��������ַ�ת�ɴ�д��<code>Character.toTitleCase</code>���������ַ����䡣
     * <p>
     * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.capitalize(null)  = null
     * StringUtil.capitalize("")    = ""
     * StringUtil.capitalize("cat") = "Cat"
     * StringUtil.capitalize("cAt") = "CAt"
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫת�����ַ���
     * @return ���ַ�Ϊ��д���ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String capitalize(String str) {
        int strLen;

        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        return new StringBuilder(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1))
                .toString();
    }

    /**
     * ���ַ��������ַ�ת��Сд�������ַ����䡣
     * <p>
     * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.uncapitalize(null)  = null
     * StringUtil.uncapitalize("")    = ""
     * StringUtil.uncapitalize("Cat") = "cat"
     * StringUtil.uncapitalize("CAT") = "CAT"
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫת�����ַ���
     * @return ���ַ�ΪСд���ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String uncapitalize(String str) {
        int strLen;

        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        if (strLen > 1 && Character.isUpperCase(str.charAt(1)) && Character.isUpperCase(str.charAt(0))) {
            return str;
        }

        return new StringBuilder(strLen).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1))
                .toString();
    }

    /**
     * ��ת�ַ����Ĵ�Сд��
     * <p>
     * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.swapCase(null)                 = null
     * StringUtil.swapCase("")                   = ""
     * StringUtil.swapCase("The dog has a BONE") = "tHE DOG HAS A bone"
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫת�����ַ���
     * @return ��Сд����ת���ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String swapCase(String str) {
        int strLen;

        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        StringBuilder buffer = new StringBuilder(strLen);

        char ch = 0;

        for (int i = 0; i < strLen; i++) {
            ch = str.charAt(i);

            if (Character.isUpperCase(ch)) {
                ch = Character.toLowerCase(ch);
            } else if (Character.isTitleCase(ch)) {
                ch = Character.toLowerCase(ch);
            } else if (Character.isLowerCase(ch)) {
                ch = Character.toUpperCase(ch);
            }

            buffer.append(ch);
        }

        return buffer.toString();
    }

    /**
     * ���ַ���ת���ɴ�д��
     * <p>
     * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.toUpperCase(null)  = null
     * StringUtil.toUpperCase("")    = ""
     * StringUtil.toUpperCase("aBc") = "ABC"
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫת�����ַ���
     * @return ��д�ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String toUpperCase(String str) {
        if (str == null) {
            return null;
        }

        return str.toUpperCase();
    }

    /**
     * ���ַ���ת����Сд��
     * <p>
     * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.toLowerCase(null)  = null
     * StringUtil.toLowerCase("")    = ""
     * StringUtil.toLowerCase("aBc") = "abc"
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫת�����ַ���
     * @return ��д�ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String toLowerCase(String str) {
        if (str == null) {
            return null;
        }

        return str.toLowerCase();
    }

    /**
     * ���ַ���ת����camel case��
     * <p>
     * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.toCamelCase(null)  = null
     * StringUtil.toCamelCase("")    = ""
     * StringUtil.toCamelCase("aBc") = "aBc"
     * StringUtil.toCamelCase("aBc def") = "aBcDef"
     * StringUtil.toCamelCase("aBc def_ghi") = "aBcDefGhi"
     * StringUtil.toCamelCase("aBc def_ghi 123") = "aBcDefGhi123"
     * </pre>
     * 
     * </p>
     * <p>
     * �˷����ᱣ�������»��ߺͿհ���������зָ�����
     * </p>
     * 
     * @param str Ҫת�����ַ���
     * @return camel case�ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String toCamelCase(String str) {
        return new WordTokenizer() {
            @Override
            protected void startSentence(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            @Override
            protected void startWord(StringBuilder buffer, char ch) {
                if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append(Character.toUpperCase(ch));
                } else {
                    buffer.append(Character.toLowerCase(ch));
                }
            }

            @Override
            protected void inWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            @Override
            protected void startDigitSentence(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void startDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void inDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void inDelimiter(StringBuilder buffer, char ch) {
                if (ch != UNDERSCORE) {
                    buffer.append(ch);
                }
            }
        }.parse(str);
    }

    /**
     * ���ַ���ת����pascal case��
     * <p>
     * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.toPascalCase(null)  = null
     * StringUtil.toPascalCase("")    = ""
     * StringUtil.toPascalCase("aBc") = "ABc"
     * StringUtil.toPascalCase("aBc def") = "ABcDef"
     * StringUtil.toPascalCase("aBc def_ghi") = "ABcDefGhi"
     * StringUtil.toPascalCase("aBc def_ghi 123") = "aBcDefGhi123"
     * </pre>
     * 
     * </p>
     * <p>
     * �˷����ᱣ�������»��ߺͿհ���������зָ�����
     * </p>
     * 
     * @param str Ҫת�����ַ���
     * @return pascal case�ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String toPascalCase(String str) {
        return new WordTokenizer() {
            @Override
            protected void startSentence(StringBuilder buffer, char ch) {
                buffer.append(Character.toUpperCase(ch));
            }

            @Override
            protected void startWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toUpperCase(ch));
            }

            @Override
            protected void inWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            @Override
            protected void startDigitSentence(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void startDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void inDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void inDelimiter(StringBuilder buffer, char ch) {
                if (ch != UNDERSCORE) {
                    buffer.append(ch);
                }
            }
        }.parse(str);
    }

    /**
     * ���ַ���ת�����»��߷ָ��Ĵ�д�ַ�����
     * <p>
     * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.toUpperCaseWithUnderscores(null)  = null
     * StringUtil.toUpperCaseWithUnderscores("")    = ""
     * StringUtil.toUpperCaseWithUnderscores("aBc") = "A_BC"
     * StringUtil.toUpperCaseWithUnderscores("aBc def") = "A_BC_DEF"
     * StringUtil.toUpperCaseWithUnderscores("aBc def_ghi") = "A_BC_DEF_GHI"
     * StringUtil.toUpperCaseWithUnderscores("aBc def_ghi 123") = "A_BC_DEF_GHI_123"
     * StringUtil.toUpperCaseWithUnderscores("__a__Bc__") = "__A__BC__"
     * </pre>
     * 
     * </p>
     * <p>
     * �˷����ᱣ�����˿հ���������зָ�����
     * </p>
     * 
     * @param str Ҫת�����ַ���
     * @return �»��߷ָ��Ĵ�д�ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String toUpperCaseWithUnderscores(String str) {
        return new WordTokenizer() {
            @Override
            protected void startSentence(StringBuilder buffer, char ch) {
                buffer.append(Character.toUpperCase(ch));
            }

            @Override
            protected void startWord(StringBuilder buffer, char ch) {
                if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append(UNDERSCORE);
                }

                buffer.append(Character.toUpperCase(ch));
            }

            @Override
            protected void inWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toUpperCase(ch));
            }

            @Override
            protected void startDigitSentence(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void startDigitWord(StringBuilder buffer, char ch) {
                if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append(UNDERSCORE);
                }

                buffer.append(ch);
            }

            @Override
            protected void inDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void inDelimiter(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }
        }.parse(str);
    }

    /**
     * ���ַ���ת�����»��߷ָ���Сд�ַ�����
     * <p>
     * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.toLowerCaseWithUnderscores(null)  = null
     * StringUtil.toLowerCaseWithUnderscores("")    = ""
     * StringUtil.toLowerCaseWithUnderscores("aBc") = "a_bc"
     * StringUtil.toLowerCaseWithUnderscores("aBc def") = "a_bc_def"
     * StringUtil.toLowerCaseWithUnderscores("aBc def_ghi") = "a_bc_def_ghi"
     * StringUtil.toLowerCaseWithUnderscores("aBc def_ghi 123") = "a_bc_def_ghi_123"
     * StringUtil.toLowerCaseWithUnderscores("__a__Bc__") = "__a__bc__"
     * </pre>
     * 
     * </p>
     * <p>
     * �˷����ᱣ�����˿հ���������зָ�����
     * </p>
     * 
     * @param str Ҫת�����ַ���
     * @return �»��߷ָ���Сд�ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String toLowerCaseWithUnderscores(String str) {
        return new WordTokenizer() {
            @Override
            protected void startSentence(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            @Override
            protected void startWord(StringBuilder buffer, char ch) {
                if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append(UNDERSCORE);
                }

                buffer.append(Character.toLowerCase(ch));
            }

            @Override
            protected void inWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            @Override
            protected void startDigitSentence(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void startDigitWord(StringBuilder buffer, char ch) {
                if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append(UNDERSCORE);
                }

                buffer.append(ch);
            }

            @Override
            protected void inDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void inDelimiter(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }
        }.parse(str);
    }

    /**
     * �����������﷨�����ɵ�<code>SENTENCE</code>��
     * 
     * <pre>
     *  SENTENCE = WORD (DELIMITER* WORD)*
     * 
     *  WORD = UPPER_CASE_WORD | LOWER_CASE_WORD | TITLE_CASE_WORD | DIGIT_WORD
     * 
     *  UPPER_CASE_WORD = UPPER_CASE_LETTER+
     *  LOWER_CASE_WORD = LOWER_CASE_LETTER+
     *  TITLE_CASE_WORD = UPPER_CASE_LETTER LOWER_CASE_LETTER+
     *  DIGIT_WORD      = DIGIT+
     * 
     *  UPPER_CASE_LETTER = Character.isUpperCase()
     *  LOWER_CASE_LETTER = Character.isLowerCase()
     *  DIGIT             = Character.isDigit()
     *  NON_LETTER_DIGIT  = !Character.isUpperCase() && !Character.isLowerCase() && !Character.isDigit()
     * 
     *  DELIMITER = WHITESPACE | NON_LETTER_DIGIT
     * </pre>
     */
    private abstract static class WordTokenizer {
        protected static final char UNDERSCORE = '_';

        /**
         * Parse sentence��
         */
        public String parse(String str) {
            if (StringUtil.isEmpty(str)) {
                return str;
            }

            int length = str.length();
            StringBuilder buffer = new StringBuilder(length);

            for (int index = 0; index < length; index++) {
                char ch = str.charAt(index);

                // ���Կհס�
                if (Character.isWhitespace(ch)) {
                    continue;
                }

                // ��д��ĸ��ʼ��UpperCaseWord����TitleCaseWord��
                if (Character.isUpperCase(ch)) {
                    int wordIndex = index + 1;

                    while (wordIndex < length) {
                        char wordChar = str.charAt(wordIndex);

                        if (Character.isUpperCase(wordChar)) {
                            wordIndex++;
                        } else if (Character.isLowerCase(wordChar)) {
                            wordIndex--;
                            break;
                        } else {
                            break;
                        }
                    }

                    // 1. wordIndex == length��˵�����һ����ĸΪ��д����upperCaseWord����֮��
                    // 2. wordIndex == index��˵��index��Ϊһ��titleCaseWord��
                    // 3. wordIndex > index��˵��index��wordIndex - 1��ȫ���Ǵ�д����upperCaseWord����
                    if (wordIndex == length || wordIndex > index) {
                        index = parseUpperCaseWord(buffer, str, index, wordIndex);
                    } else {
                        index = parseTitleCaseWord(buffer, str, index);
                    }

                    continue;
                }

                // Сд��ĸ��ʼ��LowerCaseWord��
                if (Character.isLowerCase(ch)) {
                    index = parseLowerCaseWord(buffer, str, index);
                    continue;
                }

                // ���ֿ�ʼ��DigitWord��
                if (Character.isDigit(ch)) {
                    index = parseDigitWord(buffer, str, index);
                    continue;
                }

                // ����ĸ���ֿ�ʼ��Delimiter�� 
                inDelimiter(buffer, ch);
            }

            return buffer.toString();
        }

        private int parseUpperCaseWord(StringBuilder buffer, String str, int index, int length) {
            char ch = str.charAt(index++);

            // ����ĸ����Ȼ������Ϊ��д��
            if (buffer.length() == 0) {
                startSentence(buffer, ch);
            } else {
                startWord(buffer, ch);
            }

            // ������ĸ����ΪСд��
            for (; index < length; index++) {
                ch = str.charAt(index);
                inWord(buffer, ch);
            }

            return index - 1;
        }

        private int parseLowerCaseWord(StringBuilder buffer, String str, int index) {
            char ch = str.charAt(index++);

            // ����ĸ����Ȼ������ΪСд��
            if (buffer.length() == 0) {
                startSentence(buffer, ch);
            } else {
                startWord(buffer, ch);
            }

            // ������ĸ����ΪСд��
            int length = str.length();

            for (; index < length; index++) {
                ch = str.charAt(index);

                if (Character.isLowerCase(ch)) {
                    inWord(buffer, ch);
                } else {
                    break;
                }
            }

            return index - 1;
        }

        private int parseTitleCaseWord(StringBuilder buffer, String str, int index) {
            char ch = str.charAt(index++);

            // ����ĸ����Ȼ������Ϊ��д��
            if (buffer.length() == 0) {
                startSentence(buffer, ch);
            } else {
                startWord(buffer, ch);
            }

            // ������ĸ����ΪСд��
            int length = str.length();

            for (; index < length; index++) {
                ch = str.charAt(index);

                if (Character.isLowerCase(ch)) {
                    inWord(buffer, ch);
                } else {
                    break;
                }
            }

            return index - 1;
        }

        private int parseDigitWord(StringBuilder buffer, String str, int index) {
            char ch = str.charAt(index++);

            // ���ַ�����Ȼ������Ϊ���֡�
            if (buffer.length() == 0) {
                startDigitSentence(buffer, ch);
            } else {
                startDigitWord(buffer, ch);
            }

            // �����ַ�����Ϊ���֡�
            int length = str.length();

            for (; index < length; index++) {
                ch = str.charAt(index);

                if (Character.isDigit(ch)) {
                    inDigitWord(buffer, ch);
                } else {
                    break;
                }
            }

            return index - 1;
        }

        protected boolean isDelimiter(char ch) {
            return !Character.isUpperCase(ch) && !Character.isLowerCase(ch) && !Character.isDigit(ch);
        }

        protected abstract void startSentence(StringBuilder buffer, char ch);

        protected abstract void startWord(StringBuilder buffer, char ch);

        protected abstract void inWord(StringBuilder buffer, char ch);

        protected abstract void startDigitSentence(StringBuilder buffer, char ch);

        protected abstract void startDigitWord(StringBuilder buffer, char ch);

        protected abstract void inDigitWord(StringBuilder buffer, char ch);

        protected abstract void inDelimiter(StringBuilder buffer, char ch);
    }

    // ==========================================================================
    // �ַ����ָ���� 
    //  
    // ���ַ�����ָ���ָ����ָ 
    // ==========================================================================

    /**
     * ���ַ�����ָ���ַ��ָ
     * <p>
     * �ָ������������Ŀ�������У������ķָ����ͱ�����һ��������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.split(null, *)         = null
     * StringUtil.split("", *)           = []
     * StringUtil.split("a.b.c", '.')    = ["a", "b", "c"]
     * StringUtil.split("a..b.c", '.')   = ["a", "b", "c"]
     * StringUtil.split("a:b:c", '.')    = ["a:b:c"]
     * StringUtil.split("a b c", ' ')    = ["a", "b", "c"]
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫ�ָ���ַ���
     * @param separatorChar �ָ���
     * @return �ָ����ַ������飬���ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String[] split(String str, char separatorChar) {
        if (str == null) {
            return null;
        }

        int length = str.length();

        if (length == 0) {
            return EMPTY_STRING_ARRAY;
        }

        List<String> list = createLinkedList();
        int i = 0;
        int start = 0;
        boolean match = false;

        while (i < length) {
            if (str.charAt(i) == separatorChar) {
                if (match) {
                    list.add(str.substring(start, i));
                    match = false;
                }

                start = ++i;
                continue;
            }

            match = true;
            i++;
        }

        if (match) {
            list.add(str.substring(start, i));
        }

        return list.toArray(new String[list.size()]);
    }

    private static List<String> createLinkedList() {
		return new LinkedList<String>();
	}

	/**
     * ���ַ�����ָ���ַ��ָ
     * <p>
     * �ָ������������Ŀ�������У������ķָ����ͱ�����һ��������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.split(null, *)                = null
     * StringUtil.split("", *)                  = []
     * StringUtil.split("abc def", null)        = ["abc", "def"]
     * StringUtil.split("abc def", " ")         = ["abc", "def"]
     * StringUtil.split("abc  def", " ")        = ["abc", "def"]
     * StringUtil.split(" ab:  cd::ef  ", ":")  = ["ab", "cd", "ef"]
     * StringUtil.split("abc.def", "")          = ["abc.def"]
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫ�ָ���ַ���
     * @param separatorChars �ָ���
     * @return �ָ����ַ������飬���ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String[] split(String str, String separatorChars) {
        return split(str, separatorChars, -1);
    }

    /**
     * ���ַ�����ָ���ַ��ָ
     * <p>
     * �ָ������������Ŀ�������У������ķָ����ͱ�����һ��������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.split(null, *, *)                 = null
     * StringUtil.split("", *, *)                   = []
     * StringUtil.split("ab cd ef", null, 0)        = ["ab", "cd", "ef"]
     * StringUtil.split("  ab   cd ef  ", null, 0)  = ["ab", "cd", "ef"]
     * StringUtil.split("ab:cd::ef", ":", 0)        = ["ab", "cd", "ef"]
     * StringUtil.split("ab:cd:ef", ":", 2)         = ["ab", "cdef"]
     * StringUtil.split("abc.def", "", 2)           = ["abc.def"]
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫ�ָ���ַ���
     * @param separatorChars �ָ���
     * @param max ���ص�����������������С�ڵ���0�����ʾ������
     * @return �ָ����ַ������飬���ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String[] split(String str, String separatorChars, int max) {
        if (str == null) {
            return null;
        }

        int length = str.length();

        if (length == 0) {
            return EMPTY_STRING_ARRAY;
        }

        List<String> list = createLinkedList();
        int sizePlus1 = 1;
        int i = 0;
        int start = 0;
        boolean match = false;

        if (separatorChars == null) {
            // null��ʾʹ�ÿհ���Ϊ�ָ���
            while (i < length) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = length;
                        }

                        list.add(str.substring(start, i));
                        match = false;
                    }

                    start = ++i;
                    continue;
                }

                match = true;
                i++;
            }
        } else if (separatorChars.length() == 1) {
            // �Ż��ָ�������Ϊ1������
            char sep = separatorChars.charAt(0);

            while (i < length) {
                if (str.charAt(i) == sep) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = length;
                        }

                        list.add(str.substring(start, i));
                        match = false;
                    }

                    start = ++i;
                    continue;
                }

                match = true;
                i++;
            }
        } else {
            // һ������
            while (i < length) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = length;
                        }

                        list.add(str.substring(start, i));
                        match = false;
                    }

                    start = ++i;
                    continue;
                }

                match = true;
                i++;
            }
        }

        if (match) {
            list.add(str.substring(start, i));
        }

        return list.toArray(new String[list.size()]);
    }

    // ==========================================================================
    // �ַ������Ӻ����� 
    //  
    // ���������ָ���ָ������ӳ��ַ����� 
    // ==========================================================================

    /**
     * �������е�Ԫ�����ӳ�һ���ַ�����
     * 
     * <pre>
     * StringUtil.join(null, *)                = null
     * StringUtil.join([], *)                  = ""
     * StringUtil.join([null], *)              = ""
     * StringUtil.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtil.join(["a", "b", "c"], null)  = "abc"
     * StringUtil.join(["a", "b", "c"], "")    = "abc"
     * StringUtil.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     * 
     * @param array Ҫ���ӵ�����
     * @param separator �ָ���
     * @return ���Ӻ���ַ��������ԭ����Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }

        if (separator == null) {
            separator = EMPTY_STRING;
        }

        int arraySize = array.length;
        int bufSize;

        if (arraySize == 0) {
            bufSize = 0;
        } else {
            int firstLength = array[0] == null ? 16 : array[0].toString().length();
            bufSize = arraySize * (firstLength + separator.length());
        }

        StringBuilder buf = new StringBuilder(bufSize);

        for (int i = 0; i < arraySize; i++) {
            if (separator != null && i > 0) {
                buf.append(separator);
            }

            if (array[i] != null) {
                buf.append(array[i]);
            }
        }

        return buf.toString();
    }

    /**
     * ��<code>Iterator</code>�е�Ԫ�����ӳ�һ���ַ�����
     * 
     * <pre>
     * StringUtil.join(null, *)                = null
     * StringUtil.join([], *)                  = ""
     * StringUtil.join([null], *)              = ""
     * StringUtil.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtil.join(["a", "b", "c"], null)  = "abc"
     * StringUtil.join(["a", "b", "c"], "")    = "abc"
     * StringUtil.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     * 
     * @param iterator Ҫ���ӵ�<code>Iterator</code>
     * @param separator �ָ���
     * @return ���Ӻ���ַ��������ԭ����Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String join(Iterable<?> list, String separator) {
        if (list == null) {
            return null;
        }

        StringBuilder buf = new StringBuilder(256); // JavaĬ��ֵ��16, ����ƫС

        for (Iterator<?> i = list.iterator(); i.hasNext();) {
            Object obj = i.next();

            if (obj != null) {
                buf.append(obj);
            }

            if (separator != null && i.hasNext()) {
                buf.append(separator);
            }
        }

        return buf.toString();
    }

    // ==========================================================================
    // �ַ������Һ��� ���� �ַ����ַ�����  
    // ==========================================================================

    /**
     * ���ַ����в���ָ���ַ��������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>��
     * 
     * <pre>
     * StringUtil.indexOf(null, *)         = -1
     * StringUtil.indexOf("", *)           = -1
     * StringUtil.indexOf("aabaabaa", 'a') = 0
     * StringUtil.indexOf("aabaabaa", 'b') = 2
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchChar Ҫ���ҵ��ַ�
     * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
     */
    public static int indexOf(String str, char searchChar) {
        if (str == null || str.length() == 0) {
            return -1;
        }

        return str.indexOf(searchChar);
    }

    /**
     * ���ַ����в���ָ���ַ��������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>��
     * 
     * <pre>
     * StringUtil.indexOf(null, *, *)          = -1
     * StringUtil.indexOf("", *, *)            = -1
     * StringUtil.indexOf("aabaabaa", 'b', 0)  = 2
     * StringUtil.indexOf("aabaabaa", 'b', 3)  = 5
     * StringUtil.indexOf("aabaabaa", 'b', 9)  = -1
     * StringUtil.indexOf("aabaabaa", 'b', -1) = 2
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchChar Ҫ���ҵ��ַ�
     * @param startPos ��ʼ����������ֵ�����С��0������0
     * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
     */
    public static int indexOf(String str, char searchChar, int startPos) {
        if (str == null || str.length() == 0) {
            return -1;
        }

        return str.indexOf(searchChar, startPos);
    }

    /**
     * ���ַ����в���ָ���ַ����������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>��
     * 
     * <pre>
     * StringUtil.indexOf(null, *)          = -1
     * StringUtil.indexOf(*, null)          = -1
     * StringUtil.indexOf("", "")           = 0
     * StringUtil.indexOf("aabaabaa", "a")  = 0
     * StringUtil.indexOf("aabaabaa", "b")  = 2
     * StringUtil.indexOf("aabaabaa", "ab") = 1
     * StringUtil.indexOf("aabaabaa", "")   = 0
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchStr Ҫ���ҵ��ַ���
     * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
     */
    public static int indexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }

        return str.indexOf(searchStr);
    }

    /**
     * ���ַ����в���ָ���ַ����������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>��
     * 
     * <pre>
     * StringUtil.indexOf(null, *, *)          = -1
     * StringUtil.indexOf(*, null, *)          = -1
     * StringUtil.indexOf("", "", 0)           = 0
     * StringUtil.indexOf("aabaabaa", "a", 0)  = 0
     * StringUtil.indexOf("aabaabaa", "b", 0)  = 2
     * StringUtil.indexOf("aabaabaa", "ab", 0) = 1
     * StringUtil.indexOf("aabaabaa", "b", 3)  = 5
     * StringUtil.indexOf("aabaabaa", "b", 9)  = -1
     * StringUtil.indexOf("aabaabaa", "b", -1) = 2
     * StringUtil.indexOf("aabaabaa", "", 2)   = 2
     * StringUtil.indexOf("abc", "", 9)        = 3
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchStr Ҫ���ҵ��ַ���
     * @param startPos ��ʼ����������ֵ�����С��0������0
     * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
     */
    public static int indexOf(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return -1;
        }

        // JDK1.3�����°汾��bug��������ȷ������������
        if (searchStr.length() == 0 && startPos >= str.length()) {
            return str.length();
        }

        return str.indexOf(searchStr, startPos);
    }

    /**
     * ���ַ����в���ָ���ַ������е��ַ��������ص�һ��ƥ�����ʼ������ ����ַ���Ϊ<code>null</code>���򷵻�
     * <code>-1</code>�� ����ַ�����Ϊ<code>null</code>��գ�Ҳ����<code>-1</code>��
     * 
     * <pre>
     * StringUtil.indexOfAny(null, *)                = -1
     * StringUtil.indexOfAny("", *)                  = -1
     * StringUtil.indexOfAny(*, null)                = -1
     * StringUtil.indexOfAny(*, [])                  = -1
     * StringUtil.indexOfAny("zzabyycdxx",['z','a']) = 0
     * StringUtil.indexOfAny("zzabyycdxx",['b','y']) = 3
     * StringUtil.indexOfAny("aba", ['z'])           = -1
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchChars Ҫ�������ַ�����
     * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
     */
    public static int indexOfAny(String str, char[] searchChars) {
        if (str == null || str.length() == 0 || searchChars == null || searchChars.length == 0) {
            return -1;
        }

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            for (char searchChar : searchChars) {
                if (searchChar == ch) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * ���ַ����в���ָ���ַ������е��ַ��������ص�һ��ƥ�����ʼ������ ����ַ���Ϊ<code>null</code>���򷵻�
     * <code>-1</code>�� ����ַ�����Ϊ<code>null</code>��գ�Ҳ����<code>-1</code>��
     * 
     * <pre>
     * StringUtil.indexOfAny(null, *)            = -1
     * StringUtil.indexOfAny("", *)              = -1
     * StringUtil.indexOfAny(*, null)            = -1
     * StringUtil.indexOfAny(*, "")              = -1
     * StringUtil.indexOfAny("zzabyycdxx", "za") = 0
     * StringUtil.indexOfAny("zzabyycdxx", "by") = 3
     * StringUtil.indexOfAny("aba","z")          = -1
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchChars Ҫ�������ַ�����
     * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
     */
    public static int indexOfAny(String str, String searchChars) {
        if (str == null || str.length() == 0 || searchChars == null || searchChars.length() == 0) {
            return -1;
        }

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            for (int j = 0; j < searchChars.length(); j++) {
                if (searchChars.charAt(j) == ch) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * ���ַ����в���ָ���ַ��������е��ַ����������ص�һ��ƥ�����ʼ������ ����ַ���Ϊ<code>null</code>���򷵻�
     * <code>-1</code>�� ����ַ�������Ϊ<code>null</code>��գ�Ҳ����<code>-1</code>��
     * ����ַ������ϰ���<code>""</code>�������ַ�����Ϊ<code>null</code>���򷵻�
     * <code>str.length()</code>
     * 
     * <pre>
     * StringUtil.indexOfAny(null, *)                     = -1
     * StringUtil.indexOfAny(*, null)                     = -1
     * StringUtil.indexOfAny(*, [])                       = -1
     * StringUtil.indexOfAny("zzabyycdxx", ["ab","cd"])   = 2
     * StringUtil.indexOfAny("zzabyycdxx", ["cd","ab"])   = 2
     * StringUtil.indexOfAny("zzabyycdxx", ["mn","op"])   = -1
     * StringUtil.indexOfAny("zzabyycdxx", ["zab","aby"]) = 1
     * StringUtil.indexOfAny("zzabyycdxx", [""])          = 0
     * StringUtil.indexOfAny("", [""])                    = 0
     * StringUtil.indexOfAny("", ["a"])                   = -1
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchStrs Ҫ�������ַ�������
     * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
     */
    public static int indexOfAny(String str, String[] searchStrs) {
        if (str == null || searchStrs == null) {
            return -1;
        }

        int sz = searchStrs.length;

        // String's can't have a MAX_VALUEth index.
        int ret = Integer.MAX_VALUE;

        int tmp = 0;

        for (int i = 0; i < sz; i++) {
            String search = searchStrs[i];

            if (search == null) {
                continue;
            }

            tmp = str.indexOf(search);

            if (tmp == -1) {
                continue;
            }

            if (tmp < ret) {
                ret = tmp;
            }
        }

        return ret == Integer.MAX_VALUE ? -1 : ret;
    }

    /**
     * ���ַ����в��Ҳ���ָ���ַ������е��ַ��������ص�һ��ƥ�����ʼ������ ����ַ���Ϊ<code>null</code>���򷵻�
     * <code>-1</code>�� ����ַ�����Ϊ<code>null</code>��գ�Ҳ����<code>-1</code>��
     * 
     * <pre>
     * StringUtil.indexOfAnyBut(null, *)             = -1
     * StringUtil.indexOfAnyBut("", *)               = -1
     * StringUtil.indexOfAnyBut(*, null)             = -1
     * StringUtil.indexOfAnyBut(*, [])               = -1
     * StringUtil.indexOfAnyBut("zzabyycdxx",'za')   = 3
     * StringUtil.indexOfAnyBut("zzabyycdxx", 'by')  = 0
     * StringUtil.indexOfAnyBut("aba", 'ab')         = -1
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchChars Ҫ�������ַ�����
     * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
     */
    public static int indexOfAnyBut(String str, char[] searchChars) {
        if (str == null || str.length() == 0 || searchChars == null || searchChars.length == 0) {
            return -1;
        }

        outer: for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            for (int j = 0; j < searchChars.length; j++) {
                if (searchChars[j] == ch) {
                    continue outer;
                }
            }

            return i;
        }

        return -1;
    }

    /**
     * ���ַ����в��Ҳ���ָ���ַ������е��ַ��������ص�һ��ƥ�����ʼ������ ����ַ���Ϊ<code>null</code>���򷵻�
     * <code>-1</code>�� ����ַ�����Ϊ<code>null</code>��գ�Ҳ����<code>-1</code>��
     * 
     * <pre>
     * StringUtil.indexOfAnyBut(null, *)            = -1
     * StringUtil.indexOfAnyBut("", *)              = -1
     * StringUtil.indexOfAnyBut(*, null)            = -1
     * StringUtil.indexOfAnyBut(*, "")              = -1
     * StringUtil.indexOfAnyBut("zzabyycdxx", "za") = 3
     * StringUtil.indexOfAnyBut("zzabyycdxx", "by") = 0
     * StringUtil.indexOfAnyBut("aba","ab")         = -1
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchChars Ҫ�������ַ�����
     * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
     */
    public static int indexOfAnyBut(String str, String searchChars) {
        if (str == null || str.length() == 0 || searchChars == null || searchChars.length() == 0) {
            return -1;
        }

        for (int i = 0; i < str.length(); i++) {
            if (searchChars.indexOf(str.charAt(i)) < 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * ���ַ���β����ʼ����ָ���ַ��������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�
     * <code>-1</code>��
     * 
     * <pre>
     * StringUtil.lastIndexOf(null, *)         = -1
     * StringUtil.lastIndexOf("", *)           = -1
     * StringUtil.lastIndexOf("aabaabaa", 'a') = 7
     * StringUtil.lastIndexOf("aabaabaa", 'b') = 5
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchChar Ҫ���ҵ��ַ�
     * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
     */
    public static int lastIndexOf(String str, char searchChar) {
        if (str == null || str.length() == 0) {
            return -1;
        }

        return str.lastIndexOf(searchChar);
    }

    /**
     * ���ַ���β����ʼ����ָ���ַ��������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�
     * <code>-1</code>��
     * 
     * <pre>
     * StringUtil.lastIndexOf(null, *, *)          = -1
     * StringUtil.lastIndexOf("", *,  *)           = -1
     * StringUtil.lastIndexOf("aabaabaa", 'b', 8)  = 5
     * StringUtil.lastIndexOf("aabaabaa", 'b', 4)  = 2
     * StringUtil.lastIndexOf("aabaabaa", 'b', 0)  = -1
     * StringUtil.lastIndexOf("aabaabaa", 'b', 9)  = 5
     * StringUtil.lastIndexOf("aabaabaa", 'b', -1) = -1
     * StringUtil.lastIndexOf("aabaabaa", 'a', 0)  = 0
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchChar Ҫ���ҵ��ַ�
     * @param startPos ��ָ��������ʼ��ǰ����
     * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
     */
    public static int lastIndexOf(String str, char searchChar, int startPos) {
        if (str == null || str.length() == 0) {
            return -1;
        }

        return str.lastIndexOf(searchChar, startPos);
    }

    /**
     * ���ַ���β����ʼ����ָ���ַ����������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�
     * <code>-1</code>��
     * 
     * <pre>
     * StringUtil.lastIndexOf(null, *)         = -1
     * StringUtil.lastIndexOf("", *)           = -1
     * StringUtil.lastIndexOf("aabaabaa", 'a') = 7
     * StringUtil.lastIndexOf("aabaabaa", 'b') = 5
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchStr Ҫ���ҵ��ַ���
     * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
     */
    public static int lastIndexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }

        return str.lastIndexOf(searchStr);
    }
    
    /**
     * ���ַ���β����ʼ����ָ���ַ����������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�
     * <code>-1</code>��
     * 
     * <pre>
     * StringUtil.lastIndexOf(null, *, *)          = -1
     * StringUtil.lastIndexOf(*, null, *)          = -1
     * StringUtil.lastIndexOf("aabaabaa", "a", 8)  = 7
     * StringUtil.lastIndexOf("aabaabaa", "b", 8)  = 5
     * StringUtil.lastIndexOf("aabaabaa", "ab", 8) = 4
     * StringUtil.lastIndexOf("aabaabaa", "b", 9)  = 5
     * StringUtil.lastIndexOf("aabaabaa", "b", -1) = -1
     * StringUtil.lastIndexOf("aabaabaa", "a", 0)  = 0
     * StringUtil.lastIndexOf("aabaabaa", "b", 0)  = -1
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchStr Ҫ���ҵ��ַ���
     * @param startPos ��ָ��������ʼ��ǰ����
     * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
     */
    public static int lastIndexOf(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return -1;
        }

        return str.lastIndexOf(searchStr, startPos);
    }

    /**
     * ���ַ���β����ʼ����ָ���ַ��������е��ַ����������ص�һ��ƥ�����ʼ������ ����ַ���Ϊ<code>null</code>���򷵻�
     * <code>-1</code>�� ����ַ�������Ϊ<code>null</code>��գ�Ҳ����<code>-1</code>��
     * ����ַ������ϰ���<code>""</code>�������ַ�����Ϊ<code>null</code>���򷵻�
     * <code>str.length()</code>
     * 
     * <pre>
     * StringUtil.lastIndexOfAny(null, *)                   = -1
     * StringUtil.lastIndexOfAny(*, null)                   = -1
     * StringUtil.lastIndexOfAny(*, [])                     = -1
     * StringUtil.lastIndexOfAny(*, [null])                 = -1
     * StringUtil.lastIndexOfAny("zzabyycdxx", ["ab","cd"]) = 6
     * StringUtil.lastIndexOfAny("zzabyycdxx", ["cd","ab"]) = 6
     * StringUtil.lastIndexOfAny("zzabyycdxx", ["mn","op"]) = -1
     * StringUtil.lastIndexOfAny("zzabyycdxx", ["mn","op"]) = -1
     * StringUtil.lastIndexOfAny("zzabyycdxx", ["mn",""])   = 10
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchStrs Ҫ�������ַ�������
     * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
     */
    public static int lastIndexOfAny(String str, String[] searchStrs) {
        if (str == null || searchStrs == null) {
            return -1;
        }

        int searchStrsLength = searchStrs.length;
        int index = -1;
        int tmp = 0;

        for (int i = 0; i < searchStrsLength; i++) {
            String search = searchStrs[i];

            if (search == null) {
                continue;
            }

            tmp = str.lastIndexOf(search);

            if (tmp > index) {
                index = tmp;
            }
        }

        return index;
    }

    /**
     * ����ַ������Ƿ����ָ�����ַ�������ַ���Ϊ<code>null</code>��������<code>false</code>��
     * 
     * <pre>
     * StringUtil.contains(null, *)    = false
     * StringUtil.contains("", *)      = false
     * StringUtil.contains("abc", 'a') = true
     * StringUtil.contains("abc", 'z') = false
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchChar Ҫ���ҵ��ַ�
     * @return ����ҵ����򷵻�<code>true</code>
     */
    public static boolean contains(String str, char searchChar) {
        if (str == null || str.length() == 0) {
            return false;
        }

        return str.indexOf(searchChar) >= 0;
    }

    /**
     * ����ַ������Ƿ����ָ�����ַ���������ַ���Ϊ<code>null</code>��������<code>false</code>��
     * 
     * <pre>
     * StringUtil.contains(null, *)     = false
     * StringUtil.contains(*, null)     = false
     * StringUtil.contains("", "")      = true
     * StringUtil.contains("abc", "")   = true
     * StringUtil.contains("abc", "a")  = true
     * StringUtil.contains("abc", "z")  = false
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchStr Ҫ���ҵ��ַ���
     * @return ����ҵ����򷵻�<code>true</code>
     */
    public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }

        return str.indexOf(searchStr) >= 0;
    }

    /**
     * ����ַ������Ƿ�ֻ����ָ���ַ������е��ַ���
     * <p>
     * ����ַ���Ϊ<code>null</code>���򷵻�<code>false</code>�� ����ַ�����Ϊ<code>null</code>
     * �򷵻�<code>false</code>�� ���ǿ��ַ�����Զ����<code>true</code>.
     * </p>
     * 
     * <pre>
     * StringUtil.containsOnly(null, *)       = false
     * StringUtil.containsOnly(*, null)       = false
     * StringUtil.containsOnly("", *)         = true
     * StringUtil.containsOnly("ab", '')      = false
     * StringUtil.containsOnly("abab", 'abc') = true
     * StringUtil.containsOnly("ab1", 'abc')  = false
     * StringUtil.containsOnly("abz", 'abc')  = false
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param valid Ҫ���ҵ��ַ���
     * @return ����ҵ����򷵻�<code>true</code>
     */
    public static boolean containsOnly(String str, char[] valid) {
        if (valid == null || str == null) {
            return false;
        }

        if (str.length() == 0) {
            return true;
        }

        if (valid.length == 0) {
            return false;
        }

        return indexOfAnyBut(str, valid) == -1;
    }

    /**
     * ����ַ������Ƿ�ֻ����ָ���ַ������е��ַ���
     * <p>
     * ����ַ���Ϊ<code>null</code>���򷵻�<code>false</code>�� ����ַ�����Ϊ<code>null</code>
     * �򷵻�<code>false</code>�� ���ǿ��ַ�����Զ����<code>true</code>.
     * </p>
     * 
     * <pre>
     * StringUtil.containsOnly(null, *)       = false
     * StringUtil.containsOnly(*, null)       = false
     * StringUtil.containsOnly("", *)         = true
     * StringUtil.containsOnly("ab", "")      = false
     * StringUtil.containsOnly("abab", "abc") = true
     * StringUtil.containsOnly("ab1", "abc")  = false
     * StringUtil.containsOnly("abz", "abc")  = false
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param valid Ҫ���ҵ��ַ���
     * @return ����ҵ����򷵻�<code>true</code>
     */
    public static boolean containsOnly(String str, String valid) {
        if (str == null || valid == null) {
            return false;
        }

        return containsOnly(str, valid.toCharArray());
    }

    /**
     * ����ַ������Ƿ񲻰���ָ���ַ������е��ַ���
     * <p>
     * ����ַ���Ϊ<code>null</code>���򷵻�<code>false</code>�� ����ַ�����Ϊ<code>null</code>
     * �򷵻�<code>true</code>�� ���ǿ��ַ�����Զ����<code>true</code>.
     * </p>
     * 
     * <pre>
     * StringUtil.containsNone(null, *)       = true
     * StringUtil.containsNone(*, null)       = true
     * StringUtil.containsNone("", *)         = true
     * StringUtil.containsNone("ab", '')      = true
     * StringUtil.containsNone("abab", 'xyz') = true
     * StringUtil.containsNone("ab1", 'xyz')  = true
     * StringUtil.containsNone("abz", 'xyz')  = false
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param invalid Ҫ���ҵ��ַ���
     * @return ����ҵ����򷵻�<code>true</code>
     */
    public static boolean containsNone(String str, char[] invalid) {
        if (str == null || invalid == null) {
            return true;
        }

        int strSize = str.length();
        int validSize = invalid.length;

        for (int i = 0; i < strSize; i++) {
            char ch = str.charAt(i);

            for (int j = 0; j < validSize; j++) {
                if (invalid[j] == ch) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * ����ַ������Ƿ񲻰���ָ���ַ������е��ַ���
     * <p>
     * ����ַ���Ϊ<code>null</code>���򷵻�<code>false</code>�� ����ַ�����Ϊ<code>null</code>
     * �򷵻�<code>true</code>�� ���ǿ��ַ�����Զ����<code>true</code>.
     * </p>
     * 
     * <pre>
     * StringUtil.containsNone(null, *)       = true
     * StringUtil.containsNone(*, null)       = true
     * StringUtil.containsNone("", *)         = true
     * StringUtil.containsNone("ab", "")      = true
     * StringUtil.containsNone("abab", "xyz") = true
     * StringUtil.containsNone("ab1", "xyz")  = true
     * StringUtil.containsNone("abz", "xyz")  = false
     * </pre>
     * 
     * @param str Ҫɨ����ַ���
     * @param invalidChars Ҫ���ҵ��ַ���
     * @return ����ҵ����򷵻�<code>true</code>
     */
    public static boolean containsNone(String str, String invalidChars) {
        if (str == null || invalidChars == null) {
            return true;
        }

        return containsNone(str, invalidChars.toCharArray());
    }

    /**
     * ȡ��ָ���Ӵ����ַ����г��ֵĴ�����
     * <p>
     * ����ַ���Ϊ<code>null</code>��գ��򷵻�<code>0</code>��
     * 
     * <pre>
     * StringUtil.countMatches(null, *)       = 0
     * StringUtil.countMatches("", *)         = 0
     * StringUtil.countMatches("abba", null)  = 0
     * StringUtil.countMatches("abba", "")    = 0
     * StringUtil.countMatches("abba", "a")   = 2
     * StringUtil.countMatches("abba", "ab")  = 1
     * StringUtil.countMatches("abba", "xxx") = 0
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫɨ����ַ���
     * @param subStr ���ַ���
     * @return �Ӵ����ַ����г��ֵĴ���������ַ���Ϊ<code>null</code>��գ��򷵻�<code>0</code>
     */
    public static int countMatches(String str, String subStr) {
        if (str == null || str.length() == 0 || subStr == null || subStr.length() == 0) {
            return 0;
        }

        int count = 0;
        int index = 0;

        while ((index = str.indexOf(subStr, index)) != -1) {
            count++;
            index += subStr.length();
        }

        return count;
    }

    // ==========================================================================
    // ȡ�Ӵ�������
    // ==========================================================================

    /**
     * ȡָ���ַ������Ӵ���
     * <p>
     * �������������β����ʼ���㡣����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.substring(null, *)   = null
     * StringUtil.substring("", *)     = ""
     * StringUtil.substring("abc", 0)  = "abc"
     * StringUtil.substring("abc", 2)  = "c"
     * StringUtil.substring("abc", 4)  = ""
     * StringUtil.substring("abc", -2) = "bc"
     * StringUtil.substring("abc", -4) = "abc"
     * </pre>
     * 
     * </p>
     * 
     * @param str �ַ���
     * @param start ��ʼ���������Ϊ��������ʾ��β������
     * @return �Ӵ������ԭʼ��Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String substring(String str, int start) {
        if (str == null) {
            return null;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }

        if (start > str.length()) {
            return EMPTY_STRING;
        }

        return str.substring(start);
    }

    /**
     * ȡָ���ַ������Ӵ���
     * <p>
     * �������������β����ʼ���㡣����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.substring(null, *, *)    = null
     * StringUtil.substring("", * ,  *)    = "";
     * StringUtil.substring("abc", 0, 2)   = "ab"
     * StringUtil.substring("abc", 2, 0)   = ""
     * StringUtil.substring("abc", 2, 4)   = "c"
     * StringUtil.substring("abc", 4, 6)   = ""
     * StringUtil.substring("abc", 2, 2)   = ""
     * StringUtil.substring("abc", -2, -1) = "b"
     * StringUtil.substring("abc", -4, 2)  = "ab"
     * </pre>
     * 
     * </p>
     * 
     * @param str �ַ���
     * @param start ��ʼ���������Ϊ��������ʾ��β������
     * @param end ���������������������Ϊ��������ʾ��β������
     * @return �Ӵ������ԭʼ��Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        if (end < 0) {
            end = str.length() + end;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return EMPTY_STRING;
        }

        if (start < 0) {
            start = 0;
        }

        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * ȡ�ó���Ϊָ���ַ���������ߵ��Ӵ���
     * 
     * <pre>
     * StringUtil.left(null, *)    = null
     * StringUtil.left(*, -ve)     = ""
     * StringUtil.left("", *)      = ""
     * StringUtil.left("abc", 0)   = ""
     * StringUtil.left("abc", 2)   = "ab"
     * StringUtil.left("abc", 4)   = "abc"
     * </pre>
     * 
     * @param str �ַ���
     * @param len �����Ӵ��ĳ���
     * @return �Ӵ������ԭʼ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String left(String str, int len) {
        if (str == null) {
            return null;
        }

        if (len < 0) {
            return EMPTY_STRING;
        }

        if (str.length() <= len) {
            return str;
        } else {
            return str.substring(0, len);
        }
    }

    /**
     * ȡ�ó���Ϊָ���ַ��������ұߵ��Ӵ���
     * 
     * <pre>
     * StringUtil.right(null, *)    = null
     * StringUtil.right(*, -ve)     = ""
     * StringUtil.right("", *)      = ""
     * StringUtil.right("abc", 0)   = ""
     * StringUtil.right("abc", 2)   = "bc"
     * StringUtil.right("abc", 4)   = "abc"
     * </pre>
     * 
     * @param str �ַ���
     * @param len �����Ӵ��ĳ���
     * @return �Ӵ������ԭʼ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String right(String str, int len) {
        if (str == null) {
            return null;
        }

        if (len < 0) {
            return EMPTY_STRING;
        }

        if (str.length() <= len) {
            return str;
        } else {
            return str.substring(str.length() - len);
        }
    }

    /**
     * ȡ�ô�ָ��������ʼ����ġ�����Ϊָ���ַ������Ӵ���
     * 
     * <pre>
     * StringUtil.mid(null, *, *)    = null
     * StringUtil.mid(*, *, -ve)     = ""
     * StringUtil.mid("", 0, *)      = ""
     * StringUtil.mid("abc", 0, 2)   = "ab"
     * StringUtil.mid("abc", 0, 4)   = "abc"
     * StringUtil.mid("abc", 2, 4)   = "c"
     * StringUtil.mid("abc", 4, 2)   = ""
     * StringUtil.mid("abc", -2, 2)  = "ab"
     * </pre>
     * 
     * @param str �ַ���
     * @param pos ��ʼ���������Ϊ����������<code>0</code>
     * @param len �Ӵ��ĳ��ȣ����Ϊ��������������Ϊ<code>0</code>
     * @return �Ӵ������ԭʼ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String mid(String str, int pos, int len) {
        if (str == null) {
            return null;
        }

        if (len < 0 || pos > str.length()) {
            return EMPTY_STRING;
        }

        if (pos < 0) {
            pos = 0;
        }

        if (str.length() <= pos + len) {
            return str.substring(pos);
        } else {
            return str.substring(pos, pos + len);
        }
    }

    // ==========================================================================
    // ������ȡ�Ӵ�������
    // ==========================================================================

    /**
     * ȡ�õ�һ�����ֵķָ��Ӵ�֮ǰ���Ӵ���
     * <p>
     * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ����ָ��Ӵ�Ϊ<code>null</code>
     * ��δ�ҵ����Ӵ����򷵻�ԭ�ַ�����
     * 
     * <pre>
     * StringUtil.substringBefore(null, *)      = null
     * StringUtil.substringBefore("", *)        = ""
     * StringUtil.substringBefore("abc", "a")   = ""
     * StringUtil.substringBefore("abcba", "b") = "a"
     * StringUtil.substringBefore("abc", "c")   = "ab"
     * StringUtil.substringBefore("abc", "d")   = "abc"
     * StringUtil.substringBefore("abc", "")    = ""
     * StringUtil.substringBefore("abc", null)  = "abc"
     * </pre>
     * 
     * </p>
     * 
     * @param str �ַ���
     * @param separator Ҫ�����ķָ��Ӵ�
     * @return �Ӵ������ԭʼ��Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String substringBefore(String str, String separator) {
        if (str == null || separator == null || str.length() == 0) {
            return str;
        }

        if (separator.length() == 0) {
            return EMPTY_STRING;
        }

        int pos = str.indexOf(separator);

        if (pos == -1) {
            return str;
        }

        return str.substring(0, pos);
    }

    /**
     * ȡ�õ�һ�����ֵķָ��Ӵ�֮����Ӵ���
     * <p>
     * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ����ָ��Ӵ�Ϊ<code>null</code>
     * ��δ�ҵ����Ӵ����򷵻�ԭ�ַ�����
     * 
     * <pre>
     * StringUtil.substringAfter(null, *)      = null
     * StringUtil.substringAfter("", *)        = ""
     * StringUtil.substringAfter(*, null)      = ""
     * StringUtil.substringAfter("abc", "a")   = "bc"
     * StringUtil.substringAfter("abcba", "b") = "cba"
     * StringUtil.substringAfter("abc", "c")   = ""
     * StringUtil.substringAfter("abc", "d")   = ""
     * StringUtil.substringAfter("abc", "")    = "abc"
     * </pre>
     * 
     * </p>
     * 
     * @param str �ַ���
     * @param separator Ҫ�����ķָ��Ӵ�
     * @return �Ӵ������ԭʼ��Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String substringAfter(String str, String separator) {
        if (str == null || str.length() == 0) {
            return str;
        }

        if (separator == null) {
            return EMPTY_STRING;
        }

        int pos = str.indexOf(separator);

        if (pos == -1) {
            return EMPTY_STRING;
        }

        return str.substring(pos + separator.length());
    }

    /**
     * ȡ�����һ���ķָ��Ӵ�֮ǰ���Ӵ���
     * <p>
     * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ����ָ��Ӵ�Ϊ<code>null</code>
     * ��δ�ҵ����Ӵ����򷵻�ԭ�ַ�����
     * 
     * <pre>
     * StringUtil.substringBeforeLast(null, *)      = null
     * StringUtil.substringBeforeLast("", *)        = ""
     * StringUtil.substringBeforeLast("abcba", "b") = "abc"
     * StringUtil.substringBeforeLast("abc", "c")   = "ab"
     * StringUtil.substringBeforeLast("a", "a")     = ""
     * StringUtil.substringBeforeLast("a", "z")     = "a"
     * StringUtil.substringBeforeLast("a", null)    = "a"
     * StringUtil.substringBeforeLast("a", "")      = "a"
     * </pre>
     * 
     * </p>
     * 
     * @param str �ַ���
     * @param separator Ҫ�����ķָ��Ӵ�
     * @return �Ӵ������ԭʼ��Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String substringBeforeLast(String str, String separator) {
        if (str == null || separator == null || str.length() == 0 || separator.length() == 0) {
            return str;
        }

        int pos = str.lastIndexOf(separator);

        if (pos == -1) {
            return str;
        }

        return str.substring(0, pos);
    }

    /**
     * ȡ�����һ���ķָ��Ӵ�֮����Ӵ���
     * <p>
     * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ����ָ��Ӵ�Ϊ<code>null</code>
     * ��δ�ҵ����Ӵ����򷵻�ԭ�ַ�����
     * 
     * <pre>
     * StringUtil.substringAfterLast(null, *)      = null
     * StringUtil.substringAfterLast("", *)        = ""
     * StringUtil.substringAfterLast(*, "")        = ""
     * StringUtil.substringAfterLast(*, null)      = ""
     * StringUtil.substringAfterLast("abc", "a")   = "bc"
     * StringUtil.substringAfterLast("abcba", "b") = "a"
     * StringUtil.substringAfterLast("abc", "c")   = ""
     * StringUtil.substringAfterLast("a", "a")     = ""
     * StringUtil.substringAfterLast("a", "z")     = ""
     * </pre>
     * 
     * </p>
     * 
     * @param str �ַ���
     * @param separator Ҫ�����ķָ��Ӵ�
     * @return �Ӵ������ԭʼ��Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String substringAfterLast(String str, String separator) {
        if (str == null || str.length() == 0) {
            return str;
        }

        if (separator == null || separator.length() == 0) {
            return EMPTY_STRING;
        }

        int pos = str.lastIndexOf(separator);

        if (pos == -1 || pos == str.length() - separator.length()) {
            return EMPTY_STRING;
        }

        return str.substring(pos + separator.length());
    }

    /**
     * ȡ��ָ���ָ�����ǰ���γ���֮����Ӵ���
     * <p>
     * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ����ָ��Ӵ�Ϊ<code>null</code>
     * ���򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.substringBetween(null, *)            = null
     * StringUtil.substringBetween("", "")             = ""
     * StringUtil.substringBetween("", "tag")          = null
     * StringUtil.substringBetween("tagabctag", null)  = null
     * StringUtil.substringBetween("tagabctag", "")    = ""
     * StringUtil.substringBetween("tagabctag", "tag") = "abc"
     * </pre>
     * 
     * </p>
     * 
     * @param str �ַ���
     * @param tag Ҫ�����ķָ��Ӵ�
     * @return �Ӵ������ԭʼ��Ϊ<code>null</code>��δ�ҵ��ָ��Ӵ����򷵻�<code>null</code>
     */
    public static String substringBetween(String str, String tag) {
        return substringBetween(str, tag, tag, 0);
    }

    /**
     * ȡ�������ָ���֮����Ӵ���
     * <p>
     * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ����ָ��Ӵ�Ϊ<code>null</code>
     * ���򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.substringBetween(null, *, *)          = null
     * StringUtil.substringBetween("", "", "")          = ""
     * StringUtil.substringBetween("", "", "tag")       = null
     * StringUtil.substringBetween("", "tag", "tag")    = null
     * StringUtil.substringBetween("yabcz", null, null) = null
     * StringUtil.substringBetween("yabcz", "", "")     = ""
     * StringUtil.substringBetween("yabcz", "y", "z")   = "abc"
     * StringUtil.substringBetween("yabczyabcz", "y", "z")   = "abc"
     * </pre>
     * 
     * </p>
     * 
     * @param str �ַ���
     * @param open Ҫ�����ķָ��Ӵ�1
     * @param close Ҫ�����ķָ��Ӵ�2
     * @return �Ӵ������ԭʼ��Ϊ<code>null</code>��δ�ҵ��ָ��Ӵ����򷵻�<code>null</code>
     */
    public static String substringBetween(String str, String open, String close) {
        return substringBetween(str, open, close, 0);
    }

    /**
     * ȡ�������ָ���֮����Ӵ���
     * <p>
     * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ����ָ��Ӵ�Ϊ<code>null</code>
     * ���򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.substringBetween(null, *, *)          = null
     * StringUtil.substringBetween("", "", "")          = ""
     * StringUtil.substringBetween("", "", "tag")       = null
     * StringUtil.substringBetween("", "tag", "tag")    = null
     * StringUtil.substringBetween("yabcz", null, null) = null
     * StringUtil.substringBetween("yabcz", "", "")     = ""
     * StringUtil.substringBetween("yabcz", "y", "z")   = "abc"
     * StringUtil.substringBetween("yabczyabcz", "y", "z")   = "abc"
     * </pre>
     * 
     * </p>
     * 
     * @param str �ַ���
     * @param open Ҫ�����ķָ��Ӵ�1
     * @param close Ҫ�����ķָ��Ӵ�2
     * @param fromIndex ��ָ��index������
     * @return �Ӵ������ԭʼ��Ϊ<code>null</code>��δ�ҵ��ָ��Ӵ����򷵻�<code>null</code>
     */
    public static String substringBetween(String str, String open, String close, int fromIndex) {
        if (str == null || open == null || close == null) {
            return null;
        }

        int start = str.indexOf(open, fromIndex);

        if (start != -1) {
            int end = str.indexOf(close, start + open.length());

            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }

        return null;
    }

    // ==========================================================================
    // ɾ���ַ��� 
    // ==========================================================================

    /**
     * ɾ��������<code>Character.isWhitespace(char)</code>��������Ŀհס�
     * 
     * <pre>
     * StringUtil.deleteWhitespace(null)         = null
     * StringUtil.deleteWhitespace("")           = ""
     * StringUtil.deleteWhitespace("abc")        = "abc"
     * StringUtil.deleteWhitespace("   ab  c  ") = "abc"
     * </pre>
     * 
     * @param str Ҫ������ַ���
     * @return ȥ�հ׺���ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String deleteWhitespace(String str) {
        if (str == null) {
            return null;
        }

        int sz = str.length();
        StringBuilder buffer = new StringBuilder(sz);

        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                buffer.append(str.charAt(i));
            }
        }

        return buffer.toString();
    }

    // ==========================================================================
    // �滻�Ӵ��� 
    // ==========================================================================

    /**
     * �滻ָ�����Ӵ����滻���г��ֵ��Ӵ���
     * <p>
     * ����ַ���Ϊ<code>null</code>�򷵻�<code>null</code>�����ָ���Ӵ�Ϊ<code>null</code>
     * ���򷵻�ԭ�ַ�����
     * 
     * <pre>
     * StringUtil.replace(null, *, *)        = null
     * StringUtil.replace("", *, *)          = ""
     * StringUtil.replace("aba", null, null) = "aba"
     * StringUtil.replace("aba", null, null) = "aba"
     * StringUtil.replace("aba", "a", null)  = "aba"
     * StringUtil.replace("aba", "a", "")    = "b"
     * StringUtil.replace("aba", "a", "z")   = "zbz"
     * </pre>
     * 
     * </p>
     * 
     * @param text Ҫɨ����ַ���
     * @param repl Ҫ�������Ӵ�
     * @param with �滻�ַ���
     * @return ���滻����ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String replace(String text, String repl, String with) {
        return replace(text, repl, with, -1);
    }

    /**
     * �滻ָ�����Ӵ����滻ָ���Ĵ�����
     * <p>
     * ����ַ���Ϊ<code>null</code>�򷵻�<code>null</code>�����ָ���Ӵ�Ϊ<code>null</code>
     * ���򷵻�ԭ�ַ�����
     * 
     * <pre>
     * StringUtil.replace(null, *, *, *)         = null
     * StringUtil.replace("", *, *, *)           = ""
     * StringUtil.replace("abaa", null, null, 1) = "abaa"
     * StringUtil.replace("abaa", null, null, 1) = "abaa"
     * StringUtil.replace("abaa", "a", null, 1)  = "abaa"
     * StringUtil.replace("abaa", "a", "", 1)    = "baa"
     * StringUtil.replace("abaa", "a", "z", 0)   = "abaa"
     * StringUtil.replace("abaa", "a", "z", 1)   = "zbaa"
     * StringUtil.replace("abaa", "a", "z", 2)   = "zbza"
     * StringUtil.replace("abaa", "a", "z", -1)  = "zbzz"
     * </pre>
     * 
     * </p>
     * 
     * @param text Ҫɨ����ַ���
     * @param repl Ҫ�������Ӵ�
     * @param with �滻�ַ���
     * @param max maximum number of values to replace, or <code>-1</code> if no
     *            maximum
     * @return ���滻����ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String replace(String text, String repl, String with, int max) {
        if (text == null || repl == null || with == null || repl.length() == 0 || max == 0) {
            return text;
        }

        StringBuilder buf = new StringBuilder(text.length());
        int start = 0;
        int end = 0;

        while ((end = text.indexOf(repl, start)) != -1) {
            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();

            if (--max == 0) {
                break;
            }
        }

        buf.append(text.substring(start));

        return buf.toString();
    }

    /**
     * ���ַ���������ָ�����ַ����滻����һ����
     * <p>
     * ����ַ���Ϊ<code>null</code>�򷵻�<code>null</code>��
     * 
     * <pre>
     * StringUtil.replaceChars(null, *, *)        = null
     * StringUtil.replaceChars("", *, *)          = ""
     * StringUtil.replaceChars("abcba", 'b', 'y') = "aycya"
     * StringUtil.replaceChars("abcba", 'z', 'y') = "abcba"
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchChar Ҫ�������ַ�
     * @param replaceChar �滻�ַ�
     * @return ���滻����ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String replaceChar(String str, char searchChar, char replaceChar) {
        if (str == null) {
            return null;
        }

        return str.replace(searchChar, replaceChar);
    }

    /**
     * ���ַ���������ָ�����ַ����滻����һ����
     * <p>
     * ����ַ���Ϊ<code>null</code>�򷵻�<code>null</code>����������ַ���Ϊ<code>null</code>
     * ��գ��򷵻�ԭ�ַ�����
     * </p>
     * <p>
     * ���磺
     * <code>replaceChars(&quot;hello&quot;, &quot;ho&quot;, &quot;jy&quot;) = jelly</code>
     * ��
     * </p>
     * <p>
     * ͨ�������ַ������滻�ַ����ǵȳ��ģ���������ַ������滻�ַ��������������ַ�����ɾ���� ��������ַ������滻�ַ����̣���ȱ�ٵ��ַ��������ԡ�
     * 
     * <pre>
     * StringUtil.replaceChars(null, *, *)           = null
     * StringUtil.replaceChars("", *, *)             = ""
     * StringUtil.replaceChars("abc", null, *)       = "abc"
     * StringUtil.replaceChars("abc", "", *)         = "abc"
     * StringUtil.replaceChars("abc", "b", null)     = "ac"
     * StringUtil.replaceChars("abc", "b", "")       = "ac"
     * StringUtil.replaceChars("abcba", "bc", "yz")  = "ayzya"
     * StringUtil.replaceChars("abcba", "bc", "y")   = "ayya"
     * StringUtil.replaceChars("abcba", "bc", "yzx") = "ayzya"
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫɨ����ַ���
     * @param searchChars Ҫ�������ַ���
     * @param replaceChars �滻�ַ���
     * @return ���滻����ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String replaceChars(String str, String searchChars, String replaceChars) {
        if (str == null || str.length() == 0 || searchChars == null || searchChars.length() == 0) {
            return str;
        }

        char[] chars = str.toCharArray();
        int len = chars.length;
        boolean modified = false;

        for (int i = 0, isize = searchChars.length(); i < isize; i++) {
            char searchChar = searchChars.charAt(i);

            if (replaceChars == null || i >= replaceChars.length()) {
                // ɾ��
                int pos = 0;

                for (int j = 0; j < len; j++) {
                    if (chars[j] != searchChar) {
                        chars[pos++] = chars[j];
                    } else {
                        modified = true;
                    }
                }

                len = pos;
            } else {
                // �滻
                for (int j = 0; j < len; j++) {
                    if (chars[j] == searchChar) {
                        chars[j] = replaceChars.charAt(i);
                        modified = true;
                    }
                }
            }
        }

        if (!modified) {
            return str;
        }

        return new String(chars, 0, len);
    }

    /**
     * ��ָ�����Ӵ�����һָ���Ӵ����ǡ�
     * <p>
     * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ��������ֵ��������<code>0</code>
     * ��Խ�������ֵ�������ó��ַ����ĳ�����ͬ��ֵ��
     * 
     * <pre>
     * StringUtil.overlay(null, *, *, *)            = null
     * StringUtil.overlay("", "abc", 0, 0)          = "abc"
     * StringUtil.overlay("abcdef", null, 2, 4)     = "abef"
     * StringUtil.overlay("abcdef", "", 2, 4)       = "abef"
     * StringUtil.overlay("abcdef", "", 4, 2)       = "abef"
     * StringUtil.overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
     * StringUtil.overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
     * StringUtil.overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
     * StringUtil.overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
     * StringUtil.overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
     * StringUtil.overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫɨ����ַ���
     * @param overlay �������ǵ��ַ���
     * @param start ��ʼ����
     * @param end ��������
     * @return �����Ǻ���ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String overlay(String str, String overlay, int start, int end) {
        if (str == null) {
            return null;
        }

        if (overlay == null) {
            overlay = EMPTY_STRING;
        }

        int len = str.length();

        if (start < 0) {
            start = 0;
        }

        if (start > len) {
            start = len;
        }

        if (end < 0) {
            end = 0;
        }

        if (end > len) {
            end = len;
        }

        if (start > end) {
            int temp = start;

            start = end;
            end = temp;
        }

        return new StringBuilder(len + start - end + overlay.length() + 1).append(str.substring(0, start))
                .append(overlay).append(str.substring(end)).toString();
    }

    // ==========================================================================
    // �ظ��ַ����� 
    // ==========================================================================

    /**
     * ��ָ���ַ����ظ�n�顣
     * 
     * <pre>
     * StringUtil.repeat(null, 2)   = null
     * StringUtil.repeat("", 0)     = ""
     * StringUtil.repeat("", 2)     = ""
     * StringUtil.repeat("a", 3)    = "aaa"
     * StringUtil.repeat("ab", 2)   = "abab"
     * StringUtil.repeat("abcd", 2) = "abcdabcd"
     * StringUtil.repeat("a", -2)   = ""
     * </pre>
     * 
     * @param str Ҫ�ظ����ַ���
     * @param repeat �ظ����������С��<code>0</code>������<code>0</code>
     * @return �ظ�n�ε��ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String repeat(String str, int repeat) {
        if (str == null) {
            return null;
        }

        if (repeat <= 0) {
            return EMPTY_STRING;
        }

        int inputLength = str.length();

        if (repeat == 1 || inputLength == 0) {
            return str;
        }

        int outputLength = inputLength * repeat;

        switch (inputLength) {
            case 1:

                char ch = str.charAt(0);
                char[] output1 = new char[outputLength];

                for (int i = repeat - 1; i >= 0; i--) {
                    output1[i] = ch;
                }

                return new String(output1);

            case 2:

                char ch0 = str.charAt(0);
                char ch1 = str.charAt(1);
                char[] output2 = new char[outputLength];

                for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
                    output2[i] = ch0;
                    output2[i + 1] = ch1;
                }

                return new String(output2);

            default:

                StringBuilder buf = new StringBuilder(outputLength);

                for (int i = 0; i < repeat; i++) {
                    buf.append(str);
                }

                return buf.toString();
        }
    }

    // ==========================================================================
    // Perl����chomp��chop������
    // ==========================================================================

    /**
     * ɾ���ַ���ĩβ�Ļ��з�������ַ������Ի��н�β����ʲôҲ������
     * <p>
     * ���з����������Σ�&quot;<code>\n</code>&quot;��&quot;<code>\r</code>&quot;��&quot;
     * <code>\r\n</code>&quot;��
     * 
     * <pre>
     * StringUtil.chomp(null)          = null
     * StringUtil.chomp("")            = ""
     * StringUtil.chomp("abc \r")      = "abc "
     * StringUtil.chomp("abc\n")       = "abc"
     * StringUtil.chomp("abc\r\n")     = "abc"
     * StringUtil.chomp("abc\r\n\r\n") = "abc\r\n"
     * StringUtil.chomp("abc\n\r")     = "abc\n"
     * StringUtil.chomp("abc\n\rabc")  = "abc\n\rabc"
     * StringUtil.chomp("\r")          = ""
     * StringUtil.chomp("\n")          = ""
     * StringUtil.chomp("\r\n")        = ""
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫ������ַ���
     * @return ���Ի��н�β���ַ��������ԭʼ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String chomp(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        if (str.length() == 1) {
            char ch = str.charAt(0);

            if (ch == '\r' || ch == '\n') {
                return EMPTY_STRING;
            } else {
                return str;
            }
        }

        int lastIdx = str.length() - 1;
        char last = str.charAt(lastIdx);

        if (last == '\n') {
            if (str.charAt(lastIdx - 1) == '\r') {
                lastIdx--;
            }
        } else if (last == '\r') {
        } else {
            lastIdx++;
        }

        return str.substring(0, lastIdx);
    }

    /**
     * ɾ���ַ���ĩβ��ָ���ַ���������ַ������Ը��ַ�����β����ʲôҲ������
     * 
     * <pre>
     * StringUtil.chomp(null, *)         = null
     * StringUtil.chomp("", *)           = ""
     * StringUtil.chomp("foobar", "bar") = "foo"
     * StringUtil.chomp("foobar", "baz") = "foobar"
     * StringUtil.chomp("foo", "foo")    = ""
     * StringUtil.chomp("foo ", "foo")   = "foo "
     * StringUtil.chomp(" foo", "foo")   = " "
     * StringUtil.chomp("foo", "foooo")  = "foo"
     * StringUtil.chomp("foo", "")       = "foo"
     * StringUtil.chomp("foo", null)     = "foo"
     * </pre>
     * 
     * @param str Ҫ������ַ���
     * @param separator Ҫɾ�����ַ���
     * @return ����ָ���ַ�����β���ַ��������ԭʼ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String chomp(String str, String separator) {
        if (str == null || str.length() == 0 || separator == null) {
            return str;
        }

        if (str.endsWith(separator)) {
            return str.substring(0, str.length() - separator.length());
        }

        return str;
    }

    /**
     * ɾ�����һ���ַ���
     * <p>
     * ����ַ�����<code>\r\n</code>��β����ͬʱɾ�����ǡ�
     * 
     * <pre>
     * StringUtil.chop(null)          = null
     * StringUtil.chop("")            = ""
     * StringUtil.chop("abc \r")      = "abc "
     * StringUtil.chop("abc\n")       = "abc"
     * StringUtil.chop("abc\r\n")     = "abc"
     * StringUtil.chop("abc")         = "ab"
     * StringUtil.chop("abc\nabc")    = "abc\nab"
     * StringUtil.chop("a")           = ""
     * StringUtil.chop("\r")          = ""
     * StringUtil.chop("\n")          = ""
     * StringUtil.chop("\r\n")        = ""
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫ������ַ���
     * @return ɾ�����һ���ַ����ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String chop(String str) {
        if (str == null) {
            return null;
        }

        int strLen = str.length();

        if (strLen < 2) {
            return EMPTY_STRING;
        }

        int lastIdx = strLen - 1;
        String ret = str.substring(0, lastIdx);
        char last = str.charAt(lastIdx);

        if (last == '\n') {
            if (ret.charAt(lastIdx - 1) == '\r') {
                return ret.substring(0, lastIdx - 1);
            }
        }

        return ret;
    }

    // ==========================================================================
    // ��ת�ַ�����
    // ==========================================================================

    /**
     * ��ת�ַ����е��ַ�˳��
     * <p>
     * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
     * </p>
     * 
     * <pre>
     * StringUtil.reverse(null)  = null
     * StringUtil.reverse("")    = ""
     * StringUtil.reverse("bat") = "tab"
     * </pre>
     * 
     * @param str Ҫ��ת���ַ���
     * @return ��ת����ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String reverse(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        return new StringBuilder(str).reverse().toString();
    }

    /**
     * ��תָ���ָ����ָ��ĸ��Ӵ���˳��
     * <p>
     * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
     * </p>
     * 
     * <pre>
     * StringUtil.reverseDelimited(null, *)      = null
     * StringUtil.reverseDelimited("", *)        = ""
     * StringUtil.reverseDelimited("a.b.c", 'x') = "a.b.c"
     * StringUtil.reverseDelimited("a.b.c", '.') = "c.b.a"
     * </pre>
     * 
     * @param str Ҫ��ת���ַ���
     * @param separatorChar �ָ���
     * @return ��ת����ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String reverseDelimited(String str, char separatorChar) {
        if (str == null) {
            return null;
        }

        String[] strs = split(str, separatorChar);

        //TODO
//        arrayReverse(strs);

        return join(strs, String.valueOf(separatorChar));
    }

    /**
     * ��תָ���ָ����ָ��ĸ��Ӵ���˳��
     * <p>
     * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
     * </p>
     * 
     * <pre>
     * StringUtil.reverseDelimited(null, *, *)          = null
     * StringUtil.reverseDelimited("", *, *)            = ""
     * StringUtil.reverseDelimited("a.b.c", null, null) = "a.b.c"
     * StringUtil.reverseDelimited("a.b.c", "", null)   = "a.b.c"
     * StringUtil.reverseDelimited("a.b.c", ".", ",")   = "c,b,a"
     * StringUtil.reverseDelimited("a.b.c", ".", null)  = "c b a"
     * </pre>
     * 
     * @param str Ҫ��ת���ַ���
     * @param separatorChars �ָ��������Ϊ<code>null</code>����Ĭ��ʹ�ÿհ��ַ�
     * @param separator ���������Ӵ��ķָ��������Ϊ<code>null</code>��Ĭ��ʹ�ÿո�
     * @return ��ת����ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String reverseDelimited(String str, String separatorChars, String separator) {
        if (str == null) {
            return null;
        }

        String[] strs = split(str, separatorChars);

//      TODO
//        arrayReverse(strs);

        if (separator == null) {
            return join(strs, " ");
        }

        return join(strs, separator);
    }

    // ==========================================================================
    // ȡ���ַ��������ԡ�
    // ==========================================================================

    /**
     * ���ַ���ת����ָ�����ȵ����ԣ����磺
     * ��"Now is the time for all good men"ת����"Now is the time for..."��
     * <ul>
     * <li>���<code>str</code>��<code>maxWidth</code>�̣�ֱ�ӷ��أ�</li>
     * <li>������ת�������ԣ�<code>substring(str, 0, max-3) + "..."</code>��</li>
     * <li>���<code>maxWidth</code>С��<code>4</code>�׳�
     * <code>IllegalArgumentException</code>��</li>
     * <li>���ص��ַ��������ܳ���ָ����<code>maxWidth</code>��</li>
     * </ul>
     * 
     * <pre>
     * StringUtil.abbreviate(null, *)      = null
     * StringUtil.abbreviate("", 4)        = ""
     * StringUtil.abbreviate("abcdefg", 6) = "abc..."
     * StringUtil.abbreviate("abcdefg", 7) = "abcdefg"
     * StringUtil.abbreviate("abcdefg", 8) = "abcdefg"
     * StringUtil.abbreviate("abcdefg", 4) = "a..."
     * StringUtil.abbreviate("abcdefg", 3) = IllegalArgumentException
     * </pre>
     * 
     * @param str Ҫ�����ַ���
     * @param maxWidth ��󳤶ȣ���С��<code>4</code>�����С��<code>4</code>������
     *            <code>4</code>
     * @return �ַ������ԣ����ԭʼ�ַ���Ϊ<code>null</code>�򷵻�<code>null</code>
     */
    public static String abbreviate(String str, int maxWidth) {
        return abbreviate(str, 0, maxWidth);
    }

    /**
     * ���ַ���ת����ָ�����ȵ����ԣ����磺
     * ��"Now is the time for all good men"ת����"...is the time for..."��
     * <p>
     * ��<code>abbreviate(String, int)</code>���ƣ�����������һ������߽硱ƫ������
     * ע�⣬����߽硱�����ַ�δ�س����ڽ���ַ���������ߣ���һ�������ڽ���ַ����С�
     * </p>
     * <p>
     * ���ص��ַ��������ܳ���ָ����<code>maxWidth</code>��
     * 
     * <pre>
     * StringUtil.abbreviate(null, *, *)                = null
     * StringUtil.abbreviate("", 0, 4)                  = ""
     * StringUtil.abbreviate("abcdefghijklmno", -1, 10) = "abcdefg..."
     * StringUtil.abbreviate("abcdefghijklmno", 0, 10)  = "abcdefg..."
     * StringUtil.abbreviate("abcdefghijklmno", 1, 10)  = "abcdefg..."
     * StringUtil.abbreviate("abcdefghijklmno", 4, 10)  = "abcdefg..."
     * StringUtil.abbreviate("abcdefghijklmno", 5, 10)  = "...fghi..."
     * StringUtil.abbreviate("abcdefghijklmno", 6, 10)  = "...ghij..."
     * StringUtil.abbreviate("abcdefghijklmno", 8, 10)  = "...ijklmno"
     * StringUtil.abbreviate("abcdefghijklmno", 10, 10) = "...ijklmno"
     * StringUtil.abbreviate("abcdefghijklmno", 12, 10) = "...ijklmno"
     * StringUtil.abbreviate("abcdefghij", 0, 3)        = IllegalArgumentException
     * StringUtil.abbreviate("abcdefghij", 5, 6)        = IllegalArgumentException
     * </pre>
     * 
     * </p>
     * 
     * @param str Ҫ�����ַ���
     * @param offset ��߽�ƫ����
     * @param maxWidth ��󳤶ȣ���С��<code>4</code>�����С��<code>4</code>������
     *            <code>4</code>
     * @return �ַ������ԣ����ԭʼ�ַ���Ϊ<code>null</code>�򷵻�<code>null</code>
     */
    public static String abbreviate(String str, int offset, int maxWidth) {
        if (str == null) {
            return null;
        }

        // ���������
        if (maxWidth < 4) {
            maxWidth = 4;
        }

        if (str.length() <= maxWidth) {
            return str;
        }

        if (offset > str.length()) {
            offset = str.length();
        }

        if (str.length() - offset < maxWidth - 3) {
            offset = str.length() - (maxWidth - 3);
        }

        if (offset <= 4) {
            return str.substring(0, maxWidth - 3) + "...";
        }

        // ���������
        if (maxWidth < 7) {
            maxWidth = 7;
        }

        if (offset + maxWidth - 3 < str.length()) {
            return "..." + abbreviate(str.substring(offset), maxWidth - 3);
        }

        return "..." + str.substring(str.length() - (maxWidth - 3));
    }

    // ==========================================================================
    // �Ƚ������ַ�������ͬ��
    // ==========================================================================

    /**
     * �Ƚ������ַ�����ȡ�õڶ����ַ����У��͵�һ���ַ�����ͬ�Ĳ��֡�
     * 
     * <pre>
     * StringUtil.difference("i am a machine", "i am a robot")  = "robot"
     * StringUtil.difference(null, null)                        = null
     * StringUtil.difference("", "")                            = ""
     * StringUtil.difference("", null)                          = ""
     * StringUtil.difference("", "abc")                         = "abc"
     * StringUtil.difference("abc", "")                         = ""
     * StringUtil.difference("abc", "abc")                      = ""
     * StringUtil.difference("ab", "abxyz")                     = "xyz"
     * StringUtil.difference("abcde", "abxyz")                  = "xyz"
     * StringUtil.difference("abcde", "xyz")                    = "xyz"
     * </pre>
     * 
     * @param str1 �ַ���1
     * @param str2 �ַ���2
     * @return �ڶ����ַ����У��͵�һ���ַ�����ͬ�Ĳ��֡���������ַ�����ͬ���򷵻ؿ��ַ���<code>""</code>
     */
    public static String difference(String str1, String str2) {
        if (str1 == null) {
            return str2;
        }

        if (str2 == null) {
            return str1;
        }

        int index = indexOfDifference(str1, str2);

        if (index == -1) {
            return EMPTY_STRING;
        }

        return str2.substring(index);
    }

    /**
     * �Ƚ������ַ�����ȡ�����ַ�����ʼ��ͬ������ֵ��
     * 
     * <pre>
     * StringUtil.indexOfDifference("i am a machine", "i am a robot")   = 7
     * StringUtil.indexOfDifference(null, null)                         = -1
     * StringUtil.indexOfDifference("", null)                           = -1
     * StringUtil.indexOfDifference("", "")                             = -1
     * StringUtil.indexOfDifference("", "abc")                          = 0
     * StringUtil.indexOfDifference("abc", "")                          = 0
     * StringUtil.indexOfDifference("abc", "abc")                       = -1
     * StringUtil.indexOfDifference("ab", "abxyz")                      = 2
     * StringUtil.indexOfDifference("abcde", "abxyz")                   = 2
     * StringUtil.indexOfDifference("abcde", "xyz")                     = 0
     * </pre>
     * 
     * @param str1 �ַ���1
     * @param str2 �ַ���2
     * @return ���ַ�����ʼ�������������ֵ��������ַ�����ͬ���򷵻�<code>-1</code>
     */
    public static int indexOfDifference(String str1, String str2) {
        if (str1 == str2 || str1 == null || str2 == null) {
            return -1;
        }

        int i;

        for (i = 0; i < str1.length() && i < str2.length(); ++i) {
            if (str1.charAt(i) != str2.charAt(i)) {
                break;
            }
        }

        if (i < str2.length() || i < str1.length()) {
            return i;
        }

        return -1;
    }

    // ==========================================================================
    // �����ֻ��ֽ�ת����ASCII�ַ����ĺ����� 
    // ==========================================================================

    private static final char[] DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] DIGITS_NOCASE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * ��һ��������ת����62���Ƶ��ַ�����
     */
    public static String longToString(long longValue) {
        return longToString(longValue, false);
    }

    /**
     * ��һ��������ת����62���Ƶ��ַ�����
     */
    public static String longToString(long longValue, boolean noCase) {
        char[] digits = noCase ? DIGITS_NOCASE : DIGITS;
        int digitsLength = digits.length;

        if (longValue == 0) {
            return String.valueOf(digits[0]);
        }

        if (longValue < 0) {
            longValue = -longValue;
        }

        StringBuilder strValue = new StringBuilder();

        while (longValue != 0) {
            int digit = (int) (longValue % digitsLength);
            longValue = longValue / digitsLength;

            strValue.append(digits[digit]);
        }

        return strValue.toString();
    }

    /**
     * ��һ��byte����ת����62���Ƶ��ַ�����
     */
    public static String bytesToString(byte[] bytes) {
        return bytesToString(bytes, false);
    }

    /**
     * ��һ��byte����ת����62���Ƶ��ַ�����
     */
    public static String bytesToString(byte[] bytes, boolean noCase) {
        char[] digits = noCase ? DIGITS_NOCASE : DIGITS;
        int digitsLength = digits.length;

//      TODO
//        if (isEmptyArray(bytes)) {
//            return String.valueOf(digits[0]);
//        }

        StringBuilder strValue = new StringBuilder();
        int value = 0;
        int limit = Integer.MAX_VALUE >>> 8;
        int i = 0;

        do {
            while (i < bytes.length && value < limit) {
                value = (value << 8) + (0xFF & bytes[i++]);
            }

            while (value >= digitsLength) {
                strValue.append(digits[value % digitsLength]);
                value = value / digitsLength;
            }
        } while (i < bytes.length);

        if (value != 0 || strValue.length() == 0) {
            strValue.append(digits[value]);
        }

        return strValue.toString();
    }

    // ==========================================================================
    // �����Ű溯���� 
    // ==========================================================================

    private final static Pattern CRLF_PATTERN = Pattern.compile("\\r|\\n|\\r\\n");

    /**
     * �ӵڶ��п�ʼ����ÿһ������ָ���հס�
     */
    public static String indent(String str, String indent) {
        if (isEmpty(str)) {
            return str;
        }

        StringBuilder buf = new StringBuilder();

        indent(buf, str, indent);

        return buf.toString();
    }

    /**
     * �ӵڶ��п�ʼ����ÿһ������ָ���հס�
     */
    public static void indent(Formatter buf, String str, String indent) {
        if (buf.out() instanceof StringBuilder) {
            indent((StringBuilder) buf.out(), str, indent);
        } else {
            buf.format("%s", indent(str, indent));
        }
    }

    /**
     * �ӵڶ��п�ʼ����ÿһ������ָ���հס�
     */
    public static void indent(StringBuilder buf, String str, String indent) {
        if (isEmpty(str)) {
            return;
        }

        indent = defaultIfNull(indent, EMPTY_STRING);

        if (isEmpty(indent)) {
            buf.append(str);
            return;
        }

        Matcher matcher = CRLF_PATTERN.matcher(str);
        int index = 0;

        while (matcher.find()) {
            if (index < matcher.start() && index > 0) {
                buf.append(indent);
            }

            buf.append(str, index, matcher.end());
            index = matcher.end();
        }

        if (index < str.length()) {
            if (index > 0) {
                buf.append(indent);
            }

            buf.append(str, index, str.length());
        }
    }

	public static String defaultIfNull(String indent, String str) {
		return indent==null ? str : indent;
	}
}

package com.fmlditital.emp.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class StringUtil {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
			return true;
		for (int i = 0; i < strLen; i++)
			if (!Character.isWhitespace(str.charAt(i)))
				return false;

		return true;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 以type为分界符分离str字符串,返回分离后的数组
	 * 
	 * @param str
	 *            待处理字符串
	 * @param type
	 *            分界符
	 * 
	 * @return array 处理后的字符串数组
	 */
	public static String[] split(String str, String type) {

		int begin = 0;
		int pos = 0;
		String tempstr = "";
		String[] array = null;
		Vector vec = null;
		int len = str.trim().length();
		str = str + type;

		if (len > 0) {
			while (str.length() > 0) {

				if (vec == null) {
					vec = new Vector();
				}

				pos = str.indexOf(type, begin);
				tempstr = str.substring(begin, pos);
				str = str.substring(pos + 1, str.length());
				vec.add(tempstr);
			}
		}
		if (vec != null) {
			array = new String[vec.size()];

			for (int i = 0; i < vec.size(); i++) {
				array[i] = (String) vec.get(i);
			}
		} else {
			array = new String[0];
		}

		return array;
	}

	/**
	 * 按长度把字符串前补0
	 * 
	 * @param s
	 *            需要补位字符串对象
	 * @return len 该字符串的长度
	 */
	public static String len(String s, int len) {

		if (!notNull(s))
			s = "";

		int length = len - s.length();
		for (int i = 0; i < length; i++) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * 
	 * @param s
	 *            需要判断字符串对象
	 * @return true 表示不为空 false 为空
	 * @throws java.lang.Exception
	 */
	public static boolean notNull(String s) {

		if (s == null || s.trim().length() <= 0 || "".equals(s)
				|| "null".equals(s))
			return false;
		else
			return true;
	}

	/**
	 * 返回一个布尔值，表示两个字符串是否相等
	 * 
	 * 
	 * @param objstr
	 *            字符串对象
	 * 
	 * @param bjstr
	 *            字符串对象
	 * 
	 * @return false 表示不相等 true 相等
	 * @throws java.lang.Exception
	 */
	public static boolean equals(String objstr, String bjstr) {

		boolean lean = false;
		lean = (objstr != null) && objstr.equals(bjstr);

		return lean;
	}

	/**
	 * 替换source中的str1为str2
	 * 
	 * @param source
	 *            待替换的字符串
	 * 
	 * @param str1
	 *            待替换的字符
	 * @param str2
	 *            替换的字符
	 * 
	 * @return java.lang.String 替换后的字符串
	 */
	public static String replace(String source, char str1, String str2) {
		return replace(source, String.valueOf(str1), str2);
	}

	/**
	 * 替换source中的str1为str2
	 * 
	 * @param source
	 *            待替换的字符串
	 * 
	 * @param str1
	 *            待替换的字符
	 * @param str2
	 *            替换的字符
	 * 
	 * @return java.lang.String 替换后的字符串
	 */
	public static String replace(String source, String str1, String str2) {
		if (source == null)
			return "";
		String desc = "";
		int posstart = 0;
		int posend = source.indexOf(str1, 0);
		int len = source.length();
		while (posend >= 0 && posstart < len) {
			desc += source.substring(posstart, posend) + str2;
			posstart = posend + str1.length();
			posend = source.indexOf(str1, posstart);
		}
		if (posstart < len)
			desc += source.substring(posstart, len);
		return desc;
	}

	/**
	 * 替换source中的"\n"为"换行符"
	 * 
	 * @param source
	 *            待替换的字符串
	 * 
	 * @return java.lang.String 替换后的字符串
	 */
	public static String replace(String content) {
		String[] tempValue = null;
		if (content != null) {
			if (content.indexOf("\n") != -1) {
				tempValue = StringUtil.split(content, "\n");
			}
			if (tempValue != null && tempValue.length > 0) {
				content = "";
				for (int i = 0; i < tempValue.length; i++) {
					content += tempValue[i] + "<br>";
				}
				content = content.substring(0, content.length() - 4);
			}
		}
		return content;
	}

	/**
	 * 将字符串列表转换成字符串数组
	 * 
	 * @param list
	 *            List 字符串列表
	 * 
	 * @return String[]
	 */
	public static String[] list2Array(List list) {
		String[] strs = new String[list.size()];
		for (int i = 0; i < strs.length; i++) {
			strs[i] = (String) list.get(i);
		}
		return strs;
	}

	/**
	 * 将字符串数组转换成字符串列表
	 * 
	 * @param array
	 *            String[] array 字符串数组
	 * 
	 * @return List 字符串列表
	 */
	public static List array2List(String[] array) {
		List list = null;
		if (array != null) {
			list = new ArrayList(array.length);
			for (int i = 0; i < array.length; i++) {
				list.add(list.get(i));
			}
		}
		return list;
	}

	/**
	 * 过滤Html的特殊字符
	 */
	public static String htmlEscape(String in) {
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			switch (c) {
			case '"':
				out.append("&quot;");
				break;
			case '&':
				out.append("&amp;");
				break;
			case '<':
				out.append("&lt;");
				break;
			case '>':
				out.append("&gt;");
				break;
			default:
				out.append(c);
				break;
			}
		}
		return out.toString();
	}

	/**
	 * 取String最后几位
	 */
	public static String getStrLast(String str, int i) {
		return str.substring(str.length() - i, str.length());
	}

	/**
	 * 判断是该字符串是否是图片或者是图片连接 jpg,bmp,gif,jpeg
	 */
	public static boolean isImage(String str) {
		boolean flag = false;
		String type1 = StringUtil.getStrLast(str, 4);
		if (type1.equals(".jpg") || type1.equals(".JPG")) {
			flag = true;
		}
		if (type1.equals(".bmp") || type1.equals(".BMP")) {
			flag = true;
		}
		if (type1.equals(".gif") || type1.equals(".GIF")) {
			flag = true;
		}

		String type2 = StringUtil.getStrLast(str, 5);
		if (type2.equals(".jpeg") || type2.equals(".GPEG")) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 产生随机字符串
	 */
	private static Random randGen = null;
	private static char[] numbersAndLetters = null;
	private static Object initLock = new Object();

	public static final String randomString(int length) {

		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
							+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
							.toCharArray();
					// numbersAndLetters =
					// ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
				}
			}
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
			// randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
		}
		return new String(randBuffer);
	}

	public static String urlFilter(String str) {
		String temp = "http://www.";
		String temp1 = "www.";
		String temp2 = "http://";
		if (str.indexOf(temp) > -1) {

		} else {
			if (str.indexOf(temp2) > -1) {

			} else {
				if (str.indexOf(temp1) > -1) {
					str = "http://" + str;
				} else {
					str = temp + str;
				}
			}

		}

		String s = str.substring(7);
		if (s.indexOf("/") > 0) {

		} else {
			char c = str.charAt(str.length() - 1);

			if ("/".equals(String.valueOf(c))) {

			} else {
				str = str + "/";
			}
		}

		return str;
	}
}
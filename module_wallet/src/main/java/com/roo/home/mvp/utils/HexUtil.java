package com.roo.home.mvp.utils;

public class HexUtil {
	private static final String HEX_CHARSET = "0123456789ABCDEF";
	private static final String BLANK = " ";
    /**
	  * 方法功能：字节数组byte[]转化为十六进制字符串
	  * @param byte[] b
	  * @param boolean upperCase 十六进制字符串为大写还是小写
	  * @param boolean insBlank  十六进制字符串是否用空格隔开
	  * 
	* */
	public static String byte2Hex(byte[] b, boolean upperCase, boolean insBlank){
	    if ((b == null) || (b.length == 0)) {
	      return null;
	    }
	    int size = b.length;
	    StringBuilder sb = new StringBuilder(size * 2);
	    for (int n = 0; n < size; n++)
	    {
	      if ((insBlank) && (n > 0) && (n % 4 == 0)) {
	        sb.append(" ");
	      }
	      if (upperCase) {
	        sb.append(Integer.toString((b[n] & 0xFF) + 256, 16).substring(1).toUpperCase());
	      } else {
	        sb.append(Integer.toString((b[n] & 0xFF) + 256, 16).substring(1));
	      }
	    }
	    return upperCase ? sb.toString() : sb.toString().toLowerCase();
	  }
}

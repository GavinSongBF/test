package gavin.util;

import java.security.MessageDigest;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringUtils {

    /**
     * 产生一个随机的字符串
     *
     * @param length 字符串长度
     * @return
     */
    public static String randomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格，则返回true，否则则返回false
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        }

        return false;
    }

    
    //ref: http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
    private static final String EMAIL_PATTERN = 
    		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
    		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    //ref: http://www.w3.org/TR/html-markup/datatypes.html#form.data.emailaddress
//    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:.[a-zA-Z0-9-]+)*$";
    /**
     * 判断是否合法的Email地址
     * @param target
     * @return
     */
    public final static boolean isValidEmail(String target)
    {
        if (isEmpty(target))
        {
            return false;
        }
        else
        {
            Pattern pattern = Pattern.compile(EMAIL_PATTERN,Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(target);
            if(matcher.matches()){
                return true;
            }
            return false;
        }
    }

    /**
     * 取得二进制数据的16进制显示字符串
     */
    public static String bytes2hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }

    /**
     * 判断给定的字符串是不是数字
     *
     * @param strNum
     * @return
     */
    public static boolean isNumber(String strNum) {
        boolean result = false;
        if (strNum == null || "".equalsIgnoreCase(strNum)) {
            result = false;
        } else {
            Pattern pattern = Pattern.compile("^[0-9]*$");
            Matcher matcher = pattern.matcher(strNum);
            if (matcher.matches()) {
                result = true;
            } else {
                result = false;
            }
        }

        return result;
    }


    /**
     * MD5转码
     *
     * @param input
     * @return
     */
    public static String toMD5(String input) {
        // LogUtil.d("开始对字符串进行MD5:" + input);
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byte2hex(md.digest(input.getBytes("utf-8")));
        } catch (Exception e) {
            throw new RuntimeException("sign error !");
        }
        return result;
    }

    /**
     * 二行制转字符串
     */
    public static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs.append("0").append(stmp);
            else
                hs.append(stmp);
        }

        return hs.toString().toUpperCase();
    }
    
    public static void main(String[] s)
    {
    	String[] emails = new String[] { "mkyong", "mkyong@.com.my",
    			"mkyong123@gmail.a", "mkyong123@.com", "mkyong123@.com.com",
    			".mkyong@mkyong.com", "mkyong()*@gmail.com", "mkyong@%*.com",
    			"mkyong..2002@gmail.com", "mkyong.@gmail.com",
    			"mkyong@mkyong@gmail.com", "mkyong@gmail.com.1a" };
    	
//    	String[] emails = new String[] { "mkyong@yahoo.com",
//    			"mkyong-100@yahoo.com", "_mkyong.100@yahoo.com",
//    			"mkyong111@mkyong.com", "mkyo_ng-100@mkyong.net",
//    			"mkyong.100@mkyong.com.au", "mkyong@1.com",
//    			"mkyong@gmail.com.com", "mkyong+100@gmail.com",
//    			"mkyong-100@yahoo-test.com" };
    	for(int i= 0; i < emails.length; i++)
    	{
    		boolean isEmail = StringUtils.isValidEmail(emails[i]);
        	System.out.println("isEmail=" + isEmail + ",\t" + emails[i]);
    	}
    	
    }

}

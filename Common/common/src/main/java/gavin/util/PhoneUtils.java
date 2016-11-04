package gavin.util;

/**
 * Created by Ma on 2015/3/3.
 */
public class PhoneUtils
{
    /**
     * 参考维基百科
     * http://zh.wikipedia.org/zh/%E4%B8%AD%E5%9B%BD%E5%86%85%E5%9C%B0%E7%A7%BB%E5%8A%A8%E7%BB%88%E7%AB%AF%E9%80%9A%E8%AE%AF%E5%8F%B7%E7%A0%81
     */

    private static String regMobileStr = "^1(([3][456789])|([4][7])|([5][012789])|([7][8])|([8][23478]))[0-9]{8}$";
    private static String regUnicomStr = "^1(([3][012])|([4][5])|([5][56])|([7][6])|([8][56]))[0-9]{8}$";
    private static String regTelecomStr = "^1(([3][3])|([5][3])|([7][7])|([8][019]))[0-9]{8}$";

    private String mobile = "";
    private String yysh = "UNKNOWN";
    private boolean isMobile = false;
    private boolean isPhoneNumber = false;

    public PhoneUtils(String phoneNumber)
    {
        if (phoneNumber == null)
        {
            return;
        }

        if(phoneNumber.startsWith("+86") || phoneNumber.startsWith("86"))
        {
            phoneNumber = PhoneUtils.formatNumber(phoneNumber);
        }

        if (phoneNumber.length() == 11)
        {
            /** */
            /** 第一步判断中国移动 */
            if (phoneNumber.matches(PhoneUtils.regMobileStr))
            {
                yysh = "中国移动";
                isMobile = true;
                mobile = phoneNumber;
            }
            /** */
            /** 第二步判断中国联通 */
            else if (phoneNumber.matches(PhoneUtils.regUnicomStr))
            {
                yysh = "中国联通";
                isMobile = true;
                mobile = phoneNumber;
            }
            /** */
            /** 第三步判断中国电信 */
            else if (phoneNumber.matches(PhoneUtils.regTelecomStr))
            {
                yysh = "中国电信";
                isMobile = true;
                mobile = phoneNumber;
            }
        }
        
        if(!isMobile)
        {
        	checkPhoneNumber(phoneNumber);
        }
    }

    private void checkPhoneNumber(String phoneNumber)
    {
        String number = phoneNumber.replace("-", "");
        isPhoneNumber = number.matches("^[0-9]*$");
        if(isPhoneNumber)
        {
            //带了区号
            if (number.startsWith("0"))
            {
                if(number.length() < 11 || number.length() > 12)
                {
                    isPhoneNumber = false;
                }
            }
            else if(number.length() < 7 || number.length() > 8)
            {
                isPhoneNumber = false;
            }
        }
    }

    public boolean isPhoneNumber()
    {
        return isPhoneNumber;
    }

    public String getMobile()
    {
        return mobile;
    }

    public boolean isMobile()
    {
        return isMobile;
    }

    public String getYysh()
    {
        return yysh;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("mobile:").append(this.getMobile()).append(",").append("yysh:").append(this.getYysh()).append(",")
                .append("isMobile:").append(this.isMobile()).append(",")
                .append("isPhoneNumber:").append(this.isPhoneNumber())
                .append(";");
        return str.toString();
    }

    public static void main(String[] args)
    {
        PhoneUtils mobile = new PhoneUtils("010-80770010");
        System.out.println(mobile.toString());
    }

    /**
     * 还原11位手机号
     *
     * @param num
     * @return
     */
    public static String formatNumber(String num)
    {
        if (num != null && !num.equals(""))
        {
            if (num.startsWith("+86"))
            {
                num = num.substring(3);
            }
            else if (num.startsWith("86"))
            {
                num = num.substring(2);
            }
        }
        else
        {
            num = "";
        }
        return num;
    }
}

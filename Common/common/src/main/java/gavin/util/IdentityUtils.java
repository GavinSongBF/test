package gavin.util;

import java.util.regex.Pattern;

/**
 * 身份证工具类
 */

/**
 * @author majianglin
 *
 */
public class IdentityUtils
{

    /**
     * 身份证号码是否有效
     * @param cardNumber
     * @return
     */
    public static boolean isValidIdentityCardNumber(String cardNumber)
    {
        if (cardNumber == null)
        {
            return false;
        }

        Pattern pattern = Pattern.compile("(\\d{17}[0-9a-zA-Z]|\\d{14}[0-9a-zA-Z])");
        return pattern.matcher(cardNumber).matches();
    }
}

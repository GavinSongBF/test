package gavin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理日期和时间格式的工具类
 */
public class DateUtils
{
    /**
     * 获取指定格式的时间 eg: formatDate("yyyy/MM/dd HH:mm",13333234234);
     *
     * @param format
     * @return
     */
    public static String formatDate(String format, long date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(date));
    }

    /**
     * 获取指定格式的时间 eg: formatDate("yyyy/MM/dd HH:mm", new Date());
     *
     * @param format
     * @return
     */
    public static String formatDate(String format, Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 将字符串转为时间
     *
     * @param format
     * @return
     */
    public static Date dateFromString(String format, String dateStr)
    {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try
        {
            date = dateFormat.parse(dateStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return date;
    }
}

package gavin.util;

/**
 * Created by Ma on 2015/4/9.
 */
public class NumberUtils
{
    public static String prettyPrint(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
}

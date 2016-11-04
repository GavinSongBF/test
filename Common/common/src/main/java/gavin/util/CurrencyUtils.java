package gavin.util;

/**
 * Created by Ma on 2015/3/18.
 */
public class CurrencyUtils
{
    public static String formatPrice(double price)
    {
        return String.format("%.2f", price);
    }

    public static int integerPrice(String priceString)
    {
        if(priceString == null || priceString.trim().length() == 0)
        {
            return 0;
        }

        double priceDouble;
        try
        {
            priceDouble = Double.parseDouble(priceString);
        }
        catch (Exception e)
        {
            return 0;
        }

        return (int)priceDouble;
    }
}

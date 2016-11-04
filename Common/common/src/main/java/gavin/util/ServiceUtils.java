package gavin.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Ma on 2015/3/3.
 */
public class ServiceUtils
{
    /**
     * 拨打电话
     *
     * @param phoneNumber
     */
    public static void dial(Context context, String phoneNumber)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        context.startActivity(Intent.createChooser(intent, null));
    }

    /**
     * 发送短信
     *
     * @param phoneNumber
     * @param message
     *
     */
    public static void sendMessage(Context context, String phoneNumber, String message)
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("smsto", phoneNumber, null));
        intent.putExtra("sms_body", message);
        context.startActivity(Intent.createChooser(intent, null));
    }

    /**
     * 浏览网页
     *
     * @param webUrl
     */
    public static void viewUrl(Context context, String webUrl)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(webUrl)));
        context.startActivity(Intent.createChooser(intent, null));
    }
}

package gavin.util;

import android.graphics.drawable.Drawable;
import android.text.Html;

import gavin.app.BaseApplication;

/**
 * Created by Ma on 2015/3/22.
 */
public class Emoji
{
    public static CharSequence emojiFromResource(int resId)
    {
        String html = "<img src='" + resId + "'/>";
        Html.ImageGetter imgGetter = new Html.ImageGetter()
        {
            @Override
            public Drawable getDrawable(String source)
            {
                int id = Integer.parseInt(source);
                Drawable d = BaseApplication.getInstance().getResources().getDrawable(id);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            }
        };
        CharSequence charSequence = Html.fromHtml(html, imgGetter, null);
        return charSequence;
    }
}

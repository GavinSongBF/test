package gavin.content;

import android.content.Context;
import android.content.SharedPreferences;

public final class Preferences
{
    private static final String PREFS_NAME = "app_preferences";

    private static Preferences instance;
    
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;
    
    private Preferences() {}
    
    public static Preferences getPreferences(Context context)
    {
        if (instance == null)
        {
            synchronized (Preferences.class)
            {
                instance = new Preferences();
                prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                editor = prefs.edit();
            }
        }
        
        return instance;
    }

    public void putBoolean(String key, boolean value)
    {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putFloat(String key, float value)
    {
        editor.putFloat(key, value);
        editor.commit();
    }

    public void putInt(String key, int value)
    {
        editor.putInt(key, value);
        editor.commit();
    }

    public void putLong(String key, long value)
    {
        editor.putLong(key, value);
        editor.commit();
    }

    public void putString(String key, String value)
    {
        editor.putString(key, value);
        editor.commit();
    }

    public void clear(String key)
    {
        editor.remove(key);
        editor.commit();
    }

    public void clear()
    {
        editor.clear();
        editor.commit();
    }

    public boolean getBoolean(String key, boolean value)
    {
        return prefs.getBoolean(key, value);
    }

    public float getFloat(String key, float value)
    {
        return prefs.getFloat(key, value);
    }

    public int getInt(String key, int value)
    {
        return prefs.getInt(key, value);
    }

    public long getLong(String key, long value)
    {
        return prefs.getLong(key, value);
    }

    public String getString(String key, String value)
    {
        return prefs.getString(key, value);
    }
}

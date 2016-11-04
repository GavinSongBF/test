package gavin.android;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by Ma on 2015/1/15.
 */
public class Device
{
    private static final String PREFS_KEY_UNIQUE_IDENTIFIER = "PREFS_KEY_UNIQUE_IDENTIFIER";

    private static String uniqueIdentifier = null;

    public static synchronized String getUniqueIdentifier(Context context)
    {
        if (uniqueIdentifier == null)
        {
            // 获取已经保存的
            SharedPreferences sharedPrefs = context.getSharedPreferences(PREFS_KEY_UNIQUE_IDENTIFIER,
                                                                         Context.MODE_PRIVATE);
            uniqueIdentifier = sharedPrefs.getString(PREFS_KEY_UNIQUE_IDENTIFIER, null);

            // 如果没有保存的，根据设备属性生成
            if (TextUtils.isEmpty(uniqueIdentifier))
            {
                String deviceId = getDeviceId(context);
                if (TextUtils.isEmpty(deviceId)) deviceId = getDeviceSerialNumber();
                if (TextUtils.isEmpty(deviceId)) deviceId = getSimSerialNumber(context);
                if (TextUtils.isEmpty(deviceId)) deviceId = getAndroidId(context);

                UUID uuid;
                if (TextUtils.isEmpty(deviceId))
                {
                    uuid = UUID.randomUUID();
                }
                else
                {
                    uuid = UUID.nameUUIDFromBytes(deviceId.getBytes());
                }

                uniqueIdentifier = uuid.toString();

                // 保存生成的ID
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREFS_KEY_UNIQUE_IDENTIFIER, uniqueIdentifier);
                editor.commit();
            }
        }

        return uniqueIdentifier;
    }

    /**
     * 获取设备ID，一般是IMEI
     * 缺点：有通话功能的设备才有此编号
     * 缺点：不是100%可靠的 缺点：需要权限READ_PHONE_STATE
     *
     * @param context
     * @return
     */
    private static String getDeviceId(Context context)
    {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        // Unique number (IMEI, MEID, ESN, IMSI)
        // IMEI( International Mobile Equipment Identity )
        // MEID(Mobile Equipment IDentifier)
        // The globally unique number identifying a physical piece of CDMA
        // mobile station equipment, the MEID was created to replace
        // ESNs(Electronic Serial Number)
        // ESN(Electronic Serial Number)
        // The unique number to identify CDMA mobile phones
        // IMSI(International Mobile Subscriber Identity)
        // The unique identification associated with all GSM and UMTS network
        // mobile phone users
        String imei = tm.getDeviceId();
        if (!isValidIMEI(imei))
        {
            // IMSI
            String imsi = tm.getSubscriberId();
            if (isValidIMEI(imsi))
            {
                imei = imsi;
            }
        }

        return imei;
    }

    /**
     * 判断IMEI编号是否有效 IMEI必须是字母或数字，而且不能全部是0 有的错误的IMEI全部是0，或者是星号*
     *
     * @param imei
     * @return
     */
    private static boolean isValidIMEI(String imei)
    {
        if (imei == null) return false;

        String cleanedIMEI = imei.replace("0", "");
        if (cleanedIMEI.length() == 0) { return false; }

        if (cleanedIMEI.matches("^[A-Za-z0-9]$"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 获取设备序列号 Serial number can be identified for the devices such as MIDs
     * (Mobile Internet Devices) or PMPs (Portable Media Players) which are not
     * having telephony services.
     * 缺点：Android2.3以后才可以使用
     *
     * @return
     */
    private static String getDeviceSerialNumber()
    {
        String serialNum = null;
        try
        {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            serialNum = (String) (get.invoke(c, "ro.serialno", null));
        }
        catch (Exception ignored)
        {
        }

        return serialNum;
    }

    /**
     * 获取SIM卡序列号
     * 缺点：CDMA手机返回null
     * 缺点：没装SIM卡的手机没做测试
     * @return
     */
    private static String getSimSerialNumber(Context context)
    {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }

    /**
     * 获取ANDROID_ID，64位16进制数字
     * 缺点：系统第一次启动时随即产生，刷机会重新产生，会发生不一致
     * 缺点：Android2.2版本不是100%可靠
     * 缺点：有个别手机生产商的手机ANDROID_ID完全相同
     * 缺点：有的手机为空null
     * @param context
     * @return
     */
    private static String getAndroidId(Context context)
    {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        // 9774d56d682e549c device ID appearing in ALL Droid 2 devices, and
        // other Froyo builds
        // https://code.google.com/p/android/issues/detail?id=10603
        if (androidId != null && androidId.equals("9774d56d682e549c"))
        {
            androidId = null;
        }

        return androidId;
    }

    /**
     * 获取蓝牙设备MAC地址
     * 缺点：需要权限BLUETOOTH
     * 缺点：官方blog不建议使用
     *
     * @return
     */
    @SuppressWarnings("unused")
    private static String getBlueToothMacAddress()
    {
        return BluetoothAdapter.getDefaultAdapter().getAddress();
    }

    /**
     * 获取WIFI网卡MAC地址
     * 缺点：没开WIFI的话，有的机器和版本拿不到
     * 缺点：需要权限ACCESS_WIFI_STATE
     * 缺点：官方blog不建议使用
     *
     * @return
     */
    @SuppressWarnings("unused")
    private static String getWifiMacAddress(Context context)
    {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wm.getConnectionInfo().getMacAddress();
    }
}

package gavin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class BaseUser implements Parcelable
{
    public BaseUser(){}

    public boolean isLogin(){return false;}
    public HashMap<String, Object> getBaseUserInfo(){return null;};

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
    }

    protected BaseUser(Parcel in)
    {
    }
}

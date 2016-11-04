package gavin.demo.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

import gavin.model.BaseUser;


public class User extends BaseUser implements Parcelable
{
    private long user_id;
    private String mobile;
    private String nick_name;
    private String real_name;
    private String avatar;
    private int gender;
    private int age;
    private int type;
    private String qq;
    private String  weixin;
    private String email;

    private int login_type;
    private long create_time;
    private int height;

    HashMap<String, Object> userInfo;

    public boolean isLogin()
    {
        return user_id > 0;
    }

    public HashMap<String, Object> getBaseUserInfo()
    {
        if(userInfo == null && isLogin())
        {
            userInfo = new HashMap<>();
            userInfo.put("user_id", user_id+"");
        }

        return userInfo;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(this.user_id);
        dest.writeString(this.mobile);
        dest.writeString(this.nick_name);
        dest.writeString(this.real_name);
        dest.writeString(this.avatar);
        dest.writeInt(this.gender);
        dest.writeInt(this.age);
        dest.writeInt(this.type);
        dest.writeString(this.qq);
        dest.writeString(this.weixin);
        dest.writeString(this.email);
        dest.writeInt(this.login_type);
        dest.writeLong(this.create_time);
        dest.writeInt(this.height);
    }

    public User()
    {
        super();
    }

    private User(Parcel in)
    {
        this.user_id = in.readLong();
        this.mobile = in.readString();
        this.nick_name = in.readString();
        this.real_name = in.readString();
        this.avatar = in.readString();
        this.gender = in.readInt();
        this.age = in.readInt();
        this.type = in.readInt();
        this.qq = in.readString();
        this.weixin = in.readString();
        this.email = in.readString();
        this.login_type = in.readInt();
        this.create_time = in.readLong();
        this.height = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>()
    {
        public User createFromParcel(Parcel source)
        {
            return new User(source);
        }

        public User[] newArray(int size)
        {
            return new User[size];
        }
    };


    public long getUser_id()
    {
        return user_id;
    }

    public void setUser_id(long user_id)
    {
        this.user_id = user_id;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getNick_name()
    {
        return nick_name;
    }

    public void setNick_name(String nick_name)
    {
        this.nick_name = nick_name;
    }

    public String getReal_name()
    {
        return real_name;
    }

    public void setReal_name(String real_name)
    {
        this.real_name = real_name;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public int getGender()
    {
        return gender;
    }

    public void setGender(int gender)
    {
        this.gender = gender;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getQq()
    {
        return qq;
    }

    public void setQq(String qq)
    {
        this.qq = qq;
    }

    public String getWeixin()
    {
        return weixin;
    }

    public void setWeixin(String weixin)
    {
        this.weixin = weixin;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getLogin_type()
    {
        return login_type;
    }

    public void setLogin_type(int login_type)
    {
        this.login_type = login_type;
    }

    public long getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(long create_time)
    {
        this.create_time = create_time;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    @Override
    public String toString()
    {
        return "User{" +"user_id='"+ user_id + '\''+
                ", mobile='" + mobile + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", real_name='" + real_name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", type=" + type +
                ", qq='" + qq + '\'' +
                ", weixin='" + weixin + '\'' +
                ", email='" + email + '\'' +
                ", login_type=" + login_type +
                ", create_time=" + create_time +
                ", height=" + height +
                '}';
    }
}

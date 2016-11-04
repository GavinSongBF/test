package gavin.net;

import gavin.model.BaseUser;

import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseResponse
{
    @SerializedName("user")
    private BaseUser user;

    public BaseUser getUser()
    {
        return user;
    }

    public void setUser(BaseUser user)
    {
        this.user = user;
    }
}

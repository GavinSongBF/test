package gavin.net;

import android.content.Context;
import android.widget.Toast;

import gavin.common.R;

public class BaseResponse
{
    public static int SUCCESS_CODE = 1;

    private int code;
    private String message;

    public boolean isShowServerMessage()
    {
        return code > 1000;
    }

    public boolean isSuccess()
    {
        return code == SUCCESS_CODE;
    }

    public boolean isSuccess(Context context)
    {
        boolean success = isSuccess();
        if(!success)
        {
            if(isShowServerMessage())
            {
                if(message != null && message.length() > 0)
                {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(context, R.string.totem_server_error, Toast.LENGTH_SHORT).show();
            }
        }

        return success;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}

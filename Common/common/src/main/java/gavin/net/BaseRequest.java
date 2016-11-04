package gavin.net;

import com.loopj.android.http.RequestParams;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseRequest
{
    private String jsonKeyName(String fieldName)
    {
        StringBuffer sb = new StringBuffer();

        boolean underline = true;
        
        for (char c : fieldName.toCharArray())
        {
            if (Character.isUpperCase(c))
            {
                if(underline)
                {
                    sb.append(Character.toLowerCase(c));
                }
                else
                {
                    sb.append('_').append(Character.toLowerCase(c));
                }
                
                underline = true;
            }
            else
            {
                sb.append(c);
                underline = false;
            }
        }
        return sb.toString();
    }

	public RequestParams getRequestParams()
    {
        RequestParams params = new RequestParams();

        Class<? extends Object> clazz = this.getClass();

        List<Field> fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

        Class<? extends Object> SuperClass = clazz.getSuperclass();
        if (!(SuperClass.getName().equals("java.lang.Object") || SuperClass.getName().equals("gavin.net.BaseRequest")))
        {
            throw new RuntimeException("Request object must extends directly from gavin.net.BaseReqeust");
        }
        else
        {
            fields.addAll(Arrays.asList(SuperClass.getDeclaredFields()));
        }
        
        if (fields != null)
        {
            for (Field field : fields)
            {
                String fieldName = field.getName();
                String jsonKey = jsonKeyName(fieldName);
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try
                {
                    Method getMethod = clazz.getMethod(getMethodName, new Class[0]);
                    Object value = getMethod.invoke(this, new Object[0]);
                    if (value instanceof File)
                    {
                        params.put(jsonKey, (File) value);
                    }
                    else
                    {
                        params.put(jsonKey, value != null ? String.valueOf(value) : (String) null);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        return params;
    }
	
	public static void main()
	{
	    BaseRequest request = new BaseRequest();
	    request.getRequestParams();
	}
}

package gavin.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import gavin.net.BaseResponse;

public class JSONParser
{
    public static <T extends BaseResponse> T fromJson(String json, Class<T> classOfT)
    {
        try
        {
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            return gson.fromJson(json, classOfT);
        }
        catch (Exception e)
        {
            try
            {
                T object = classOfT.newInstance();
                object.setCode(9999);
                object.setMessage("服务器返回的数据格式错误");
                return object;
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
        }

        return null;
    }

    public static String toJson(Object object)
    {
        return new Gson().toJson(object);
    }

    public static <T> List<T> listFromJson(String json, Class<T> classOfT)
    {
        return new Gson().fromJson(json, new GenericType(classOfT));
    }

    private static class GenericType implements ParameterizedType
    {
        private Class itemClazz;

        public GenericType(Class itemClazz)
        {
            this.itemClazz = itemClazz;
        }

        public Type[] getActualTypeArguments()
        {
            return new Type[]{itemClazz};
        }

        public Type getRawType()
        {
            return ArrayList.class;
        }

        public Type getOwnerType()
        {
            return null;
        }
    }
}

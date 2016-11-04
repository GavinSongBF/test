package gavin.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ma on 2015/2/10.
 */
public class Product implements Parcelable
{
    private String name;
    private String image;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.name);
        dest.writeString(this.image);
    }

    public Product()
    {
    }

    private Product(Parcel in)
    {
        this.name = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>()
    {
        public Product createFromParcel(Parcel source)
        {
            return new Product(source);
        }

        public Product[] newArray(int size)
        {
            return new Product[size];
        }
    };
}

package gavin.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter
{
    private int mDropDownResource;
    private int mResource;
    private List<? extends SpinnerItem> values;

    public SpinnerAdapter(Context context, int resId, List<? extends SpinnerItem> values)
    {
        super(context, resId, values);
        this.mResource = resId;
        this.values = values;
    }

    public void setDropDownViewResource(int resource)
    {
        this.mDropDownResource = resource;
    }

    public int getCount(){
        return values.size();
    }

    public SpinnerItem getItem(int position){
        return values.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return createViewFromResource(position, convertView, parent, mResource);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return createViewFromResource(position, convertView, parent, mDropDownResource);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource)
    {
        TextView textView;

        if (convertView == null)
        {
            textView = (TextView)LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }
        else
        {
            textView = (TextView)convertView;
        }

        SpinnerItem item = (SpinnerItem)getItem(position);
        textView.setText(item.getSpinnerTitle());

        return textView;
    }

    public interface SpinnerItem
    {
        public String getSpinnerTitle();
    }
}

package gavin.widget;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleBaseAdapter<T> extends BaseAdapter
{
    protected Context context;
    protected List<T> data;
    protected int resId;

    public SimpleBaseAdapter(Context context, List<T> data, int resId)
    {
        this.context = context;
        this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.resId = resId;
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public T getItem(int position)
    {
        if (position >= data.size())
        {
            return null;
        }
        return data.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    /**
     * 使用该getItemView方法替换原来的getView方法，需要子类实现
     *
     * @param position
     * @param convertView
     * @param holder
     */
    public abstract void reuseItemView(int position, View convertView, ViewHolder holder);

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (null == convertView)
        {
            convertView = View.inflate(context, resId, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        reuseItemView(position,convertView, holder);

        return convertView;
    }

    public class ViewHolder
    {
        private SparseArray<View> views = new SparseArray<View>();
        private View convertView;

        public ViewHolder(View convertView)
        {
            this.convertView = convertView;
        }

        public <T extends View> T getView(int resId)
        {
            View v = views.get(resId);
            if (null == v)
            {
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (T) v;
        }
    }

    public void addAll(List<T> elem)
    {
        data.addAll(elem);
    }

    public void remove(T elem)
    {
        data.remove(elem);
    }

    public void remove(int index)
    {
        data.remove(index);
    }

    public void replaceAll(List<T> elem)
    {
        data.clear();
        data.addAll(elem);
    }

    public void clear()
    {
        data.clear();
    }
}

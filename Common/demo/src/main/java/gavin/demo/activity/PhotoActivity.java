package gavin.demo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import gavin.activity.PhotoBrowserActivity;
import gavin.app.PhotoPickerActivity;
import gavin.demo.R;
import gavin.demo.util.ImageUploader;
import gavin.util.ImageUtils;

/**
 * Created by majianglin on 3/24/15.
 */
public class PhotoActivity extends PhotoPickerActivity implements View.OnLongClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        findView(R.id.photo).setOnLongClickListener(this);
    }

    public void choosePhoto(View v)
    {
        //点击如果有图，查看大图，如果没图，选择图片
        if (v.getTag() == null)
        {
            pickImage(v.getId());
        }
        else
        {
            ArrayList<String> data = new ArrayList();
            data.add("http://img2.cache.netease.com/photo/0001/2015-03-24/900x600_ALGC8QHL00AO0001.jpg");
            data.add("http://img6.cache.netease.com/photo/0001/2015-03-24/900x600_ALGJ2LL100AO0001.jpg");

            Intent intent = new Intent(this, PhotoBrowserActivity.class);
            intent.putStringArrayListExtra("photos", data);
            startActivity(intent);
        }
    }

    @Override
    public boolean onLongClick(View v)
    {
        //长按如果有图，提示删除
        if(v.getTag() != null)
        {
            deletePhotoPrompts(v.getId(), R.mipmap.ic_launcher);
            return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != RESULT_OK)
        {
            return;
        }


        Bitmap bitmap = super.imagePickerResult(requestCode, resultCode, data, 1024);

        int viewId = requestCode;

        ImageView imageView = findView(viewId);
        if (imageView != null)
        {
            imageView.setImageBitmap(bitmap);

            //保存并上传
            String fileName = viewId + ".jpg";
            ImageUtils.saveBitmap(this, bitmap, fileName);
            imageView.setTag(fileName);  //或者URL
            ImageUploader.uploadImage(this, fileName, viewId);
        }
    }
}

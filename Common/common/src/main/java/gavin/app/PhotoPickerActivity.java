package gavin.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.io.File;

import gavin.util.ImagePicker;
import gavin.util.ImageUtils;

public class PhotoPickerActivity extends BaseActivity
{
    private static final String KEY_FILE_PATH = "gavin.captureImageFile";


    private File captureImageFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
        {
            String filePath = savedInstanceState.getString(KEY_FILE_PATH);
            if(filePath!= null)
            {
                captureImageFile = new File(filePath);
            }
        }
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        if(captureImageFile != null)
        {
            bundle.putString(KEY_FILE_PATH, captureImageFile.getAbsolutePath());
        }
    }

    protected void pickImage(final int requestCode)
    {
        CharSequence actions[] = new CharSequence[] {"相册", "拍照"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("选取照片");
        builder.setCancelable(true);
        builder.setItems(actions, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case 0:
                        ImagePicker.openPhotoAlbums(PhotoPickerActivity.this, requestCode);
                        break;
                    case 1:
                        captureImageFile = ImagePicker.openCamera(PhotoPickerActivity.this, requestCode);
                        break;
                    default:
                        break;
                }
            }
        });
        builder.show();
    }

    protected Bitmap imagePickerResult(int requestCode, int resultCode, Intent data, int maxSize)
    {
        if(resultCode != RESULT_OK)
        {
            captureImageFile = null;
            return null;
        }

        Bitmap bitmap = null;

        if(data == null || data.getData() == null)
        {
            if(captureImageFile != null)
            {
                //拍照取得
                bitmap = ImageUtils.resizeWithRotate(captureImageFile.getAbsolutePath(), maxSize);
            }
        }
        else
        {
            Uri uri = data.getData();
            if(uri != null)
            {
                //相册取得
                bitmap = ImageUtils.resizeWithRotate(context, uri, maxSize);
            }
        }

        return bitmap;
    }

    protected void deletePhotoPrompts(final int viewId, final int defautResouceId)
    {
        CharSequence actions[] = new CharSequence[] {"删除照片"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("删除照片");
        builder.setCancelable(true);
        builder.setItems(actions, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case 0:
                        deletePhotoConfirm(viewId, defautResouceId);
                        break;
                    default:
                        break;
                }
            }
        });
        builder.show();
    }

    private void deletePhotoConfirm(final int viewId, final int defautResouceId)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("确定要删除照片？");
        //        builder.setIcon(gavin.common.R.mipmap.ic_launcher);
        builder.setCancelable(true);
        builder.setNeutralButton("按错了",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("删除照片", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
                deletePhoto(viewId, defautResouceId);
            }
        });
        builder.show();
    }


    protected void deletePhoto(final int viewId, int defaultResourceId)
    {
        ImageView imageView = findView(viewId);
        if(imageView != null)
        {
            imageView.setTag(null);
            imageView.setImageResource(defaultResourceId);
            imageView.setOnLongClickListener(null);
        }
    }
}

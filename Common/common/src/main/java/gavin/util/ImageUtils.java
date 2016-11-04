package gavin.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片缩放类，参考: Android Samples: DisplayingBitmaps
 */
public class ImageUtils
{
    public static final String TAG = ImageUtils.class.getSimpleName();

    /**
     * 判断图片是否需要翻转（正方向的不用翻转）
     *
     * @param photoPath
     * @return
     */
    public static boolean needRotate(String photoPath)
    {
        int degrees = rotateDegree(photoPath);
        return degrees != 0;
    }

    /**
     * 判断图片需要翻转的度数（正方向的不用翻转）
     *
     * @param photoPath
     * @return
     */
    public static int rotateDegree(String photoPath)
    {
        int degree = 0;
        try
        {
            ExifInterface exif = new ExifInterface(photoPath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation)
            {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                default:
                    break;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return degree;
    }

    /**
     * 缩小图片并翻转到正方向
     *
     * @param photoPath
     * @param maxSize 设置最大边不超过maxSize像素，实际可能要小得多，因为重新采样的缩放倍数是2的指数倍
     *
     * @return
     */
    public static Bitmap resizeWithRotate(String photoPath, int maxSize)
    {
        int degrees = rotateDegree(photoPath);


        Bitmap bitmap = null;

        //先缩小
        Bitmap smallBitmap = decodeSampledBitmapFromFile(photoPath, maxSize, maxSize);

        //是否需要旋转
        if (degrees != 0)
        {
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);

            bitmap = Bitmap.createBitmap(smallBitmap, 0, 0, smallBitmap.getWidth(), smallBitmap.getHeight(), matrix, true);
            smallBitmap.recycle();
        }
        else
        {
            bitmap = smallBitmap;
        }

        return bitmap;
    }

    /**
     * 缩小图片并翻转到正方向
     *
     * @param context
     * @param uri
     * @param maxSize 设置最大边不超过maxSize像素，实际可能要小得多，因为重新采样的缩放倍数是2的指数倍
     *
     * @return
     */
    public static Bitmap resizeWithRotate(Context context, Uri uri, int maxSize)
    {
        String filePath = ImagePicker.getPath(context, uri);
        int degrees = rotateDegree(filePath);

        Bitmap bitmap = null;

        //先缩小
        Bitmap smallBitmap = decodeSampledBitmapFromUri(context, uri, maxSize, maxSize);

        //是否需要旋转
        if (degrees != 0)
        {
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            bitmap = Bitmap.createBitmap(smallBitmap, 0, 0, smallBitmap.getWidth(), smallBitmap.getHeight(), matrix, true);
            smallBitmap.recycle();
        }
        else
        {
            bitmap = smallBitmap;
        }

        return bitmap;
    }

    /**
     * 保存图片到应用内部存储区域
     *
     * @param context
     * @param bitmap
     * @param fileName 文件名，不需要路径
     *
     * @return 保存成功状态
     */
    public static boolean saveBitmap(Context context, Bitmap bitmap, String fileName)
    {
        FileOutputStream out = null;
        try
        {
            out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            boolean success = bitmap.compress(Bitmap.CompressFormat.JPEG, 60, out); // bmp is your Bitmap instance
            out.flush();

            return success;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (out != null)out.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 保存图片到一个绝对路径，可以是外部存储
     * @param bitmap
     * @param filePath 文件绝对路径
     *
     * @return 保存成功状态
     */
    public static boolean saveBitmap(Bitmap bitmap, String filePath)
    {
        FileOutputStream out = null;
        try
        {
            out = new FileOutputStream(filePath);
            boolean success = bitmap.compress(Bitmap.CompressFormat.JPEG, 60, out); // bmp is your Bitmap instance
            out.flush();

            return success;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (out != null)out.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 保存图片到一个绝对路径，可以是外部存储
     *
     * @param context
     * @param src 原图片文件Uri
     * @param dst 目的图片文件
     * @param rate 压缩比，JPG图片60就可以
     *
     * @return 保存成功状态
     */


    public static boolean compress(Context context, Uri src, File dst, int rate)
    {
        try
        {
            Bitmap bmp = decodeSampledBitmapFromUri(context, src, 1024, 1024);
            FileOutputStream fos = new FileOutputStream(dst);
            bmp.compress(Bitmap.CompressFormat.JPEG, rate, fos);
            bmp.recycle();
            fos.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Decode and sample down a bitmap from a file to the requested width and height.
     *
     * @param filePath The full path of the file to decode
     * @param reqWidth The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return A bitmap sampled down from the original with the same aspect ratio and dimensions
     *         that are equal to or greater than the requested width and height
     */
    public static Bitmap decodeSampledBitmapFromFile(String filePath, int reqWidth, int reqHeight)
    {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * Decode and sample down a bitmap from a file to the requested width and height.
     *
     * @param context
     * @param uri
     * @param reqWidth The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return A bitmap sampled down from the original with the same aspect ratio and dimensions
     *         that are equal to or greater than the requested width and height
     */
    public static Bitmap decodeSampledBitmapFromUri(Context context, Uri uri, int reqWidth, int reqHeight)
    {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        InputStream is = null;

        try
        {
            is = context.getContentResolver().openInputStream(uri);
            BitmapFactory.decodeStream(is, null, options);
            is.close();
            is = context.getContentResolver().openInputStream(uri);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            // options.inSampleSize = 4;

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
            is.close();
            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Calculate an inSampleSize for use in a {@link android.graphics.BitmapFactory.Options} object when decoding
     * bitmaps using the decode* methods from {@link android.graphics.BitmapFactory}. This implementation calculates
     * the closest inSampleSize that is a power of 2 and will result in the final decoded bitmap
     * having a width and height equal to or larger than the requested width and height.
     *
     * @param options An options object with out* params already populated (run through a decode*
     *            method with inJustDecodeBounds==true
     * @param reqWidth The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return The value to be used for inSampleSize
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // BEGIN_INCLUDE (calculate_sample_size)
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth)
        {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            long totalPixels = width * height / inSampleSize;

            // Anything more than 2x the requested pixels we'll sample down further
            final long totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels > totalReqPixelsCap)
            {
                inSampleSize *= 2;
                totalPixels /= 2;
            }
        }
        return inSampleSize;
        // END_INCLUDE (calculate_sample_size)
    }
}

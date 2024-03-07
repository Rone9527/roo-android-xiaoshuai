package com.roo.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.roo.core.R;
import com.huantansheng.easyphotos.utils.bitmap.BitmapUtils;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DrawableProvider;
import com.jess.arms.utils.FastBlur;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by fg607 on 15-11-26.
 */
public class ImageUtils {

    // 将byte[]转换成InputStream
    public static InputStream byte2InputStream(byte[] b) {
        return new ByteArrayInputStream(b);
    }

    // 将InputStream转换成byte[]
    public static byte[] inputStream2Bytes(InputStream is) {
        StringBuilder str = new StringBuilder();
        byte[] readByte = new byte[1024];
        int readCount = -1;
        try {
            while ((readCount = is.read(readByte, 0, 1024)) != -1) {
                str.append(new String(readByte).trim());
            }
            return str.toString().getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 将Bitmap转换成InputStream
    public static InputStream bitmap2InputStream(Bitmap bm) {
        return bitmap2InputStream(bm, 100);
    }

    // 将Bitmap转换成InputStream
    public static InputStream bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    // 将InputStream转换成Bitmap
    public static Bitmap inputStream2Bitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }

    // Drawable转换成InputStream
    public static InputStream drawable2InputStream(Drawable d) {
        Bitmap bitmap = drawable2Bitmap(d);
        return bitmap2InputStream(bitmap);
    }

    // InputStream转换成Drawable
    public static Drawable inputStream2Drawable(InputStream is) {
        Bitmap bitmap = inputStream2Bitmap(is);
        return bitmap2Drawable(bitmap);
    }

    // Drawable转换成byte[]
    public static byte[] drawable2Bytes(Drawable d) {
        Bitmap bitmap = drawable2Bitmap(d);
        return bitmap2Bytes(bitmap);
    }

    // byte[]转换成Drawable
    public static Drawable bytes2Drawable(byte[] b) {
        Bitmap bitmap = bytes2Bitmap(b);
        return bitmap2Drawable(bitmap);
    }

    // Bitmap转换成byte[]
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    // byte[]转换成Bitmap
    public static Bitmap bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return null;
    }

    // Drawable转换成Bitmap
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    // Bitmap转换成Drawable
    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    // apk编译时为了向下兼容，会根据vector生产相应的png
    // 4.4的系统运行此代码时其实用的是png资源。这就是为什么5.0以上会报错，而4.4不会的原因
    public static Bitmap getVectorBitmap(Context context, @DrawableRes int drawableId) {
        Bitmap bitmap;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(drawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        }
        return bitmap;
    }

    /**
     * 给图片添加水印，水印会根据图片宽高自动缩放处理
     *
     * @param watermark              水印
     * @param image                  添加水印的图片
     * @param offsetX                添加水印的X轴偏移量
     * @param offsetY                添加水印的Y轴偏移量
     * @param srcWaterMarkImageWidth 水印对应的原图片宽度,即ui制作水印时候参考的图片画布宽度,应该是已知的图片最大宽度
     */
    public static void addImage(Bitmap watermark, Bitmap image, int srcWaterMarkImageWidth, int offsetX, int offsetY) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        if (0 == imageWidth || 0 == imageHeight) {
            throw new RuntimeException("AlbumBuilder: 加水印的原图宽或高不能为0！");
        }
        int watermarkWidth = watermark.getWidth();
        int watermarkHeight = watermark.getHeight();
        float scale = imageWidth / (float) srcWaterMarkImageWidth;
        if (scale > 1) scale = 1;
        else if (scale < 0.4) scale = 0.4f;
        int scaleWatermarkWidth = (int) (watermarkWidth * scale);
        int scaleWatermarkHeight = (int) (watermarkHeight * scale);
        Bitmap scaleWatermark = Bitmap.createScaledBitmap(watermark, scaleWatermarkWidth, scaleWatermarkHeight, true);
        Canvas canvas = new Canvas(image);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawBitmap(scaleWatermark, offsetX, offsetY, paint);
        BitmapUtils.recycle(scaleWatermark);
    }

    public static void addText(Context context, Bitmap mBitmap, int offsetX, int offsetY, String content, int textSize, @ColorInt int textColor) {
        //获取原始图片与水印图片的宽与高
        Canvas mCanvas = new Canvas(mBitmap);
        //添加文字
        Paint mPaint = new Paint();
        mPaint.setColor(textColor);
        mPaint.setTextSize(ArmsUtils.sp2px(context, textSize));
        //水印的位置坐标
        mCanvas.drawText(content, offsetX, offsetY, mPaint);
        mCanvas.save();
        mCanvas.restore();
    }

    public static Bitmap convertText2Bitmap(Context context, String content, int textSize, @ColorInt int textColor) {
        int textSizePx = ArmsUtils.sp2px(context, textSize);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSizePx);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(content) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(content, 0, baseline, paint);
        return image;
    }

    public static Bitmap copyBitmap(Bitmap bmSrc) {
        //创建图片副本
        //1.在内存中创建一个与原图一模一样大小的bitmap对象，创建与原图大小一致的白纸
        Bitmap bmCopy = Bitmap.createBitmap(bmSrc.getWidth(), bmSrc.getHeight(), bmSrc.getConfig());

        //2.创建画笔对象
        Paint paint = new Paint();

        //3.创建画板对象，把白纸铺在画板上
        Canvas canvas = new Canvas(bmCopy);

        //4.开始作画，把原图的内容绘制在白纸上
        canvas.drawBitmap(bmSrc, new Matrix(), paint);
        return bmCopy;
    }

    /**
     * 缩小图片
     *
     * @param scaleWidth  宽度
     * @param scaleHeight 高度
     */
    public static Bitmap scaleBitmap(String filename, int scaleWidth, int scaleHeight) {
        if (scaleWidth == 0 || scaleHeight == 0) {
            throw new RuntimeException("scaleBitmap scaleWidth or scaleHeight can not be 0");
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);
        //缩放系数
        int inSampleSize = 1;
        //bitmap 实际尺寸
        int height = options.outHeight;
        int width = options.outWidth;

        //根据scalewidth 和scaleheight计算缩放系数
        if (width > scaleWidth || height > scaleHeight) {
            int halfWidht = width / 2;
            int halfHeight = height / 2;
            while ((halfHeight / inSampleSize) >= scaleHeight && (halfWidht / inSampleSize) >= scaleWidth) {
                inSampleSize *= 2;
            }
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
    }

    /**
     * @param scaleWidth  宽度缩放比例
     * @param scaleHeight 高度缩放比例
     */
    public static Bitmap scaleBitmap2(Bitmap bitmap, float scaleWidth, float scaleHeight) {
        if (scaleWidth == 0 || scaleHeight == 0 || bitmap == null) {
            return bitmap;
        } else {
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 创建缩放后的图片
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
    }

    /**
     * 裁剪
     *
     * @param bitmap     原图
     * @param cropWidth
     * @param cropHeight
     * @return 裁剪后的图像
     */
    public static Bitmap cropBitmap(Bitmap bitmap, int cropWidth, int cropHeight) {
        return Bitmap.createBitmap(bitmap, 0, 0, cropWidth, cropHeight, null, false);
    }

    /**
     * @param newWidth  宽度
     * @param newHeight 高度
     */
    public static Bitmap scaleBitmap3(Bitmap bitmap, double newWidth, double newHeight) {
        if (newWidth == 0 || newHeight == 0 || bitmap == null) {
            return bitmap;
        } else {
            // 记录bitmap的宽高
            //bitmap原始的宽
            int oldwidth = bitmap.getWidth();
            int oldHeight = bitmap.getHeight();
            // 创建一个matrix容器
            Matrix matrix = new Matrix();
            // 计算缩放比例
            //bitmap被缩放的比例
            float scaleWidth = (float) (newWidth / oldwidth);
            float scaleHeight = (float) (newHeight / oldHeight);
            // 开始缩放
            matrix.postScale(scaleWidth, scaleHeight);
            // 创建缩放后的图片
            return Bitmap.createBitmap(bitmap, 0, 0, oldwidth, oldHeight, matrix, true);
        }
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @param size   大小
     * @return
     */
    public static Bitmap drawCircleView(Bitmap bitmap, int size) {

        //前面同上，绘制图像分别需要bitmap，canvas，paint对象
        bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);//==========创建的图片的长宽为128
        Bitmap bm = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);//此处的长宽应该与上一行保持一致
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //这里需要先画出一个圆
        canvas.drawCircle(size / 2, size / 2, size / 2, paint);//===========此处应该为上面长宽的一半
        //圆画好之后将画笔重置一下
        paint.reset();
        //设置图像合成模式，该模式为只在源图像和目标图像相交的地方绘制源图像
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bm;
    }

    public static Drawable toRoundDrawable(Drawable drawable, int size) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            bitmap = bd.getBitmap();
        } else {
            bitmap = drawable2Bitmap(drawable);
        }
        bitmap = drawCircleView(bitmap, size);
        return bitmap2Drawable(bitmap);
    }

    public static Bitmap tintBitmap(Bitmap inBitmap, int tintColor) {
        if (inBitmap == null) {
            return null;
        }
        Bitmap outBitmap = Bitmap.createBitmap(inBitmap.getWidth(), inBitmap.getHeight(), inBitmap.getConfig());
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(inBitmap, 0, 0, paint);
        return outBitmap;
    }


    /**
     * 将 {@link Bitmap} 高斯模糊并返回
     *
     * @param bkg
     * @param width
     * @param height
     * @return
     */
    public static Bitmap blurBitmap(Bitmap bkg, int width, int height, int radius) {
        //获取模糊度->越大模糊效果越大
        long startMs = System.currentTimeMillis();
        float scaleFactor = 8;
        //放大到整个view的大小
        Bitmap reSizeBitmap = DrawableProvider.getReSizeBitmap(bkg, width, height);
        Bitmap overlay = Bitmap.createBitmap((int) (width / scaleFactor)
                , (int) (height / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(reSizeBitmap, 0, 0, paint);
        overlay = FastBlur.doBlur(overlay, radius, true);
        Log.w("test", "cost " + (System.currentTimeMillis() - startMs) + "ms");
        return overlay;
    }

    /**
     * 首先尝试通过字节数组或者流，只去加载图片的外边缘，此时必须指定BitmapFactory.Options 的inJustDecodeBounds成员名，
     * 将其只为true，一旦设置为true，BitmapFactory解码后返回值为null，
     * 通过Options的outHeight和outWidth可以获得图片的宽高。然后根据大小制定合适的缩放比例，通过options.inSampleSize，
     * 大大降低加载图片导致内存溢出的风险！
     *
     * @return 以最大高宽度的范围进行限定
     */
    public static Bitmap getReSizeBitmap(byte[] bytes, int imageMaxHeight, int imageMaxWidth) {

        //1.对于图片的二次采样,主要得到图片的宽与高
        int sampleSize = 1; //默认缩放为1
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;  //仅仅解码边缘区域
        //如果指定了inJustDecodeBounds，decodeByteArray将返回为空
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        //得到宽与高
        int height = options.outHeight;
        int width = options.outWidth;

        //2.图片实际的宽与高，根据默认最大大小值，得到图片实际的缩放比例
        while ((height / sampleSize > imageMaxHeight)
                || (width / sampleSize > imageMaxWidth)) {
            sampleSize *= 2;
        }

        //3.不再只加载图片实际边缘
        options.inJustDecodeBounds = false;
        //4.并且制定[缩放比例]
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 以高度进行缩放
     */
    public static Bitmap getReSizeBitmapByHeight(byte[] bytes, int imageHeight) {
        int sampleSize = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        int height = options.outHeight;
        if (imageHeight > 0) {
            sampleSize = height / imageHeight;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 以宽度进行缩放
     */
    public static Bitmap getReSizeBitmapByWidth(byte[] bytes, int imageWidth) {
        int sampleSize = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        int width = options.outWidth;
        if (imageWidth > 0) {
            sampleSize = width / imageWidth;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 以最小的一条边进行缩放
     */
    public static Bitmap getReSizeBitmapComplete(byte[] bytes, int imageSize) {
        int sampleSize = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        if (imageSize > 0) {
            sampleSize = Math.min(options.outWidth, options.outHeight) / imageSize;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }


    /**
     * 截取scrollview的屏幕
     *
     * @param viewGroup
     * @return
     */
    public static Bitmap getBitmapByView(ViewGroup viewGroup) {
        int h = 0;
        int w = 0;
        Bitmap bitmap;
        // 获取scrollview实际高度
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            h += viewGroup.getChildAt(i).getHeight();
            w += viewGroup.getChildAt(i).getWidth();
            viewGroup.getChildAt(i).setBackgroundColor(ResourceUtil.getColor(R.color.white));
        }
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        viewGroup.draw(canvas);
        return bitmap;
    }

    /**
     * 压缩图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap) {
        if (bitmap == null) {
            return bitmap;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 100) {
            // 重置baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 每次都减少10
            options -= 10;
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        return BitmapFactory.decodeStream(isBm, null, null);
    }
}

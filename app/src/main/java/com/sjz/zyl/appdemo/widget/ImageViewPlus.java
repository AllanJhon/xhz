package com.sjz.zyl.appdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.sjz.zyl.appdemo.R;

import org.kymjs.kjframe.widget.RoundImageView;

/**
 * 生成矩形圆角图片
 *
 * @author by 张迎乐(yingle1991@gmail.com)
 * @since 2018-01-27.
 */
public class ImageViewPlus extends ImageView {
    /**
     * android.widget.ImageView
     */
    public static final int TYPE_NONE = 0;
    /**
     * 圆形
     */
    public static final int TYPE_CIRCLE = 1;
    /**
     * 圆角矩形
     */
    public static final int TYPE_ROUNDED_RECT = 2;

    private static final int DEFAULT_TYPE = TYPE_NONE;
    private static final int DEFAULT_BORDER_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_RECT_ROUND_RADIUS = 0;
    private Bitmap currentBitmap; // 当前显示的bitmap
    private int mType;
    private int mBorderColor;
    private int mBorderWidth;
    private int mRectRoundRadius;

    private Paint mPaintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF mRectBorder = new RectF();
    private RectF mRectBitmap = new RectF();

    private Bitmap mRawBitmap;
    private BitmapShader mShader;
    private Matrix mMatrix = new Matrix();

    public ImageViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        //取xml文件中设定的参数
        mType=2;

    }

    private void initCurrentBitmap() {
        Bitmap temp = null;
        Drawable drawable = getDrawable();
        if (drawable instanceof BitmapDrawable) {
            temp = ((BitmapDrawable) drawable).getBitmap();
        }
        if (temp != null) {
            currentBitmap = temp;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Bitmap rawBitmap1 = getBitmap(getDrawable());
//        Bitmap rawBitmap=bimapRound(rawBitmap1,40);
//        if (rawBitmap != null && mType != TYPE_NONE){
//            int viewWidth = getWidth();
//            int viewHeight = getHeight();
//            int viewMinSize = Math.min(viewWidth, viewHeight);
//            float dstWidth = mType == TYPE_CIRCLE ? viewMinSize : viewWidth;
//            float dstHeight = mType == TYPE_CIRCLE ? viewMinSize : viewHeight;
//            float halfBorderWidth = mBorderWidth / 2.0f;
//            float doubleBorderWidth = mBorderWidth * 2;
//
//            if (mShader == null || !rawBitmap.equals(mRawBitmap)){
//                mRawBitmap = rawBitmap;
//                mShader = new BitmapShader(mRawBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//            }
//            if (mShader != null){
//                mMatrix.setScale((dstWidth - doubleBorderWidth) / rawBitmap.getWidth(), (dstHeight - doubleBorderWidth) / rawBitmap.getHeight());
//                mShader.setLocalMatrix(mMatrix);
//            }
//
//            mPaintBitmap.setShader(mShader);
//            mPaintBorder.setStyle(Paint.Style.STROKE);
//            mPaintBorder.setStrokeWidth(mBorderWidth);
//            mPaintBorder.setColor( Color.TRANSPARENT);
//                mRectBorder.set(halfBorderWidth, halfBorderWidth, dstWidth - halfBorderWidth, dstHeight - halfBorderWidth);
//                mRectBitmap.set(0.0f, 0.0f, dstWidth - doubleBorderWidth, dstHeight - doubleBorderWidth);
//                float borderRadius = mRectRoundRadius - halfBorderWidth > 0.0f ? mRectRoundRadius - halfBorderWidth : 0.0f;
//                float bitmapRadius = mRectRoundRadius - mBorderWidth > 0.0f ? mRectRoundRadius - mBorderWidth : 0.0f;
//                canvas.drawRoundRect(mRectBorder, borderRadius, borderRadius, mPaintBorder);
//                canvas.translate(mBorderWidth, mBorderWidth);
//                canvas.drawRoundRect(mRectBitmap, bitmapRadius, bitmapRadius, mPaintBitmap);
//        }else {
//            super.onDraw(canvas);
//        }

//        initCurrentBitmap();
//        if (currentBitmap == null || getWidth() == 0 || getHeight() == 0) {
//            return;
//        }
//        this.measure(0, 0);
//        int width = getWidth();
//        int height = getHeight();
//        int radius = 10; // 半径
////        //新建一个bitmap对象
////        Bitmap bitmap=Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
////        //将画布的Bitmap改成新建的bitmap
////        canvas.setBitmap(bitmap);
//        Paint paint=new Paint();
//        paint.setColor(Color.TRANSPARENT);
////        canvas.drawRect(0, 0, 50, 50, paint);
//
//
//        // 初始化缩放比
//        float widthScale = width * 1.0f / currentBitmap.getWidth();
//        float heightScale = height * 1.0f / currentBitmap.getHeight();
//        Matrix matrix = new Matrix();
//        matrix.setScale(widthScale, heightScale);
//
//        // 初始化绘制纹理图
//        BitmapShader bitmapShader = new BitmapShader(currentBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        // 根据控件大小对纹理图进行拉伸缩放处理
//        bitmapShader.setLocalMatrix(matrix);
//
//        // 初始化目标bitmap
//        Bitmap targetBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//
//        paint.setAntiAlias(true);
//        paint.setShader(bitmapShader);
//
//        // 利用画笔将纹理图绘制到画布上面
//        canvas.drawRoundRect(new RectF(0, 0, width, width), radius, radius, paint);

        initCurrentBitmap();
        if (currentBitmap == null || getWidth() == 0 || getHeight() == 0) {
            return;
        }
        this.measure(0, 0);
        int width = getWidth();
        int height = getHeight();
//        int radius = 0; // 半径
//        if (mBorderInsideColor != 0 && mBorderOutsideColor != 0) { // 画两个边框
//            radius = (width < height ? width : height) / 2 - 2
//                    * mBorderThickness;
//            // 画内圆
//            drawCircleBorder(canvas, radius + mBorderThickness / 2, width,
//                    height, mBorderInsideColor);
//            // 画外圆
//            drawCircleBorder(canvas, radius + mBorderThickness
//                    + mBorderThickness / 2, width, height, mBorderOutsideColor);
//        } else if (mBorderInsideColor != 0 && mBorderOutsideColor == 0) { // 画一个边框
//            radius = (width < height ? width : height) / 2 - mBorderThickness;
//            drawCircleBorder(canvas, radius + mBorderThickness / 2, width,
//                    height, mBorderInsideColor);
//        } else if (mBorderInsideColor == 0 && mBorderOutsideColor != 0) {// 画一个边框
//            radius = (width < height ? width : height) / 2 - mBorderThickness;
//            drawCircleBorder(canvas, radius + mBorderThickness / 2, width,
//                    height, mBorderOutsideColor);
//        } else { // 没有边框
//            radius = (width < height ? width : height) / 2;
//        }
        Bitmap roundBitmap = roundBitmapByShader(currentBitmap,width,height,40);
//        canvas.drawBitmap(roundBitmap, 0, 0,
//                null);
//        canvas.drawRoundRect(new RectF(0, 0, width, width), 10, 10, null);

        canvas.drawBitmap(roundBitmap,0,0,null);

    }

    private Bitmap getBitmap(Drawable drawable){
        if (drawable instanceof BitmapDrawable){
            return ((BitmapDrawable)drawable).getBitmap();
        } else if (drawable instanceof ColorDrawable){
            Rect rect = drawable.getBounds();
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;
            int color = ((ColorDrawable)drawable).getColor();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * 根据原图添加圆角
     *
     * @param mBitmap
     * @return
     */
    private Bitmap bimapRound(Bitmap mBitmap,float index){
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        //设置矩形大小
        Rect rect = new Rect(0,0,mBitmap.getWidth(),mBitmap.getHeight());
        RectF rectf = new RectF(rect);

        // 相当于清屏
        canvas.drawARGB(0, 0, 0, 0);
        //画圆角
        canvas.drawRoundRect(rectf, index, index, paint);
        // 取两层绘制，显示上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 把原生的图片放到这个画布上，使之带有画布的效果
        canvas.drawBitmap(mBitmap, rect, rect, paint);
        return bitmap;

    }

    /**
     * 利用BitmapShader绘制圆角图片
     *
     * @param bitmap
     *              待处理图片
     * @param outWidth
     *              结果图片宽度，一般为控件的宽度
     * @param outHeight
     *              结果图片高度，一般为控件的高度
     * @param radius
     *              圆角半径大小
     * @return
     *              结果图片
     */
    private Bitmap roundBitmapByShader(Bitmap bitmap, int outWidth, int outHeight, int radius) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }
        // 初始化缩放比
        float widthScale = outWidth * 1.0f / bitmap.getWidth();
        float heightScale = outHeight * 1.0f / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);

        // 初始化绘制纹理图
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // 根据控件大小对纹理图进行拉伸缩放处理
        bitmapShader.setLocalMatrix(matrix);

        // 初始化目标bitmap
        Bitmap targetBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);

        // 初始化目标画布
        Canvas targetCanvas = new Canvas(targetBitmap);

        // 初始化画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        // 利用画笔将纹理图绘制到画布上面
        targetCanvas.drawRoundRect(new RectF(0, 0, outWidth, outHeight), radius, radius, paint);

        return targetBitmap;
    }
}

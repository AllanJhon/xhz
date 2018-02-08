package com.sjz.zyl.appdemo.utils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sjz.zyl.appdemo.AppConfig;
import com.sjz.zyl.appdemo.R;
import com.sjz.zyl.appdemo.domain.Article;

import org.kymjs.kjframe.KJBitmap;

/**
 * Created by admin on 2018/1/27.
 */
public class MyRowItemView extends LinearLayout{

    private Boolean isDraw;
    private ImageView imageView;
    private TextView myTextView;
    private LinearLayout linearLayout;
    private Context mContext;
    public MyRowItemView(Context context) {
        super(context);
    }
    public MyRowItemView(Context context, AttributeSet attr) {
        super(context,attr);
        View v=LayoutInflater.from(context).inflate(R.layout.row_item_view,this,true);
        mContext=context;
        imageView=(ImageView)v.findViewById(R.id.img);
        linearLayout=(LinearLayout)v.findViewById(R.id.rowLiner);
        myTextView=(TextView)v.findViewById(R.id.text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.setBackgroundResource(R.drawable.app_logo);
//        canvas.drawColor(Color.WHITE);
//        if (isDraw) {
//            canvas.drawColor(Color.YELLOW);
//        }
//        Paint  txtpaint = new Paint();
//        txtpaint.setColor(Color.WHITE);
//        canvas.drawRect(0, 0, getWidth(), getHeight(), txtpaint);
        super.onDraw(canvas);
        this.setBackgroundResource(R.color.click);
//        this.setBackgroundColor(Color.WHITE);
//        this.setBackgroundResource(R.color.white);
//        if (isDraw) {
//            this.setBackgroundColor(getResources().getColor(R.color.click));
//        }

    }

    public void isDraw(boolean isDraw) {
        linearLayout.setBackgroundResource(R.color.white);
        this.isDraw = isDraw;
        if (isDraw) {
            linearLayout.setBackgroundResource(R.color.click);
        }
//        postInvalidate();
    }

    public void setImageView(int ResId){
        imageView.setImageResource(ResId);
    }

    public void setImageView(String url){
        KJBitmap kjb = new KJBitmap();

        if(kjb.getMemoryCache(url)!=null){
            kjb.displayCacheOrDefult(imageView,
                    url,R.drawable.finance);
        }else {
            kjb.displayLoadAndErrorBitmap(imageView,
                    url,R.drawable.loading,R.drawable.app_logo);
        }
    }

    public void setMyTextView(String str){
        myTextView.setText(str);
    }

}

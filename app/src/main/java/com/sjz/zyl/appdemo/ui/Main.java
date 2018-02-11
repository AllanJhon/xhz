package com.sjz.zyl.appdemo.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.sjz.zyl.appdemo.R;
import com.sjz.zyl.appdemo.domain.Article;
import com.sjz.zyl.appdemo.domain.Categories;
import com.sjz.zyl.appdemo.domain.News;
import com.sjz.zyl.appdemo.utils.Parser;
import com.sjz.zyl.appdemo.utils.Utils;
import com.sjz.zyl.appdemo.utils.adapter.ExpandRowGridAdapter;
import com.sjz.zyl.appdemo.utils.adapter.MyViewPagerAdapter;
import com.sjz.zyl.appdemo.utils.entity.BaseData;
import com.sjz.zyl.appdemo.utils.entity.XhzCategories;
import com.sjz.zyl.appdemo.utils.entity.ZhaoPin;
import com.sjz.zyl.appdemo.utils.view.CurrenPositionView;
import com.sjz.zyl.appdemo.utils.view.MyRowItemView;
import com.sjz.zyl.appdemo.utils.view.MyTextView;
import com.sjz.zyl.appdemo.widget.ImageViewPlus;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.KJDB;
import org.kymjs.kjframe.bitmap.BitmapConfig;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author by 张迎乐(yingle1991@gmail.com)
 * @since 2018-01-25.
 */
public class Main extends KJActivity implements ExpandRowGridAdapter.OnClick {

    //招聘数据
    private ViewFlipper flipper;
//    private int[] resId = {R.drawable.main_1, R.drawable.main_2, R.drawable.main_3};
//    private String[] resId={"http://sj-xhz.oss-cn-qingdao.aliyuncs.com/2018-01-28-11-41-27-5a6db6e71b9b6.jpg","http://sj-xhz.oss-cn-qingdao.aliyuncs.com/2018-01-28-11-49-29-5a6db8c9736a9.jpg"};
    private List<News> newses=new ArrayList<News>();
    private LinearLayout linearLayout;
    private LinearLayout image_linearLayout;
    private float start;
    private float end;
    private static int NUM = 3;//总图片的数量
    /**
     * 自动进行轮播的标记：AUTO=1
     */
    private static final int AUTO = 0x01;
    /**
     * 向左滑动 上一张的标记：PREVIOUS=2
     */
    private static final int PREVIOUS = 0x02;
    /**
     * 向右滑动 下一张的标记：NEXT=3
     */
    private static final int NEXT = 0x03;
    List<View> mDotList = new ArrayList<View>();
    private List<XhzCategories> data;
    private LinearLayout currenPositionLinear;
    private final Integer duration = 200;
    private RelativeLayout rel;
    private String itemId;
    //记录点击位置
    private String clickPosition = "";
    private MyRowItemView clicktxt;
    //下拉的 圆点显示layout
    private LinearLayout circlelayout;
    private int lastlocation = -1;
    private MyRowItemView myRowItemView;
    private ExpandRowGridAdapter.OnClick listener;
    //每行显示条目个数
    private static final int NUM_LINE = 4;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_main);
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        linearLayout = (LinearLayout) findViewById(R.id.show_dot);
        initData();

        /*
        * 动态导入的方式为ViewFlipper加入子View
        * */
        for (int i = 0; i <newses.size(); i++) {
            LayoutInflater inflater = getLayoutInflater();
            int height = (int) (Main.this.getResources().getDisplayMetrics().density * 10 + 0.1f);
            int weight = (int) (Main.this.getResources().getDisplayMetrics().density * 10 + 0.1f);
            LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(height, weight);
            View v = inflater.inflate(R.layout.circle_view, null);
//            v.setPadding(10, 10, 10, 10);

            vlp.setMargins(10, 10, 10, 10);
            if (i == 0) {
                v.setBackgroundResource(R.drawable.dot_focus);
            }
            v.setLayoutParams(vlp);

            linearLayout.addView(v);
            ImageViewPlus imageViewPlus = getImageView(Parser.getUrl(newses.get(i).getNewsLogo()));
            imageViewPlus.setBackgroundColor(Color.TRANSPARENT);
            //新闻图片点击时间
            imageViewPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int current = flipper.getDisplayedChild();
//                Toast.makeText(getApplicationContext(),"点击了"+newses.get(current).getNewsID(),Toast.LENGTH_SHORT);
//                    ViewInject.toast(newses.get(current).getNewsID()+"");
                    Intent intent = new Intent(Main.this, NewsActivity.class);
                    intent.putExtra("id", newses.get(current).getNewsID());
                    intent.putExtra("value", newses.get(current).getNewsTitle());
                    setResult(RESULT_OK, intent);
                    startActivity(intent);
                }
            });
            flipper.addView(imageViewPlus);
            flipper.setBackgroundColor(Color.TRANSPARENT);
            mDotList.add(v);
        }
        initView();
        sendMes();

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AUTO:
                    showNext();
                    sendMes();
                    break;
                case PREVIOUS:
                    //必须先移除掉自动轮播的模式，否则会出现图片轮播错乱
                    mHandler.removeMessages(AUTO);
                    showPre();
                    sendMes();
                    break;
                case NEXT:
                    mHandler.removeMessages(AUTO);
                    showNext();
                    sendMes();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 发送信息，自动进行轮播
     */
    public void sendMes() {
        Message msgs = new Message();
        msgs.what = AUTO;
        //发送延迟消息，做到轮播的效果
        mHandler.sendMessageDelayed(msgs, 2000);
    }

    private ImageViewPlus getImageView(String resId) {
        ImageViewPlus image = new ImageViewPlus(this, null);
        KJBitmap kjb = new KJBitmap(new BitmapConfig());
        if(!"".equals(resId)) {
            kjb.display(image,
                    resId);
        }else{
            kjb.displayCacheOrDefult(image,
                    resId,R.drawable.finance);
        }
        return image;
    }

    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start = event.getX();
                break;
            case MotionEvent.ACTION_MOVE://判断向左滑动还是向右滑动
                end = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (end - start < -120) {
                    showPre();
                } else if (end - start > 120) {
                    showNext();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 自动滑动显示下一张图片 以及 跟着变动的小点
     */
    private void showNext() {
        flipper.setInAnimation(this, R.anim.right_in);
        flipper.setOutAnimation(this, R.anim.right_out);
        flipper.showNext();
        int current = flipper.getDisplayedChild();
        if (current == 0) {
            mDotList.get(NUM - 1).setBackgroundResource(R.drawable.dot_normal);
        } else {
            mDotList.get(current - 1).setBackgroundResource(R.drawable.dot_normal);
        }
        mDotList.get(current).setBackgroundResource(R.drawable.dot_focus);
    }

    /**
     * 向左滑动  显示上一张图片
     */
    private void showPre() {
        flipper.setInAnimation(this, R.anim.left_in);
        flipper.setOutAnimation(this, R.anim.left_out);
        flipper.showPrevious();
        int current = flipper.getDisplayedChild();
        if (current == NUM - 1) {
            mDotList.get(0).setBackgroundResource(R.drawable.dot_normal);
        } else {
            mDotList.get(current + 1).setBackgroundResource(R.drawable.dot_normal);
        }
        mDotList.get(current).setBackgroundResource(R.drawable.dot_focus);
    }

    private void initView() {
        currenPositionLinear = (LinearLayout) findViewById(R.id.zp_curr_potion_linear);
        image_linearLayout=(LinearLayout)findViewById(R.id.image_linearLayout);
        for (int num = 0; num < data.size(); num++) {
            XhzCategories xhzCategories = data.get(num);
            CurrenPositionView currenPositionView = new CurrenPositionView(this);
            currenPositionView.setData(xhzCategories);
            currenPositionView.setViewId(num);
            currenPositionView.init();
            if (num > 0) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                if (num == data.size() - 1) {
                    params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                }
                currenPositionLinear.addView(currenPositionView, params);
            } else {
                currenPositionLinear.addView(currenPositionView);
            }

        }

        //新闻图片点击时间
        currenPositionLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = flipper.getDisplayedChild();
//                Toast.makeText(getApplicationContext(),"点击了"+newses.get(current).getNewsID(),Toast.LENGTH_SHORT);
//                ViewInject.toast(newses.get(current).getNewsID()+"");
                Intent intent = new Intent(Main.this, NewsActivity.class);
                intent.putExtra("id", newses.get(current).getNewsID());
                intent.putExtra("value", newses.get(current).getNewsTitle());
                setResult(RESULT_OK, intent);
                startActivity(intent);
            }
        });
    }

    public void initData() {
        if (listener == null) {
            listener = this;
        }
        if (data == null) {
//            data = Utils.getJobType(this);
            data=Utils.getCategories(this);
            newses=Utils.getNews(this);
            NUM = newses.size();
        }
    }

    public class OnItemClick implements View.OnClickListener, ViewPager.OnPageChangeListener {
        private final int position;
        private final int tag;
        //        private final MyTextView view;
        private final MyRowItemView myRowItemView;
        private final TableLayout tableLayout;
        private final XhzCategories xhzCategories;

        public OnItemClick(XhzCategories xhzCategories, int position, int tag, TableLayout table, MyTextView view) {
            this.position = position;
            this.tag = tag;
//            this.view = view;
            this.myRowItemView = null;
            this.tableLayout = table;
            this.xhzCategories = xhzCategories;
        }

        public OnItemClick(XhzCategories xhzCategories, int position, int tag, TableLayout table, MyRowItemView view) {
            this.position = position;
            this.tag = tag;
//            this.view = null;
            this.myRowItemView = view;
            this.tableLayout = table;
            this.xhzCategories = xhzCategories;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if (lastlocation != arg0 && lastlocation != -1) {
                ImageView mimage = (ImageView) circlelayout
                        .findViewWithTag(lastlocation + 1 + 10);
                if (mimage != null) {
                    mimage.setImageResource(R.mipmap.black_circle);
                }
            }

            ImageView image = (ImageView) circlelayout
                    .findViewWithTag(arg0 + 1 + 10);
            if (image != null) {
                image.setImageResource(R.mipmap.orange_circle);
            }

            lastlocation = arg0;
        }

        @Override
        public void onPageSelected(int arg0) {
            myRowItemView.setBackgroundColor(getResources().getColor(R.color.click));
        }

        private void init(View v, int location) {
            List<Categories> list;
            ViewPager pager = (ViewPager) v.findViewById(R.id.expand_item);
            // 底部的圆点初始化
            circlelayout = (LinearLayout) v.findViewById(R.id.circle_layout);
            circlelayout.removeAllViews();
            int radio = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
            // 初始化一个params 用于圆点大小设置
            LinearLayout.LayoutParams circleparams = new LinearLayout.LayoutParams(radio, radio);
            circleparams.setMargins(0, 0, 10, 0);
            List<View> views = new ArrayList<>();
            // 下拉表格的行数
            int row = xhzCategories.getArticleType().get(location).getCategoriesList().size() / NUM_LINE;
            if (row > 3) {
                row = 3;
            } else if (row < 3) {
                if (xhzCategories.getArticleType().get(location).getCategoriesList().size() % NUM_LINE != 0) {
                    row += 1;
                }
            }
            // 计算生成 view的数量
            int i = xhzCategories.getArticleType().get(location).getCategoriesList().size() / 12;
            int len = xhzCategories.getArticleType().get(location).getCategoriesList().size() % 12;
            if (len > 0) {
                i += 1;
            }
            // View的 初始化
            for (int n = 1; n <= i; n++) {
                View view = LayoutInflater.from(Main.this).inflate(R.layout.viewpager_child, null);
                GridView grid = (GridView) view.findViewById(R.id.expand_grid);
                // 计算每个 gridview 存放多少数据
                if (i == 0) {
                    list = xhzCategories.getArticleType().get(location).getCategoriesList();
                } else if (n < i) {
                    // 每一个 表只有3列 最多 4行 每个表最多12个
                    list = xhzCategories.getArticleType().get(location).getCategoriesList().subList((n - 1) * 12, n * 12);
                } else {
                    int size = xhzCategories.getArticleType().get(location).getCategoriesList().size();
                    list = xhzCategories.getArticleType().get(location).getCategoriesList().subList((n - 1) * 12, size);
                }
                ExpandRowGridAdapter ea = new ExpandRowGridAdapter(Main.this, list, R.layout.row_item_view);
                ea.setOnClick(listener);
                grid.setAdapter(ea);
                views.add(grid);
                if (i > 1) {
                    circlelayout.setVisibility(View.VISIBLE);
                    // viewpager 底部的 点标识
                    ImageView image = new ImageView(Main.this);
                    image.setTag(n + 10);
                    image.setImageResource(R.mipmap.black_circle);
                    circlelayout.addView(image, circleparams);
                } else {
                    circlelayout.setVisibility(View.GONE);
                }
            }
            MyViewPagerAdapter mpa = new MyViewPagerAdapter(views);
            // 宽度每一个格高度 42dp 转成px
//            int height = (int) (Main.this.getResources().getDisplayMetrics().density * 47 + 0.1f);
            int height = (int) (Main.this.getResources().getDisplayMetrics().density * 90 + 0.1f);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, row * height);
            pager.setLayoutParams(params);
            pager.setAdapter(mpa);
            pager.setOnPageChangeListener(this);
        }

        // 收缩动画
        private void collapse(final View v) {
            final int initialHeight = v.getMeasuredHeight();
            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime,
                                                   Transformation t) {
                    // interpolatedTime 0-1 进行变化 为1的时候表示动画已经完成
                    if (interpolatedTime == 1) {
                        v.setVisibility(View.GONE);
                    } else {
                        // 同样的原理 让控件的高度不断变化
                        v.getLayoutParams().height = initialHeight
                                - (int) (initialHeight * interpolatedTime);
                        v.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            animation.setDuration(duration);
            v.startAnimation(animation);
        }

        // 展开动画
        private void expand(final View v, int location) {
            init(v, location);
            v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            final int targetHeight = v.getMeasuredHeight();
            // 最开始显示下拉控件的时候 将高度设为0 达到不显示的效果
            v.getLayoutParams().height = 0;
            v.setVisibility(View.VISIBLE);

            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime,
                                                   Transformation t) {
                    // 不断变化高度来显示控件
                    v.getLayoutParams().height = (interpolatedTime == 1) ? RelativeLayout.LayoutParams.WRAP_CONTENT
                            : (int) (targetHeight * interpolatedTime);
                    v.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };
            animation.setDuration(duration);
            v.startAnimation(animation);
        }

        @Override
        public void onClick(View v) {
            String tempTag = (String) v.getTag();
            v.setBackgroundColor(getColor(R.color.click));
            if (rel == null) {
                rel = (RelativeLayout) tableLayout.findViewWithTag(tag);
                expand(rel, position);
                myRowItemView.isDraw(true);
            } else {
                if (rel.getVisibility() == View.VISIBLE) {
                    collapse(rel);
                    myRowItemView.isDraw(false);
                } else {
                    if (tempTag.equals(clickPosition)) {
                        expand(rel, position);
                    }
                    myRowItemView.isDraw(true);
                }

                if (!tempTag.equals(clickPosition)) {
                    rel = (RelativeLayout) tableLayout.findViewWithTag(tag);
                    expand(rel, position);
                    // 上一次的箭头去除
                    clicktxt.isDraw(false);
                    myRowItemView.isDraw(true);
                }
            }
            clickPosition = tempTag;
            clicktxt = myRowItemView;
        }
    }


    public void onClick(int dataId, String str) {
        Intent intent = new Intent(Main.this,TitleActivity.class);
        intent.putExtra("id", dataId);
        intent.putExtra("value", str);
        setResult(RESULT_OK, intent);
        startActivity(intent);
//        finish();
//        Toast.makeText(getApplicationContext(),"点击了"+str,Toast.LENGTH_SHORT);
    }
    public void onClick(int dataId, String str,String displayType) {
        KJDB kjdb=KJDB.create();
       List<Article> articleList= kjdb.findAllByWhere(Article.class,"ArticleCategoryID = "+dataId);
        if (displayType.equals("0")&&articleList.size()>0) {
            Intent intent = new Intent(Main.this, DetailActivity.class);
            intent.putExtra("id", articleList.get(0).getArticleID());
            intent.putExtra("value", str);
            setResult(RESULT_OK, intent);
            startActivity(intent);
        }else if(articleList.size()==0){
            ViewInject.toast("该分类下无文章！");
        }else if(displayType.equals("1")){
            Intent intent = new Intent(Main.this, ArticleListActivity.class);
            intent.putExtra("id", dataId);
            intent.putExtra("value", str);
            setResult(RESULT_OK, intent);
            startActivity(intent);
        }
//        finish();
//        Toast.makeText(getApplicationContext(),"点击了"+str,Toast.LENGTH_SHORT);
    }
}

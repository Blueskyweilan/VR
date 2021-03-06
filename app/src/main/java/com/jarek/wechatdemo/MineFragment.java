package com.jarek.wechatdemo;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MineFragment extends Fragment implements View.OnClickListener{
    private ViewPager viewPager;
    private ArrayList<View> pageview;
    private TextView collectLayout;
    private TextView shareLayout;
    private TextView concernLayout;
    // 滚动条图片
    private ImageView scrollbar;
    // 滚动条初始偏移量
    private int offset = 0;
    // 当前页编号
    private int currIndex = 0;
    // 滚动条宽度
    private int bmpW;
    //一倍滚动量
    private int one;
    //我的收藏、分享、关注列表
    private List<Share> mycollectList=new ArrayList<>();
    private List<Share> myshareList=new ArrayList<>();
    private List<Share> myconcernList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.mine_fragment, container, false);
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
//        加载收藏、分享、关注页面
        View view1 = inflater.inflate(R.layout.my_collect, null);
        View view2 = inflater.inflate(R.layout.my_share, null);
        View view3 = inflater.inflate(R.layout.my_concern, null);
        collectLayout = (TextView)view.findViewById(R.id.my_collection);
        shareLayout = (TextView)view.findViewById(R.id.my_share);
        concernLayout = (TextView)view.findViewById(R.id.my_concern);
        scrollbar = (ImageView)view.findViewById(R.id.scrollbar);
        collectLayout.setOnClickListener(this);
        shareLayout.setOnClickListener(this);
        concernLayout.setOnClickListener(this);
        pageview =new ArrayList<View>();
        //添加想要切换的界面
        pageview.add(view1);
        pageview.add(view2);
        pageview.add(view3);
        //数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter(){
            @Override
            //获取当前窗体界面数
            public int getCount() {
                // TODO Auto-generated method stub
                return pageview.size();
            }
            @Override
            //判断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0==arg1;
            }
            //使从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(pageview.get(arg1));
            }
            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1){
                ((ViewPager)arg0).addView(pageview.get(arg1));
                return pageview.get(arg1);
            }
        };
        //绑定适配器
        viewPager.setAdapter(mPagerAdapter);

        initmycollect();//初始化收藏列表数据
        ShareAdapter adapter=new ShareAdapter(getActivity(),R.layout.share_item,mycollectList);
        ListView mycollectlist=(ListView)view1.findViewById(R.id.MyCollectList);
        mycollectlist.setAdapter(adapter);

        initmycollect();//初始化分享列表数据
        ShareAdapter adapter2=new ShareAdapter(getActivity(),R.layout.share_item,myshareList);
        ListView mycollectlist2=(ListView)view2.findViewById(R.id.MyShareList);
        mycollectlist2.setAdapter(adapter);

        initmycollect();//初始化关注列表数据
        ShareAdapter adapter3=new ShareAdapter(getActivity(),R.layout.share_item,myconcernList);
        ListView mycollectlist3=(ListView)view3.findViewById(R.id.MyConcernList);
        mycollectlist3.setAdapter(adapter);
        //设置viewPager的初始界面为第一个界面
        viewPager.setCurrentItem(0);

        //添加切换界面的监听器
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        // 获取滚动条的宽度
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.scrollbar).getWidth();
        //为了获取屏幕宽度，新建一个DisplayMetrics对象
        DisplayMetrics displayMetrics = new DisplayMetrics();
        //将当前窗口的一些信息放在DisplayMetrics类中
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //得到屏幕的宽度
        int screenW = displayMetrics.widthPixels;
        //计算出滚动条初始的偏移量
        offset = (screenW / 3 - bmpW) / 3;
        //计算出切换一个界面时，滚动条的位移量
        one = offset * 3 + bmpW;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        //将滚动条的初始位置设置成与左边界间隔一个offset
        scrollbar.setImageMatrix(matrix);
        return view;
    }

    public int randomByMinMax(int min, int max) {
        return new Random().nextInt(max + 1 - min) + min;
    }

    private void initmycollect(){
        SimpleDateFormat formatter  =new  SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
//        Date curDate =new Date(System.currentTimeMillis()+ randomByMinMax(10000, 99999));//获取当前时间
//        String str =formatter.format(curDate);
        for (int i=0;i<8;i++){
            //int shareId,String shareName,int headImage,String shareWords,int shareImages,String shareDate,String shareComment
            Share shareuser1=new Share(1,"apple",R.drawable.header1,"apple is delicious",R.drawable.lijiang1,formatter.format(new Date(System.currentTimeMillis()+ randomByMinMax(10000, 99999))),"yes I think so that.");
            mycollectList.add(shareuser1);
            Share shareuser2=new Share(2,"orange",R.drawable.header2,"orange is delicious",R.drawable.lijiang2,formatter.format(new Date(System.currentTimeMillis()+ randomByMinMax(10000, 99999))),"yes I think so that.");
            mycollectList.add(shareuser2);
            Share shareuser3=new Share(3,"pear",R.drawable.header3,"pear is delicious",R.drawable.hangzhou1,formatter.format(new Date(System.currentTimeMillis()+ randomByMinMax(10000, 99999))),"yes I think so that.");
            mycollectList.add(shareuser3);
            Share shareuser4=new Share(4,"pear",R.drawable.header4,"pear is delicious",R.drawable.hangzhou2,formatter.format(new Date(System.currentTimeMillis()+ randomByMinMax(10000, 99999))),"yes I think so that.");
            mycollectList.add(shareuser4);
            Share shareuser5=new Share(5,"pear",R.drawable.header5,"pear is delicious",R.drawable.hangzhou3,formatter.format(new Date(System.currentTimeMillis()+ randomByMinMax(10000, 99999))),"yes I think so that.");
            mycollectList.add(shareuser5);
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    /**
                     * TranslateAnimation的四个属性分别为
                     * float fromXDelta 动画开始的点离当前View X坐标上的差值
                     * float toXDelta 动画结束的点离当前View X坐标上的差值
                     * float fromYDelta 动画开始的点离当前View Y坐标上的差值
                     * float toYDelta 动画开始的点离当前View Y坐标上的差值
                     **/
                    animation = new TranslateAnimation(one, 0, 0, 0);
                    break;
                case 1:
                    animation = new TranslateAnimation(offset, one, 0, 0);
                    break;
                case 2:
                    animation = new TranslateAnimation(offset*2, one*2, 0, 0);
                    break;
            }
            //arg0为切换到的页的编码
            currIndex = arg0;
            // 将此属性设置为true可以使得图片停在动画结束时的位置
            animation.setFillAfter(true);
            //动画持续时间，单位为毫秒
            animation.setDuration(200);
            //滚动条开始动画
            scrollbar.startAnimation(animation);
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.my_collection:
                //点击“我的收藏”时切换到第一页
                viewPager.setCurrentItem(0);
                break;
            case R.id.my_share:
                //点击“我的分享”时切换的第二页
                viewPager.setCurrentItem(1);
                break;
            case R.id.my_concern:
                //点击“我的关注”时切换的第三页
                viewPager.setCurrentItem(2);
                break;
        }
    }

}

package com.nju.Flash.image_manipulation.MyLayout;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by randy on 14-3-3.
 */
public class SlidingLayout extends LinearLayout implements View.OnTouchListener {
    //用于判断滑动和显示左侧菜单的手指速度
    public final static int SNAP_SPEED=200;//????

    private int screen_Width=0;//屏幕宽度
    /*
            left由左侧布局的宽度来决定，与leftmargin有关。左侧布局最多可以滑动的左侧边缘宽度
            right  恒为0
     */
    private int left_Edge=0;
    private int right_Edge=0;
    //左侧布局完全显示后，给右侧留下的宽度
    private int leftLayoutPadding=80;

    /*
            记录手指的运动的坐标
     */
    private int xMove;
    private int xUp;

    //左侧是否显现，只有在完全显现和消失时才改变
    private boolean isLeftLayoutVisible=false;

    //左右两部分的布局
    private View left_Layout=null;
    private View right_Layout=null;

    //用于监听的view
    private View mBindView=null;

    //左右侧的布局参数，确定左侧布局的宽度，改变leftmargin的值,还有右侧的宽度
    private MarginLayoutParams leftLayoutParams=null;
    private MarginLayoutParams rightLayoutParams=null;

    //计算手指移动的速度
    private VelocityTracker fingerVelocityTracker=null;



    public SlidingLayout(Context context) {
        super(context);
    }

    /**
     * 获得屏幕的宽度
     * @param context
     * @param attrs
     */
    public SlidingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        screen_Width= windowManager.getDefaultDisplay().getWidth();

    }

    /**
     * 绑定监听侧滑事件的view,滑动view，显示隐藏的layout
     * @param bindView
     */
    public void setScrollEvent(View bindView) {
        mBindView=bindView;
        mBindView.setOnTouchListener(this);
    }


    /**
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public SlidingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public void scrollToLeftLayout() {
//        new ScrollTask()
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    /**
     * Created by randy on 14-3-3.
     */
    public class ScrollTask extends AsyncTask<Integer,Integer,Integer> {
        @Override
        protected Integer doInBackground(Integer... integers) {
            int leftMargin=leftLayoutParams.leftMargin;
            //根据传入的速度来滚动界面，当滚动到达左或右界面时，跳出循环
            while(true) {
                leftMargin=leftMargin+integers[0];
                if(leftMargin>right_Edge) {
                    leftMargin=right_Edge;
                    break;
                }
                if(leftMargin<left_Edge) {
                    leftMargin=left_Edge;
                    break;
                }
                publishProgress(leftMargin);//TODO:??????搞懂用法
                //为产生滚动效果，每次循环使线程睡眠20毫秒，才能看出滚动
                sleep(20);
            }
            if(integers[0]>0) {
                isLeftLayoutVisible=true;
            } else {
                isLeftLayoutVisible=false;
            }
            return leftMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            leftLayoutParams.leftMargin=values[0];
            left_Layout.setLayoutParams(leftLayoutParams);
//            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            leftLayoutParams.leftMargin=integer;
            left_Layout.setLayoutParams(leftLayoutParams);
// super.onPostExecute(integer);
        }

        /**
         *
         * @param mills
         */
        private void sleep(long mills) {
           try {
               Thread.sleep(mills);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
        }
    }

}

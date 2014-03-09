package com.nju.Flash.image_manipulation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.nju.Flash.R;


/**
 * Created with IntelliJ IDEA.
 * User: Thunder
 * Date: 14-2-26
 * Time: 上午10:25
 * To change this template use File | Settings | File Templates.
 */
public class Image_manipulation_main_activity extends Activity implements View.OnClickListener {

    //for test
    private MyManipulationView2 myView=null;
    //
    private Intent photoUriIntent=null;//启动此activity的Intent,内有photo的地址信息
	private LinearLayout LL_show=null;
    private Image photo=null;//要进行操作的图片
	private boolean isBig=false;//图片是否大于屏幕分辨率
    private boolean isEraser=false;//
    //下边是activity的各个组件
    private ImageView showImageView=null;
    private Button pen_Start_Button=null;
    private Button cutoff_Start_Button=null;//
    private Button eraserButton=null;//橡皮或画笔
    private Button cleanAllButton=null;//清空
    private Button saveButton=null;//保存
    private Button showButton=null;//显示

	/**
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_handle_activity);
		photoUriIntent=getIntent();
        init();
		



	}

    /**
     * 处理整个activity的图片和按钮的初始化问题
     */
    private void init(){
        initMyView();
        initPhoto(photoUriIntent);
        initButton();
    }
    private void initButton() {
        pen_Start_Button=(Button)findViewById(R.id.pen_start_button);
//        pen_Start_Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showImageView.setOnTouchListener(new Pen_Start_Button_Listener());
//            }
//        });
        pen_Start_Button.setOnClickListener(this);
        cutoff_Start_Button=(Button)findViewById(R.id.cut_off_start_button);
        cutoff_Start_Button.setOnClickListener(this);
    }


	/**
	 * 形成菜单栏
	 * @param menu
	 * @return
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.image_handle_activity_menu, menu);
		return true;
	}

	/**
	 * 处理菜单栏被选中的action
	 * @param item
	 * @return
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.image_handle_net_share: {
				//TODO:杨松来写
			}
			default:
				return super.onOptionsItemSelected(item);
		}

	}

	/**
	 *
	 * @param intent
	 */
	private void initPhoto(Intent intent) {

        photo.setActivity(this);
        photo.createPhoto(intent.getData());
        myView.setImageUri(photo.getUri());
        //Toast.makeText(getApplicationContext(),photo.getPhotoName(),Toast.LENGTH_LONG).show();
        //myView.editPicture(photo.getPhotoPath());





//        showImageView=(ImageView)findViewById(R.id.image_handle_imageView);
//        showImageView.setImageURI(photo.getUri());
    }

	/**
	 * 对于大于屏幕分辨率的照片进行缩放
	 * @param bitmap    改变的图形
	 * @param width      屏幕的宽
	 * @param height	 屏幕的高
	 * @return      Bitmap
	 */
	private Bitmap resizeBitmap (Bitmap bitmap,float width,float height) {
		int w=bitmap.getWidth();
		int h=bitmap.getHeight();

		Matrix matrix=new Matrix();
		float scaleWidth=((float)width/w);
		float scaleHeight=((float)height/h);
		matrix.postScale(scaleWidth,scaleHeight);
		Bitmap bitmap1=Bitmap.createBitmap(bitmap,0,0,w,h,matrix,true);
		return bitmap1;
	}
    private void initMyView() {

        //设置myview的属性


        myView=new MyManipulationView2(getBaseContext());
        myView.setBackgroundColor(Color.TRANSPARENT);
        //将view加入布局中
        LL_show=(LinearLayout)findViewById(R.id.ll_show);
        LL_show.removeAllViews();
        LL_show.addView(myView);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.cut_off_start_button:// 画图形状的选择

                selectShape();
                break;
            case R.id.pen_start_button:

                eraser();

        }
    }
    public void eraser() {
        if(isEraser) {
            //当前显示为“画笔"
            //调用view的setDrawTool()方法
            myView.setDrawTool(OperationEnum.pen);
            //点击后设置按钮为“橡皮"
            pen_Start_Button.setText("画笔");
            isEraser=false;
        }else {
            //当前显示为"橡皮"
            myView.setDrawTool(OperationEnum.eraser);
            pen_Start_Button.setText("橡皮");
            isEraser=true;
        }
    }
    /*
	 * 选择形状
	 */
    public void selectShape() {
        final String[] mItems = { "直线", "矩形", "圆形", "三角形", "立方体", "圆柱体", "涂鸦" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Image_manipulation_main_activity.this);
        builder.setTitle("选择形状");

        builder.setItems(mItems, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //调用方法setDrawTool（）进行相应的实例化
                myView.setDrawTool(OperationEnum.getEnum(which));  ///TODO:???????
                Toast.makeText(getApplicationContext(),
                        "选择了: " + mItems[which], Toast.LENGTH_SHORT).show();

                // 如果选择了图形，则将按钮eraserButton设置显示为“橡皮”
//                eraserButton.setText("橡皮");
                    //TODO:?????
                isEraser = false;

            }

        }).setIcon(R.drawable.ic_launcher);

        builder.create().show();
    }
}

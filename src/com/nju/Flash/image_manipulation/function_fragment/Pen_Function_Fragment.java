package com.nju.Flash.image_manipulation.function_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.nju.Flash.R;
import com.nju.Flash.image_manipulation.Image_manipulation_main_activity;

/**
 * Created by randy on 14-3-12.
 */
public class Pen_Function_Fragment extends Fragment {
    private Button pen_Start_Button=null;
    private Button cutoff_Start_Button=null;
    private Image_manipulation_main_activity activity=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        pen_Start_Button=(Button)findViewById(R.id.pen_start_button);

//        pen_Start_Button.setOnClickListener(this);
//        cutoff_Start_Button=(Button)findViewById(R.id.cut_off_start_button);
//        cutoff_Start_Button.setOnClickListener(this);
//        cutoff_Start_Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                实现点击一下menu展示左侧布局，再点击一下隐藏左侧布局的功能
//                if (slidingLayout.isLeftLayoutVisible()) {
//                    slidingLayout.scrollToRightLayout();
//                } else {
//                    slidingLayout.scrollToLeftLayout();
//                }
//            }
   //     });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.pen_function_layout, container, false);
        return messageLayout;
    }
}

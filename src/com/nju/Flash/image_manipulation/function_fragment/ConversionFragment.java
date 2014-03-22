package com.nju.Flash.image_manipulation.function_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.nju.Flash.R;
import com.nju.Flash.image_manipulation.Image_manipulation_main_activity;
import com.nju.Flash.image_manipulation.MyManipulationView2;
import com.nju.Flash.image_manipulation.image_util.ImageUtil;

/**
 * For Flash
 *
 * @author 杨涛
 *         On 14-3-22 下午3:58 by IntelliJ IDEA
 */
public class ConversionFragment extends Fragment {
    private Button circular_bead_button = null;
    private Button inverted_image_button = null;
    private Button rotate_button = null;
    private Button overturn_button = null;
    private Button fuzzy_button = null;
    private Button soften_button = null;
    private Button emboss_button = null;
    private Button sharpen_image_ameliorate_button = null;
    private Button film_button = null;
    private Button sunshine_button = null;
    private Image_manipulation_main_activity activity = null;
    private MyManipulationView2 handleView = null;

    public ConversionFragment(Image_manipulation_main_activity activity) {
        this.activity = activity;
        this.handleView = activity.getView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conversion_layout, container, false);
        if (view != null) {
            circular_bead_button = (Button) view.findViewById(R.id.circular_bead_button);
            inverted_image_button = (Button) view.findViewById(R.id.inverted_image_button);
            rotate_button = (Button) view.findViewById(R.id.rotate_button);
            overturn_button = (Button) view.findViewById(R.id.overturn_button);
            fuzzy_button = (Button) view.findViewById(R.id.fuzzy_button);
            soften_button = (Button) view.findViewById(R.id.soften_button);
            emboss_button = (Button) view.findViewById(R.id.emboss_button);
            sharpen_image_ameliorate_button = (Button) view.findViewById(R.id.sharpen_image_ameliorate_button);
            film_button = (Button) view.findViewById(R.id.film_button);
            sunshine_button = (Button) view.findViewById(R.id.sunshine_button);
        }

        //圆角
        circular_bead_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.getRoundedCornerBitmap(handleView.getFloorBitmap(),10.0f));
            }
        });

        //倒影
        inverted_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.createReflectionImageWithOrigin(handleView.getFloorBitmap()));
            }
        });

        //旋转
        rotate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.postRotateBitamp(handleView.getFloorBitmap(),60f));
            }
        });

        //翻转
        overturn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.reverseBitmap(handleView.getFloorBitmap(),0));
            }
        });

        //模糊
        fuzzy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.blurImage(handleView.getFloorBitmap()));
            }
        });

        //柔化
        soften_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.blurImageAmeliorate(handleView.getFloorBitmap()));
            }
        });

        //锐化
        emboss_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.emboss(handleView.getFloorBitmap()));
            }
        });

        //锐化（拉普拉斯变换）
        sharpen_image_ameliorate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.sharpenImageAmeliorate(handleView.getFloorBitmap()));
            }
        });

        //底片
        film_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.film(handleView.getFloorBitmap()));
            }
        });

        //光照
        sunshine_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.sunshine(handleView.getFloorBitmap(),0,0));
            }
        });

        return view;
    }
}
package com.nju.Flash.image_manipulation.function_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nju.Flash.R;

/**
 * Created by randy on 14-3-12.
 */
public class Attribute_Function_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.pen_function_layout, container, false);
        return messageLayout;
    }
}

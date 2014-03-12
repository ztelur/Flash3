package com.nju.Flash.time_capsule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.nju.Flash.R;


/**
 * Created with IntelliJ IDEA.
 * User: Thunder
 * Date: 14-2-26
 * Time: 下午12:42
 * To change this template use File | Settings | File Templates.
 */
public class Time_capsule_main_activity extends Activity{
	private Button take_photo_button=null;
	private Button watch_time_capsule_button=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_capsule_main_activity);
        take_photo_button=(Button)findViewById(R.id.time_capsule_take_photo_button);
        watch_time_capsule_button=(Button)findViewById(R.id.time_capsule_watch_button);
        initButton();
	}

    /**
     * 初始化按钮
     */
    private void initButton() {
        take_photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(Time_capsule_main_activity.this,RecordActivity.class);
                startActivity(intent);
            }
        });
        watch_time_capsule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(Time_capsule_main_activity.this,ShowTimeCapsuleActivity.class);
                startActivity(intent);
            }
        });
    }
}

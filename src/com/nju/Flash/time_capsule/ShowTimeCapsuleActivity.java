package com.nju.Flash.time_capsule;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.nju.Flash.R;

import java.io.File;

/**
 * For SampleRecordForPhoto
 *
 * @author 杨涛
 *         On 14-3-3 下午6:50 by IntelliJ IDEA
 */
public class ShowTimeCapsuleActivity extends Activity {
    private static String photoFileName = null;
    private static Uri photoUri = null;
    private ImageView imageView = null;
    private TextView timeCapsuleView = null;
    private static String text = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_time_cpasule);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar2);  //titlebar为自己标题栏的布局

        imageView = (ImageView) findViewById(R.id.timeCapsulePhotoView);
        timeCapsuleView = (TextView)findViewById(R.id.timeCapsuleText);
        imageView.setImageURI(photoUri);
        timeCapsuleView.setText(text);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playOrPause();
            }
        });

        Photo.setActivity(this);
    }

    public static void initialize(String name){
        photoFileName = name;
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        filePath += "/flash/record/"+name+".txt";
        text = IOHelper.read(new File(filePath));
        text = text.substring(text.indexOf("||") + 2, text.length() - 1);
        photoUri = Uri.parse(IOHelper.readUri(new File(filePath)));
        Photo.createPhoto(photoUri,name);
    }

    private void playOrPause(){
        Record record = Record.getInstance(this);
        if(record.mediaPlayer==null)
            record.prepareForPlay();
        if(record.isPlaying()) {
            record.pause();
        }else{
            record.play();
        }
    }
}

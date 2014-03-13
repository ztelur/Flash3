package com.nju.Flash.time_capsule;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.nju.Flash.R;


import java.io.File;
import java.util.Date;

public class RecordActivity extends Activity {
    private  RecordButton mRecordButton = null;
    private ImageView photoView = null;
    private TextView dataText = null;
    private EditText editText = null;
    private Intent timeServiceIntent;
    private int year;
    private int month;
    private int day;
    private int hourOfDay;
    private int minute;

    private static Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecordActivity.display = getWindowManager().getDefaultDisplay();
        timeServiceIntent = new Intent(this, TimeService.class);
        startService(timeServiceIntent);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_record);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  //titlebar为自己标题栏的布局
        editText = (EditText) findViewById(R.id.editText1);

        startSystemAlbum();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.record, menu);
        return true;
    }

    private void startSystemAlbum() {
        Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && null != data) {
            initializePhoto(data.getData());
            photoView = (ImageView) this.findViewById(R.id.imageView1);
            try {
            photoView.setImageURI(Photo.getUri());
            }catch (NullPointerException e) {
                Toast.makeText(getApplicationContext(),"No photoView",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        setRecordButton();
        initializeButtons();
    }

    private void setRecordButton() {
        mRecordButton = (RecordButton) findViewById(R.id.record_button);
        mRecordButton.setSavePath(Record.getInstance(this).getFilePath());
        mRecordButton.setOnFinishedRecordListener(new RecordButton.OnFinishedRecordListener() {
            @Override
            public void onFinishedRecord(String audioPath) {
                Log.i("RECORD!!!", "finished!!!!!!!!!! save to " + audioPath);
            }
        });
    }

    private void initializeButtons() {
        setPlayButton();
        setCancelButton();
        setSaveButton();
        setDataRecorder();
        check();
    }

    private void check() {
        File recordFile = new File(Record.getInstance(this).getFilePath());
        if (recordFile.exists()) {
            IOHelper.initializeIOHelper();
            String content = IOHelper.read();
            if (content != null && !content.equals("")) {
                int splitLocation = content.indexOf("||");
                if (splitLocation != -1) {
                    String text = content.substring(splitLocation + 2, content.length() - 1);
                    String time = content.substring(0, splitLocation);
                    editText.setText(text);
                    dataText.setText("时间胶囊将在" + time + "准时打开(点击修改)");
                    timeSplit(time);
                }
            }
        }
    }

    private void timeSplit(String time) {
        int temp = time.indexOf("年");
        year = Integer.parseInt(time.substring(0, temp));
        int pre = temp;
        temp = time.indexOf("月");
        month = Integer.parseInt(time.substring(pre + 1, temp));
        pre = temp;
        temp = time.indexOf("日");
        day = Integer.parseInt(time.substring(pre+1, temp));
        pre = temp;
        temp = time.indexOf("点");
        hourOfDay = Integer.parseInt(time.substring(pre+1, temp));
        pre = temp;
        temp = time.indexOf("分");
        minute = Integer.parseInt(time.substring(pre+1, temp));
    }

    private void setDataRecorder() {
        dataText = (TextView) findViewById(R.id.dateTextView);
        dataText.setClickable(true);
        dataText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog();
            }
        });
    }

    private void dateDialog() {
        Date curDate = new Date(System.currentTimeMillis());
        DatePickerDialog datePickerDialog = new DatePickerDialog(RecordActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                setDate(year, monthOfYear, dayOfMonth);
            }
        }, curDate.getYear(), curDate.getMonth() + 1, curDate.getDay());
        TimePickerDialog timePickerDialog = new TimePickerDialog(RecordActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                setTime(hourOfDay, minute);
            }
        }, curDate.getHours(), curDate.getMinutes(), true);
        datePickerDialog.setTitle("请选择年月");
        timePickerDialog.setTitle("请选择时间");
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        timePickerDialog.show();
        datePickerDialog.show();
    }

    private void setTime(int hourOfDay, int minute) {
        dataText.setText((String) dataText.getText() + hourOfDay + "点" + minute + "分准时打开(点击修改)");
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }

    private void setDate(int year, int monthOfYear, int dayOfMonth) {
        dataText.setText("时间胶囊将在" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
        this.year = year;
        this.month = monthOfYear + 1;
        this.day = dayOfMonth;
    }

    private void setPlayButton() {
        Button playButton = (Button) findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playListener(v);
            }
        });
    }

    private void setCancelButton() {
        Button playButton = (Button) findViewById(R.id.back_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelListener(v);
            }
        });
    }

    private void setSaveButton() {
        final Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.requestFocus();
                saveListener();
                //RecordActivity.this.finish();
            }
        });
    }

    private void saveListener() {
        IOHelper.initializeIOHelper();
        String  content = year+"年"+month+"月"+day+"日"+hourOfDay+"点"+minute+"分||";
        content+= editText.getText().toString();
        IOHelper.write(content);
        Toast.makeText(getBaseContext(), "保存成功", Toast.LENGTH_SHORT).show();
        stopService(timeServiceIntent);
        startService(timeServiceIntent);
    }

    private void initializePhoto(Uri uri) {
        Photo.setActivity(this);
        Photo.createPhoto(uri);
    }

    public void cancelListener(View target) {
        dialog();
    }

    public void playListener(View target) {
        if(Record.getInstance(this).isPlaying()){
            Record.getInstance(this).stop();
        } else {
            Record.getInstance(this).play();
        }
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);
        builder.setMessage("确认退出吗？");
        builder.setTitle("退出确认");
        builder.setPositiveButton("确认", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                RecordActivity.this.finish();
            }
        });

        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public static Display getDisplay() {
        return RecordActivity.display;
    }

}

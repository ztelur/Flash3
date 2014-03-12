package com.nju.Flash.time_capsule;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import com.nju.Flash.R;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * For SampleRecordForPhoto
 *
 * @author 杨涛
 *         On 14-3-3 上午8:17 by IntelliJ IDEA
 */
public class TimeService extends Service {
    private static int count = 0;
    private static final String TAG = "TimeService";
    public static final String ACTION = "com.lql.service.ServiceDemo";

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "ServiceDemo onBind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.v(TAG, "ServiceDemo onCreate");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.v(TAG, "ServiceDemo onStart");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "ServiceDemo onStartCommand");
        getTimeList();
        return super.onStartCommand(intent, flags, startId);
    }

    private void getTimeList() {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.i("TEST", "SDcard path = "+dirPath);
        dirPath += "/flash/record/";
        File file = new File(dirPath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            String temp = null;
            String textTemp = null;
            Timer timer;
            if (files != null && files.length != 0)
                for (File f : files) {
                    if (f.getName().endsWith(".txt") && f.isFile()) {
                        temp = IOHelper.read(f);
                        if (temp != null && !temp.equals("")) {
                            timer = new Timer(f.getName());
                            Log.i("TEST", "new task is going on");
                            int splitLocation = temp.indexOf("||");
                            textTemp = temp.substring(splitLocation + 2, temp.length() - 1);
                            temp = temp.substring(0, splitLocation);
                            timer.schedule(task(f.getName().split(".txt")[0], textTemp), timeSplit(temp));
                        }
                    }
                }
        }
    }

    private Date timeSplit(String time) {
        Date date = new Date();
        int temp = time.indexOf("年");
        date.setYear(Integer.parseInt(time.substring(0, temp)) - 1900);
        int pre = temp;
        temp = time.indexOf("月");
        date.setMonth(Integer.parseInt(time.substring(pre + 1, temp)) - 1);
        pre = temp;
        temp = time.indexOf("日");
        date.setDate(Integer.parseInt(time.substring(pre + 1, temp)));
        pre = temp;
        temp = time.indexOf("点");
        date.setHours(Integer.parseInt(time.substring(pre + 1, temp)));
        pre = temp;
        temp = time.indexOf("分");
        date.setMinutes(Integer.parseInt(time.substring(pre + 1, temp)));
        return date;
    }

    private TimerTask task(final String name, final String text) {
        return new TimerTask() {
            @Override
            public void run() {
                notification(name, text);
            }
        };
    }

    private void notification(String name, String text) {
        Log.i("Test", "Notification going");
        //消息通知栏
        //定义NotificationManager
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
        //定义通知栏展现的内容信息
        int icon = R.drawable.ic_launcher;//TODO:///杨涛。。。图片问题？？
        CharSequence tickerText = "时间胶囊";
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);

        //定义下拉通知栏时要展现的内容信息
        Context context = getApplicationContext();
        CharSequence contentTitle = "您的" + name + "时间胶囊已经准备打开";
        CharSequence contentText = text;
        Intent notificationIntent = new Intent(this, ShowTimeCapsuleActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText,
                contentIntent);

        ShowTimeCapsuleActivity.initialize(name);

        //用mNotificationManager的notify方法通知用户生成标题栏消息通知
        mNotificationManager.notify(count++, notification);
    }
}

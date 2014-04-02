package com.nju.Flash.time_capsule.content;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

public class Photo {
	private static Photo photo;
	private static Uri photoUri;
	private static String name;
	private static Activity activity;
    private static int screenWidth = -1;
    private static int screenHeight = -1;

	private Photo(Uri uri) {
		Photo.photoUri = uri;
		Photo.name = getPhotoName();
	}

    public Photo(Uri uri, String name) {
        Photo.photoUri = uri;
        Photo.name = name;
    }

    private String getPhotoName() {
		String[] filePathColumns = { MediaStore.Images.Media.DATA };
		Cursor c = activity.getContentResolver().query(photoUri, filePathColumns, null, null, null);
		c.moveToFirst();
		int columnIndex = c.getColumnIndex(filePathColumns[0]);
		String picturePath = c.getString(columnIndex);
		String pathSplit[] = picturePath.split("/");
		String fileName = pathSplit[pathSplit.length-1];
		fileName = fileName.substring(0,fileName.lastIndexOf("."));
		return fileName;
	}

	public static void createPhoto(Uri uri) {
		photo = new Photo(uri);
	}

    public static void createPhoto(Uri uri,String name) {
        photo = new Photo(uri,name);
    }

	public static Photo getInstance() {
		if (photo == null) {
			createPhoto(Uri.parse("/res/drawable/defaultpic.jpg"));
		}
		return photo;
	}

	public static Uri getUri() {
		return photoUri;
	}

	public static void setActivity(Activity activity) {
		Photo.activity = activity;
	}

	public static String getName(){
		return name;
	}

    public static Bitmap thumb() {
        String photoPath = getPhotoPath();
        getDisplayPX();
        BitmapFactory.Options options = getSize(photoPath);
        options.inSampleSize = options.outWidth / screenWidth;
        options.inPurgeable = true;
        options.inInputShareable = true;
        int height = options.outHeight * screenWidth / options.outWidth;
        options.outWidth = screenWidth;
        options.outHeight = height;
        options.inJustDecodeBounds = false;
        System.out.println("Width = "+options.outWidth+" ;Height = "+options.outHeight);
        return BitmapFactory.decodeFile(photoPath, options);
    }

    private static BitmapFactory.Options getSize(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);//这里返回的bmp是null
        return options;
    }
    private static void getDisplayPX(){
        DisplayMetrics dm =activity.getResources().getDisplayMetrics();
        screenWidth = /*px2dip*/(dm.widthPixels);
        screenHeight = /*px2dip*/(dm.heightPixels);
        System.out.println("Screen Width = "+screenWidth+" ;Screen Height = "+screenHeight);
    }
    private static String getPhotoPath() {
        String[] filePathColumns = { MediaStore.Images.Media.DATA };
        Cursor c = activity.getContentResolver().query(photoUri, filePathColumns, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePathColumns[0]);
        String picturePath = c.getString(columnIndex);
        return picturePath;
    }
}

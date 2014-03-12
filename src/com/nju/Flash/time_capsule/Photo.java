package com.nju.Flash.time_capsule;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class Photo {
	private static Photo photo;
	private static Uri photoUri;
	private static String name;
	private static Activity activity;

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
			createPhoto(Uri.parse("/res/drawable/nophoto.jpg"));
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
}

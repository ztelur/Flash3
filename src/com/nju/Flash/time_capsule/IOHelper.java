package com.nju.Flash.time_capsule;

import android.os.Environment;
import com.nju.Flash.time_capsule.content.Photo;
import com.nju.Flash.time_capsule.exception.ContentErrorException;
import com.nju.Flash.time_capsule.exception.EmptyFileException;
import com.nju.Flash.time_capsule.exception.FileReadFailException;

import java.io.*;

/**
 * For SampleRecordForPhoto
 *
 * @author 杨涛
 *         On 14-3-2 下午6:39 by IntelliJ IDEA
 *         <p/>
 *         程序所使用的文件内容格式规定为
 *         line1：true/false //该时间胶囊是否到时间被打开过
 *         line2：时间胶囊标题
 *         line3：时间胶囊内容
 *         line4：时间胶囊对应照片URI
 *         line5：时间胶囊打开时间
 *         <p/>
 *         读取所返回的内容为包含以上内容的String[] content
 *         content[0]：true/false
 *         content[1]：标题
 *         content[2]：内容
 *         content[3]：照片URI
 *         content[4]：时间
 */
public class IOHelper {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final int IS_OPENED_CONTENT = 0;
    public static final int TITLE_CONTENT = 1;
    public static final int TEXT_CONTENT = 2;
    public static final int PHOTO_URL_CONTENT = 3;
    public static final int TIME_CONTENT = 4;
    private static String filePath;
    private static File file;

    public static void initializeIOHelper() {
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        filePath += "/flash/record/";
        File fileDir = new File(filePath);
        fileDir.mkdirs();
        filePath += (Photo.getName() + ".txt");
        file = new File(filePath);
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void write(String[] content) throws ContentErrorException {
        if (content == null || content.length != 5) {
            throw new ContentErrorException();
        } else {
            try {
                String temp = "";
                for (String line : content)
                    temp += (line + LINE_SEPARATOR);
                FileWriter writer = new FileWriter(file, false);
                writer.write(temp);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String[] read() throws FileReadFailException, EmptyFileException {
        return read(file);
    }

    public static String[] read(File file) throws FileReadFailException, EmptyFileException {
        String[] content = new String[5];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            for (int i = 0; i < 5; i++) {
                content[i] = bufferedReader.readLine();
                if(i==0&&(content[i]==null||content[i].equals("")))
                    throw new EmptyFileException();
                if (content[i] == null || content[i].equals(""))
                    throw new FileReadFailException();
            }
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

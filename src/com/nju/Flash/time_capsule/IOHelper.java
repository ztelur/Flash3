package com.nju.Flash.time_capsule;

import android.os.Environment;

import java.io.*;

/**
 * For SampleRecordForPhoto
 *
 * @author 杨涛
 *         On 14-3-2 下午6:39 by IntelliJ IDEA
 */
public class IOHelper {
    private static String filePath;
    private static File file;
    public static void initializeIOHelper (){
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        filePath += "/flash/record/";
        File fileDir = new File(filePath);
        fileDir.mkdirs();
        filePath += (Photo.getName()+".txt");
        file = new File(filePath);
        if(!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void write(String context){
        context += (System.getProperty("line.separator")+Photo.getUri().toString());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(context.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            //BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            //Log.i("TEST",context);
            //bufferedWriter.write(context);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String read(){
        return read(file);
    }

    public static String read(File file){
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String readUri(File file){
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            bufferedReader.readLine();
            return bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

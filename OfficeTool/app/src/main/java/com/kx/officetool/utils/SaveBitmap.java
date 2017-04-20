package com.kx.officetool.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.os.Environment.DIRECTORY_PICTURES;

public class SaveBitmap {
    private  String ALBUM_PATH
            =   Environment.getExternalStorageDirectory() + "/horoscope_image/";


    public SaveBitmap(Context context){
        ALBUM_PATH=context.getExternalFilesDir(DIRECTORY_PICTURES).getPath();
    }
    public String getPathBefore(){
      return ALBUM_PATH;
    }
    public  void saveString(String str) {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) { // SD卡根目录的hello.text  
            filePath = ALBUM_PATH + "down.txt";
            Log.e("guochaox","filePath=="+filePath);
     /*   } else  // 系统下载缓存根目录的hello.text
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + "down.txt";*/

            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    File dir = new File(file.getParent());
                    dir.mkdirs();
                    file.createNewFile();
                }
                FileOutputStream outStream = new FileOutputStream(file);
                outStream.write(str.getBytes());
                outStream.close();
                Log.e("guochao", "saveString");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void downImage(final String mPath){
        final String[] split = mPath.split("/");
        final int length = split.length;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String filePath = mPath;
                    byte[] data = getImage(filePath);
                    if(data!=null) {
                        Bitmap mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap
                        Log.e("guochao",ALBUM_PATH);
                       // saveFile(mBitmap,split[length-1]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public byte[] getImage(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        InputStream inStream = conn.getInputStream();
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return readStream(inStream);
        }
        return null;
    }


    public static byte[] readStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    public void saveFile(Bitmap bm, String fileName) {
        Bitmap.CompressFormat format=null;
        File dirFile = new File(ALBUM_PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();

        }

        try {
            if(fileName.endsWith("jpg") || fileName.endsWith("jpeg")){
                format=Bitmap.CompressFormat.JPEG;
            }else {
                format=Bitmap.CompressFormat.PNG;
            }
        File myCaptureFile = new File(ALBUM_PATH + fileName);
        BufferedOutputStream bos = null;
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(format, 100, bos);
        bos.flush();
        bos.close();
        Log.e("guochao","saveFile") ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String readFileSdcardFile(){
        try {
            File file = new File(ALBUM_PATH + "down.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String readline = "";
            StringBuffer sb = new StringBuffer();
            while ((readline = br.readLine()) != null) {
                System.out.println("readline:" + readline);
                sb.append(readline);
            }
            br.close();
            Log.e("guochao","读取成功：" + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
         return null;
    }

     
    public Bitmap getDiskBitmap(String pathString)
    {
        Bitmap bitmap = null;
        try
        {
            File file = new File(pathString);
            if(file.exists())
            {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e)
        {

        }


        return bitmap;
    }

}

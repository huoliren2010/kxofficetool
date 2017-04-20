package com.kx.officetool.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();
    private String local_image_path;

    private String mRootDir;

    public FileUtils(){
        mRootDir = Environment.getExternalStorageDirectory()+ File.separator;
    }

    public FileUtils(Context context, String local_image_path) {
        this.local_image_path = local_image_path;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 保存图片到制定路径
     *
     * @param filename
     * @param bitmap
     */
    public void saveBitmap(String filename, Bitmap bitmap) {
        if (!isExternalStorageWritable()) {
            Log.i(TAG, "SD卡不可用，保存失败");
            return;
        }

        if (bitmap == null) {
            return;
        }

        try {

            File file = new File(local_image_path,filename);
            FileOutputStream outputstream = new FileOutputStream(file);
            if((filename.indexOf("png") != -1)||(filename.indexOf("PNG") != -1))
            {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputstream);
            }  else{
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputstream);
            }

            outputstream.flush();
            outputstream.close();

        } catch (FileNotFoundException e) {
            Log.i(TAG, e.getMessage());
        } catch (IOException e) {
            Log.i(TAG, e.getMessage());
        }
    }

    /**
     * 返回当前应用 SD 卡的绝对路径 like
     * /storage/sdcard0/Android/data/com.example.test/files
     */
    public String getAbsolutePath() {
        File root = new File(local_image_path);
        if(!root.exists()){
            root.mkdirs();

        }


        return local_image_path;


    }

    public boolean isBitmapExists(String filename) {
        File dir =new File(local_image_path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //context.getExternalFilesDir(null);
        File file = new File(dir, filename);

        return file.exists();
    }


    public File createFile(String fileName) throws IOException {
        File file=new File(mRootDir +fileName);
        file.createNewFile();
        return file;
    }

    public File writeFile(String fileName, String s){
        File file=null;
        FileOutputStream fos = null;
        try {
            file=createFile(fileName);
            fos = new FileOutputStream( mRootDir +fileName);
            fos.write(s.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally{

            try {
                if(fos!=null)
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}

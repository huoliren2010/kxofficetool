package com.kx.officetool.infos;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.kx.officetool.R;
import com.kx.officetool.utils.BitmapCache;
import com.kx.officetool.utils.FileUtils;
import com.kx.officetool.utils.HttpUtil;
import com.kx.officetool.utils.diskcache.DiskLruCache;

public class LoadUserAvatar {
    private static final String DISK_CACHE_SUBDIR = "UserAvatar";
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 20; // 20MB
    // 最大线程数
    private static final int MAX_THREAD_NUM = 5;
    // 一级内存缓存基于 LruCache
    private BitmapCache bitmapCache;
    // 二级文件缓存
    private DiskLruCache mDiskCache = null;

    // 线程池
    private ExecutorService threadPools = null;
    private static LoadUserAvatar mInstance = null;

    public static LoadUserAvatar getInstance(Context context){
        if(mInstance == null){
            mInstance = new LoadUserAvatar(context.getApplicationContext());
        }
        return mInstance;
    }
    private LoadUserAvatar(Context context) {
        bitmapCache = new BitmapCache();
        File cacheDir = DiskLruCache.getDiskCacheDir(context, DISK_CACHE_SUBDIR);
        mDiskCache = DiskLruCache.openCache(context, cacheDir, DISK_CACHE_SIZE);
        threadPools = Executors.newFixedThreadPool(MAX_THREAD_NUM);
    }

    @SuppressLint("HandlerLeak")
    public Bitmap loadImage(final ImageView imageView, final String imageUrl,
                            final ImageDownloadedCallBack imageDownloadedCallBack) {
        if(TextUtils.isEmpty(imageUrl))return null;
        // 先从内存中拿
        Bitmap bitmap = bitmapCache.getBitmap(imageUrl);

        if (bitmap != null && !bitmap.isRecycled()) {
            return bitmap;
        }

        // 从文件中找
        bitmap = mDiskCache.get(imageUrl);
        if (bitmap != null && !bitmap.isRecycled()) {
            // 先缓存到内存
            bitmapCache.putBitmap(imageUrl, bitmap);
            return bitmap;

        }

        // 内存和文件中都没有再从网络下载
        if (imageUrl != null && !imageUrl.equals("")) {
            final Handler handler = new Handler() {
                @SuppressLint("HandlerLeak")
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 111 && imageDownloadedCallBack != null) {
                        Bitmap bitmap = (Bitmap) msg.obj;
                        imageDownloadedCallBack.onImageDownloaded(imageView,
                                bitmap);
                    }
                }
            };

            Thread thread = new Thread() {
                @SuppressLint("NewApi")
                @Override
                public void run() {
                    Log.i("aaaa", Thread.currentThread().getName()
                            + " is running");
                    Bitmap bitmap = HttpUtil.getThumbnail(imageUrl);

                    // 图片下载成功后缓存并执行回调刷新界面
                    if (bitmap != null) {

                        // 先缓存到内存
                        bitmapCache.putBitmap(imageUrl, bitmap);
                        // 缓存到文件系统
                        mDiskCache.put(imageUrl, bitmap);

                        Message msg = new Message();
                        msg.what = 111;
                        msg.obj = bitmap;
                        handler.sendMessage(msg);

                    }
                }
            };

            threadPools.execute(thread);
        }

        return null;
    }

    public void put(String url, Bitmap bitmap) {
        bitmapCache.putBitmap(url, bitmap);
        mDiskCache.put(url, bitmap);
    }


    /**
     * 图片下载完成回调接口
     */
    public interface ImageDownloadedCallBack {
        void onImageDownloaded(ImageView imageView, Bitmap bitmap);
    }

    public void loadAvatar(ImageView imageView, String imageUrl){
        if(imageView == null)return;
        if(TextUtils.isEmpty(imageUrl))return;
        Bitmap bmp = loadImage(imageView, imageUrl, new LoadUserAvatar.ImageDownloadedCallBack() {
            @Override
            public void onImageDownloaded(ImageView imageView, Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });
        if (bmp != null && !bmp.isRecycled()) {
            imageView.setImageBitmap(bmp);
        } else imageView.setImageResource(R.mipmap.ic_personal_default);
    }

}

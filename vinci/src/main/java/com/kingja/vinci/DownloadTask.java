package com.kingja.vinci;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import com.kingja.vinci.Cache.LruCache;
import com.kingja.vinci.Downloader.Downloader;
import com.kingja.vinci.Downloader.OkHttpDownloader;
import com.kingja.vinci.Request;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Description:TODO
 * Create Time:2017/5/4 14:53
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DownloadTask implements Runnable {
    private Handler handler;
    private Request request;
    private LruCache cache;

    public DownloadTask(Handler handler, Request request, Downloader downloader, LruCache cache) {
        this.handler = handler;
        this.request = request;
        this.cache = cache;
    }

    @Override
    public void run() {
        OkHttpDownloader okHttpDownloader = new OkHttpDownloader();
        Bitmap bitmap=null;
        try {
            Response response = okHttpDownloader.load(request.url);
            InputStream in = response.body().byteStream();
             bitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            Log.e(TAG, "任务出错: "+e.getMessage());
            e.printStackTrace();
            return;
        }
        final Bitmap finalBitmap = bitmap;
        request.imageView.post(new Runnable() {
            @Override
            public void run() {
                cache.set(request.url, finalBitmap);
                request.imageView.setImageBitmap(finalBitmap);
            }
        });
    }
}

package com.kingja.vinci.Downloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import com.kingja.vinci.Cache.LruCache;
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

    public DownloadTask(Handler handler, Request request, LruCache cache) {
        this.handler = handler;
        this.request = request;
        this.cache = cache;
    }

    @Override
    public void run() {
        Log.e(TAG, "Thread: "+Thread.currentThread().getName() );
        OkHttpDownloader okHttpDownloader = new OkHttpDownloader();
        try {
            Response response = okHttpDownloader.load(request.url);
            InputStream inputStream = response.body().byteStream();
            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            request.imageView.post(new Runnable() {
                @Override
                public void run() {
                    cache.set(request.url,bitmap);
                    request.imageView.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

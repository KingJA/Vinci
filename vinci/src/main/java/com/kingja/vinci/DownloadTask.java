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
    private Downloader downloader;
    private LruCache cache;

    public DownloadTask( Request request,LruCache cache, Downloader downloader ) {
        this.request = request;
        this.downloader = downloader;
        this.cache = cache;
    }

    @Override
    public void run() {
        Bitmap bitmap;
        try {
            Response response = downloader.load(request.url);
            InputStream in = response.body().byteStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            Log.e(TAG, "任务出错: " + e.getMessage());
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

package com.kingja.vinci;

import android.graphics.Bitmap;
import android.util.Log;

import com.kingja.vinci.Cache.LruCache;
import com.kingja.vinci.Downloader.Downloader;

import static android.content.ContentValues.TAG;

/**
 * Description:TODO
 * Create Time:2017/5/4 14:53
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoadTask implements Runnable {
    private Request request;
    private Downloader downloader;
    private LruCache cache;
    private Dispather dispather;

    public LoadTask(Request request, LruCache cache, Downloader downloader, Dispather dispather) {
        this.request = request;
        this.downloader = downloader;
        this.cache = cache;
        this.dispather = dispather;
    }

    @Override
    public void run() {
        Bitmap bitmap;
        Log.e(TAG, "网络请求数据: " + Thread.currentThread().getName());
        bitmap = downloader.load(request.url);
        if (bitmap == null) {
            request.imageView.post(new Runnable() {
                @Override
                public void run() {
                    dispather.finishRequest(request);
                    dispather.exeueteReadyRequest();
                    if (request.errorRes != 0) {
                        request.imageView.setImageDrawable(request.context.getResources().getDrawable(request.errorRes));
                    }
                }
            });
            return;
        }
        dispather.finishRequest(request);
        dispather.exeueteReadyRequest();

        final Bitmap finalBitmap = bitmap;
        request.imageView.post(new Runnable() {
            @Override
            public void run() {
                request.imageView.setImageBitmap(finalBitmap);
                cache.set(request.url, finalBitmap);
            }
        });
    }
}

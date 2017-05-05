package com.kingja.vinci;

import android.content.Context;
import android.graphics.Bitmap;

import com.kingja.vinci.Cache.LruCache;
import com.kingja.vinci.Downloader.Downloader;
import com.kingja.vinci.Downloader.OkHttpDownloader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Description:TODO
 * Create Time:2017/5/4 9:25
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Vinci {
    static Vinci singleton;
     Context context;
    Downloader downloader;
    private LruCache cache;
    private ExecutorService threadPool;

    private final Dispather dispather;


    public Vinci(Context context, LruCache cache, ExecutorService threadPool, Downloader downloader) {
        this.context = context;
        this.cache = cache;
        this.downloader = downloader;
        this.threadPool = threadPool;
        dispather = new Dispather(1, threadPool, cache, downloader);
        dispather.start();
    }

    public void addRequest(Request request) {
        dispather.addRequest(request);
    }

    public static Vinci with(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        if (singleton == null) {
            synchronized (Vinci.class) {
                if (singleton == null) {
                    singleton = new Vinci.Builder(context).build();
                }
            }
        }
        return singleton;
    }

    public Bitmap getCache(Request request) {
        return cache.get(request.url);
    }


    public RequestCreator load(String url) {
        return new RequestCreator(this, url);
    }

    public static class Builder {
        private Context context;
        private Downloader downloader;
        private LruCache cache;
        private ExecutorService threadPool;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }

        public Vinci build() {
            if (threadPool == null) {
                threadPool = Executors.newCachedThreadPool();
            }
            if (cache == null) {
                cache = new LruCache(context);
            }
            if (downloader == null) {
                downloader = new OkHttpDownloader();
            }
            return new Vinci(context, cache, threadPool, downloader);
        }
    }
}

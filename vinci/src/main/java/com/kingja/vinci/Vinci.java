package com.kingja.vinci;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.kingja.vinci.Cache.LruCache;
import com.kingja.vinci.Downloader.Downloader;
import com.kingja.vinci.Downloader.OkHttpDownloader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.kingja.vinci.Dispatcher.COMPLETE_DOWNLOAD;

/**
 * Description:TODO
 * Create Time:2017/5/4 9:25
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Vinci {
    static Vinci singleton;
    private Context context;
    Downloader downloader;
    private LruCache cache;
    private ExecutorService threadPool;
    Dispatcher dispatcher;

    static Handler mainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case COMPLETE_DOWNLOAD:
                    break;
            }
        }
    };
    private final WorkThreadManager workThreadManager;


    public Vinci(Context context, Handler mainHandler, LruCache cache, Dispatcher dispatcher) {
        this.context = context;
        this.mainHandler = mainHandler;
        this.cache = cache;
        this.dispatcher = dispatcher;
        workThreadManager = new WorkThreadManager(1, dispatcher);
        workThreadManager.start();
    }

    public void addRequest(Request request) {
        workThreadManager.addRequest(request);
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

    public void setCache(Request key, Bitmap bitmap) {
        cache.set(key.url, bitmap);
    }

    public void loadImage(Request request) {
        dispatcher.dealTask(request);
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
            Dispatcher dispatcher = new Dispatcher(context, mainHandler, threadPool, cache, downloader);
            return new Vinci(context, mainHandler, cache, dispatcher);
        }
    }
}

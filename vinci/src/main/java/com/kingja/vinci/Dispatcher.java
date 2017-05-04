package com.kingja.vinci;

import android.content.Context;
import android.os.Handler;

import com.kingja.vinci.Cache.LruCache;
import com.kingja.vinci.Downloader.DownloadTask;
import com.kingja.vinci.Downloader.Downloader;

import java.util.concurrent.ExecutorService;

/**
 * Description:TODO
 * Create Time:2017/5/4 13:24
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Dispatcher {
    public static final int COMPLETE_DOWNLOAD = 1;
    private Context context;
    private Handler mainHandler;
    private ExecutorService threadPool;
    private LruCache cache;
    private Downloader downloader;

    public Dispatcher(Context context, Handler mainHandler, ExecutorService threadPool, LruCache cache, Downloader
            downloader) {
        this.context = context;
        this.mainHandler = mainHandler;
        this.threadPool = threadPool;
        this.cache = cache;
        this.downloader = downloader;
    }

    public void dealTask(Request request) {
        threadPool.execute(new DownloadTask(mainHandler, request,cache));
    }
}

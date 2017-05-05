package com.kingja.vinci;


import android.util.Log;

import com.kingja.vinci.Cache.LruCache;
import com.kingja.vinci.Downloader.Downloader;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;

import static android.content.ContentValues.TAG;

/**
 * Description:管理请求队里，暂停，取消请求
 * Create Time:2017/5/4 15:46
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Dispather {
    private int workThreadCount;
    private BlockingQueue<Request> mRequestQueue = new PriorityBlockingQueue<>();
    private WorkThread[] workThreads = null;
    private  ExecutorService threadPool;
    private LruCache cache;
    private Downloader downloader;

    public Dispather(int workThreadCount, ExecutorService threadPool, LruCache cache, Downloader downloader) {
        this.workThreadCount = workThreadCount;
        this.threadPool = threadPool;
        this.cache = cache;
        this.downloader = downloader;
    }


    public void start() {
        workThreads = new WorkThread[workThreadCount];
        for (int i = 0; i < workThreadCount; i++) {
            workThreads[i] = new WorkThread(mRequestQueue,threadPool,cache,downloader);
            workThreads[i].start();
        }
    }

    public void addRequest(Request request) {
        mRequestQueue.add(request);
        Log.e(TAG, "加入队列: "+mRequestQueue.size() );
    }
}

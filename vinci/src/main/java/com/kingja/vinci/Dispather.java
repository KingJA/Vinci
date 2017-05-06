package com.kingja.vinci;


import android.util.Log;

import com.kingja.vinci.Cache.LruCache;
import com.kingja.vinci.Downloader.Downloader;

import java.util.Iterator;
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
    private int maxRequestCount = 6;
    private BlockingQueue<Request> mRequestQueue = new PriorityBlockingQueue<>();
    private BlockingQueue<Request> mReadyQueue = new PriorityBlockingQueue<>();
    private WorkThread[] workThreads = null;
    private ExecutorService threadPool;
    private LruCache cache;
    private Downloader downloader;

    public Dispather(int workThreadCount, ExecutorService threadPool, LruCache cache, Downloader downloader) {
        this.workThreadCount = workThreadCount;
        this.threadPool = threadPool;
        this.cache = cache;
        this.downloader = downloader;
    }


    public void start() {
//        workThreads = new WorkThread[workThreadCount];
//        for (int i = 0; i < workThreadCount; i++) {
//            workThreads[i] = new WorkThread(mRequestQueue, threadPool, cache, downloader);
//            workThreads[i].start();
//        }
    }

    public void addRequest(Request request) {
        if (mRequestQueue.size() < maxRequestCount) {
            mRequestQueue.add(request);
            threadPool.execute(new DownloadTask(request, cache, downloader, this));
            Log.e(TAG, "直接执行请求: " + mReadyQueue.size());
        } else {
            mReadyQueue.add(request);
            Log.e(TAG, "加入等待队列: " + mReadyQueue.size());
        }
    }

    public synchronized void  finishRequest(Request request) {
        mRequestQueue.remove(request);
    }

    public synchronized void exeueteReadyRequest() {
        if (mRequestQueue.size() >= maxRequestCount) {
            return;
        }
        if (mReadyQueue.isEmpty()) {
            return;
        }
        for (Iterator<Request> i = mReadyQueue.iterator(); i.hasNext(); ) {
            Request request = i.next();
            i.remove();
            mRequestQueue.add(request);
            threadPool.execute(new DownloadTask(request, cache, downloader, this));
            if (mRequestQueue.size() >= maxRequestCount) {
                return;
            }
        }
    }
}

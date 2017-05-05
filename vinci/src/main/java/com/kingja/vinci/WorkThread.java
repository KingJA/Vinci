package com.kingja.vinci;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.kingja.vinci.Cache.LruCache;
import com.kingja.vinci.Downloader.Downloader;

import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Description:TODO
 * Create Time:2017/5/4 15:55
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class WorkThread extends Thread {

    private BlockingQueue<Request> requestQueue;
    private ExecutorService threadPool;
    private LruCache cache;
    private Downloader downloader;

    public WorkThread(BlockingQueue<Request> requestQueue, ExecutorService threadPool, LruCache cache, Downloader
            downloader) {
        this.requestQueue = requestQueue;
        this.threadPool = threadPool;
        this.cache = cache;
        this.downloader = downloader;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                if (requestQueue.size() > 0) {
                    Request request = requestQueue.take();
                    Log.e(TAG, "取出任务: "+request.url );
                    threadPool.execute(new DownloadTask(request,cache,downloader));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

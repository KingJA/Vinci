package com.kingja.vinci;


import android.support.annotation.WorkerThread;
import android.util.Log;

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
public class WorkThreadManager {
    private int workThreadCount;
    private BlockingQueue<Request> mRequestQueue = new PriorityBlockingQueue<>();
    private WorkThread[] workThreads = null;
    private  Dispatcher dispatcher;

    public WorkThreadManager(int workThreadCount, Dispatcher dispatcher) {
        this.workThreadCount = workThreadCount;
        this.dispatcher = dispatcher;
    }


    public void start() {
        workThreads = new WorkThread[workThreadCount];
        for (int i = 0; i < workThreadCount; i++) {
            workThreads[i] = new WorkThread(mRequestQueue,dispatcher);
            workThreads[i].start();
        }
    }

    public void addRequest(Request request) {
        mRequestQueue.add(request);
        Log.e(TAG, "加入队列: "+mRequestQueue.size() );
    }
}

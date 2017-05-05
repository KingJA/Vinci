package com.kingja.vinci;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.util.concurrent.BlockingQueue;

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
    private Dispatcher dispatcher;

    public WorkThread(BlockingQueue<Request> requestQueue, Dispatcher dispatcher) {
        this.requestQueue = requestQueue;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                if (requestQueue.size() > 0) {
                    Request request = requestQueue.take();
                    Log.e(TAG, "取出任务: "+request.url );
                    dispatcher.dealTask(request);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

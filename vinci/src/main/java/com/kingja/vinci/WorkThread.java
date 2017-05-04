package com.kingja.vinci;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.util.concurrent.BlockingQueue;

import okhttp3.Response;

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
                Request request = requestQueue.take();
                dispatcher.dealTask(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

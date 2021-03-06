package com.kingja.vinci;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.widget.ImageView;

/**
 * Description:TODO
 * Create Time:2017/5/4 10:32
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RequestCreator {
    private Vinci vinci;
    private final Request.Builder configBuilder;

    public RequestCreator(Vinci vinci, String url) {
        this.vinci = vinci;
        configBuilder = new Request.Builder(url);
        configBuilder.setContext(vinci.context);
    }

    public RequestCreator placeholderRes(@DrawableRes int placeholderResId) {
        configBuilder.placeholderRes(placeholderResId);
        return this;
    }

    public RequestCreator errorRes(@DrawableRes int errorRes) {
        configBuilder.errorRes(errorRes);
        return this;
    }

    public void into(final ImageView imageView) {

        //合成请求Request
        Request request = configBuilder.setTarget(imageView).build();

        if (vinci.isRunning(request)) {
            Log.e("RequestCreator", "正在加载中...: ");
            return;
        }

        //设置TAG，防止图片错位
        imageView.setTag(request.url);

        //判断是否在主线程
        Utils.checkMain();
        //有占位图则先展示占位图
        if (request.placeholderRes != 0) {
            imageView.setImageDrawable(vinci.context.getResources().getDrawable(request.placeholderRes));
        }
        //如果有缓存则从缓存中获取Bitmap
        final Bitmap cacheBitmap = vinci.getCache(request);
        if (cacheBitmap != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(cacheBitmap);
                }
            });
            return;
        }
        //没有则利用下载器获取
        vinci.addRequest(request);
    }


}

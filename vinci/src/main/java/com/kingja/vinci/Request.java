package com.kingja.vinci;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.util.Comparator;

/**
 * Description:TODO
 * Create Time:2017/5/4 10:34
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Request implements Comparable<Request>{
    public String url;
    public int placeholderRes;
    public int errorRes;
    public ImageView imageView;
    public Context context;

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    private boolean finished;


    public Request(Builder builder) {
        this.url = builder.url;
        this.placeholderRes = builder.placeholderResId;
        this.errorRes = builder.errorRes;
        this.imageView = builder.imageView;
        this.context = builder.context;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Request) {
            return this.url.equals(((Request) obj).url);
        }
        return false;
    }

    @Override
    public int compareTo(@NonNull Request o) {
        return 1;
    }

    public static class Builder {
        private String url;
        private int placeholderResId;
        private int errorRes;
        private Context context;
        public ImageView imageView;

        public Builder(String url) {
            this.url = url;
        }

        public Builder placeholderRes(@DrawableRes int placeholderResId) {
            this.placeholderResId = placeholderResId;
            return this;
        }

        public Builder errorRes(@DrawableRes int errorRes) {
            this.errorRes = errorRes;
            return this;
        }

        public Builder setTarget(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Request build() {
            return new Request(this);
        }


    }
}

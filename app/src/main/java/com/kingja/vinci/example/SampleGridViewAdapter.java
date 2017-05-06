package com.kingja.vinci.example;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kingja.vinci.Vinci;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

final class SampleGridViewAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> urls = new ArrayList<>();

    public SampleGridViewAdapter(Context context) {
        this.context = context;
        Collections.addAll(urls, Data.URLS);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String url = getItem(position);

        // Trigger the download of the URL asynchronously into the image view.

        ViewHolder viewHolder ;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView = View.inflate(context, R.layout.item_iv, null);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Vinci.with(context)
                .load(url)
                .placeholderRes(R.drawable.placeholder)
                .errorRes(R.drawable.error)
                .into(viewHolder.iv);
        return convertView;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public String getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        private ImageView iv;

    }
}

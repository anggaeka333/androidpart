package com.angga.project.anggata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ItemLogAdapter extends BaseAdapter {

    Context context;

    public ItemLogAdapter (Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_log, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.time = (TextView) convertView.findViewById(R.id.text_time);
            viewHolder.lat = (TextView) convertView.findViewById(R.id.text_lat);
            viewHolder.lon = (TextView) convertView.findViewById(R.id.text_long);
            viewHolder.v = (TextView) convertView.findViewById(R.id.text_v);
            convertView.setTag(viewHolder);
        }

        else
            viewHolder = (ViewHolder) convertView.getTag();

        return convertView;
    }

    static class ViewHolder {
        TextView time;
        TextView lat;
        TextView lon;
        TextView v;
    }
}

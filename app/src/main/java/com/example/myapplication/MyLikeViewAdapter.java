package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MyLikeViewAdapter extends BaseAdapter {
    Context context;
    MyLikeViewAdapter.ViewHolder holder;
    int layout;
    ArrayList<LikeViewVO> data;
    LayoutInflater inflater;

    public MyLikeViewAdapter(Context context, int layout, ArrayList<LikeViewVO> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
        inflater =(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(layout, null);
            holder = new MyLikeViewAdapter.ViewHolder(convertView);

            //holder.tv_resLoc.setText(data.get(position).getResLoc());
            holder.tv_resName.setText(data.get(position).getResName());
            holder.tv_dishName.setText(data.get(position).getDishName());
            holder.tv_starScore.setText(data.get(position).getStarScore());
            holder.iv_dishImg.setImageResource(data.get(position).getDishImg());

            //Rounded-Circle
            RoundedCorners corners = new RoundedCorners(14);
            RequestOptions options = RequestOptions.bitmapTransform(corners)
                    .placeholder(R.mipmap.ic_launcher)
                    .skipMemoryCache(true) // Skip memory cache
                    .diskCacheStrategy(DiskCacheStrategy.NONE);//Do not buffer disk hard disk

            Glide.with(convertView).load(data.get(position).getDishImg()).apply(options).into(holder.iv_dishImg);

        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv_dishImg;
        TextView tv_resName, tv_dishName, tv_starScore;
        //TextView tv_resLoc;

        public ViewHolder(View itemView) {
            iv_dishImg = itemView.findViewById(R.id.iv_dishImg);
            //tv_resLoc = itemView.findViewById(R.id.tv_resLoc);
            tv_resName = itemView.findViewById(R.id.tv_resName);
            tv_dishName = itemView.findViewById(R.id.tv_dishName);
            tv_starScore = itemView.findViewById(R.id.tv_starScore);
        }

    }

}

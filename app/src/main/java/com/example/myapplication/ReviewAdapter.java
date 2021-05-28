package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {

    Context context;
    ReviewAdapter.ViewHolder holder;
    int layout;
    ArrayList<ReviewVO> data;
    LayoutInflater inflater;
    RatingBar rating_value;

    public ReviewAdapter(Context context, int layout, ArrayList<ReviewVO> data) {
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
            holder = new ReviewAdapter.ViewHolder(convertView);

//            holder.tv_reviewStarPoint.setText(String.valueOf(data.get(position).getStarPoint()));

            try {
                rating_value.setRating(Float.parseFloat(data.get(position).getStarPoint()));
                holder.tv_reviewWriteText.setText(data.get(position).getReviewText());
                holder.tv_reviewWriteDate.setText(data.get(position).getWriteDate());
                holder.tv_reviewUserID.setText(data.get(position).getUserName());

                if(data.get(position).getReviewImage().equals("a")){
                    holder.iv_reviewWriteImage.setVisibility(View.GONE);
                }
                Glide.with(convertView)
                        .load(data.get(position).getReviewImage())
                        .into(holder.iv_reviewWriteImage);

//                holder.iv_reviewWriteImage.setImageBitmap(originalBm);



                /*File files = new File("content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F35/ORIGINAL/NONE/image%2Fjpeg/1169728720");
                if(files.exists()) {

                    Uri uri = Uri.parse("content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F35/ORIGINAL/NONE/image%2Fjpeg/1169728720");
                    holder.iv_reviewWriteImage.setImageURI(uri);
//                    holder.iv_reviewWriteImage.setImageResource(data.get(position).getReviewImage());
                    Log.d("review Uri", uri.toString());
                }*/

            }catch (Exception e){
                e.printStackTrace();
            }




            holder.iv_reviewProfileImage.setImageResource(data.get(position).getProfileImage());


            // Circle
            Glide.with(convertView).load(data.get(position).getProfileImage()).apply(new RequestOptions().circleCrop()).into(holder.iv_reviewProfileImage);

            //Rounded-Circle
            RoundedCorners corners = new RoundedCorners(14);
            RequestOptions options = RequestOptions.bitmapTransform(corners)
                    .placeholder(R.mipmap.ic_launcher)
                    .skipMemoryCache(true) // Skip memory cache
                    .diskCacheStrategy(DiskCacheStrategy.NONE);//Do not buffer disk hard disk

//            Glide.with(convertView).load(data.get(position).getReviewImage()).apply(options).into(holder.iv_reviewWriteImage);

        }

        return convertView;
    }


    class ViewHolder {
        ImageView iv_reviewProfileImage, iv_reviewWriteImage;
        TextView tv_reviewUserID, tv_reviewWriteDate, tv_reviewWriteText, tv_reviewStarPoint;

        public ViewHolder(View itemView) {
            iv_reviewProfileImage = itemView.findViewById(R.id.iv_reviewProfileImage);
            iv_reviewWriteImage = itemView.findViewById(R.id.iv_reviewWriteImage);
            tv_reviewUserID = itemView.findViewById(R.id.tv_reviewUserID);
            tv_reviewWriteDate = itemView.findViewById(R.id.tv_reviewWriteDate);
            tv_reviewWriteText = itemView.findViewById(R.id.tv_reviewWriteText);
            rating_value = itemView.findViewById(R.id.rb_reviewscore);
            //tv_reviewStarPoint = itemView.findViewById(R.id.tv_reviewStarPoint);
        }

    }
}

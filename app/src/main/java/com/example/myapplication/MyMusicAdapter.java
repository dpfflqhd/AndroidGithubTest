package com.example.myapplication;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.ArrayList;

public class MyMusicAdapter extends BaseAdapter {

    Context context;
    MyMusicAdapter.ViewHolder holder;
    int layout;
    ArrayList<MyMusicVO> data;
    LayoutInflater inflater;
    MediaPlayer mediaPlayer;

    public MyMusicAdapter(Context context, int layout, ArrayList<MyMusicVO> data) {
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
            holder = new MyMusicAdapter.ViewHolder(convertView);

            holder.tv_myMusicName.setText(data.get(position).getMusicName());
            holder.iv_myMusicImg.setImageResource(data.get(position).getMusicImg());

            // 음악 플레이어

            // MediaPlayer 객체가 재생할 음악 지정
            mediaPlayer = MediaPlayer.create(context, data.get(position).getMusicId());
            // 무한 반복 옵션
            mediaPlayer.setLooping(true);

            // 사용자의 Seekbar 임의 조절시 작동하는 코드
            holder.sbar_myMusicBar.setMax(mediaPlayer.getDuration());
            holder.sbar_myMusicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(fromUser)
                        mediaPlayer.seekTo(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            // Seekbar 추적 쓰레드
            class musicStartThread extends Thread {
                @Override
                public void run(){
                    while(mediaPlayer.isPlaying()){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        holder.sbar_myMusicBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                }
            }

            // 재생버튼 눌렀을때
            holder.btn_myMusicPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer.start();

                    Thread musicStartThread = new musicStartThread();
                    musicStartThread.start();
                }
            });





            // 일시정지버튼 눌렀을때
            holder.btn_myMusicPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer.pause();
                }
            });

            // 정지버튼 눌렀을때
            holder.btn_myMusicStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer.stop();
                    try {
                        mediaPlayer.prepare();
                    } catch(IOException ie) {
                        ie.printStackTrace();
                    }
                    mediaPlayer.seekTo(0);
                }
            });



            //Rounded-Circle
            RoundedCorners corners = new RoundedCorners(14);
            RequestOptions options = RequestOptions.bitmapTransform(corners)
                    .placeholder(R.mipmap.ic_launcher)
                    .skipMemoryCache(true) // Skip memory cache
                    .diskCacheStrategy(DiskCacheStrategy.NONE);//Do not buffer disk hard disk

            Glide.with(convertView).load(data.get(position).getMusicImg()).apply(options).into(holder.iv_myMusicImg);

        }

        return convertView;
    }

    class ViewHolder {
        ImageView iv_myMusicImg;
        TextView tv_myMusicName;
        ImageButton btn_myMusicPlay, btn_myMusicPause, btn_myMusicStop;
        SeekBar sbar_myMusicBar;

        public ViewHolder(View itemView) {
            iv_myMusicImg = itemView.findViewById(R.id.iv_myMusicImg);
            tv_myMusicName = itemView.findViewById(R.id.tv_myMusicName);
            btn_myMusicPlay = itemView.findViewById(R.id.btn_myMusicPlay);
            btn_myMusicPause = itemView.findViewById(R.id.btn_myMusicPause);
            btn_myMusicStop = itemView.findViewById(R.id.btn_myMusicStop);
            sbar_myMusicBar = itemView.findViewById(R.id.sbar_myMusicBar);
        }

    }

}

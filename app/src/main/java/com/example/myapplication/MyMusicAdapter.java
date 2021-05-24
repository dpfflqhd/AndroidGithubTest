package com.example.myapplication;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    int pausePosition = 0;

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
            holder.btn_myMusicPlay.setImageResource(R.drawable.playbtn);
            holder.btn_myMusicStop.setImageResource(R.drawable.stopbtn);
            holder.btn_myMusicPause.setImageResource(R.drawable.pausebtn);


            //Rounded-Circle
            RoundedCorners corners = new RoundedCorners(14);
            RequestOptions options = RequestOptions.bitmapTransform(corners)
                    .placeholder(R.mipmap.ic_launcher)
                    .skipMemoryCache(true) // Skip memory cache
                    .diskCacheStrategy(DiskCacheStrategy.NONE);//Do not buffer disk hard disk

            Glide.with(convertView).load(data.get(position).getMusicImg()).apply(options).into(holder.iv_myMusicImg);

        }

        // 음악 플레이어
        // 재생버튼 눌렀을때
        holder.btn_myMusicPlay.setOnClickListener(new View.OnClickListener() {
            final MyMusicAdapter.ViewHolder tempHolder = holder;
            @Override
            public void onClick(View v) {
                // Seekbar 추적 쓰레드
                class musicStartThread extends Thread {
                    @Override
                    public void run(){
                        try {
                                while (mediaPlayer.isPlaying()) {
                                    tempHolder.sbar_myMusicBar.setProgress(mediaPlayer.getCurrentPosition());
                                    Thread.sleep(1000);
                                }

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                    }
                }

                if (mediaPlayer == null) {
//                    mediaPlayer = MediaPlayer.create(context.getApplicationContext(), data.get(position).getMusicId());
                    try {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setDataSource(data.get(position).getMusicId());
                        Log.d("music", data.get(position).getMusicId());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if(!mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(pausePosition);
                    mediaPlayer.start();
                }

                Thread musicStartThread = new musicStartThread();
                musicStartThread.start();

                // 사용자의 Seekbar 임의 조절시 작동하는 코드
                Log.v("hhd", "setMax : " + mediaPlayer.getDuration());
                tempHolder.sbar_myMusicBar.setMax(mediaPlayer.getDuration());
                Log.v("hhd", "seekbar : " + holder.sbar_myMusicBar);
                tempHolder.sbar_myMusicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

            }
        });

        // 일시정지버튼 눌렀을때
        holder.btn_myMusicPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                    pausePosition = mediaPlayer.getCurrentPosition();
                }
            }
        });

        // 정지버튼 눌렀을때
        holder.btn_myMusicStop.setOnClickListener(new View.OnClickListener() {
            final MyMusicAdapter.ViewHolder tempHolder = holder;

            @Override
            public void onClick(View v) {

                // Seekbar 정지 쓰레드
                class musicStopThread extends Thread {
                    @Override
                    public void run(){
                        try {
                            if (mediaPlayer != null) {
                                mediaPlayer.stop();
                                tempHolder.sbar_myMusicBar.setProgress(0);
                            }
                            Thread.sleep(1000);
                            mediaPlayer = null;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Thread musicStopThread = new musicStopThread();
                musicStopThread.start();

            }
        });

        return convertView;
    }



    class ViewHolder {
        final ImageView iv_myMusicImg;
        final TextView tv_myMusicName;
        final ImageButton btn_myMusicPlay, btn_myMusicPause, btn_myMusicStop;
        final SeekBar sbar_myMusicBar;

        public ViewHolder(View itemView) {
            iv_myMusicImg = itemView.findViewById(R.id.iv_myMusicImg);
            tv_myMusicName = itemView.findViewById(R.id.tv_myMusicName);
            btn_myMusicPlay = itemView.findViewById(R.id.btn_myMusicPlay);
            btn_myMusicPause = itemView.findViewById(R.id.btn_myMusicPause);
            btn_myMusicStop = itemView.findViewById(R.id.btn_myMusicStop);
            sbar_myMusicBar = itemView.findViewById(R.id.sbar_myMusicBar);
            Log.v("btn", btn_myMusicPause+"");
        }

    }

}

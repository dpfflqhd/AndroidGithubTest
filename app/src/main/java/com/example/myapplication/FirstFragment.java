package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.theartofdev.edmodo.cropper.CropImage;

public class FirstFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    private String name;

    TextView tv_likeview, tv_myMusciView2, tv_firstfrag_name;
    ImageView profileImage;

    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance(int page, String title) {
        FirstFragment fragment = new FirstFragment();
        /*Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);*/
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);


        tv_likeview = view.findViewById(R.id.tv_likeview);
        profileImage = view.findViewById(R.id.profileImage);
        tv_myMusciView2 = view.findViewById(R.id.tv_myMusciView2);
        tv_firstfrag_name = view.findViewById(R.id.tv_firstfrag_name);


        Bundle bundle = getArguments(); //번들 안의 텍스트 불러오기
        title = bundle.getString("send"); //fragment1의 TextView에 전달 받은 text 띄우기
        name = bundle.getString("name");
        Log.d("firstfrag title", title);

        tv_firstfrag_name.setText(name + "님 환영합니다");



        tv_likeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    Intent intent = new Intent(getContext(), MyLikeViewActivity.class);
                    intent.putExtra("id", title);
                    Thread.sleep(10);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        tv_myMusciView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyMusicActivity.class);
                intent.putExtra("id", title);
                startActivity(intent);
            }
        });

        return view;
    }
}
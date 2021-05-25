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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.theartofdev.edmodo.cropper.CropImage;

public class FirstFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    TextView tv_likeview, tv_myMusciView2;
    ImageView profileImage;

    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance(int page, String title) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        tv_likeview = view.findViewById(R.id.tv_likeview);
        profileImage = view.findViewById(R.id.profileImage);
        tv_myMusciView2 = view.findViewById(R.id.tv_myMusciView2);

        Log.d("firstfrag title:", title);
        Glide.with(getContext()).load(R.drawable.paddington).apply(new RequestOptions().circleCrop()).into(profileImage);


        tv_likeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyLikeViewActivity.class);
                intent.putExtra("id", title);
                startActivity(intent);
            }
        });

        tv_myMusciView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyMusicActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FirstFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    TextView tv_likeview;
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

        String email = "";
        Bundle bundle = getArguments(); //번들 안의 텍스트 불러오기
        email = bundle.getString("id");

        Log.d("1-1번째 email  : ", ""+email);

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        tv_likeview = view.findViewById(R.id.tv_likeview);
        profileImage = view.findViewById(R.id.profileImage);
        String email = "";
        Bundle bundle = getArguments(); //번들 안의 텍스트 불러오기
        email = bundle.getString("id");

        Log.d("1번째 email  : ", ""+email);

        Glide.with(getContext()).load(R.drawable.paddington).apply(new RequestOptions().circleCrop()).into(profileImage);


        tv_likeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyLikeViewActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
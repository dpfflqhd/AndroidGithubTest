package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;

import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class SecondFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // 전역 변수 선언
    ImageView selectedImage;
    Button searchImage;
    Drawable tempImg;
    Bitmap bm;
    RequestQueue requestQueue;

    // newInstance constructor for creating fragment with arguments
    public static SecondFragment newInstance(int page, String title) {
        SecondFragment fragment = new SecondFragment();
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
        if (page != 0) {
            page = getArguments().getInt("someInt", 0);
            title = getArguments().getString("someTitle");
        }
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        // 요소 초기화. view를 통해 접근해야 id 찾기가 가능.
        selectedImage = view.findViewById(R.id.selectedImage);
        searchImage = view.findViewById(R.id.searchImage);

        selectedImage.setImageResource(R.drawable.camera);
        tempImg = selectedImage.getDrawable();

        // 그림을 클릭하면 갤러리에 접근하도록 하는 코드.
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
//              intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }

    // 인텐트 결과 정의
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 크롭된 이미지를 클릭했을때의 코드
        if (requestCode == 1 && resultCode == RESULT_OK) {

            try {
                // 선택한 이미지에서 비트맵 생성
                InputStream in = getActivity().getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                // 이미지뷰에 세팅
                selectedImage.setImageBitmap(img);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
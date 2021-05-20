package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class SecondFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // 전역 변수 선언
    ImageView selectedImage;
    Button btn_searchImage;
    Drawable tempImg;
    Bitmap bm;
    RequestQueue requestQueue;
    int REQUEST_TAKE_ALBUM = 5000;
    int REQUEST_CODE_PROFILE_IMAGE_PICK = 5001;
    private static final String TEMP_FOOD_IMAGE_NAME = "tempfoodimage.jpg";
    private Uri mTempImageUri;


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
        btn_searchImage = view.findViewById(R.id.btn_searchImage);

        selectedImage.setImageResource(R.drawable.camera);
        tempImg = selectedImage.getDrawable();

        // 그림을 클릭하면 갤러리에 접근하도록 하는 코드.
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 갤러리에 접근
               getAlbum();
            }
        });

        return view;
    }

    // 인텐트 결과 정의
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 크롭된 이미지를 클릭했을때의 코드
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            try {
                Log.d("크롭", "크롭 사진 받기 성공");

                // 크롭된 이미지 받아오는 코드
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri resultUri = result.getUri();
                selectedImage.setImageURI(resultUri);

                // 끝 다듬는 코드
//                RoundedCorners corners = new RoundedCorners(14);
//                RequestOptions options = RequestOptions.bitmapTransform(corners)
//                        .placeholder(R.mipmap.ic_launcher)
 //                       .skipMemoryCache(true) // Skip memory cache
  //                      .diskCacheStrategy(DiskCacheStrategy.NONE);//Do not buffer disk hard disk

//                Glide.with(getContext()).load(img).apply(options).into(selectedImage);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_TAKE_ALBUM && resultCode ==RESULT_OK) {
            getCrop(data.getData());
        }

    }
    
    // 이미지 크롭하는 코드
    public void getCrop(Uri uri) {
        CropImage.activity().start(getContext(), this);
    }

    // 갤러리에 접근하는 코드
    public void getAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        intent.putExtra("crop", true);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

}
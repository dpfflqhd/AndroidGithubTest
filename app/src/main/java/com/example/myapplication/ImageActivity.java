package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageActivity extends AppCompatActivity {

    ImageView selectedImage;
    Button searchImage;
    Drawable tempImg;
    Bitmap bm;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        
        // 요소 초기화
        selectedImage = findViewById(R.id.selectedImage);
        searchImage = findViewById(R.id.searchImage);

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


        // 현재 선택된 그림을 검색하는 버튼을 눌렀을때의 코드. Drawable을 Bitmap으로 전환한뒤 다시 한번 인코딩을 한다.
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                bm = BitmapFactory.decodeResource(getResources(), R.drawable.testimage);
//                String encodedTemp = BitmapToString(bm);
//                Log.d("인코딩 결과: ", encodedTemp);

                // 서버에 요청 보내는 requestQuest 객체 생성
                requestQueue = Volley.newRequestQueue(getApplicationContext());

                // 플라스크 주소
                String deepModelURL = "http://127.0.0.1:5000/";
                String google = "https://www.google.co.kr/";

                // 본격적으로 요청
                StringRequest requestServer = new StringRequest(
                        Request.Method.POST,
                        google,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("요청", "성공");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("요청", "실패");
                            }
                        }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("data", encodedTemp);
                        params.put("data", "TTTTTEST");
                        return params;
                    }
                };

                requestQueue.add(requestServer);
            }
        });



        // 이미지 접근 권한
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR);

        if(permissionCheck== PackageManager.PERMISSION_DENIED){
            Log.d("권한?", "yes");
        }else{
            Log.d("권한?", "no");
        }


    }


    // 인텐트 결과 정의
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 크롭된 이미지를 클릭했을때의 코드
        if (requestCode == 1 && resultCode == RESULT_OK) {

                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지뷰에 세팅
                    selectedImage.setImageBitmap(img);

                } catch (Exception e) {
                    e.printStackTrace();
                }

        }

    }

    // Bitmap을 String으로 전환하는 코드. Base64 압축을 사용한다.
    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);
        return encodedImage;
    }

    // Bitmap을 Jpeg로 저장하는 코드. 일단 가져오기만 함.
    public static String saveBitmapToJpeg(Context context, Bitmap bitmap){

        File storage = context.getCacheDir(); // 이 부분이 임시파일 저장 경로
        String fileName = "TEST.jpg"; // 파일이름은 마음대로!
        File tempFile = new File(storage,fileName);

        try{
            tempFile.createNewFile(); // 파일을 생성해주고
            FileOutputStream out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90 , out); // 넘거 받은 bitmap을 jpeg(손실압축)으로 저장해줌
            out.close(); // 마무리로 닫아줍니다.

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile.getAbsolutePath(); // 임시파일 저장경로를 리턴해주면 끝!
    }




}
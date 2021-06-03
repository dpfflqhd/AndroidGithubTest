package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Fragment3Child2 extends Fragment {
    String food = "";
    String name = "";
    String menu = "";
    String img = "";
    String store_name = "";
    String rating = "";
    String like = "";
    String store1 = "";
    String store2 = "";
    String store3 = "";

    String sameName1 = "";
    String sameName2 = "";
    String sameName3 = "";

    String sameMenu1 = "";
    String sameMenu2 = "";
    String sameMenu3 = "";

    String sameStore1 = "";
    String sameStore2 = "";
    String sameStore3 = "";
    String sameStore4 = "";
    String sameStore5 = "";
    String sameStore6 = "";
    String document_id = "";
    String sameImg = "";

    ImageView img_top1;
    Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView tv_top_menu1, tv_top_menu2, tv_top_menu3, tv_top_name1, tv_top_name2, tv_top_name3, tv_sea_name1, tv_sea_name2, tv_sea_name3,
            tv_sea_menu1, tv_sea_menu2, tv_sea_menu3, tv_ai_menu1, tv_ai_menu2, tv_ai_menu3, tv_ai_name1, tv_ai_name2, tv_ai_name3;

    List<LikeSortVO> name_list = new ArrayList<>();
    int cnt = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment3_child2, container, false);

        Bundle bundle = getArguments(); //번들 안의 텍스트 불러오기
        sameStore1 = bundle.getString("sameStore1");
        sameStore2 = bundle.getString("sameStore2");
        sameStore3 = bundle.getString("sameStore3");
        sameStore4 = bundle.getString("sameStore4");
        sameStore5 = bundle.getString("sameStore5");
        sameStore6 = bundle.getString("sameStore6");

        Log.d("frag3child2 :", sameStore1);
        img_top1 = view.findViewById(R.id.img_top1);
        ImageView img_top2 = view.findViewById(R.id.img_top2);
        ImageView img_top3 = view.findViewById(R.id.img_top3);

        ImageView img_ai1 = view.findViewById(R.id.img_ai1);
        ImageView img_ai2 = view.findViewById(R.id.img_ai2);
        ImageView img_ai3 = view.findViewById(R.id.img_ai3);

        ImageView img_month1 = view.findViewById(R.id.img_month1);
        ImageView img_month2 = view.findViewById(R.id.img_month2);
        ImageView img_month3 = view.findViewById(R.id.img_month3);

        ImageView img_sea1 = view.findViewById(R.id.img_sea1);
        ImageView img_sea2 = view.findViewById(R.id.img_sea2);
        ImageView img_sea3 = view.findViewById(R.id.img_sea3);

        ImageView img_magazine1 = view.findViewById(R.id.img_magazine1);
        ImageView img_magazine2 = view.findViewById(R.id.img_magazine2);
        ImageView img_magazine3 = view.findViewById(R.id.img_magazine3);


        //TextView 초기화
        tv_top_menu1 = view.findViewById(R.id.tv_top_menu1);
        tv_top_menu2 = view.findViewById(R.id.tv_top_menu2);
        tv_top_menu3 = view.findViewById(R.id.tv_top_menu3);
        tv_top_name1 = view.findViewById(R.id.tv_top_name1);
        tv_top_name2 = view.findViewById(R.id.tv_top_name2);
        tv_top_name3 = view.findViewById(R.id.tv_top_name3);

        tv_sea_menu1 = view.findViewById(R.id.tv_sea_menu1);
        tv_sea_menu2 = view.findViewById(R.id.tv_sea_menu2);
        tv_sea_menu3 = view.findViewById(R.id.tv_sea_menu3);

        tv_sea_name1 = view.findViewById(R.id.tv_sea_name1);
        tv_sea_name2 = view.findViewById(R.id.tv_sea_name2);
        tv_sea_name3 = view.findViewById(R.id.tv_sea_name3);

        tv_ai_menu1 = view.findViewById(R.id.tv_ai_menu1);
        tv_ai_menu2 = view.findViewById(R.id.tv_ai_menu2);
        tv_ai_menu3 = view.findViewById(R.id.tv_ai_menu3);

        tv_ai_name1 = view.findViewById(R.id.tv_ai_name1);
        tv_ai_name2 = view.findViewById(R.id.tv_ai_name2);
        tv_ai_name3 = view.findViewById(R.id.tv_ai_name3);




        img_ai1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=어부피자";

                if(uri != null){

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(uri));
                    startActivity(intent);
                }
            }
        });

        img_ai2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=문쏘";

                if(uri != null){

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(uri));
                    startActivity(intent);
                }
            }
        });

        img_ai3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=제주판타스틱버거";

                if(uri != null){

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(uri));
                    startActivity(intent);
                }
            }
        });

        img_sea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=야스마루";

                if(uri != null){

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(uri));
                    startActivity(intent);
                }
            }
        });

        img_sea2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String uri = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=빨간모자마법사";

                if(uri != null){

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(uri));
                    startActivity(intent);
                }*/
                Intent intent = new Intent(context, SearchResultActivity.class);
                intent.putExtra("data", "coco");
                intent.putExtra("email", "test1@gmail.com");
                intent.putExtra("acc", "1");
                intent.putExtra("sameStore1", "sirae");
                intent.putExtra("sameStore2", "snake");
                intent.putExtra("sameStore3", "burger");
                intent.putExtra("audio", "test2_1");
                intent.putExtra("name", "sandmann");
                startActivity(intent);
            }
        });

        img_sea3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=소영이네돈까스물회";

                if(uri != null){

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(uri));
                    startActivity(intent);
                }
            }
        });

        img_magazine1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=멍게비빔밥";

                if(uri != null){

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(uri));
                    startActivity(intent);
                }
            }
        });

        img_magazine2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=마라장어매운탕";

                if(uri != null){

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(uri));
                    startActivity(intent);
                }
            }
        });

        img_magazine3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=전복해물라면";

                if(uri != null){

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(uri));
                    startActivity(intent);
                }
            }
        });

        //ai 추천 음식점 3개
        db.collection("store")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                sameName1 = document.getString("name");
                                sameMenu1 = document.getString("menu");
                                sameImg = document.getString("img");

                                document_id = document.getId();
                                //성별 추천
                                if(sameStore1.equals(document_id)){

                                    tv_ai_menu1.setText(sameMenu1);
                                    tv_ai_name1.setText(sameName1);
                                    Glide.with(context).load(sameImg).into(img_ai1);
                                }

                                if(sameStore2.equals(document_id)){
                                    tv_ai_name2.setText(sameName1);
                                    tv_ai_menu2.setText(sameMenu1);
                                    Glide.with(context).load(sameImg).into(img_ai2);
                                }

                                if(sameStore3.equals(document_id)){
                                    tv_ai_menu3.setText(sameMenu1);
                                    tv_ai_name3.setText(sameName1);
                                    Glide.with(context).load(sameImg).into(img_ai3);
                                }

                                if(sameStore4.equals(document_id)){
                                    tv_sea_menu1.setText(sameMenu1);
                                    tv_sea_name1.setText(sameName1);
                                    Glide.with(context).load(sameImg).into(img_sea1);
                                }

                                if(sameStore5.equals(document_id)){
                                    tv_sea_menu2.setText(sameMenu1);
                                    tv_sea_name2.setText(sameName1);
                                    Glide.with(context).load(sameImg).into(img_sea2);
                                }

                                if(sameStore6.equals(document_id)){
                                    tv_sea_menu3.setText(sameMenu1);
                                    tv_sea_name3.setText(sameName1);
                                    Glide.with(context).load(sameImg).into(img_sea3);
                                }

                            }
                        }
                        else {
                            Log.w("받아오기실패", "Error getting documents.", task.getException());
                        }
                    }
                });

//        Log.d("ai store", store1 + "/"  + store2 + "/" + store3);
        context = container.getContext();

        //별점 순으로 정렬 후 ArrayList에 저장
        db.collection("store").orderBy("rating", Query.Direction.DESCENDING).limit(3)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                name = document.getString("name");
                                menu = document.getString("menu");
                                img = document.getString("img");
                                rating = document.getString("rating");
                                like = document.getString("like");
                                Log.d("like", like);
                                store_name = document.getId();



                                //이미지액티비티에서 받아온 결과값(음식점이름)에 해당되는 데이터베이스 불러오기
                                name_list.add(new LikeSortVO(name, menu, img, rating, Integer.parseInt(like), store_name));


                            }
                            if(name_list != null && !name_list.isEmpty()){
                                for(int i = 0 ; i < name_list.size(); i++){
                                    Log.d("rating sort", ""+ name_list.get(i).getRating() + "/" + name_list.get(i).getName() + "/" + name_list.get(i).getImg());
                                }
                            }

                            if(name_list != null && !name_list.isEmpty()){
                                String imageStr = name_list.get(0).getImg();
                                Glide.with(context).load(imageStr).into(img_top1);
                                String imageStr1 = name_list.get(1).getImg();
                                Glide.with(context).load(imageStr1).into(img_top2);
                                String imageStr2 = name_list.get(2).getImg();
                                Glide.with(context).load(imageStr2).into(img_top3);

                                tv_top_menu1.setText(name_list.get(0).getMenu());
                                tv_top_menu2.setText(name_list.get(1).getMenu());
                                tv_top_menu3.setText(name_list.get(2).getMenu());

                                tv_top_name1.setText(name_list.get(0).getName());
                                tv_top_name2.setText(name_list.get(1).getName());
                                tv_top_name3.setText(name_list.get(2).getName());


                                img_top1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String uri = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query="+name_list.get(0).getName();

                                        if(uri != null){

                                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                                    Uri.parse(uri));
                                            startActivity(intent);

                                        }


                                    }
                                });

                                img_top2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String uri = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query="+name_list.get(1).getName();

                                        if(uri != null){

                                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                                    Uri.parse(uri));
                                            startActivity(intent);
                                        }
                                    }
                                });

                                img_top3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String uri = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query="+name_list.get(2).getName();

                                        if(uri != null){

                                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                                    Uri.parse(uri));
                                            startActivity(intent);
                                        }
                                    }
                                });

                            }
                        } else {
                            Log.w("받아오기실패", "Error getting documents.", task.getException());
                        }
                    }
                });

        return view;
    }


    public class loadImageTask extends AsyncTask<Bitmap, Void, Bitmap> {

        private String url;

        public loadImageTask(String url) {

            this.url = url;
        }
        @Override
        protected Bitmap doInBackground(Bitmap... params) {

            Bitmap imgBitmap = null;

            try {
                URL url1 = new URL(url);
                URLConnection conn = url1.openConnection();
                conn.connect();
                int nSize = conn.getContentLength();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), nSize);
                imgBitmap = BitmapFactory.decodeStream(bis);
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return imgBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bit) {
            super.onPostExecute(bit);
            img_top1.setImageBitmap(bit);
        }
    }
}
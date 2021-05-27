package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fragment3Child2 extends Fragment {
    String food = "";
    String name = "";
    String menu = "";
    String img = "";
    String store_name = "";
    String rating = "";
    String like = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    List<LikeSortVO> name_list = new ArrayList<>();
    List<String> menu_list = new ArrayList<>();
    List<String> img_list = new ArrayList<>();
    List<String> rating_list = new ArrayList<>();
    List<String> store_name_list = new ArrayList<>();
    List<Integer> like_list = new ArrayList<>();

    String[] top3 = new String[3];

    int cnt = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment3_child2, container, false);

        ImageView img_top1 = view.findViewById(R.id.img_top1);
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
                                    Log.d("rating sort", ""+ name_list.get(i).getRating() + "/" + name_list.get(i).getName());
                                }
                            }
                        } else {
                            Log.w("받아오기실패", "Error getting documents.", task.getException());
                        }
                    }
                });











        return inflater.inflate(R.layout.fragment_fragment3_child2, container, false);
    }
}
package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Fragment3Child2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment3_child1, container, false);

        ImageView img_top1 = view.findViewById(R.id.img_top11);
        ImageView img_top2 = view.findViewById(R.id.img_top22);
        ImageView img_top3 = view.findViewById(R.id.img_top33);

        ImageView img_ai1 = view.findViewById(R.id.img_ai11);
        ImageView img_ai2 = view.findViewById(R.id.img_ai22);
        ImageView img_ai3 = view.findViewById(R.id.img_ai33);

        ImageView img_month1 = view.findViewById(R.id.img_month1);
        ImageView img_month2 = view.findViewById(R.id.img_month2);
        ImageView img_month3 = view.findViewById(R.id.img_month3);

        ImageView img_sea1 = view.findViewById(R.id.img_sea11);
        ImageView img_sea2 = view.findViewById(R.id.img_sea22);
        ImageView img_sea3 = view.findViewById(R.id.img_sea33);



        return inflater.inflate(R.layout.fragment_fragment3_child2, container, false);
    }
}
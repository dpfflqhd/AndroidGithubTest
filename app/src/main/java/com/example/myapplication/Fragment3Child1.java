package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment3Child1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment3_child1, container, false);

        Button btn_jeonra = view.findViewById(R.id.btn_jeonra);
        Button btn_gangwon = view.findViewById(R.id.btn_gangwon);
        Button btn_gyoenggi = view.findViewById(R.id.btn_gyeonggi);
        Button btn_gyeongsang = view.findViewById(R.id.btn_gyeongsang);
        Button btn_choong = view.findViewById(R.id.btn_choong);

        btn_jeonra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_gangwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_gyoenggi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_gyeongsang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_choong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return inflater.inflate(R.layout.fragment_fragment3_child1, container, false);
    }
}
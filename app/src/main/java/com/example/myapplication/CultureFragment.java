package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class CultureFragment extends Fragment {

    private Button btn_fragment_culture_photo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_culture, container, false);
        // Inflate the layout for this fragment

        btn_fragment_culture_photo = fragment.findViewById(R.id.btn_culture_fragment_photo);

        btn_fragment_culture_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CulturePhotoActivity.class);
                startActivity(intent);
            }
        });
        return fragment;
    }
}
package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import net.daum.mf.map.api.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Fragment3Child1 extends Fragment {
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment3_child1, container, false);

        Button btn_jeonra = view.findViewById(R.id.btn_jeonra);
        Button btn_gangwon = view.findViewById(R.id.btn_gangwon);
        Button btn_gyoenggi = view.findViewById(R.id.btn_gyeonggi);
        Button btn_gyeongsang = view.findViewById(R.id.btn_gyeongsang);
        Button btn_choong = view.findViewById(R.id.btn_choong);

        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo("com.example.myapplication", PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        getHashKey();


        MainSwipeActivity mainSwipeActivity = new MainSwipeActivity();
        //수정해야할듯
//        MapView mapView = new MapView(mainSwipeActivity);

        RelativeLayout mapViewContainer = (RelativeLayout) view.findViewById(R.id.MapView);
//        mapViewContainer.addView(mapView);

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

    private void getHashKey(){
        PackageInfo packageInfo = null;
//        MapActivity mapActivity = (MapActivity)getActivity();

        try {
//            assert mapActivity != null;
            packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), PackageManager.GET_SIGNATURES);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity)
            activity = (Activity) context;
    }
}
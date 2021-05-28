package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class ThirdFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnPolygonClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener, ActivityCompat.OnRequestPermissionsResultCallback {
    // Store instance variables
    private String title;
    private int page;
    private MapView googlemap = null;
    private Context context;
    private CheckBox mMyLocationButtonCheckbox;
    private CheckBox mMyLocationLayerCheckbox;
    Button btn_seoul, btn_gangwon, btn_chung, btn_geungsang, btn_gwang, btn_jeju;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    private EditText edt_map;
    Button btn_map_search;
    TextView map_text;

    // newInstance constructor for creating fragment with arguments
    public static ThirdFragment newInstance(int page, String title) {
        ThirdFragment fragment = new ThirdFragment();
        /*Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);*/
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");*/

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        btn_seoul = view.findViewById(R.id.btn_seoul);
        btn_chung = view.findViewById(R.id.btn_chung);
        btn_gangwon = view.findViewById(R.id.btn_gangwon);
        btn_geungsang = view.findViewById(R.id.btn_geungsang);
        btn_gwang = view.findViewById(R.id.btn_gwang);
        btn_jeju = view.findViewById(R.id.btn_jeju);

        /*mMyLocationButtonCheckbox = (CheckBox) findViewById(R.id.mylocationbutton_toggle);
        mMyLocationLayerCheckbox = (CheckBox) findViewById(R.id.mylocationlayer_toggle);*/

//        title = getArguments().getString("send");

        context = container.getContext();
        //처음 childfragment 지정
//        getFragmentManager().beginTransaction().add(R.id.child_fragment, new Fragment3Child2()).commit();


        googlemap = (MapView) view.findViewById(R.id.GoogleMapView1);
        googlemap.getMapAsync(this);



        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (googlemap != null) {
            googlemap.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        googlemap.onStart();
    }

    @Override
    public void onStop(){
        googlemap.onStop();
        super.onStop();
    }

    @Override
    public void onResume(){
        super.onResume();
        googlemap.onResume();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

//        googleMap.setMyLocationEnabled(true);
//        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMyLocationClickListener(this);

//        googleMap.isMyLocationEnabled();

        LatLng center = new LatLng(35.893762, 127.890505);


        LatLng bareut = new LatLng(35.28279225131622, 129.25899915573476);
        LatLng bulgogi = new LatLng(36.77968115750337, 126.46597305576968);
        LatLng burger = new LatLng(33.308437, 126.778977);
        LatLng chik = new LatLng(37.349143613720685, 127.93243572509789);
        LatLng cho = new LatLng(37.401803015745976, 126.72303581160617);
        LatLng coco = new LatLng(35.809451, 127.152982);
        LatLng dag = new LatLng(36.40301059653384, 127.23358373681153);
        LatLng dang = new LatLng(35.09912906027624, 129.02847059805896);
        LatLng don = new LatLng(37.76264787792444, 128.87587404045064);
        LatLng dongbaek = new LatLng(35.15874268118544, 129.06421459806043);
        LatLng fish = new LatLng(33.441830138798046, 126.89275771151483);
        LatLng galbi = new LatLng(37.022800963059254, 128.17525624043282);
        LatLng go = new LatLng(37.5132311501985, 127.10269233600526);
        LatLng gwanganri = new LatLng(35.15628721278359, 129.12158236922477);
        LatLng han = new LatLng(37.49047339819476, 127.98657522695109);
        LatLng hang = new LatLng(37.35992607848986, 127.03896002509816);
        LatLng hong = new LatLng(37.51465704121981, 129.11991619626636);
        LatLng hwang = new LatLng(35.836034228059866, 129.21213822691183);
        LatLng jamami = new LatLng(35.23040868249358, 129.08537778086955);
        LatLng jin = new LatLng(36.7827595728768, 126.45209425761942);
        LatLng jingal = new LatLng(35.9896394470207, 126.70897712506545);
        LatLng kagawa = new LatLng(35.154893018004266, 129.06563684038912);
        LatLng kao = new LatLng(37.56448677676377, 126.98390714044595);
        LatLng kor = new LatLng(37.88220644389705, 127.72359572511093);
        LatLng kum = new LatLng(37.58071153086614, 127.00485722695323);
        LatLng mali = new LatLng(37.35162766680353, 127.94605874044082);
        LatLng mama = new LatLng(37.77215617327386, 128.87800061161528);
        LatLng man = new LatLng(33.39851211597267, 126.24690046544791);
        LatLng mok = new LatLng(33.30663397919366, 126.78386174219742);
        LatLng moon = new LatLng(33.40592702231109, 126.25654967540055);
        LatLng mul = new LatLng(37.44477075057408, 129.17788462510043);
        LatLng nak = new LatLng(37.26431481907047, 126.99740860995429);
        LatLng oct = new LatLng(33.553872691219205, 126.80791025569584);
        LatLng pong = new LatLng(35.843939553014046, 127.1277647808837);
        LatLng pyeon = new LatLng(37.57896353485892, 127.05025845578898);
        LatLng red = new LatLng(33.29053234048347, 126.30137875384027);
        LatLng sakana = new LatLng(35.16462549701258, 129.15719829806073);
        LatLng sinbul = new LatLng(36.809707107446606, 127.10606306444876);
        LatLng sirae = new LatLng(37.49979526796714, 126.93152668592803);
        LatLng snake = new LatLng(37.59270710586012, 127.01772764229631);
        LatLng snoopy = new LatLng(37.75368610053254, 128.8980712864787);
        LatLng sot = new LatLng(36.479509827489046, 127.04754759624136);
        LatLng spa = new LatLng(37.557446942009946, 126.65321387769944);
        LatLng sukju = new LatLng(37.51158257819225, 127.11400981160894);
        LatLng theflower = new LatLng(34.86626812951516, 128.69117285572509);
        LatLng two = new LatLng(35.8143346842598, 127.15681294225399);
        LatLng vivi = new LatLng(35.22733467716698, 128.87656156922645);
        LatLng yasmaru = new LatLng(35.15621057165188, 129.06431043853925);
        LatLng zerk = new LatLng(37.54919363367814, 126.91750922695252);
        LatLng zuk = new LatLng(36.775023179438875, 126.42633476926262);





        googleMap.addMarker(new MarkerOptions()
                .position(bareut)
                .title("바롯식당"));
        googleMap.addMarker(new MarkerOptions()
                .position(bulgogi)
                .title("백반의 신"));
        googleMap.addMarker(new MarkerOptions()
                .position(burger)
                .title("제주 판타스틱버거"));
        googleMap.addMarker(new MarkerOptions()
                .position(chik)
                .title("칡산에"));
        googleMap.addMarker(new MarkerOptions()
                .position(cho)
                .title("홍대구루메"));
        googleMap.addMarker(new MarkerOptions()
                .position(coco)
                .title("코코스톤"));
        googleMap.addMarker(new MarkerOptions()
                .position(dag)
                .title("마세오른"));
        googleMap.addMarker(new MarkerOptions()
                .position(dang)
                .title("18번 완당집"));
        googleMap.addMarker(new MarkerOptions()
                .position(don)
                .title("소영이네 돈까스물회"));
        googleMap.addMarker(new MarkerOptions()
                .position(dongbaek)
                .title("동백아가씨1961"));
        googleMap.addMarker(new MarkerOptions()
                .position(fish)
                .title("어부피자"));
        googleMap.addMarker(new MarkerOptions()
                .position(galbi)
                .title("청풍떡갈비"));
        googleMap.addMarker(new MarkerOptions()
                .position(go)
                .title("테이스팅룸"));
        googleMap.addMarker(new MarkerOptions()
                .position(gwanganri)
                .title("광안리 끄티집"));
        googleMap.addMarker(new MarkerOptions()
                .position(han)
                .title("하누&카누"));
        googleMap.addMarker(new MarkerOptions()
                .position(hang)
                .title("산골항아리바베큐"));
        googleMap.addMarker(new MarkerOptions()
                .position(hong)
                .title("홍대포"));
        googleMap.addMarker(new MarkerOptions()
                .position(hwang)
                .title("황남경주식당"));
        googleMap.addMarker(new MarkerOptions()
                .position(jamami)
                .title("자마미등갈비"));
        googleMap.addMarker(new MarkerOptions()
                .position(jin)
                .title("진국집"));
        googleMap.addMarker(new MarkerOptions()
                .position(jingal)
                .title("진갈비"));
        googleMap.addMarker(new MarkerOptions()
                .position(kagawa)
                .title("카가와식당"));
        googleMap.addMarker(new MarkerOptions()
                .position(kao)
                .title("반티엔야오 카오위"));
        googleMap.addMarker(new MarkerOptions()
                .position(kor)
                .title("날다낀닭"));
        googleMap.addMarker(new MarkerOptions()
                .position(kum)
                .title("쿰피르스테이크"));
        googleMap.addMarker(new MarkerOptions()
                .position(mali)
                .title("박순례손말이고기산정집"));
        googleMap.addMarker(new MarkerOptions()
                .position(mama)
                .title("마마타타"));
        googleMap.addMarker(new MarkerOptions()
                .position(man)
                .title("면뽑는 선생 만두빚는 아내"));
        googleMap.addMarker(new MarkerOptions()
                .position(mok)
                .title("목스키친"));
        googleMap.addMarker(new MarkerOptions()
                .position(moon)
                .title("문쏘"));
        googleMap.addMarker(new MarkerOptions()
                .position(mul)
                .title("성원닭갈비"));
        googleMap.addMarker(new MarkerOptions()
                .position(nak)
                .title("낙원타코 롯데몰수원점"));
        googleMap.addMarker(new MarkerOptions()
                .position(oct)
                .title("떡하니 문어떡볶이"));
        googleMap.addMarker(new MarkerOptions()
                .position(pong)
                .title("김피라 전국체인"));
        googleMap.addMarker(new MarkerOptions()
                .position(pyeon)
                .title("정편집"));
        googleMap.addMarker(new MarkerOptions()
                .position(red)
                .title("빨간모자 마법사"));
        googleMap.addMarker(new MarkerOptions()
                .position(sakana)
                .title("사카나식당"));
        googleMap.addMarker(new MarkerOptions()
                .position(sinbul)
                .title("퍼센토"));
        googleMap.addMarker(new MarkerOptions()
                .position(sirae)
                .title("시래불고기 화풍정"));
        googleMap.addMarker(new MarkerOptions()
                .position(snake)
                .title("마녀주방 성신여대점"));
        googleMap.addMarker(new MarkerOptions()
                .position(snoopy)
                .title("거기"));
        googleMap.addMarker(new MarkerOptions()
                .position(sot)
                .title("솥뚜껑 매운탕"));
        googleMap.addMarker(new MarkerOptions()
                .position(spa)
                .title("샤이바나 청라스퀘어세븐점"));
        googleMap.addMarker(new MarkerOptions()
                .position(sukju)
                .title("현차이나"));
        googleMap.addMarker(new MarkerOptions()
                .position(theflower)
                .title("더꽃"));
        googleMap.addMarker(new MarkerOptions()
                .position(two)
                .title("두이모 비빔밥와플"));
        googleMap.addMarker(new MarkerOptions()
                .position(vivi)
                .title("비비리에또"));
        googleMap.addMarker(new MarkerOptions()
                .position(yasmaru)
                .title("야스마루"));
        googleMap.addMarker(new MarkerOptions()
                .position(zerk)
                .title("자이온보트"));
        googleMap.addMarker(new MarkerOptions()
                .position(zuk)
                .title("풍전뚝집"));


        googleMap.setOnMarkerClickListener(markerClickListener);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                MapDialog mapDialog = new MapDialog(context);
                mapDialog.show();

                btn_map_search = mapDialog.findViewById(R.id.btn_map_search);
                map_text = mapDialog.findViewById(R.id.map_text);
                map_text.setText(marker.getTitle());

                btn_map_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Toast.makeText(context, marker.getTitle(), Toast.LENGTH_SHORT).show();


                            String uri = null;
                            if (marker.getTitle().equals("코코스톤")) {
                                uri = "https://www.google.com/maps/dir/%EC%BD%94%EC%BD%94%EC%8A%A4%ED%86%A4+%EC%A0%84%EB%9D%BC%EB%B6%81%EB%8F%84+%EC%A0%84%EC%A3%BC%EC%8B%9C+%EC%99%84%EC%82%B0%EA%B5%AC+%EB%8F%99%EC%84%9C%ED%95%99%EB%8F%99+142-4/%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90/@35.4408271,127.0239927,10z/data=!4m13!4m12!1m5!1m1!1s0x3570249be35cf17f:0x685d0abd6c880e11!2m2!1d127.1529605!2d35.809484!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            } else if (marker.getTitle().equals("바롯식당")) {
                                uri = "https://www.google.com/maps/dir/%EC%9D%B8%EC%B2%9C%EA%B0%80%EB%93%A0+%EC%A0%84%EB%9D%BC%EB%B6%81%EB%8F%84+%EA%B3%A0%EC%B0%BD%EA%B5%B0+%EC%95%84%EC%82%B0%EB%A9%B4+%EC%9B%90%ED%8F%89%EA%B8%B8+9/%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90/@35.2850495,126.4694162,10z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x35719ff27a338f7d:0xc8f42d5a844ee5ca!2m2!1d126.6277615!2d35.4625537!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            } else if (marker.getTitle().equals("백반의 신")) {
                                uri = "https://www.google.com/maps/dir/%EC%84%9C%EC%82%B0%EB%B6%88%EA%B3%A0%EA%B8%B0+%EB%B0%B1%EB%B0%98%EC%9D%98%EC%8B%A0+%EC%B6%A9%EC%B2%AD%EB%82%A8%EB%8F%84+%EC%84%9C%EC%82%B0%EC%8B%9C+%EB%8F%99%EB%AC%B8%EB%8F%99+189-10/%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90/@35.9398695,126.1241549,9z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x357a5db68f3c1491:0x495e935073c5bbcc!2m2!1d126.4659683!2d36.7794637!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            } else if (marker.getTitle().equals("제주 판타스틱버거")) {
                                uri = "https://www.google.co.kr/maps/dir/%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84+%EC%84%9C%EA%B7%80%ED%8F%AC%EC%8B%9C+%ED%91%9C%EC%84%A0%EB%A9%B4+%ED%86%A0%EC%82%B0%EC%A4%91%EC%95%99%EB%A1%9C15%EB%B2%88%EA%B8%B8+6+%ED%8C%90%ED%83%80%EC%8A%A4%ED%8B%B1%EB%B2%84%EA%B1%B0/%EC%A0%9C%EC%A3%BC%EC%8B%9C+%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84/@33.4090683,126.6034857,12z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x350d070b02a48ccb:0x4b9741cefed0cd29!2m2!1d126.7790718!2d33.3084898!1m5!1m1!1s0x350ce0858a6d79fd:0x4b9a8869e1919ce2!2m2!1d126.5311884!2d33.4996213?hl=ko";
                            } else if (marker.getTitle().equals("칡산에")) {
                                uri = "https://www.google.com/maps/dir/%EC%B9%A1%EC%82%B0%EC%97%90+%EA%B0%95%EC%9B%90%EB%8F%84+%EC%9B%90%EC%A3%BC%EC%8B%9C+%EB%8B%A8%EA%B3%84%EB%8F%99+825-2/%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90/@36.3174728,126.2428876,8z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x356374544c71bb9b:0x4f92c9625c2adcd3!2m2!1d127.932466!2d37.349023!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            } else if (marker.getTitle().equals("홍대구루메")) {
                                uri = "https://www.google.com/maps/dir/%ED%99%8D%EB%8C%80%EA%B5%AC%EB%A3%A8%EB%A9%94+%EC%9D%B8%EC%B2%9C%EB%85%BC%ED%98%84%EC%A0%90(%EC%95%84%EC%9D%B4%ED%94%8C%EB%A0%89%EC%8A%A4+2%EC%B8%B5)+%EC%9D%B8%EC%B2%9C%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EB%8F%99%EA%B5%AC+%EB%85%BC%ED%98%842%EB%8F%99+%EB%85%BC%EA%B3%A0%EA%B0%9C%EB%A1%9C123%EB%B2%88%EA%B8%B8+17+%EC%95%84%EC%9D%B4%ED%94%8C%EB%A0%89%EC%8A%A4+2%EC%B8%B5/%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90/@36.3174728,126.2428876,8z/data=!4m13!4m12!1m5!1m1!1s0x357b7a6dc46d6ad3:0xbd70ff0cccfeb751!2m2!1d126.723068!2d37.4016411!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            } else if (marker.getTitle().equals("마세오른")) {
                                uri = "https://www.google.com/maps/dir/%EB%A7%88%EC%84%B8%EC%98%A4%EB%A5%B8+%EC%B6%A9%EC%B2%AD%EB%82%A8%EB%8F%84+%EA%B3%B5%EC%A3%BC%EC%8B%9C+%EB%B0%98%ED%8F%AC%EB%A9%B4+%EB%B4%89%EA%B3%A1%EB%A6%AC+467-31+KR/%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90/@35.7684094,126.4527456,9z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x357ab52bb1a75a59:0x272bcb04b83a351c!2m2!1d127.2335218!2d36.4027025!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            } else if (marker.getTitle().equals("18번 완당집")) {
                                uri = "https://www.google.com/maps/dir/18%EB%B2%88%EC%99%84%EB%8B%B9%EC%A7%91+%EB%B6%80%EC%82%B0%EA%B4%91%EC%97%AD%EC%8B%9C+%EC%A4%91%EA%B5%AC+%EB%82%A8%ED%8F%AC%EB%8F%99+%EB%B9%84%ED%94%84%EA%B4%91%EC%9E%A5%EB%A1%9C+31/%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90/@35.8427466,125.7266539,7z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x3568e9a089ebd5ad:0x59a27f458fb19449!2m2!1d129.0284706!2d35.0989623!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            } else if (marker.getTitle().equals("소영이네 돈까스물회")) {
                                uri = "https://www.google.com/maps/dir/%EC%86%8C%EC%98%81%EC%9D%B4%EB%84%A4%EB%8F%88%EA%B9%8C%EC%8A%A4%EB%AC%BC%ED%9A%8C+%EA%B0%95%EC%9B%90%EB%8F%84+%EA%B0%95%EB%A6%89%EC%8B%9C+%EC%A0%95%EC%9B%90%EB%A1%9C+54+KR+1%EC%B8%B5/%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90/@36.419529,125.6091375,7z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x3561e539f638a0b7:0x46a129f252b025a9!2m2!1d128.8758955!2d37.7624613!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            } else if (marker.getTitle().equals("동백아가씨1961")) {
                                uri = "https://www.google.com/maps/dir/%EB%8F%99%EB%B0%B1%EC%95%84%EA%B0%80%EC%94%A81961+%EB%B6%80%EC%82%B0%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%B6%80%EC%82%B0%EC%A7%84%EA%B5%AC+%EC%A0%84%ED%8F%AC%EB%8F%99+%EC%84%9C%EC%A0%84%EB%A1%9C37%EB%B2%88%EA%B8%B8+18/%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90/@35.1754729,125.7480508,7z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x3568ebb19eb8728b:0xefc51cba020965e6!2m2!1d129.0642146!2d35.1585585!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            } else if (marker.getTitle().equals("어부피자")) {
                                uri = "https://www.google.com/maps/dir/%EC%96%B4%EB%B6%80%ED%94%BC%EC%9E%90+%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84+%EC%84%9C%EA%B7%80%ED%8F%AC%EC%8B%9C+%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84,+%EC%84%B1%EC%82%B0%EC%9D%8D+%EC%88%98%EC%82%B0%EB%A6%AC+225+KR/%EC%A0%9C%EC%A3%BC%EC%8B%9C+%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84/@33.4653006,126.1445466,9z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x350d1395b2f9ce73:0x8e37dad1ec53d26a!2m2!1d126.8926323!2d33.443463!1m5!1m1!1s0x350ce0858a6d79fd:0x4b9a8869e1919ce2!2m2!1d126.5311884!2d33.4996213";
                            } else if (marker.getTitle().equals("청풍떡갈비")) {
                                uri = "https://www.google.com/maps/dir/%EC%B2%AD%ED%92%8D%EB%96%A1%EA%B0%88%EB%B9%84+%EC%B6%A9%EC%B2%AD%EB%B6%81%EB%8F%84+%EC%A0%9C%EC%B2%9C%EC%8B%9C+%EA%B8%88%EC%84%B1%EB%A9%B4+%EC%84%B1%EB%82%B4%EB%A6%AC+192/%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90/@36.3263552,125.2600877,7z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x356388473cd8c3e1:0x3b55a13230b82264!2m2!1d128.1752777!2d37.0226211!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            } else if (marker.getTitle().equals("테이스팅룸")) {
                                uri = "https://www.google.com/maps/dir/%EC%B9%98%EC%A6%88%EB%A3%B8X%ED%85%8C%EC%9D%B4%EC%8A%A4%ED%8C%85%EB%A3%B8+Seoul+Songpa-gu+%EC%9E%A0%EC%8B%A46%EB%8F%99/%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90/@36.3077491,125.8357485,8z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x357ca50a93fdf00d:0x48f2189075c1a5b9!2m2!1d127.1044286!2d37.5140543!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            } else if (marker.getTitle().equals("광안리 끄티집")) {
                                uri = "https://www.google.com/maps/dir/%EA%B4%91%EC%95%88%EB%A6%AC+%EB%81%84%ED%8B%B0%EC%A7%91+%EB%B6%80%EC%82%B0%EA%B4%91%EC%97%AD%EC%8B%9C+%EC%88%98%EC%98%81%EA%B5%AC+%EB%AF%BC%EB%9D%BD%EB%8F%99+%EB%AF%BC%EB%9D%BD%EB%A1%9C13%EB%B2%88%EA%B8%B8+21/%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90/@35.8431744,125.7264293,7z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x3568ed3cbfacc795:0x4e21e2cfbf5e9e66!2m2!1d129.1215931!2d35.1561732!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            } else if (marker.getTitle().equals("하누&카누")) {
                                uri = "https://www.google.com/maps/dir/%ED%95%98%EB%88%84%26%EC%B9%B4%EB%88%84+%ED%9A%A1%EC%84%B1%EC%A0%90+%EA%B0%95%EC%9B%90%EB%8F%84+%ED%9A%A1%EC%84%B1%EA%B5%B0+%ED%9A%A1%EC%84%B1%EC%9D%8D+%EC%9D%8D%EC%83%81%EB%A6%AC+285-2+%ED%9A%A1%EC%84%B1%EC%9D%8D+%EC%9D%8D%EC%83%81%EB%A6%AC+285-2%EB%B2%88%EC%A7%80+%ED%9A%A1%EC%84%B1%EA%B5%B0+%EA%B0%95%EC%9B%90%EB%8F%84+KR+25232/%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90/@36.2913619,126.313153,8z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x3563a78e5f0296fb:0x2179f2a0060ddf99!2m2!1d127.9865645!2d37.4903287!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            } else if (marker.getTitle().equals("산골항아리바베큐")) {
                                uri = "https://www.google.com/maps/dir/%EC%82%B0%EA%B3%A8+%ED%95%AD%EC%95%84%EB%A6%AC%EB%B0%94%EB%B2%A0%ED%81%90+%EA%B2%BD%EA%B8%B0%EB%8F%84+%EC%9A%A9%EC%9D%B8%EC%8B%9C+%EC%88%98%EC%A7%80%EA%B5%AC+%EA%B3%A0%EA%B8%B0%EB%8F%99+%EC%9D%B4%EC%A2%85%EB%AC%B4%EB%A1%9C169%EB%B2%88%EA%B8%B8+3/%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90/@36.2913619,126.313153,8z/data=!4m13!4m12!1m5!1m1!1s0x357b5eb2a27e13f1:0x62b77baf9d4dba21!2m2!1d127.0387092!2d37.3599692!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            } else if (marker.getTitle().equals("면뽑는 선생 만두빚는 아내")) {
                                uri = "https://www.google.com/maps/dir/%EB%A9%B4%EB%BD%91%EB%8A%94%EC%84%A0%EC%83%9D%EB%A7%8C%EB%91%90%EB%B9%9A%EB%8A%94%EC%95%84%EB%82%B4,+%ED%98%91%EC%9E%AC%EB%A6%AC+%ED%95%9C%EB%A6%BC%EC%9D%8D+%EC%A0%9C%EC%A3%BC%EC%8B%9C+%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84/%EC%A0%9C%EC%A3%BC%EC%8B%9C+%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84/@33.4500769,126.1107823,10z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x350c613501b8d64b:0xec98cc589b85c31b!2m2!1d126.2493752!2d33.3998472!1m5!1m1!1s0x350ce0858a6d79fd:0x4b9a8869e1919ce2!2m2!1d126.5311884!2d33.4996213";
                            } else if (marker.getTitle().equals("목스키친")) {
                                uri = "https://www.google.com/maps/dir/mokskitchen-%EB%AA%A9%EC%8A%A4%ED%82%A4%EC%B9%9C+%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84+%EC%84%9C%EA%B7%80%ED%8F%AC%EC%8B%9C+%ED%91%9C%EC%84%A0%EB%A9%B4+%ED%86%A0%EC%82%B0%EB%A6%AC+423-3/%EC%A0%9C%EC%A3%BC%EC%8B%9C+%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84/@33.4314515,126.1705177,9z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x350d09dc0566a3b5:0xea66495a2de2c9b3!2m2!1d126.7838081!2d33.3064995!1m5!1m1!1s0x350ce0858a6d79fd:0x4b9a8869e1919ce2!2m2!1d126.5311884!2d33.4996213";
                            } else if (marker.getTitle().equals("문쏘")) {
                                uri = "https://www.google.com/maps/dir/%EB%AC%B8%EC%8F%98+%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84+%EC%A0%9C%EC%A3%BC%EC%8B%9C+%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84,+KR+%ED%95%9C%EB%A6%BC%EC%9D%8D+%ED%95%9C%EB%A6%BC%EC%9D%8D+%EC%98%B9%ED%8F%AC%EB%A6%AC326-3+%EB%8C%80%ED%95%9C%EB%AF%BC%EA%B5%AD+%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84/%EC%A0%9C%EC%A3%BC%EC%8B%9C+%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84/@33.4500769,126.1131562,10z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x350c608758a60011:0x8cca5648bec31258!2m2!1d126.2566867!2d33.4056209!1m5!1m1!1s0x350ce0858a6d79fd:0x4b9a8869e1919ce2!2m2!1d126.5311884!2d33.4996213";
                            } else if (marker.getTitle().equals("떡하니 문어떡볶이")) {
                                uri = "https://www.google.com/maps/dir/%EB%96%A1%ED%95%98%EB%8B%88+%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84+%EC%A0%9C%EC%A3%BC%EC%8B%9C+%EA%B5%AC%EC%A2%8C%EC%9D%8D+%ED%96%89%EC%9B%90%EB%A1%9C9%EA%B8%B8+9-5/%EC%A0%9C%EC%A3%BC%EC%8B%9C+%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84/@33.5283788,126.3862953,10z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x350d17c3ec7dd4d1:0x81a17ebde7a32717!2m2!1d126.8078888!2d33.5537833!1m5!1m1!1s0x350ce0858a6d79fd:0x4b9a8869e1919ce2!2m2!1d126.5311884!2d33.4996213";
                            } else if (marker.getTitle().equals("김피라 전국체인")) {
                                uri = "https://www.google.com/maps/dir/%EA%B9%80%ED%94%BC%EB%9D%BC%EB%8C%80%EC%A0%84%EB%91%94%EC%82%B0%EC%A0%90+%EB%91%94%EC%82%B0%EB%8F%99+1006%EB%B2%88%EC%A7%80+2%EC%B8%B5+201%ED%98%B8+%ED%81%AC%EB%A6%AC%EC%8A%A4%ED%83%88%EB%B9%8C%EB%94%A9+%EC%84%9C%EA%B5%AC+%EB%8C%80%EC%A0%84%EA%B4%91%EC%97%AD%EC%8B%9C+KR/%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90+%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+60+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+2%EC%B8%B5/@35.8592873,126.5530904,9z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x35654bde733855d5:0x1b8091427680b1d1!2m2!1d127.377808!2d36.3539002!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            } else if (marker.getTitle().equals("빨간모자 마법사")) {
                                uri = "https://www.google.com/maps/dir/%EB%B9%A8%EA%B0%84%EB%AA%A8%EC%9E%90+%EB%A7%88%EB%B2%95%EC%82%AC+%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84+%EC%84%9C%EA%B7%80%ED%8F%AC%EC%8B%9C+%EC%95%88%EB%8D%95%EB%A9%B4+%EC%84%9C%EA%B4%91%EB%A6%AC+1419-5/%EC%A0%9C%EC%A3%BC%EC%8B%9C+%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84/@33.393365,126.1368109,10z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x350c5d217be3a273:0xdfd8d7b6d6541c75!2m2!1d126.3014324!2d33.2903799!1m5!1m1!1s0x350ce0858a6d79fd:0x4b9a8869e1919ce2!2m2!1d126.5311884!2d33.4996213";
                            } else if (marker.getTitle().equals("사카나식당")) {
                                uri = "https://www.google.com/maps/dir/%EC%82%AC%EC%B9%B4%EB%82%98+%EC%8B%9D%EB%8B%B9+%EB%B6%80%EC%82%B0%EA%B4%91%EC%97%AD%EC%8B%9C+%ED%95%B4%EC%9A%B4%EB%8C%80%EA%B5%AC+%EC%9A%B0%EB%8F%99+%EC%9A%B0%EB%8F%991%EB%A1%9C+34/%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%9D%B8%EC%9E%AC%EA%B0%9C%EB%B0%9C%EC%9B%90+%EA%B4%91%EC%A3%BC%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%86%A1%ED%95%98%EB%8F%99+%EC%86%A1%EC%95%94%EB%A1%9C+60+%EA%B4%91%EC%A3%BCCGI%EC%84%BC%ED%84%B0+2%EC%B8%B5/@35.9301739,125.7365762,7z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x35688d0e4c3f633f:0x906f9de509064aea!2m2!1d129.1571983!2d35.1644501!1m5!1m1!1s0x35718b03033bfeef:0x9148e0a92fb527ab!2m2!1d126.8777619!2d35.1104947";
                            }


                            if (uri != null) {

                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                        Uri.parse(uri));
                                startActivity(intent);
                            }

                            mapDialog.dismiss();
                        }


                });


//                Toast.makeText(context, marker.getTitle(), Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        //폴리곤 그리기, 서울경기
        Polygon polygon1 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(38.206105, 127.084685),
                        new LatLng(37.994690, 126.855089),
                        new LatLng(37.772301, 126.587176),
                        new LatLng(37.581635, 126.582925),
                        new LatLng(37.40714581044991, 126.61069127131509),
                        new LatLng(37.14597510815547, 126.71168186029567),
                        new LatLng(37.12299915656004, 126.7501530732499),
                        new LatLng(36.94785145060869, 127.06887661788514),
                        new LatLng(36.95338860835477, 127.34833712673947),
                        new LatLng(37.17270422327296, 127.71787167124407),
                        new LatLng(37.47391388375802, 127.80101695105884),
                        new LatLng(37.673439494313804, 127.57467704254977),
                        new LatLng(37.99629947912301, 127.54927153036529),
                        new LatLng(38.16900262914078, 127.25595348566473)
                ));
        // Store a data object with the polygon, used here to indicate an arbitrary type.

        // Style the polygon.
        polygon1.setTag("서울경기");
        onPolygonClick(polygon1);
        stylePolygon(polygon1);

        //폴리곤 그리기, 강원도
        Polygon polygon2 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(38.24865070131243, 128.05642590292405),
                        new LatLng(38.04829823452251, 127.76246297283443),
                        new LatLng(37.733824258748584, 127.68407285814388),
                        new LatLng(37.459503625387285, 127.92577571177311),
                        new LatLng(37.16855805751112, 128.10215346982693),
                        new LatLng(37.074800879436886, 128.5855591770854),
                        new LatLng(37.09564584237275, 128.97750975053827),
                        new LatLng(37.241399993008336, 129.1996150754949),
                        new LatLng(37.56831766661448, 129.0493673556713),
                        new LatLng(37.991689114400955, 128.6900793300062),
                        new LatLng(38.24865070131243, 128.53983161018257),
                        new LatLng(38.238389605771275, 128.14788103672973)
                ));
        // Store a data object with the polygon, used here to indicate an arbitrary type.

        // Style the polygon.
        polygon2.setTag("강원");
        onPolygonClick(polygon2);
        stylePolygon(polygon2);

        //폴리곤 그리기, 충청도
        Polygon polygon3 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(36.82542188610863, 127.13972191951532),
                        new LatLng(36.804502832939754, 126.83269397031059),
                        new LatLng(36.890189247011115, 126.7652174786739),
                        new LatLng(36.9791030039372, 126.63750141870682),
                        new LatLng(37.03064736550342, 126.4781996664898),
                        new LatLng(36.928621496773516, 126.26808614076151),
                        new LatLng(36.82426035239752, 126.16920919110957),
                        new LatLng(36.6988388609221, 126.20354146529426),
                        new LatLng(36.532394886911405, 126.30928486978317),
                        new LatLng(36.416444061046406, 126.3752028362178),
                        new LatLng(36.39433852427961, 126.50154561428604),
                        new LatLng(36.03450879016824, 126.58202117119225),
                        new LatLng(35.943392755717355, 126.7138571040615),
                        new LatLng(36.04339252409388, 126.85942594660463),
                        new LatLng(36.1188637868803, 127.07640591945196),
                        new LatLng(35.901132581014856, 127.2659200729515),
                        new LatLng(35.88702068016429, 127.28457423888274),
                        new LatLng(35.86476544852268, 127.51254053946917),
                        new LatLng(35.9989638319116, 127.71361293642383),
                        new LatLng(36.19426257426161, 127.70262660868471),
                        new LatLng(36.50396327792635, 127.62846889644575),
                        new LatLng(36.681632869383094, 127.60228553070554),
                        new LatLng(36.81587824784466, 127.62151160424898),
                        new LatLng(36.81587824784466, 127.3166410094888)
                ));
        // Store a data object with the polygon, used here to indicate an arbitrary type.

        // Style the polygon.
        polygon3.setTag("충청");
        onPolygonClick(polygon3);
        stylePolygon(polygon3);

        //폴리곤 그리기, 경상도
        Polygon polygon4 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(36.79283334413312, 128.33732382468298),
                        new LatLng(36.714324045138106, 127.95190576078767),
                        new LatLng(36.31531071427593, 127.92577572255747),
                        new LatLng(35.96184607203096, 127.97803579901785),
                        new LatLng(35.75006440374087, 128.05642591370844),
                        new LatLng(35.53240179633736, 127.95190576078767),
                        new LatLng(35.23948572527577, 128.1478810475141),
                        new LatLng(35.186114073292146, 128.61168922609994),
                        new LatLng(35.16475558992005, 129.12122497158867),
                        new LatLng(35.59616928465684, 129.50664303548396),
                        new LatLng(36.056963107662185, 129.33679778698774),

                        new LatLng(36.44153712328924, 129.37599284433304),
                        new LatLng(36.839900347896105, 129.40212288256322),
                        new LatLng(36.98614583448306, 129.04283485689808)
                ));
        // Store a data object with the polygon, used here to indicate an arbitrary type.

        // Style the polygon.
        polygon4.setTag("경상");
        onPolygonClick(polygon4);
        stylePolygon(polygon4);

        //폴리곤 그리기, 전남광주
        Polygon polygon5 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(35.979548068556035, 127.04574788526315),
                        new LatLng(35.85602594733408, 126.82178157130757),
                        new LatLng(35.45262110238029, 126.58661122723584),
                        new LatLng(35.03113800344417, 126.45596103608489),
                        new LatLng(34.72030321912834, 126.69113138015662),
                        new LatLng(34.83298255846808, 127.33784982635382),
                        new LatLng(34.988334150470784, 127.65141028511609),
                        new LatLng(35.26082455129012, 127.88004811963029),
                        new LatLng(35.59616927639587, 127.82125553361234),
                        new LatLng(35.818955483609855, 127.55342264175287),
                        new LatLng(35.813658289155065, 127.25292720210571)
                ));
        // Store a data object with the polygon, used here to indicate an arbitrary type.

        // Style the polygon.
        polygon5.setTag("전라광주");
        onPolygonClick(polygon5);
        stylePolygon(polygon5);

        //폴리곤 그리기, 제주
        Polygon polygon6 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(33.502383914059344, 126.5157553067123),
                        new LatLng(33.486187725645195, 126.38951707048659),
                        new LatLng(33.447304515369936, 126.30406349519534),
                        new LatLng(33.39056857723387, 126.19530439937013),
                        new LatLng(33.2997140132103, 126.15451973843567),
                        new LatLng(33.229886381978154, 126.21666779319291),
                        new LatLng(33.21851381073804, 126.2516260739939),
                        new LatLng(33.19413903730585, 126.29241073492835),
                        new LatLng(33.231510914312764, 126.33319539586282),
                        new LatLng(33.22176326767379, 126.43612811155455),
                        new LatLng(33.21688903701302, 126.51769743342346),
                        new LatLng(33.225012603918806, 126.62451440253751),
                        new LatLng(33.26074733532043, 126.70996797782877),
                        new LatLng(33.294844129827375, 126.77405815929721),
                        new LatLng(33.30945296433035, 126.83232196063213),
                        new LatLng(33.37272962773975, 126.88281725512243),
                        new LatLng(33.41164625306562, 126.91000702907874),
                        new LatLng(33.45054544908893, 126.94690743659085),
                        new LatLng(33.52829151280107, 126.88475938183358),
                        new LatLng(33.567138360368126, 126.79736367983118),
                        new LatLng(33.55419135174643, 126.67695182373896),
                        new LatLng(33.52829151280107, 126.55653996764676)
                ));
        // Store a data object with the polygon, used here to indicate an arbitrary type.

        // Style the polygon.
        polygon6.setTag("제주");
        onPolygonClick(polygon6);
        stylePolygon(polygon6);

        googleMap.moveCamera((CameraUpdateFactory.newLatLng(center)));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(6));

        btn_seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng seoul = new LatLng(37.547603408597126, 127.08308196668837);
                googleMap.moveCamera((CameraUpdateFactory.newLatLng(seoul)));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(8));
            }
        });

        btn_jeju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng jeju = new LatLng(33.36720039682756, 126.54884494826352);
                googleMap.moveCamera((CameraUpdateFactory.newLatLng(jeju)));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(9));

            }
        });

        btn_gangwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng gangwon = new LatLng(37.759651326220094, 128.40918140687052);
                googleMap.moveCamera((CameraUpdateFactory.newLatLng(gangwon)));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(8));
            }
        });

        btn_gwang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng gangwon = new LatLng(35.287490198328946, 127.18106961372285);
                googleMap.moveCamera((CameraUpdateFactory.newLatLng(gangwon)));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(8));
            }
        });

        btn_geungsang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng gangwon = new LatLng(35.69172533229539, 128.53329910039557);
                googleMap.moveCamera((CameraUpdateFactory.newLatLng(gangwon)));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(8));
            }
        });

        btn_chung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng gangwon = new LatLng(36.48881920434342, 127.38357740633558);
                googleMap.moveCamera((CameraUpdateFactory.newLatLng(gangwon)));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(8));
            }
        });
    }


    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dot.



    @Override
    public void onPolygonClick(Polygon polygon) {
        // Flip the values of the red, green, and blue components of the polygon's color.
        int color = polygon.getStrokeColor() ^ 0x00ffffff;
        polygon.setStrokeColor(color);
        color = polygon.getFillColor() ^ 0x00ffffff;
        polygon.setFillColor(color);

//        Toast.makeText(context, "Area type " + polygon.getTag().toString(), Toast.LENGTH_SHORT).show();
        Log.d("폴리곤 클릭", "");
    }

    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_GREEN_ARGB = 0xff388E3C;
    private static final int COLOR_PURPLE_ARGB = 0xff81C784;
    private static final int COLOR_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_BLUE_ARGB = 0xffF9A825;

    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dash.
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
    private static final List<PatternItem> PATTERN_POLYGON_BETA =
            Arrays.asList(DOT, GAP, DASH, GAP);


    private void stylePolygon(Polygon polygon) {
        String type = "";
        // Get the data object stored with the polygon.
        if (polygon.getTag() != null) {
            type = polygon.getTag().toString();
        }

        List<PatternItem> pattern = null;
//        int strokeColor = COLOR_BLACK_ARGB;
        int strokeColor = COLOR_PURPLE_ARGB;
//        int fillColor = COLOR_WHITE_ARGB;
        int fillColor = 0;

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "서울경기":
                // Apply a stroke pattern to render a dashed line, and define colors.
                pattern = PATTERN_POLYGON_ALPHA;
                strokeColor = COLOR_GREEN_ARGB;
//                fillColor = COLOR_PURPLE_ARGB;
                break;
            case "강원":
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_ALPHA;
                strokeColor = COLOR_GREEN_ARGB;
//                fillColor = COLOR_BLUE_ARGB;
                break;

            case "충청":
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_ALPHA;
                strokeColor = COLOR_GREEN_ARGB;
                break;

            case "경상":
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_ALPHA;
                strokeColor = COLOR_GREEN_ARGB;
//                fillColor = COLOR_BLUE_ARGB;
                break;

            case "전라광주":
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_ALPHA;
                strokeColor = COLOR_GREEN_ARGB;
//                fillColor = COLOR_BLUE_ARGB;
                break;

            case "제주":
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_ALPHA;
                strokeColor = COLOR_GREEN_ARGB;
//                fillColor = COLOR_BLUE_ARGB;
                break;
        }

        polygon.setStrokePattern(pattern);
        polygon.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
        polygon.setStrokeColor(strokeColor);
        polygon.setFillColor(fillColor);
    }

    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            // Retrieve the data from the marker.
            Integer clickCount = (Integer) marker.getTag();

            // Check if a click count was set, then display the click count.
            if (clickCount != null) {
                clickCount = clickCount + 1;
                marker.setTag(clickCount);

                String uri = "https://naver.com";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(uri));
                startActivity(intent);

//                https://www.google.com/maps/dir/35.161123,126.836917/37.5487836,127.2872/@36.3496332,125.9409072,8z/data=!3m1!4b1
                /*String uri ="http://maps.google.com/maps/dir/35.161123,126.836917/37.548783572815914,127.28719999820369";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(uri));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_LAUNCHER );
                intent.setClassName("com.google.android.apps.maps", "com.example.myapplication");
                startActivity(intent);
                Toast.makeText(context, marker.getTitle(), Toast.LENGTH_SHORT).show();*/
            }



            // Return false to indicate that we have not consumed the event and that we wish
            // for the default behavior to occur (which is for the camera to move such that the
            // marker is centered and for the marker's info window to open, if it has one).
            return false;
        }
    };

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(context, "Current location:\n" + location, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(context, "MyLocation button clicked", Toast.LENGTH_SHORT)
                .show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
}


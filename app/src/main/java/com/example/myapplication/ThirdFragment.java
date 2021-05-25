package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class ThirdFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnPolygonClickListener {
    // Store instance variables
    private String title;
    private int page;
    private MapView googlemap = null;
    private Context context;
    Button btn_seoul, btn_gangwon, btn_chung, btn_geungsang, btn_gwang, btn_jeju;


    // newInstance constructor for creating fragment with arguments
    public static ThirdFragment newInstance(int page, String title) {
        ThirdFragment fragment = new ThirdFragment();
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
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

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

        context = container.getContext();
        //처음 childfragment 지정
        getFragmentManager().beginTransaction().add(R.id.child_fragment, new Fragment3Child1()).commit();

        //하위버튼
        Button subButton1 = view.findViewById(R.id.subButton1);
        Button subButton2 = view.findViewById(R.id.subButton2);

        googlemap = (MapView)view.findViewById(R.id.GoogleMapView1);
        googlemap.getMapAsync(this);

        //클릭 이벤트 - child fragment로 이동
        subButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googlemap.setVisibility(View.VISIBLE);
                getFragmentManager().beginTransaction().replace(R.id.child_fragment, new Fragment3Child1()).commit();
            }
        });
        subButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googlemap.setVisibility(View.INVISIBLE);
                getFragmentManager().beginTransaction().replace(R.id.child_fragment, new Fragment3Child2()).commit();
            }
        });





        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(googlemap != null){
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

    @Override


    public void onMapReady(GoogleMap googleMap) {
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
        LatLng gwanganri= new LatLng(35.15628721278359, 129.12158236922477);
        LatLng han= new LatLng(37.49047339819476, 127.98657522695109);
        LatLng hang= new LatLng(37.35992607848986, 127.03896002509816);
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
        LatLng two = new LatLng( 35.8143346842598, 127.15681294225399);
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
}


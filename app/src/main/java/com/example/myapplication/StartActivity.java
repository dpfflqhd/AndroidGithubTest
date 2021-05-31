package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    String dialog_id, dialog_pw;
    private FirebaseAuth mAuth;
    Button btn_start_login, btn_start_join;
    EditText edt_loginid, edt_loginpw;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "파이어베이스 확인";
    String age = "";
    String gender = "";
    String real_age = "";
    String real_gender = "";
    String id = "";
    String name = "";
    String menu = "";
    String store_name = "";
    String[] receive_split;
    private static String CONNECT_MSG = "connect";
    String email = "";
    Connect connect = new Connect();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        btn_start_login = findViewById(R.id.btn_start_login);
        btn_start_join = findViewById(R.id.btn_start_join);



        db.collection("store").orderBy("rating", Query.Direction.DESCENDING).limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                name = document.getString("name");
                                menu = document.getString("menu");
                                store_name = document.getId();

                                //이미지액티비티에서 받아온 결과값(음식점이름)에 해당되는 데이터베이스 불러오기
//                                name_list.add(new LikeSortVO(name, menu, img, rating, Integer.parseInt(like), store_name));
                            }
                        } else {
                            Log.w("받아오기실패", "Error getting documents.", task.getException());
                        }
                    }
                });

        btn_start_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이얼로그 생성하고 보여주기
                LoginDialog loginDialog = new LoginDialog(StartActivity.this);
                loginDialog.show();

                edt_loginid = loginDialog.findViewById(R.id.edt_loginid);
                edt_loginpw = loginDialog.findViewById(R.id.edt_loginpw);


                // 다이얼로그에서 로그인버튼 클릭 시 포토액티비티로 이동
                loginDialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        email = edt_loginid.getText().toString();
                        String pw = edt_loginpw.getText().toString();

                        db.collection("users")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                id = document.getString("id");
                                                age = document.getString("age");
                                                gender = document.getString("gender");

                                                Log.d("login get data id", id + gender + age);
                                                if(email.equals(id)){
                                                    real_age = age;
                                                    real_gender = gender;
                                                    Log.d("login real_id, name",  real_age + real_gender);
                                                }
                                            }


                                            connect.execute(CONNECT_MSG);


                                        } else {
                                            Log.w("받아오기실패", "Error getting documents.", task.getException());
                                        }
                                    }
                                });

                        mAuth.signInWithEmailAndPassword(email, pw)
                                .addOnCompleteListener(StartActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

//                                            Intent intent = new Intent(StartActivity.this, BottomNaviActivity.class);

                                            /*Bundle bundle = new Bundle();
                                            bundle.putString("id", email);
                                            Fragment secondFragment = new SecondFragment();
                                            secondFragment.setArguments(bundle);*/

                                            /*intent.putExtra("id", email);
                                            Log.d("보낸 id : ", email);
                                            startActivity(intent);*/
//                                            finish();
                                        } else {
                                            Toast.makeText(StartActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                        //connect



                    }
                });
            }
        });





        // 회원가입버튼 클릭 시
        btn_start_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join_intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(join_intent);
            }
        });
    }


    //파이썬 서버와 통신하는 부분 AsyncTask는 비동기 통신을 하는데 사용
    private class Connect extends AsyncTask< String , String,Void > {

        //통신을 위한 변수선언 및 서버 IP를 설정한다(파이썬의 IP주소를 입력)
        private String output_message;
        private String input_message;
        private DataOutputStream dataOutput;
        private DataInputStream dataInput;
        private String SERVER_IP = "220.93.169.14";
        private String STOP_MSG = "stop";
        private int BUF_SIZE = 100;
        private Socket client;


        @Override
        protected Void doInBackground(String... strings) {

            try {
                //소켓에 서버 IP와 PORT 번호를 담아 통신한다.
                client = new Socket(SERVER_IP, 8089);
                dataOutput = new DataOutputStream(client.getOutputStream());
                dataInput = new DataInputStream(client.getInputStream());

                output_message = age + "/" + gender + "/" + store_name;
                dataOutput.writeUTF(output_message);

            } catch (UnknownHostException e) {
                String str = e.getMessage().toString();
                Log.w("discnt", str + " 1");
            } catch (IOException e) {
                String str = e.getMessage().toString();
                Log.w("discnt", str + " 2");
            }

            while (true){
                try {

                    if(input_message == null) {
                        byte[] buf = new byte[BUF_SIZE];
                        Log.d("buf", buf.toString());
                        int read_Byte = dataInput.read(buf);
                        input_message = new String(buf, 0, read_Byte);

                        if (!input_message.equals(STOP_MSG)) {
                            publishProgress(input_message);
//                            connect.cancel(true);
                            break;

                        } else {
                            break;
                        }
                    }

//                    connect.cancel(true);


                    /*Log.d("receive 확인", receive_split[0]);
                    Log.d("receive 1확인", receive_split[1]);
                    Log.d("receive_split[2]", receive_split[2]);*/





                    Thread.sleep(10);

                } catch (IOException | InterruptedException e) {
//                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... params){
            Log.d("보낸 메세지: " , output_message);
            Log.d("받은 메세지: " , params[0]);

            receive_split = params[0].split("/");

            Intent result_intent= new Intent(getApplicationContext(), BottomNaviActivity.class);
            result_intent.putExtra("id", email);
            result_intent.putExtra("sameStore1", receive_split[0]);
            result_intent.putExtra("sameStore2", receive_split[1]);
            result_intent.putExtra("sameStore3", receive_split[2]);

            result_intent.putExtra("sameStore4", receive_split[3]);
            result_intent.putExtra("sameStore5", receive_split[4]);
            result_intent.putExtra("sameStore6", receive_split[5]);
            startActivity(result_intent);
            finish();


            /*Log.d("receive_split[0]", receive_split[0]);
            Log.d("receive_split[1]", receive_split[1]);*/

            /*비슷한 음식점
            Log.d("receive_split[2]", receive_split[2]);
            Log.d("receive_split[3]", receive_split[3]);
            Log.d("receive_split[4]", receive_split[4]);*/
        }

    }

}
package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class StartActivity extends AppCompatActivity {

    String dialog_id, dialog_pw;
    private FirebaseAuth mAuth;
    Button btn_start_login, btn_start_join;
    EditText edt_loginid, edt_loginpw;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "파이어베이스 확인";


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

                        String email = edt_loginid.getText().toString();
                        String pw = edt_loginpw.getText().toString();

                        mAuth.signInWithEmailAndPassword(email, pw)
                                .addOnCompleteListener(StartActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            Intent intent = new Intent(StartActivity.this, MainSwipeActivity.class);

                                            /*Bundle bundle = new Bundle();
                                            bundle.putString("id", email);
                                            Fragment secondFragment = new SecondFragment();
                                            secondFragment.setArguments(bundle);*/

                                            intent.putExtra("id", email);
                                            Log.d("보낸 id : ", email);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(StartActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
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

}
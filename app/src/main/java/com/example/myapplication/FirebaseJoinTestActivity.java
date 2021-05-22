package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseJoinTestActivity extends AppCompatActivity {
    private Button btn_jointest;
    private FirebaseAuth mAuth;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = "파이어베이스 확인 : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_join_test);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        btn_jointest = findViewById(R.id.btn_jointest);

        btn_jointest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CollectionReference productRef = db.collection("test");
                DocumentReference productRef1 = db.collection("test").document("store");
                // Get the document
                productRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            // ...

                        } else {
                            Log.d(TAG, "Error getting document.", task.getException());
                        }
                    }
                });

                // Get a subcollection
                productRef1.collection("coco").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
//                                        String addr = document.getString("addr");
                                        String key = document.getId();

                                        if(key.equals("bareut")){
                                            String addr = document.getString("addr");
                                            String name = document.getString("name");
                                            Log.d(TAG, "주소 :" + addr +"/"+ "이름 : " + name);

                                        }
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.d(TAG, "Error getting subcollection.", task.getException());
                                }
                            }
                        });

            }
        });
    }
}
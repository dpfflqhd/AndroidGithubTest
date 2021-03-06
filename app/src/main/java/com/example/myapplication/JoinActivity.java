package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {
    final String TAG = "파이어베이스";
    Spinner aSpinner;
    String sex = null;
    ImageView img_id, img_pw, img_name, img_age, img_sex, img_addr;
    Spinner addr_spinner;
    String addr;
    EditText edt_join_id, edt_join_pw, edt_join_fn, edt_join_age, edt_join_sex, edt_join_address;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private Button btn_join_go;
    String result;
    MemberSQLiteOpenHelper helper;
    static final String DB_NAME = "member3.db";
    static final int DB_VERSION = 1;
    SQLiteDatabase database;
    private String data1;
    ArrayList<String> data;
    String gender;
    ArrayAdapter<String> adapter;

    private FirebaseAuth mAuth;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();



        String[] models = getResources().getStringArray(R.array.sizes);
        addr_spinner = findViewById(R.id.spinner);
        aSpinner = findViewById(R.id.spinner3);
        /*img_id = findViewById(R.id.img_id);
        img_pw = findViewById(R.id.img_pw);
        img_name = findViewById(R.id.img_name);
        img_age = findViewById(R.id.img_age);
        img_sex = findViewById(R.id.img_sex);
        img_addr = findViewById(R.id.img_addr);

        img_id.setImageResource(R.drawable.ic_launcher_person2_foreground);
        img_pw.setImageResource(R.drawable.ic_launcher_pw_foreground);
        img_name.setImageResource(R.drawable.ic_launcher_name_foreground);
        img_age.setImageResource(R.drawable.ic_launcher_age_foreground);
        img_sex.setImageResource(R.drawable.ic_launcher_sex_foreground);
        img_addr.setImageResource(R.drawable.ic_launcher_adr_foreground);*/


        ArrayAdapter age_adapter = ArrayAdapter.createFromResource(this, R.array.sizes, android.R.layout.simple_spinner_dropdown_item);
//        ArrayAdapter<String> age_adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, models);
        age_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        aSpinner.setAdapter(age_adapter);

        aSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 여기에 To Do
                sex = aSpinner.getSelectedItem().toString();
                Log.d("성별 : ", ":"+sex);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        addr_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addr = addr_spinner.getSelectedItem().toString();
                Log.d("주소 : ", ""+ addr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //Realtime database 부분
        String db_url = "https://finfooproject-default-rtdb.firebaseio.com/";
        // 파이어베이스 DB에 접근하는 객체(=Connection)
        FirebaseDatabase real_database = FirebaseDatabase.getInstance(db_url);

        //파이어베이스 DB에 저장된 특정 경로를 참조하는 객체
        DatabaseReference myRef = real_database.getReference("member");
        mAuth = FirebaseAuth.getInstance();

        helper = new MemberSQLiteOpenHelper(getApplicationContext(), DB_NAME, null, DB_VERSION);

        database = helper.getWritableDatabase(); //데이터베이스에 데이터를 읽고 쓰는 기능

        initView();

//        btn_age = findViewById(R.id.btn_age);

        Date today = new Date();      // birthday 버튼의 초기화를 위해 date 객체와 SimpleDataFormat 사용
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        result = dateFormat.format(today);




        //Date Fragment 관련 메소드 실행
        this.InitializeView();
        this.InitializeListener();

        btn_join_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = edt_join_id.getText().toString();
                String pw = edt_join_pw.getText().toString();
                String name = edt_join_fn.getText().toString();
                String age = edt_join_age.getText().toString();
                signUp();

                //파이어베이스에 url 저장
                // Create a new user with a first and last name
                Map<String, Object> user1 = new HashMap<>();
                user1.put("id", id);
                user1.put("pw", pw);
                user1.put("name", name);
                user1.put("age", age);
                user1.put("addr", addr);
                user1.put("gender", sex);

                // Add a new document with a generated ID
                db.collection("users")
                        .add(user1)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("데이터베이스 추가:", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("데이터베이스 추가실패:", "Error adding document", e);
                            }
                        });

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

                productRef1.collection("coco").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        String addr = document.getString("addr");
                                        String key = document.getId();
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.d(TAG, "Error getting subcollection.", task.getException());
                                }
                            }
                        });

                myRef.child("User").setValue(new User(id, pw, name, age, sex, addr));

                Intent login_intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(login_intent);
                finish();


            }
        });

        //RealTime Database 부분
        //접근한 경로에 데이터를 저장
        // 데이터베이스에 변화가 생겼을 때 실시간으로 동작하는 메소드
        myRef.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 데이터베이스에 변경된 데이터를 접근
                // whenever data at this location is updated.
                /*String value = dataSnapshot.getValue(String.class);*/
                //저장할 떄 chile()로 특정경로를 접근해서 저장한 경우
                /*String value = dataSnapshot.child("content").child("msg").getValue(String.class);*/
                /*String value = dataSnapshot.getValue(String.class)*/
//                User user = dataSnapshot.getValue(User.class);
//                Log.d("회원정보 실시간", ""+user);
//                Toast.makeText(JoinActivity.this, "회원가입에 성공했습니다." ,Toast.LENGTH_SHORT).show();
                /*Log.d(TAG, "Value is: " + value);*/
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void initView() {
        edt_join_fn = findViewById(R.id.edt_join_name);
        edt_join_id = findViewById(R.id.edt_join_id);
        edt_join_pw = findViewById(R.id.edt_join_pw);
        edt_join_age = findViewById(R.id.edt_join_age);
        btn_join_go = findViewById(R.id.btn_join_go);
        data = new ArrayList<>();
        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1, data);
    }

    public void InitializeView()
    {
//        edt_age = findViewById(R.id.edt_age);
    }

    public void InitializeListener()
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
//                edt_age.setText(result);
//                year + "년" + monthOfYear + "월" + dayOfMonth + "일"
            }
        };
    }

    public void OnClickHandler(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 2019, 5, 24);

        dialog.show();
    } //Age Date설정 끝


    private void signUp(){
        String id = edt_join_id.getText().toString();
        String password=edt_join_pw.getText().toString();

        if(id.length()>0 && password.length()>0){
            if(password.equals(password)){
                mAuth.createUserWithEmailAndPassword(id, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(JoinActivity.this, "회원가입에 성공했습니다." ,Toast.LENGTH_SHORT).show();
                                    Log.d("회원가입 정보 :", ","+id + "," + password);
                                } else {
//                                    if(task.getException().toString() !=null){
                                        Toast.makeText(JoinActivity.this, "회원가입에 실패했습니다." ,Toast.LENGTH_SHORT).show();
                                        Log.d("회원가입 정보 :", ","+id + "," + password);
//                                    }
                                }
                            }
                        });
            }
            else{
                Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다." ,Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(JoinActivity.this, "아아디와 비밀번호를 확인해주세요." ,Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("signUp", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("signUp", "signInWithCredential:failure", task.getException());
                        }
                        // ...
                    }
                });
    }
}



package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class ImageActivity extends AppCompatActivity {
    ImageView image1;
    Button searchImage, btn_image_send;

    EditText edt_url;
    private String imageName;
    TextView send_textView, read_textView;







    Drawable tempImg;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String addr = "";
    String document_id = "";
    String [] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    Uri uri;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    RequestQueue requestQueue;

    //TCP/IP 통신부분
    private Socket client;
    private DataOutputStream dataOutput;
    private DataInputStream dataInput;
    private static String SERVER_IP = "192.168.128.1";
    private static String CONNECT_MSG = "connect";
    private static String STOP_MSG = "stop";
    private static int BUF_SIZE = 100;
    String image_url = "";
    String email = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        image1 = findViewById(R.id.selectedImage2);
        searchImage = findViewById(R.id.searchImage);
        edt_url = findViewById(R.id.edt_image_url);
        btn_image_send = findViewById(R.id.btn_image_send);
        image1.setImageResource(R.drawable.camera);
        send_textView = findViewById(R.id.send_textView);
        read_textView = findViewById(R.id.read_textView);
        tempImg = image1.getDrawable();

        Intent secondIntent = getIntent();
        email = secondIntent.getStringExtra("id");

        //권한 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 0);
        }

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        // 요소 초기화
//        selectedImage = findViewById(R.id.selectedImage);
//        searchImage = findViewById(R.id.searchImage);
        image1 = findViewById(R.id.selectedImage2);
        searchImage = findViewById(R.id.searchImage);
        edt_url = findViewById(R.id.edt_image_url);
        btn_image_send = findViewById(R.id.btn_image_send);
        image1.setImageResource(R.drawable.camera);
        send_textView = findViewById(R.id.send_textView);
        read_textView = findViewById(R.id.read_textView);
        tempImg = image1.getDrawable();

        Intent second_Intent = getIntent();
        email = second_Intent.getStringExtra("id");

        //권한 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 0);
        }

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());



        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

//                                document_id = document.getId();
                                addr = document.getString("id");
                                Log.d("id는요", email + "/" + addr);


//                                                if(addr.equals(email)) {
//                                                  Log.d(TAG, document.getId() + " => " + document.getData());

                                Log.d("받아온 데이터 :", document.getId() + " => " + addr);
//                                                } else {
                                Log.d("받아오기 실패1", "");
//                                                }
                            }
                        } else {
                            Log.w("받아오기실패", "Error getting documents.", task.getException());
                        }
                    }
                });







        //서버로 보내기 버튼 클릭시 동작
            btn_image_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    dataSend();

                /*Connect connect = new Connect();
                connect.execute(CONNECT_MSG);*/

                    }



            });
    }









    //여기부터 옮겨갔음
    private void dataSend() {
        String path = getImagePathToUri(uri);

        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
        Log.d("riversRef : ", riversRef.getPath());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

        String data1 = path.replaceAll("/storage/emulated/0/Pictures/|/storage/emulated/0/Download/", "");
//                        String data2 = path.replace("/storage/emulated/0/Download/", "");
        Log.d("저장된 파일 경로 :", path);
        Log.d("data1 : ", data1);
//                        /storage/emulated/0/Download/


        storageRef.child("images/" + data1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                image_url = uri.toString();
                Log.d("처음받아온 image_url", image_url);

                // Got the download URL for 'users/me/profile.png'
                Log.d("받아온 url : ", "" + uri);


                Log.d("imgae_url : ", image_url);

                //파이어베이스에 url 저장
                // Create a new user with a first and last name
                Map<String, Object> user1 = new HashMap<>();

                user1.put("id", email);
                user1.put("url", image_url);

                Log.d("넣었냐?", image_url);

                // Add a new document with a generated ID
                db.collection("imageurl")
                        .add(user1)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                document_id = documentReference.getId();
                                Log.d("데이터베이스 추가:", "DocumentSnapshot added with ID: " + documentReference.getId());

                                Connect connect = new Connect();
                                connect.execute(CONNECT_MSG);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("데이터베이스 추가실패:", "Error adding document", e);
                            }
                        });


//                        String data1 = image_url.replace("&", "asdf");
//                        String data2 = data1.replace("%", "zxcv");
//                        Log.d("& 제거", data1);
//                        Log.d("% 제거", data2);
//                        edt_url.setText(image_url);
                /*try {
                    JSONObject a = buidJsonObject();
                    String b = a.getString("user");
                    Log.d("플라스크에서 받아온 이미지 url : ", b);


                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void getImageBtn(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 1);

        /*searchImage.setVisibility(View.INVISIBLE);
        btn_image_send.setVisibility(View.VISIBLE);*/


    }

    //플라스크에서 결과 이미지url 받아오기
    /*private JSONObject buidJsonObject() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageInByte = baos.toByteArray();
        String img_array = Base64.encodeToString(imageInByte, Base64.DEFAULT);

//        Log.d("플라스크에서 받아온 이미지 url : ", a);
        // String img_array = new String(imageInByte);
        try {
            baos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        jsonObject.accumulate("user",img_array);

        return jsonObject;
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try{
            // 사진을 선택하고 왔을 때만 처리한다.
            if(resultCode == RESULT_OK){
                // 선택한 이미지를 지칭하는 Uri 객체를 얻어온다.
                uri = data.getData();
                // Uri 객체를 통해서 컨텐츠 프로바이더를 통해 이미지의 정보를 가져온다.
                ContentResolver resolver = getContentResolver();
                Cursor cursor = resolver.query(uri, null, null, null, null);
                cursor.moveToNext();

                // 사용자가 선택한 이미지의 경로 데이터를 가져온다.
                int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                String source = cursor.getString(index);

                // 경로 데이터를 통해서 이미지 객체를 생성한다
                Bitmap bitmap = BitmapFactory.decodeFile(source);

                // 이미지의 크기를 조정한다.
                Bitmap bitmap2 = resizeBitmap(1024, bitmap);

                // 회전 각도 값을 가져온다.
                float degree = getDegree(source);
                Bitmap bitmap3 = rotateBitmap(bitmap2, degree);

                image1.setImageBitmap(bitmap3);
                Log.d("이미지 url", "d"+uri);

                // 여기부터 jsp 전송구간
                Toast.makeText(getBaseContext(), "resultCode : " + data, Toast.LENGTH_SHORT).show();


            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Bitmap resizeBitmap(int targetWith, Bitmap source){
        double ratio = (double)targetWith / (double)source.getWidth();

        int targetHeight = (int)(source.getHeight() * ratio);

        Bitmap result = Bitmap.createScaledBitmap(source, targetWith, targetHeight, false);

        if(result != source){
            source.recycle();
        }
        return result;
    }

    public float getDegree(String source){
        try{
            ExifInterface exif = new ExifInterface(source);

            int degree = 0;

            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            switch (ori){
                case ExifInterface.ORIENTATION_ROTATE_90 :
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180 :
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270 :
                    degree = 270;
                    break;
            }
            return (float)degree;
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0.0f;
    }

    public Bitmap rotateBitmap(Bitmap bitmap, float degree){
        try{

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            Matrix matrix = new Matrix();
            matrix.postRotate(degree);

            Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            bitmap.recycle();


            return bitmap2;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }// 갤러리에서 이미지 선택해서 이미지뷰에 듸우기

    public String getImagePathToUri(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴
        String[] proj = {MediaStore.Images.Media.DATA};

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(data, null, null, null, null);
        cursor.moveToNext();


        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);
        Log.d("test", imgPath);

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        Toast.makeText(ImageActivity.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
        this.imageName = imgName;

//        DoFileUpload("http://localhost:8085/AndroidServer/FileReceive.jsp", imgPath);

        return imgPath;
    }

    private class Connect extends AsyncTask< String , String,Void > {
        private String output_message;
        private String input_message;

        @Override
        protected Void doInBackground(String... strings) {
            try {
                client = new Socket(SERVER_IP, 8086);
                dataOutput = new DataOutputStream(client.getOutputStream());
                dataInput = new DataInputStream(client.getInputStream());
//                output_message = strings[0];
                output_message = " a " + email + "/" + document_id;
                Log.d("document_id 어디갔냐", document_id);


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
                    byte[] buf = new byte[BUF_SIZE];
                    int read_Byte  = dataInput.read(buf);
                    input_message = new String(buf, 0, read_Byte);
                    if (!input_message.equals(STOP_MSG)){
                        publishProgress(input_message);
                    }
                    else{
                        break;
                    }
                    Thread.sleep(2);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... params){
            send_textView.append("보낸 메세지: " + output_message );
            read_textView.append("받은 메세지: " + params[0]);
            Log.d("보낸 메세지: " , output_message);
            Log.d("받은 메세지: " , params[0]);

            Intent result_intent = new Intent(getApplicationContext(), SearchResultActivity.class);
            result_intent.putExtra("data", params[0]);
            result_intent.putExtra("email", email);
            startActivity(result_intent);
            image1.setImageResource(R.drawable.camera);
            /*send_textView.setText(""); // Clear the chat box
            send_textView.append("보낸 메세지: " + output_message );
            read_textView.setText(""); // Clear the chat box
            read_textView.append("받은 메세지: " + params[0]);*/
        }
    }
}

























//나머지부분
       /* selectedImage.setImageResource(R.drawable.camera);
        tempImg = selectedImage.getDrawable();

        // 그림을 클릭하면 갤러리에 접근하도록 하는 코드.
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
//              intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });*/


        /*// 현재 선택된 그림을 검색하는 버튼을 눌렀을때의 코드. Drawable을 Bitmap으로 전환한뒤 다시 한번 인코딩을 한다.
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                bm = BitmapFactory.decodeResource(getResources(), R.drawable.testimage);
//                String encodedTemp = BitmapToString(bm);
//                Log.d("인코딩 결과: ", encodedTemp);

                // 서버에 요청 보내는 requestQuest 객체 생성
                requestQueue = Volley.newRequestQueue(getApplicationContext());

                // 플라스크 주소
                String deepModelURL = "http://127.0.0.1:5000/";
                String google = "https://www.google.co.kr/";

                // 본격적으로 요청
                StringRequest requestServer = new StringRequest(
                        Request.Method.POST,
                        google,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("요청", "성공");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("요청", "실패");
                            }
                        }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("data", encodedTemp);
                        params.put("data", "TTTTTEST");
                        return params;
                    }
                };

                requestQueue.add(requestServer);
            }
        });



        // 이미지 접근 권한
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR);

        if(permissionCheck== PackageManager.PERMISSION_DENIED){
            Log.d("권한?", "yes");
        }else{
            Log.d("권한?", "no");
        }


    }


    // 인텐트 결과 정의
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 크롭된 이미지를 클릭했을때의 코드
        if (requestCode == 1 && resultCode == RESULT_OK) {

                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지뷰에 세팅
                    selectedImage.setImageBitmap(img);

                } catch (Exception e) {
                    e.printStackTrace();
                }

        }

    }

    // Bitmap을 String으로 전환하는 코드. Base64 압축을 사용한다.
    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);
        return encodedImage;
    }

    // Bitmap을 Jpeg로 저장하는 코드. 일단 가져오기만 함.
    public static String saveBitmapToJpeg(Context context, Bitmap bitmap){

        File storage = context.getCacheDir(); // 이 부분이 임시파일 저장 경로
        String fileName = "TEST.jpg"; // 파일이름은 마음대로!
        File tempFile = new File(storage,fileName);

        try{
            tempFile.createNewFile(); // 파일을 생성해주고
            FileOutputStream out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90 , out); // 넘거 받은 bitmap을 jpeg(손실압축)으로 저장해줌
            out.close(); // 마무리로 닫아줍니다.

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile.getAbsolutePath(); // 임시파일 저장경로를 리턴해주면 끝!
    }*/




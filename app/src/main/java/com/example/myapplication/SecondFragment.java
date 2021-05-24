package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class SecondFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    ImageView selectedImage;
    Button btn_searchImage, btn_resultimage;
    Drawable tempImg;
    Bitmap bm;
    RequestQueue requestQueue;
    int REQUEST_TAKE_ALBUM = 5000;
    private static final String TEMP_FOOD_IMAGE_NAME = "tempfoodimage.jpg";
    private Uri mTempImageUri;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String addr = "";
    String document_id = "";
    String [] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    Uri uri;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    String image_url = "";
    String email = "";
    private static String CONNECT_MSG = "connect";
//    private DataOutputStream dataOutput;

    /* private static String SERVER_IP = "192.168.128.1";
     private static String CONNECT_MSG = "connect";
     private static String STOP_MSG = "stop";
     private static int BUF_SIZE = 100;
     String image_url = "";
     String email = "";*/
    private String imageName;
    //    Connect connect = new Connect();
    int cnt = 0;





    // newInstance constructor for creating fragment with arguments
    public static SecondFragment newInstance(int page, String title) {
        SecondFragment fragment = new SecondFragment();
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
        if (page != 0) {
            page = getArguments().getInt("someInt", 0);
            title = getArguments().getString("someTitle");
        }

        try{
            EventBus.getDefault().register(this);
        }catch (Exception e){}






    }

    // Inflate the view for the fragment based on layout XML
    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        // 요소 초기화. view를 통해 접근해야 id 찾기가 가능.
        selectedImage = view.findViewById(R.id.selectedImage);
        btn_searchImage = view.findViewById(R.id.btn_searchImage);
        btn_resultimage = view.findViewById(R.id.btn_resultimage);
        selectedImage.setImageResource(R.drawable.camera);
        tempImg = selectedImage.getDrawable();

        /*Intent second_intent = getActivity().getIntent();
        email = second_intent.getStringExtra("id");*/




        Bundle bundle = getArguments(); //번들 안의 텍스트 불러오기
        email = bundle.getString("id");
        Log.d("2번째 email 데이터 프래그먼트 받아오기 : ", ""+ email);

        /*bundle*/

       /* Bundle bundle = this.getArguments();
        if (getArguments() != null) {
            email = bundle.getString("id");
            Log.d("2번째 email 데이터 프래그먼트 받아오기 : ", ""+email);
        }*/




        //권한 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 0);
        }

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());



        // 그림을 클릭하면 갤러리에 접근하도록 하는 코드.
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 갤러리에 접근
                getAlbum();
            }
        });

        btn_searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlbum();
//                Connect connect = new Connect();

            }
        });

        btn_resultimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataSend();

            }
        });

        return view;
    }

    // 인텐트 결과 정의
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 이미지 크롭으로 연결하는 코드
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            uri = data.getData();
            Log.d("크롭", "크롭 사진 받기 성공");

            // 크롭된 이미지 받아오는 코드
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri resultUri = result.getUri();
            selectedImage.setImageURI(resultUri);
        } else if (requestCode == REQUEST_TAKE_ALBUM && resultCode ==RESULT_OK) {
            getCrop(data.getData());
        }

    }

    // 이미지 크롭하는 코드
    public void getCrop(Uri uri) {
        CropImage.activity(uri).start(getContext(), this);
    }

    // 갤러리에 접근하는 코드
    public void getAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        intent.putExtra("crop", true);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

































    //여기부터 ImageActivity에서 가져온 코드

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
                db.collection("users")
                        .add(user1)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                document_id = documentReference.getId();
                                Log.d("데이터베이스 추가:", "DocumentSnapshot added with ID: " + documentReference.getId());

//                               Connect connect = new Connect();
//
                                Connect connect = new Connect();
                                connect.execute(CONNECT_MSG);


//                                new Connect().execute(CONNECT_MSG);

//                                new Connect().cancel(true);




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
        String[] proj = {MediaStore.Images.Media.DATA};

        ContentResolver resolver = getActivity().getContentResolver();
        Cursor cursor = resolver.query(data, null, null, null, null);
        cursor.moveToNext();


        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);

        Log.d("test", imgPath);

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
//        Toast.makeText(ImageActivity.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
        this.imageName = imgName;

//        DoFileUpload("http://localhost:8085/AndroidServer/FileReceive.jsp", imgPath);

        return imgPath;
    }

    private class Connect extends AsyncTask< String , String,Void > {
        private String output_message;
        private String input_message;
        private DataOutputStream dataOutput;
        private DataInputStream dataInput;
        private String SERVER_IP = "192.168.128.1";
        private String STOP_MSG = "stop";
        private int BUF_SIZE = 100;
        private Socket client;





        @Override
        protected Void doInBackground(String... strings) {

            try {
                client = new Socket(SERVER_IP, 8086);
                dataOutput = new DataOutputStream(client.getOutputStream());
                dataInput = new DataInputStream(client.getInputStream());
//                output_message = strings[0];
                output_message = "1"+ "/" + email + "/" + document_id;
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
            /*send_textView.append("보낸 메세지: " + output_message );
            read_textView.append("받은 메세지: " + params[0]);*/
            Log.d("보낸 메세지: " , output_message);
            Log.d("받은 메세지: " , params[0]);

            Intent result_intent = new Intent(getActivity(), SearchResultActivity.class);
            result_intent.putExtra("data", params[0]);
            result_intent.putExtra("email", email);
            startActivity(result_intent);
            selectedImage.setImageResource(R.drawable.camera);

            /*send_textView.setText(""); // Clear the chat box
            send_textView.append("보낸 메세지: " + output_message );
            read_textView.setText(""); // Clear the chat box
            read_textView.append("받은 메세지: " + params[0]);*/
        }



    }

}
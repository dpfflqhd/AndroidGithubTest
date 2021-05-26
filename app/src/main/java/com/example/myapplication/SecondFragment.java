package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.os.Handler;
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
import com.google.android.gms.maps.model.LatLng;
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
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class SecondFragment extends Fragment {


    private Context context;
    private String title;
    private int page;

    // 전역 변수 선언
    ImageView selectedImage;
    Button btn_searchImage, btn_resultimage;
    Drawable tempImg;
    Bitmap bm;
    RequestQueue requestQueue;
    int REQUEST_TAKE_ALBUM = 5000;
    int REQUEST_CODE_PROFILE_IMAGE_PICK = 5001;
    private static final String TEMP_FOOD_IMAGE_NAME = "tempfoodimage.jpg";
    private Uri mTempImageUri;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String addr = "";
    String document_id = "";
    String [] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    Uri uri;
    Uri resultUri;
    String imageName;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    String image_url = "";
    String email = "";
    private static String CONNECT_MSG = "connect";
    Intent result_intent;
    String audio = "";

    String[] receive_split;
//    private DataOutputStream dataOutput;

   /* private static String SERVER_IP = "192.168.128.1";
    private static String CONNECT_MSG = "connect";
    private static String STOP_MSG = "stop";
    private static int BUF_SIZE = 100;
    String image_url = "";
    String email = "";*/
//    Connect connect = new Connect();
    int cnt = 0;





    // newInstance constructor for creating fragment with arguments
    public static SecondFragment newInstance(int page, String title) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        Log.d("secondefrag newIns", title + page);
        fragment.setArguments(args);



        return fragment;


    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Inflate the view for the fragment based on layout XML
    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        context = container.getContext();


        // 요소 초기화. view를 통해 접근해야 id 찾기가 가능.
        selectedImage = view.findViewById(R.id.selectedImage);
        btn_searchImage = view.findViewById(R.id.btn_searchImage);
        btn_resultimage = view.findViewById(R.id.btn_resultimage);
        selectedImage.setImageResource(R.drawable.camera);
        tempImg = selectedImage.getDrawable();


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

        //이미지 검색 버튼 클릭시 앨범으로 이동
        btn_searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlbum();
            }
        });

        //결과 확인 버튼 클릭시 결과페이지로 이동
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
            resultUri = data.getData();
            Log.d("크롭", "크롭 사진 받기 성공");

            // 크롭된 이미지 받아오는 코드
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            resultUri = result.getUri();
            selectedImage.setImageURI(resultUri);
            Log.d("resultUri : ", resultUri.toString());

        } else if (requestCode == REQUEST_TAKE_ALBUM && resultCode ==RESULT_OK) {
            getCrop(data.getData());
            resultUri = data.getData();
            uri = resultUri;
            Log.d("onActivit을때 uri", uri.toString());
        }
    }

    // 이미지 크롭하는 코드
    public void getCrop(Uri uri) {
        CropImage.activity(uri).start(getContext(), this);
    }

    // 갤러리에 접근하는 코드
    public void getAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        intent.putExtra("crop", true);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }


    //이미지의 경로를 구한 뒤 데이터베이스에 저장한다
    private void dataSend() {
        String path = getImagePathToUri(uri);
        Log.d("uri dataSend", resultUri.toString());

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
        Log.d("저장된 파일 경로 :", path);
        Log.d("data1 : ", data1);

        storageRef.child("images/" + data1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                image_url = uri.toString();
                Log.d("받아온 url : ", image_url);


                //파이어베이스에 url 저장
                Map<String, Object> user1 = new HashMap<>();
                email = title;
                Log.d("secondfragment title ", title);
                user1.put("id", email);
                user1.put("url", image_url);
                Log.d("id + url 데이터베이스 저장", email + "/" + image_url);

                //파이베이스 imageurl 컬렉션에 데이터 넣기
                db.collection("imageurl")
                        .add(user1)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                document_id = documentReference.getId();
                                Log.d("데이터베이스 추가:", "DocumentSnapshot added with ID: " + documentReference.getId());

                                //Connect 함수를 통해 파이썬과 안드로이드 통신 GO !!
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
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }


    //이미지의 경로값을 가져온다~
    public String getImagePathToUri(Uri data) {

        String[] proj = {MediaStore.Images.Media.DATA};
        Log.d("받아온 데이터", data.toString());
        ContentResolver resolver = context.getContentResolver();

        //Cursor query문을 통해서 안드로이드 앱 내에 저장되어 있는 이미지의 경로 값을 얻어온다. (데이터베이스 쿼리문과 유사)
        Cursor cursor = resolver.query(data, null, null, null, null);
        cursor.moveToNext();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        //이미지의 경로 값 imgPath에 저장
        String imgPath = cursor.getString(column_index);
        Log.d("test", imgPath);

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        this.imageName = imgName;

        return imgPath;
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
                client = new Socket(SERVER_IP, 8088);
                dataOutput = new DataOutputStream(client.getOutputStream());
                dataInput = new DataInputStream(client.getInputStream());

//                output_message = strings[0];

                //output_message는 파이썬으로 보낼 데이터, a는 a,b,c 로 이미지 분류모델, 음악파일, 추천분류 모델을 구분하기 위해서 넣음
                //ex) a 면 파이썬에서 이미지 분류~ b 면 음악파일~ c면 추천분류~
                //이전 코드를 보면 파이어베이스에 imageurl 이란 컬렉션에 id 와 url 을 저장했다.
                //컬렉션 : 데이터베이스 이름, 문서(document) : 테이블 이름, id, url : 변수 이름
                //파이썬으로 id 와 문서이름을 보낸다. 파이썬에서는 document_id 로 테이블을 찾고 받은 id와 일치하는 변수 값을 찾는다.

                //정리해보면 안드로이드에서 id와 url을 저장하고 document값을 파이썬으로 보냄
                //파이썬에서는 document값과 id를 기반으로 특정 테이블을 찾고 그 url을 가져온다.
                //그 url을 가지고 이미지 파일을 저장한 다음 음식분류 모델을 돌린다.
                //분류가 끝나면 해당 음식이름을 안드로이드로 return한다.
                //안드로이드에서는 그 음식이름을 기반으로 결과페이지에서 음식점 정보를 가져와 뿌려준다.
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
                    Log.d("buf", buf.toString());
                    int read_Byte  = dataInput.read(buf);
                    input_message = new String(buf, 0, read_Byte);

                    if (!input_message.equals(STOP_MSG)){
                        publishProgress(input_message);
                    } else{
                        break;
                    }

                    String path = "/storage/emulated/0/Download/";
                    //path에는 "sdcard/ImageList/" 와 같은 값이 들어갑니다.
                    // 2. 경로를 이용해 File 객체를 생성한다.
                    File list = new File(path);
                    //3. list객체에서 이미지목록만 추려 낸다.

                    String[] fileList = list.list(new FilenameFilter() {
                        public boolean accept(File dir, String filename) {
                            Boolean bOK = false;
                            if(filename.toLowerCase().endsWith(".mp3")) bOK = true;
                            if(filename.toLowerCase().endsWith(".mp4")) bOK = true;
                            return bOK;
                        }
                    });

                    for(int i = 0; i < fileList.length; i++){
                        Log.d("fileList", fileList[i]);
                    }

                    List<String> userAudioList = new ArrayList<String>();
                    String test = "";
                    String tes = email.replace("@gmail.com", ""); //test20

                    int test_int = 0;
                    //만약에 hello.mp3, test20_1.mp3, test20_2.mp3, test20_3.mp3 가 저장되어 있다면 = fileList
                    //fileList에 있는 파일 중 사용자 아이디와 일치하는 파일만을 추출해서 userAudioList에 저장

                    if(fileList[0] != null) {
                        for (int i = 0; i < fileList.length; i++) {
                            if (fileList[i].contains(tes)) {
                                test = fileList[i];
                            }
                            if (test != null) {
                                userAudioList.add(test);
                            } else {
                                userAudioList.add("a");
                            }
                        }
                    }

                    if(userAudioList != null && !userAudioList.isEmpty()){
                        for(int i = 0; i < userAudioList.size(); i++){
                            Log.d("userAudioList", "" + userAudioList.get(i));
                        }
                    } else {
                        Log.d("userAudioList is ", "null");
                    }
                    //userAudioList에는 test20_1.mp3, test20_2.mp3, test20_3.mp3 파일이 저장되어 있다.
                    //여기서 숫자 1,2,3 만을 추출하여 서로를 비교하고 가장 높은 숫자를 저장한다.

                    int audio_replace_int = 0;
                    String audio_replace = "";

                    if(userAudioList != null && !userAudioList.isEmpty()){
                        for (int i = 0; i < userAudioList.size(); i++) {
                            //test20_1.mp3, test20_2.mp3, test20_3.mp3 or 아무 파일이 없을떄
                            //audio_cnt = 1,2,3
                            String audi = email.replace("@gmail.com","");
                            Log.d("audi", ""+audi);

                            Log.d("user audi", ""+userAudioList.get(i));

                            String audi_user = userAudioList.get(i);
                            audio_replace = audi_user.replace(audi + "_", "").replace(".mp3", "").replace(" ","");
                            Log.d("audio_replace string", ""+audio_replace);

                            if(!audio_replace.equals("")){
                                int audio_cnt = Integer.parseInt(audio_replace);
                                Log.d("audio_cnt", ""+audio_cnt);
                                if(audio_cnt > test_int){
                                    test_int = audio_cnt;
                                }
                            }
                        }
                    } else {
                        Log.d("audio_replace is ", "null");
                    }

                    if(audio_replace != null){
                        audio_replace_int = Integer.parseInt(audio_replace);
                        Log.d("a = ", ""+String.valueOf(audio_replace_int));
                    }


                    Log.d("receive 확인", receive_split[0]);
                    Log.d("receive 1확인", receive_split[1]);

                    audio = tes + "_" + String.valueOf(test_int + 1);
                    Log.d("audio", audio);

                    result_intent= new Intent(getActivity(), SearchResultActivity.class);
                    result_intent.putExtra("data", receive_split[0]);
                    result_intent.putExtra("acc", receive_split[1]);
                    result_intent.putExtra("email", email);
                    result_intent.putExtra("audio", audio);
                    startActivity(result_intent);

                    File file = new  File("/storage/emulated/0/Download/"+ audio +".mp3");

                    InputStream is = client.getInputStream();
                    FileOutputStream fos = new FileOutputStream(file);

                    int readBytes;
                    byte[] buffer = new byte[1024];
                    while ((readBytes = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, readBytes);
                        Log.d("fos write", fos.toString());
                    }





                    is.close();
                    fos.close();

                    Thread.sleep(10);




                } catch (IOException | InterruptedException e) {
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
            Log.d("receive_split[0]", receive_split[0]);
            Log.d("receive_split[1]", receive_split[1]);
            Log.d("secondfrag audio", audio);




            selectedImage.setImageResource(R.drawable.camera);
        }
    }
}
package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GalleryTestActivity extends AppCompatActivity {

    String [] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    ImageView image1;
    private String imageName;
    private Button btn_send;
    Uri uri;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    RequestQueue requestQueue;
    EditText edt_url;

    //TCP/IP 통신부분
    private Socket client;
    private DataOutputStream dataOutput;
    private DataInputStream dataInput;
    private static String SERVER_IP = "192.168.128.1";
    private static String CONNECT_MSG = "connect";
    private static String STOP_MSG = "stop";
    private static int BUF_SIZE = 100;
    String image_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_test);

        btn_send = findViewById(R.id.btn_send);

        image1 = (ImageView)findViewById(R.id.image1);
        edt_url = findViewById(R.id.edt_url);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permission_list, 0);
        }

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        //플라스크로 보내기 Volley
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String path = getImagePathToUri(uri);


                Uri file = Uri.fromFile(new File(path));
                StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
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

                String data1 = path.replace("/storage/emulated/0/Pictures/", "");
                Log.d("저장된 파일 경로 :", path);


                storageRef.child("images/"+data1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Log.d("받아온 url : ", ""+uri);

                        image_url = uri.toString();
                        Log.d("imgae_url : ", image_url);

                        String data1 = image_url.replace("&", "asdf");
                        String data2 = data1.replace("%", "zxcv");
                        Log.d("& 제거", data1);
                        Log.d("% 제거", data2);

                        edt_url.setText(image_url);

                        Connect connect = new Connect();
                        connect.execute(CONNECT_MSG);

                        String server_url = "http://172.22.0.1:9000?rs="+ data2;
//                        String server_url = "http://172.22.0.1:8085/AndroidServer/AndroidTest3?rs="+ data2;

                        /*StringRequest request = new StringRequest(
                                Request.Method.GET,
                                server_url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {


                                        Toast.makeText(GalleryTestActivity.this, "성공", Toast.LENGTH_SHORT).show();

                                    }
                                },

                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(GalleryTestActivity.this, "실패", Toast.LENGTH_SHORT).show();
                                        Log.d("확인 : ", "ㅇ");
                                    }
                                }

                        )
                        {
                            protected Map<String, String> getParams() throws AuthFailureError {
                                //POST방식으로 데이터 전송 시 이 메소드를 통해서 전송
                                Map<String, String> params = new HashMap<String, String>();

                                //put(key값, value 값) --> url?key=value로 변환되어 전송됨

                                params.put("data", "test"); //url?data=Hello




                                return params;
                            }
                        };*/

                        /*requestQueue.add(request);*/

                        try {
                            JSONObject a = buidJsonObject();
                            String b = a.getString("user");
                            Log.d("플라스크에서 받아온 이미지 url : ", b);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });









            }
        });



    }

    //플라스크에서 결과 이미지url 받아오기
    private JSONObject buidJsonObject() throws JSONException {

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
    }
    
    



    public void getImageBtn(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 1);
    }



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
        Toast.makeText(GalleryTestActivity.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
        this.imageName = imgName;

//        DoFileUpload("http://localhost:8085/AndroidServer/FileReceive.jsp", imgPath);

        return imgPath;
    }


    public void HttpFileUpload(String urlString, String params, String fileName) {
        String lineEnd = "\r\n";
        String boundary = "androidupload";
        File targetFile = null;
        String twoHyphens = "--";

        try {

            FileInputStream mFileInputStream = new FileInputStream(fileName);
            URL connectUrl = new URL(urlString);
            Log.d("Test", "mFileInputStream  is " + mFileInputStream);

            // HttpURLConnection 통신
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            // write data
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            int bytesAvailable = mFileInputStream.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            byte[] buffer = new byte[bufferSize];
            int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

            Log.d("Test", "image byte is " + bytesRead);

            // read image
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = mFileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            Log.e("Test", "File is written");
            mFileInputStream.close();
            dos.flush();
            // finish upload...

            // get response
            InputStream is = conn.getInputStream();

            StringBuffer b = new StringBuffer();
            for (int ch = 0; (ch = is.read()) != -1; ) {
                b.append((char) ch);
            }
            is.close();
            Log.e("Test", b.toString());

        } catch (Exception e) {
            Log.d("Test", "exception " + e.getMessage());
            // TODO: handle exception
        }
    } // end of HttpFileUpload()

    private class Connect extends AsyncTask< String , String,Void > {
        private String output_message;
        private String input_message;

        @Override
        protected Void doInBackground(String... strings) {
            try {
                client = new Socket(SERVER_IP, 8084);
                dataOutput = new DataOutputStream(client.getOutputStream());
                dataInput = new DataInputStream(client.getInputStream());
//                output_message = strings[0];
                output_message = image_url;
                dataOutput.writeUTF(output_message);

            } catch (UnknownHostException e) {
                String str = e.getMessage().toString();
//                Log.w("discnt", str + " 1");
            } catch (IOException e) {
                String str = e.getMessage().toString();
//                Log.w("discnt", str + " 2");
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
            Log.d("보낸 메세지: " , output_message);
            Log.d("받은 메세지: " , params[0]);

            /*send_textView.setText(""); // Clear the chat box
            send_textView.append("보낸 메세지: " + output_message );
            read_textView.setText(""); // Clear the chat box
            read_textView.append("받은 메세지: " + params[0]);*/
        }
    }
}
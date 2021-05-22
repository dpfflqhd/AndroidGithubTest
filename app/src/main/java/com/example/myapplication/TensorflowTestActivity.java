package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TensorflowTestActivity extends AppCompatActivity {

    private static final int FROM_ALBUM = 1;    // onActivityResult 식별자
    private static final int FROM_CAMERA = 2;   // 카메라는 사용 안함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tensorflow_test);

        // 인텐트의 결과는 onActivityResult 함수에서 수신.
        // 여러 개의 인텐트를 동시에 사용하기 때문에 숫자를 통해 결과 식별(FROM_ALBUM 등등)
        findViewById(R.id.button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");                      // 이미지만 image/*
                intent.setAction(Intent.ACTION_GET_CONTENT);    // 카메라(ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, FROM_ALBUM);
            }
        });


    }

    private Interpreter getTfliteInterpreter(String modelPath) {
        try {
            return new Interpreter(loadModelFile(TensorflowTestActivity.this, modelPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public MappedByteBuffer loadModelFile(Activity activity, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    // 사진첩에서 사진 파일 불러와 버섯종류분류하는 코드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 카메라를 다루지 않기 때문에 앨범 상수에 대해서 성공한 경우에 대해서만 처리
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != FROM_ALBUM || resultCode != RESULT_OK)
            return;

        //각 모델에 따른 input , output shape 각자 맞게 변환
        // mobilenetcheck.h5 일시 224 * 224 * 3
        float[][][][] input = new float[1][224][224][3];
        float[][] output = new float[1][50]; //tflite에 버섯 종류 5개라서 (내기준)

        try {
            int batchNum = 0;
            InputStream buf = getContentResolver().openInputStream(data.getData());
            Bitmap bitmap = BitmapFactory.decodeStream(buf);
            buf.close();

            //이미지 뷰에 선택한 사진 띄우기
            ImageView iv = findViewById(R.id.imageView_1);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageBitmap(bitmap);

            // x,y 최댓값 사진 크기에 따라 달라짐 (조절 해줘야함)
            for (int x = 0; x < 224; x++) {
                for (int y = 0; y < 224; y++) {
                    int pixel = bitmap.getPixel(x, y);
                    input[batchNum][x][y][0] = Color.red(pixel) / 1.0f;
                    input[batchNum][x][y][1] = Color.green(pixel) / 1.0f;
                    input[batchNum][x][y][2] = Color.blue(pixel) / 1.0f;
                }
            }

            // 자신의 tflite 이름 써주기
            Interpreter lite = getTfliteInterpreter("converted_model.tflite");
            lite.run(input, output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView tv_output = findViewById(R.id.textView_1);
        int i;

        // 텍스트뷰에 무슨 버섯인지 띄우기 but error남 ㅜㅜ 붉은 사슴뿔만 주구장창
        for (i = 0; i < 50; i++) {
            if (output[0][i] * 100 > 90) {
//                if (i == 0) {
//                    tv_output.setText(String.format("개나리 광대버섯  %d %.5f", i, output[0][0] * 100));

                float[] data2 = new float[50];
                /*Arrays.toString(output[0]);*/


                /*data2[i] = output[0];*/
                Log.d("텐서플로우 결과 : ", i+"번째//정확도 : "+ data2[i]);
/*
                String[] categories = ["baleut","bulgogi","burger","chik","cho","coco","dag","dang","don",
                        "dongbaek","fish","galbi","go","gwang","han","hang","hong",
                        "hwang","jamami","jin","jingal","kagawa","kao","kor","kum","mali",
                        "mama","man","mok","moon","mul","nak","oct","pong","pyeon","red",
                        "sakana","sinbul","sirae","snake","snoopy","sot","spa","sukju",
                        "theflower","two","vivi","yasmaru","zerk","zuk"]

                List<Integer> values = Arrays.asList(1, 2, 3);*/

                /*for(i=0; i <=50; i++){
                    Collections.max(values);
                    Collections.max()*100;
                }

                List<Integer> values1 = Arrays.asList(100, 22, 14);
                int min = Collections.min(values); // 14
                int max = Collections.max(values); // 100*/



//                    Log.d("텐서플로우 결과 : ", data2[i]);
//                }
            /*else if (i == 1) {
                    tv_output.setText(String.format("붉은사슴뿔버섯,%d  %.5f", i, output[0][1] * 100));
                    Log.d("텐서플로우 결과2 : ", String.format("붉은사슴뿔버섯  %d %.5f", i, output[1][0] * 100));

                } else if (i == 2) {
                    tv_output.setText(String.format("새송이버섯,%d, %.5f", i, output[0][2] * 100));
                    Log.d("텐서플로우 결과3 : ", String.format("새송이버섯  %d %.5f", i, output[2][0] * 100));

                } else if (i == 3) {
                    tv_output.setText(String.format("표고버섯, %d, %.5f", i, output[0][3] * 100));
                    Log.d("텐서플로우 결과4 : ", String.format("표고버섯  %d %.5f", i, output[3][0] * 100));

                } else if (i == 4){
                    tv_output.setText(String.format("화경버섯, %d, %.5f", i, output[0][4] * 100));
                    Log.d("텐서플로우 결과5 : ", String.format("화경버섯  %d %.5f", i, output[4][0] * 100));

                } else if (i == 5){
                    Log.d("텐서플로우 결과 : ", String.format("5  %d %.5f", i, output[0][5] * 100));

                } else if (i == 6){
                    Log.d("텐서플로우 결과 : ", String.format("6  %d %.5f", i, output[0][6] * 100));

                } else if (i == 7){
                    Log.d("텐서플로우 결과 : ", String.format("7  %d %.5f", i, output[0][7] * 100));

                } else if (i == 8){
                    Log.d("텐서플로우 결과 : ", String.format("8  %d %.5f", i, output[0][8] * 100));

                } else if (i == 9){
                    Log.d("텐서플로우 결과 : ", String.format("9  %d %.5f", i, output[0][9] * 100));

                } else if (i == 10){
                    Log.d("텐서플로우 결과 : ", String.format("10  %d %.5f", i, output[0][10] * 100));

                } else if (i == 11){
                    Log.d("텐서플로우 결과 : ", String.format("11  %d %.5f", i, output[0][11] * 100));

                } else if (i == 12){
                    Log.d("텐서플로우 결과 : ", String.format("12  %d %.5f", i, output[0][12] * 100));

                } else if (i == 13){
                    Log.d("텐서플로우 결과 : ", String.format("13  %d %.5f", i, output[0][13] * 100));

                } else if (i == 14){
                    Log.d("텐서플로우 결과 : ", String.format("14  %d %.5f", i, output[0][14] * 100));

                } else if (i == 15 ){
                    Log.d("텐서플로우 결과 : ", String.format("15  %d %.5f", i, output[0][15] * 100));

                } else if (i == 16){
                    Log.d("텐서플로우 결과 : ", String.format("16  %d %.5f", i, output[0][16] * 100));

                } else if (i == 17){
                    Log.d("텐서플로우 결과 : ", String.format("17  %d %.5f", i, output[0][17] * 100));

                } else if (i == 18){
                    Log.d("텐서플로우 결과 : ", String.format("18  %d %.5f", i, output[0][18] * 100));

                } else if (i == 19){
                    Log.d("텐서플로우 결과 : ", String.format("19  %d %.5f", i, output[0][19] * 100));

                } else if (i == 20){
                    Log.d("텐서플로우 결과 : ", String.format("20  %d %.5f", i, output[0][20] * 100));

                } else if (i == 21){
                    Log.d("텐서플로우 결과 : ", String.format("21  %d %.5f", i, output[0][21] * 100));

                } else if (i == 22){
                    Log.d("텐서플로우 결과 : ", String.format("22  %d %.5f", i, output[0][22] * 100));

                } else if (i == 23){
                    Log.d("텐서플로우 결과 : ", String.format("23  %d %.5f", i, output[0][23] * 100));

                } else if (i == 24){
                    Log.d("텐서플로우 결과 : ", String.format("24  %d %.5f", i, output[0][24] * 100));

                } else if (i == 25){
                    Log.d("텐서플로우 결과 : ", String.format("25  %d %.5f", i, output[0][25] * 100));

                } else if (i == 26){
                    Log.d("텐서플로우 결과 : ", String.format("26  %d %.5f", i, output[0][26] * 100));

                } else if (i == 27){
                    Log.d("텐서플로우 결과 : ", String.format("27  %d %.5f", i, output[0][27] * 100));

                } else if (i == 28){
                    Log.d("텐서플로우 결과 : ", String.format("28  %d %.5f", i, output[0][28] * 100));

                } else if (i ==29 ){
                    Log.d("텐서플로우 결과 : ", String.format("29  %d %.5f", i, output[0][29] * 100));

                } else if (i == 30){
                    Log.d("텐서플로우 결과 : ", String.format("30  %d %.5f", i, output[0][30] * 100));

                } else if (i == 31){
                    Log.d("텐서플로우 결과 : ", String.format("31  %d %.5f", i, output[0][31] * 100));

                } else if (i == 32){
                    Log.d("텐서플로우 결과 : ", String.format("32  %d %.5f", i, output[0][32] * 100));

                } else if (i == 33){
                    Log.d("텐서플로우 결과 : ", String.format("33  %d %.5f", i, output[0][33] * 100));

                } else if (i == 34){
                    Log.d("텐서플로우 결과 : ", String.format(" 34 %d %.5f", i, output[0][34] * 100));

                } else if (i == 35){
                    Log.d("텐서플로우 결과 : ", String.format("35  %d %.5f", i, output[0][35] * 100));

                } else if (i == 36) {
                    Log.d("텐서플로우 결과 : ", String.format("36  %d %.5f", i, output[0][36] *100));
                } else if (i == 37){
                    Log.d("텐서플로우 결과 : ", String.format("37  %d %.5f", i, output[0][37] * 100));
                } else if (i == 38){
                    Log.d("텐서플로우 결과 : ", String.format("38  %d %.5f", i, output[0][38] * 100));
                } else if (i == 39){
                    Log.d("텐서플로우 결과 : ", String.format("39  %d %.5f", i, output[0][39] * 100));
                } else if (i == 40){
                    Log.d("텐서플로우 결과 : ", String.format("40  %d %.5f", i, output[0][40] * 100));
                } else if (i == 41){
                    Log.d("텐서플로우 결과 : ", String.format("41  %d %.5f", i, output[0][41] * 100));
                } else if (i == 42){
                    Log.d("텐서플로우 결과 : ", String.format("42  %d %.5f", i, output[0][42] * 100));
                } else if (i == 43){
                    Log.d("텐서플로우 결과 : ", String.format("43  %d %.5f", i, output[0][43] * 100));
                } else if (i == 44){
                    Log.d("텐서플로우 결과 : ", String.format("44  %d %.5f", i, output[0][44] * 100));
                } else if (i == 45){
                    Log.d("텐서플로우 결과 : ", String.format("45  %d %.5f", i, output[0][45] * 100));
                } else if (i == 46){
                    Log.d("텐서플로우 결과 : ", String.format("46  %d %.5f", i, output[0][46] * 100));
                } else if (i == 47){
                    Log.d("텐서플로우 결과 : ", String.format("47  %d %.5f", i, output[0][47] * 100));
                } else if (i == 48){
                    Log.d("텐서플로우 결과 : ", String.format("48  %d %.5f", i, output[0][48] * 100));
                } else if (i == 49){
                    Log.d("텐서플로우 결과 : ", String.format("49  %d %.5f", i, output[0][49] * 100));
                }*/
//                    Log.d("텐서플로우 결과 : ", String.format("50  %d %.5f", i, output[0][50] * 100));


            } else {
                continue;
            }
        }
    }
}


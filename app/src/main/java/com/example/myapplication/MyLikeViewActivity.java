package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MyLikeViewActivity extends AppCompatActivity {

    MyLikeViewAdapter adapter;
    ArrayList<LikeViewVO> data;
    ListView lv_like;
    ImageView iv_like_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_like_view2);

        lv_like = findViewById(R.id.lv_like);
        iv_like_back = findViewById(R.id.iv_like_back);
        data = new ArrayList<LikeViewVO>();

        iv_like_back.setImageResource(R.drawable.next1);

        // 데이터 생성 부분. 일단 임시로 아무거나 만듬.
        for (int a=0; a<10; a++) {
            data.add(new LikeViewVO(R.drawable.zambalaya,"장소"+a, "장소명"+a, "음식명"+a, "별점"+a, "확인"));
        }

        adapter = new MyLikeViewAdapter(MyLikeViewActivity.this, R.layout.activity_my_like_view, data);

        // 어댑터 실행
        lv_like.setAdapter(adapter);

    }

    public class ImageHelper {
        public Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                    .getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            return output;
        }
    }
}
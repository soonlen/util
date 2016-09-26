package com.wzf.com.commutil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.com.commutil.util.BitmapUtil;
import com.wzf.com.mylibrary.TestActivity;
import com.wzf.com.mylibrary.TestUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.desert);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(BitmapUtil.createReflectionImageWithOrigin(bitmap));
        bitmap.recycle();


        ((TextView)findViewById(R.id.tv)).setText(TestUtil.libraryText());


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secActivity();
            }
        });

    }

    private void secActivity() {
        Intent intent =new Intent(this, TestActivity.class);
        startActivity(intent);
    }
}

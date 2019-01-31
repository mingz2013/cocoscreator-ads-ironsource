package me.mingz;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.cocos2dx.javascript.AppActivity;

import java.io.IOException;
import java.io.InputStream;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            // for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }


        // 初始化布局，设置相关参数
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);


        // 初始化图片控件
        ImageView imView = new ImageView(this);
        imView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        imView.setScaleType(ImageView.ScaleType.CENTER_CROP);


        AssetManager assets = getResources().getAssets();
        // 文件路径是从assets文件夹下开始的，开头不要加/
        String fileName = "res/img1.jpg";


        try {
            InputStream is = assets.open(fileName);
            Bitmap bm = BitmapFactory.decodeStream(is);
            imView.setImageBitmap(bm);


        } catch (IOException e) {
            e.printStackTrace();
        }


        // 将控件加入布局
        layout.addView(imView);
        setContentView(layout);


        // 1000毫秒为1秒
        int time = 2000;


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, AppActivity.class));
                finish();
            }
        }, time);
    }

}

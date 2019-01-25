package me.mingz.ads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.cocos2dx.lib.Cocos2dxActivity;

public class AdsActivity extends Cocos2dxActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        AdsPlugin.getInstance().init(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        AdsPlugin.getInstance().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        AdsPlugin.getInstance().onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AdsPlugin.getInstance().onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        AdsPlugin.getInstance().onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();

        AdsPlugin.getInstance().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        AdsPlugin.getInstance().onDestroy();
    }

}

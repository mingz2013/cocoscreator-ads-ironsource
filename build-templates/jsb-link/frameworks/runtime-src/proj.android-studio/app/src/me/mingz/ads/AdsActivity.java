package me.mingz.ads;


import android.os.Bundle;
import android.util.Log;

import org.cocos2dx.lib.Cocos2dxActivity;

public class AdsActivity extends Cocos2dxActivity {

    private static final String  TAG = "[AdsActivity]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");


        AdsPluginManager.getInstance().init(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");

        AdsPluginManager.getInstance().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        AdsPluginManager.getInstance().onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AdsPluginManager.getInstance().onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        AdsPluginManager.getInstance().onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();

        AdsPluginManager.getInstance().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        AdsPluginManager.getInstance().onDestroy();
    }

}

package me.mingz.ads;


import android.os.Bundle;

import org.cocos2dx.lib.Cocos2dxActivity;

public class AdsActivity extends Cocos2dxActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        AdsPluginManager.getInstance().init(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

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

        AdsPluginManager.getInstance().onDestroy();
    }

}

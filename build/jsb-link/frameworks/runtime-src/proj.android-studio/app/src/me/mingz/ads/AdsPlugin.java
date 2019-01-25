package me.mingz.ads;

import android.app.Activity;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;
import org.json.JSONException;
import org.json.JSONObject;

public class AdsPlugin {

    public static AdsPlugin mInstace = null;

    public static AdsPlugin getInstance() {
        if (null == mInstace){
            mInstace = new AdsPlugin();

        }
        return mInstace;
    }



    private Cocos2dxActivity mActivity = null;

    public AdsPlugin() {

    }


    protected void init(Cocos2dxActivity activity){
        mActivity = activity;
    }

    protected void onResume(){

    }

    protected void onPause() {

    }


    protected void onDestroy() {

    }

    protected void onStart() {

    }


    protected void onStop() {

    }

    protected void onRestart() {

    }


    public void setActivity(Cocos2dxActivity activity){
        this.mActivity = activity;
    }
    public Cocos2dxActivity getActivity() {
        return mActivity;
    }

    public void emitWindowEvent(final String event) {
        this.emitWindowEvent(event, new JSONObject());
    }


    public void emitWindowEvent(final String event, final JSONObject data){
        // 一定要在 GL 线程中执行
        this.getActivity().runOnGLThread(new Runnable() {
            @Override
            public void run() {
                JsbCall.emitWindowEvent(event, data);
            }
        });
    }


    public boolean exec(final String action, final JSONObject args, final CallbackContext callbackContext) throws JSONException {
        return true;
    }

}

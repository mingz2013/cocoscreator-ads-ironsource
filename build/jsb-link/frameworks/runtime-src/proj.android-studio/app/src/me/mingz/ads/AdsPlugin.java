package me.mingz.ads;

import android.util.Log;
import android.view.View;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class AdsPlugin {

    private static final String  TAG = "[AdsPlugin]";

    private Cocos2dxActivity mActivity = null;

    public AdsPlugin() {

    }

    public Cocos2dxActivity getActivity() {
        return mActivity;
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




    public void emitWindowEvent(final String event) {
        this.emitWindowEvent(event, new JSONObject());
    }


    public void emitWindowEvent(final String event, final JSONObject data){
        JsbCall.emitWindowEvent(event, data);
    }

    public View getView() {
        Log.d(TAG, "getView: ");

        return this.getActivity().getWindow().getDecorView().findViewById(android.R.id.content);

    }





    public boolean exec(final String action, final JSONObject args, final CallbackContext callbackContext) throws JSONException {
        return false;
    }

}

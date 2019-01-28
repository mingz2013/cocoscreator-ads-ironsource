package me.mingz.ads;

import android.util.Log;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;
import org.json.JSONException;
import org.json.JSONObject;



public class JsbCall {

    static final private String TAG = "[JsbCall]";

    public static void exec(String service, String action, String params, String callbackId) {
        Log.d(TAG, "exec: " +  service +" "+ action+" " + params+" " + callbackId);
        final CallbackContext callbackContext = new CallbackContext(callbackId);
        try{
            boolean ret = JsbCall.exec(service, action, new JSONObject(params), callbackContext);
//            if(ret){
//                callbackContext.success("");
//            }else{
//                callbackContext.failure("");
//            }

        }catch (JSONException e){
            Log.e(TAG, "exec: e: ", e);
            callbackContext.failure(String.format("params to json or params error, params: %s", params));
        }

    }



    private static boolean exec(String service, String action, JSONObject args, final CallbackContext callbackContext) throws JSONException{

        return AdsPluginManager.getInstance().exec(service, action, args, callbackContext);


    }





    public static void emitWindowEvent(final String event, final JSONObject data){
        Log.d(TAG, "successCall: " + event + " data: " + data.toString());
        Cocos2dxActivity ctx = AdsPluginManager.getInstance().getActivity();
        ctx.runOnGLThread(new Runnable() {
            @Override
            public void run() {
                Cocos2dxJavascriptJavaBridge.evalString(String.format("window.JsbNativeCall.emitWindowEvent('%s', '%s')", event, data.toString()));
            }
        });

    }


    public static void successCall(final String callbackId, final String params){
        Log.d(TAG, "successCall: " + callbackId + " params: " + params);



        Cocos2dxActivity ctx = AdsPluginManager.getInstance().getActivity();
        ctx.runOnGLThread(new Runnable() {
            @Override
            public void run() {
                Cocos2dxJavascriptJavaBridge.evalString("cc.log(\"Javascript Java bridge!\")");
                Log.d(TAG, "successCall: log....");
                Cocos2dxJavascriptJavaBridge.evalString("cc.log(\"successcall....cc.log.....test.....\");");

                String jsStr = String.format("window.JsbNativeCall.callBackCallSuccess('%s', '%s')", callbackId, params);
                Log.d(TAG, "successCall: evalString: " + jsStr);
                Cocos2dxJavascriptJavaBridge.evalString(jsStr);
            }
        });



    }

    public static void failureCall(final String callbackId, final String params){
        Log.d(TAG, "failureCall: " + callbackId + " params: " + params);

        Cocos2dxActivity ctx = AdsPluginManager.getInstance().getActivity();
        ctx.runOnGLThread(new Runnable() {
            @Override
            public void run() {
                String jsStr = String.format("window.JsbNativeCall.callBackCallFailure('%s', '%s')", callbackId, params);
                Log.d(TAG, "failureCall: evalString: " + jsStr);
                Cocos2dxJavascriptJavaBridge.evalString(jsStr);
            }
        });


    }












}

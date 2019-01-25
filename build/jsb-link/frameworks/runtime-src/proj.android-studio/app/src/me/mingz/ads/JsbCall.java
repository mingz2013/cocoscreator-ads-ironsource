package me.mingz.ads;

import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;
import org.json.JSONException;
import org.json.JSONObject;

public class JsbCall {

    public static void exec(String action, String params, String callbackId) {
        final CallbackContext callbackContext = new CallbackContext(callbackId);
        try{
            JsbCall.exec(action, new JSONObject(params), callbackContext);
        }catch (JSONException e){
            callbackContext.failure(String.format("params to json or params error, params: %s", params));
        }

    }



    private static void exec(String action, JSONObject args, final CallbackContext callbackContext) throws JSONException{
        AdsPlugin.getInstance().exec(action, args, callbackContext);

    }



    public static void emitWindowEvent(final String event, final JSONObject data){
        Cocos2dxJavascriptJavaBridge.evalString(String.format("JsbNativeCall.emitWindowEvent('%s', '%s')", event, data.toString()));
    }


    public static void successCall(String callbackId, String params){
        Cocos2dxJavascriptJavaBridge.evalString(String.format("JsbNativeCall.callBackCallSuccess('%s', '%s)", callbackId, params));
    }

    public static void failureCall(String callbackId, String params){
        Cocos2dxJavascriptJavaBridge.evalString(String.format("JsbNativeCall.callBackCallFailure('%s', '%s)", callbackId, params));
    }












}

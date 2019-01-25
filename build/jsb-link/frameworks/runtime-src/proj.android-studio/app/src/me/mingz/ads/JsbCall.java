package me.mingz.ads;

import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;
import org.json.JSONException;
import org.json.JSONObject;

public class JsbCall {

    public static void exec(String service, String action, String params, String callbackId) {
        final CallbackContext callbackContext = new CallbackContext(callbackId);
        try{
            boolean ret = JsbCall.exec(service, action, new JSONObject(params), callbackContext);
//            if(ret){
//                callbackContext.success("");
//            }else{
//                callbackContext.failure("");
//            }

        }catch (JSONException e){
            callbackContext.failure(String.format("params to json or params error, params: %s", params));
        }

    }



    private static boolean exec(String service, String action, JSONObject args, final CallbackContext callbackContext) throws JSONException{

        return AdsPluginManager.getInstance().exec(service, action, args, callbackContext);


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

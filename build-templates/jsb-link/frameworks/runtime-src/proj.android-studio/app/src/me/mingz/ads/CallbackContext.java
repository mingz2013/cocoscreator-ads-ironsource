package me.mingz.ads;


public class CallbackContext {

    private String mCallbackId = null;
    public CallbackContext(String callbackId){
        mCallbackId = callbackId;
    }

    public void success(String params){
        JsbCall.successCall(mCallbackId, params);
    }


    public void failure(String params){
        JsbCall.failureCall(mCallbackId, params);
    }






}

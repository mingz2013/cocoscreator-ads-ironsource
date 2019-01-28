package me.mingz.ads;

import android.util.Log;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AdsPluginManager {

    final private static String TAG = "[AdsPluginManager]";

    private Cocos2dxActivity mActivity = null;
    private Map<String, AdsPlugin> mPlugins = null;


    public static AdsPluginManager mInstace = null;

    public static AdsPluginManager getInstance() {
        if (null == mInstace) {
            mInstace = new AdsPluginManager();

        }
        return mInstace;
    }


    public AdsPluginManager() {
        Log.d(TAG, "AdsPluginManager: ");
        mPlugins = new HashMap<String, AdsPlugin>();
        mPlugins.put("IronsourcePlugin", new IronsourcePlugin());
        mPlugins.put("AdMobPlugin", new AdMobPlugin());
    }


    public Cocos2dxActivity getActivity(){
        return mActivity;
    }


    protected void init(Cocos2dxActivity activity) {
        Log.d(TAG, "init: ");
        mActivity = activity;

        for (Map.Entry<String, AdsPlugin> entry : mPlugins.entrySet()) {
//            System.out.println("Key: "+ entry.getKey()+ " Value: "+entry.getValue());
            entry.getValue().init(activity);

        }


    }

    protected void onResume() {
        Log.d(TAG, "onResume: ");
        for (Map.Entry<String, AdsPlugin> entry : mPlugins.entrySet()) {
//            System.out.println("Key: "+ entry.getKey()+ " Value: "+entry.getValue());
            entry.getValue().onResume();

        }
    }

    protected void onPause() {
        for (Map.Entry<String, AdsPlugin> entry : mPlugins.entrySet()) {
//            System.out.println("Key: "+ entry.getKey()+ " Value: "+entry.getValue());
            entry.getValue().onPause();

        }
    }


    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        for (Map.Entry<String, AdsPlugin> entry : mPlugins.entrySet()) {
//            System.out.println("Key: "+ entry.getKey()+ " Value: "+entry.getValue());
            entry.getValue().onDestroy();

        }
    }

    protected void onStart() {
        Log.d(TAG, "onStart: ");
        for (Map.Entry<String, AdsPlugin> entry : mPlugins.entrySet()) {
//            System.out.println("Key: "+ entry.getKey()+ " Value: "+entry.getValue());
            entry.getValue().onStart();

        }
    }


    protected void onStop() {
        Log.d(TAG, "onStop: ");
        for (Map.Entry<String, AdsPlugin> entry : mPlugins.entrySet()) {
//            System.out.println("Key: "+ entry.getKey()+ " Value: "+entry.getValue());
            entry.getValue().onStop();

        }
    }

    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
        for (Map.Entry<String, AdsPlugin> entry : mPlugins.entrySet()) {
//            System.out.println("Key: "+ entry.getKey()+ " Value: "+entry.getValue());
            entry.getValue().onRestart();

        }
    }



    public boolean exec(final String service, final String action, final JSONObject args, final CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, "exec: ");
        AdsPlugin plugin = mPlugins.get(service);

        if(plugin != null){
            return plugin.exec(action, args, callbackContext);
        }else{
            String msg = String.format("exec: unknown service: ", service);
            Log.e(TAG, msg);

            callbackContext.failure(msg);
            return false;
        }

    }
}

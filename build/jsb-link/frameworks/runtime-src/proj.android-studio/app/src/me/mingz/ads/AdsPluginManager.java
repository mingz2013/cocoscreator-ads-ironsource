package me.mingz.ads;

import android.util.Log;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

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
        mPlugins.put("IronsourcePlugin", new IronsourcePlugin());
        mPlugins.put("AdMobPlugin", new AdMobPlugin());
    }


    protected void init(Cocos2dxActivity activity) {
        mActivity = activity;

        for (Map.Entry<String, AdsPlugin> entry : mPlugins.entrySet()) {
//            System.out.println("Key: "+ entry.getKey()+ " Value: "+entry.getValue());
            entry.getValue().init(activity);

        }


    }

    protected void onResume() {
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
        for (Map.Entry<String, AdsPlugin> entry : mPlugins.entrySet()) {
//            System.out.println("Key: "+ entry.getKey()+ " Value: "+entry.getValue());
            entry.getValue().onDestroy();

        }
    }

    protected void onStart() {
        for (Map.Entry<String, AdsPlugin> entry : mPlugins.entrySet()) {
//            System.out.println("Key: "+ entry.getKey()+ " Value: "+entry.getValue());
            entry.getValue().onStart();

        }
    }


    protected void onStop() {
        for (Map.Entry<String, AdsPlugin> entry : mPlugins.entrySet()) {
//            System.out.println("Key: "+ entry.getKey()+ " Value: "+entry.getValue());
            entry.getValue().onStop();

        }
    }

    protected void onRestart() {
        for (Map.Entry<String, AdsPlugin> entry : mPlugins.entrySet()) {
//            System.out.println("Key: "+ entry.getKey()+ " Value: "+entry.getValue());
            entry.getValue().onRestart();

        }
    }



    public boolean exec(final String service, final String action, final JSONObject args, final CallbackContext callbackContext) throws JSONException {
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

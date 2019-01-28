package me.mingz.ads;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class AdMobPlugin extends AdsPlugin {

    public static final String TAG = "<AdMobPlugin>: ";
    // adtype use with event
    public static final String ADTYPE_BANNER = "banner";
    public static final String ADTYPE_INTERSTITIAL = "interstitial";
    public static final String ADTYPE_NATIVE = "native";
    public static final String ADTYPE_REWARDVIDEO = "rewardedvideo";

    // events emit to js
    public static final String EVENT_ON_AD_CLOSED = "onAdClosed";
    public static final String EVENT_ON_AD_FAILED_TO_LOAD = "onAdFailedToLoad";
    public static final String EVENT_ON_AD_LEFT_APPLICATION = "onAdLeftApplication";
    public static final String EVENT_ON_AD_OPENED = "onAdOpened";
    public static final String EVENT_ON_AD_LOADED = "onAdLoaded";
    public static final String EVENT_ON_AD_CLICKED = "onAdClicked";
    public static final String EVENT_ON_AD_IMPRESSION = "onAdImpression";
    public static final String EVENT_ON_REWARDED_VIDEO_STARTED = "onRewardedVideoStarted";
    public static final String EVENT_ON_REWARDED_VIDEO_COMPLETED = "onRewardedVideoCompleted";
    public static final String EVENT_ON_REWARDED = "onRewarded";

    // adsize, banner
    public static final String ADSIZE_BANNER = "BANNER";//new AdSize(320, 50, "320x50_mb");
    public static final String ADSIZE_FULL_BANNER = "FULL_BANNER";//new AdSize(468, 60, "468x60_as");
    public static final String ADSIZE_LARGE_BANNER = "LARGE_BANNER";//new AdSize(320, 100, "320x100_as");
    public static final String ADSIZE_LEADERBOARD = "LEADERBOARD";//new AdSize(728, 90, "728x90_as");
    public static final String ADSIZE_MEDIUM_RECTANGLE = "MEDIUM_RECTANGLE";//new AdSize(300, 250, "300x250_as");
    public static final String ADSIZE_WIDE_SKYSCRAPER = "WIDE_SKYSCRAPER";//new AdSize(160, 600, "160x600_as");
    public static final String ADSIZE_SMART_BANNER = "SMART_BANNER";//new AdSize(-1, -2, "smart_banner");

    // admob offical example test id
    public static final String TEST_ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713";
    public static final String TEST_ADMOB_UNIT_ID_BANNER = "ca-app-pub-3940256099942544/6300978111";
    public static final String TEST_ADMOB_UNIT_ID_INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712";
    public static final String TEST_ADMOB_UNIT_ID_INTERSTITIAL_VIDEO = "ca-app-pub-3940256099942544/8691691433";
    public static final String TEST_ADMOB_UNIT_ID_REWARDED_VIDEO = "ca-app-pub-3940256099942544/5224354917";
    public static final String TEST_ADMOB_UNIT_ID_NATIVE_ADVANCED = "ca-app-pub-3940256099942544/2247696110";
    public static final String TEST_ADMOB_UNIT_ID_NATIVE_ADVANCED_VIDEO = "ca-app-pub-3940256099942544/1044960115";

    // run type
    public static final String RUN_TYPE_PROD = "RUN_TYPE_PROD";
    // two ways to get test ads, use one of test units id. or enable test devices
    public static final String RUN_TYPE_TEST_WITH_TEST_ID = "RUN_TYPE_TEST_WITH_TEST_ID";
    public static final String RUN_TYPE_TEST_WITH_DEVICE = "RUN_TYPE_TEST_WITH_DEVICE";


    // banner position
    final private int BANNER_POSITION_NO_CHANGE = 0;
    final private int BANNER_POSITION_TOP_LEFT = 1;
    final private int BANNER_POSITION_TOP_CENTER = 2;
    final private int BANNER_POSITION_TOP_RIGHT = 3;
    final private int BANNER_POSITION_LEFT = 4;
    final private int BANNER_POSITION_CENTER = 5;
    final private int BANNER_POSITION_RIGHT = 6;
    final private int BANNER_POSITION_BOTTOM_LEFT = 7;
    final private int BANNER_POSITION_BOTTOM_CENTER = 8;
    final private int BANNER_POSITION_BOTTOM_RIGHT = 9;
    final private int BANNER_POSITION_POS_XY = 10;


    // real ids
    private String admobAppId;
    private String admobUnitIdBanner;
    private String admobUnitIdInterstitial;
    private String admobUnitIdInterstitialVideo;
    private String admobUnitIdRewardedVideo;
    private String admobUnitIdNativeAdvanced;
    private String admobUnitIdNativeAdvancedVideo;
    // test device, use to test with device
    private String testDevice = "33BE2250B43518CCDA7DE426D04EE231";

    // run type
    private String runType = RUN_TYPE_PROD;



    // ads
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;





    // banner
    private AdView mBannerView = null;

    private ViewGroup mParentView = null;

    private RelativeLayout mOverlapLayout = null;
    private LinearLayout mSplitLayout = null;

    private boolean mBannerVisible = false;
    private boolean mOverlap = true;
    private int mBannerPosition = BANNER_POSITION_BOTTOM_CENTER;
    private int posX = 0;
    private int posY = 0;


    /*************************utils******************************/

    public void emitWindowEvent(final String event, final String adType) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.putOpt("adType", adType);
        } catch (JSONException e) {
            Log.e(TAG, "fireWindowEvent: e:", e);
        }
        emitWindowEvent(event, jsonObj);
    }


    public void emitWindowEvent(final String event, final String adType, final JSONObject data) {
        try {
            data.put("adType", adType);
        } catch (JSONException e) {
            Log.e(TAG, "fireWindowEvent: e: ", e);
        }

        emitWindowEvent(event, data);

    }

    private void emitWindowEvent(final String event, final String adType, final int errCode) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.putOpt("adType", adType);
            jsonObj.putOpt("errCode", errCode);
            jsonObj.putOpt("errReason", getErrorReason(errCode));
        } catch (JSONException e) {
            Log.e(TAG, "fireWindowEvent: e:", e);
        }
        emitWindowEvent(event, jsonObj);
    }



    public String getErrorReason(int errorCode) {
        String errorReason = "";
        switch (errorCode) {
            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                errorReason = "Internal error";
                break;
            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                errorReason = "Invalid request";
                break;
            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                errorReason = "Network Error";
                break;
            case AdRequest.ERROR_CODE_NO_FILL:
                errorReason = "No fill";
                break;
            default:
                errorReason = "Unknown Error";
                break;
        }
        return errorReason;
    }

    public AdSize getBannerSize(String size, int w, int h) {
        switch (size) {
            case ADSIZE_BANNER:
                return AdSize.BANNER;
            case ADSIZE_FULL_BANNER:
                return AdSize.FULL_BANNER;
            case ADSIZE_LARGE_BANNER:
                return AdSize.LARGE_BANNER;
            case ADSIZE_LEADERBOARD:
                return AdSize.LEADERBOARD;
            case ADSIZE_MEDIUM_RECTANGLE:
                return AdSize.MEDIUM_RECTANGLE;
            case ADSIZE_WIDE_SKYSCRAPER:
                return AdSize.WIDE_SKYSCRAPER;
            case ADSIZE_SMART_BANNER:
                return AdSize.SMART_BANNER;
            default:
                return new AdSize(w, h);
        }
    }







    /*************************overwrite******************************/

    @Override
    public void onResume() {
        if (mRewardedVideoAd != null) {
            mRewardedVideoAd.resume(this.getActivity());
        }
        if (mBannerView != null) {
            mBannerView.resume();
        }


        super.onResume();
    }


    @Override
    public void onPause() {
        if (mRewardedVideoAd != null) {
            mRewardedVideoAd.pause(this.getActivity());
        }
        if (mBannerView != null) {
            mBannerView.pause();
        }

        super.onPause();
    }


    @Override
    public void onDestroy() {
        if (mRewardedVideoAd != null) {
            mRewardedVideoAd.destroy(this.getActivity());
        }
        if (mBannerView != null) {
            mBannerView.destroy();
        }
        super.onDestroy();
    }


    @Override
    public boolean exec(String action, JSONObject args, CallbackContext callbackContext) throws JSONException {
        JSONObject params = args;

        if (action.equals("init")) {
            this.execActionInit(params, callbackContext);
            return true;
        } else if (action.equals("createInterstitial")) {
            this.execActionCreateInterstitial(params, callbackContext);
            return true;
        } else if (action.equals("loadInterstitial")) {
            this.execActionLoadInterstitial(params, callbackContext);
            return true;
        } else if (action.equals("isInterstitialLoading")) {
            this.execActionIsInterstitialLoading(params, callbackContext);
            return true;
        } else if (action.equals("isInterstitialLoaded")) {
            this.execActionIsInterstitialLoaded(params, callbackContext);
            return true;
        } else if (action.equals("showInterstitial")) {
            this.execActionShowInterstitial(params, callbackContext);
            return true;
        } else if (action.equals("createRewardedVideo")) {
            this.execActionCreateRewardedVideo(params, callbackContext);
            return true;
        } else if (action.equals("loadRewardedVideo")) {
            this.execActionLoadRewardedVideo(params, callbackContext);
            return true;
        } else if (action.equals("showRewardedVideo")) {
            this.execActionShowRewardedVideo(params, callbackContext);
            return true;
        } else if (action.equals("createBanner")) {
            this.execActionCreateBanner(params, callbackContext);
            return true;
        } else if (action.equals("loadBanner")) {
            this.execActionLoadBanner(params, callbackContext);
            return true;
        } else if (action.equals("showBanner")) {
            this.execActionShowBanner(params, callbackContext);
            return true;
        } else if (action.equals("hideBanner")) {
            this.execActionHideBanner(params, callbackContext);
            return true;
        }
        return super.exec(action, args, callbackContext);
    }

    /*************************execute action******************************/

    private void execActionInit(JSONObject args, CallbackContext callbackContext) throws JSONException {


        this.init(args);

        callbackContext.success("");


    }


    private void init(JSONObject args) throws JSONException {
        this.testDevice = args.getString("testDevice");

        this.mOverlap = args.getBoolean("overlap");
        this.runType = args.getString("runType");


        if (this.runType.equals(RUN_TYPE_TEST_WITH_TEST_ID)) {
            this.admobAppId = TEST_ADMOB_APP_ID;
            this.admobUnitIdBanner = TEST_ADMOB_UNIT_ID_BANNER;
            this.admobUnitIdInterstitial = TEST_ADMOB_UNIT_ID_INTERSTITIAL;
            this.admobUnitIdInterstitialVideo = TEST_ADMOB_UNIT_ID_INTERSTITIAL_VIDEO;
            this.admobUnitIdRewardedVideo = TEST_ADMOB_UNIT_ID_REWARDED_VIDEO;
            this.admobUnitIdNativeAdvanced = TEST_ADMOB_UNIT_ID_NATIVE_ADVANCED;
            this.admobUnitIdNativeAdvancedVideo = TEST_ADMOB_UNIT_ID_NATIVE_ADVANCED_VIDEO;
        } else {
            this.admobAppId = args.getString("admobAppId");
            this.admobUnitIdBanner = args.getString("admobUnitIdBanner");
            this.admobUnitIdInterstitial = args.getString("admobUnitIdInterstitial");
            this.admobUnitIdInterstitialVideo = args.getString("admobUnitIdInterstitialVideo");
            this.admobUnitIdRewardedVideo = args.getString("admobUnitIdRewardedVideo");
            this.admobUnitIdNativeAdvanced = args.getString("admobUnitIdNativeAdvanced");
            this.admobUnitIdNativeAdvancedVideo = args.getString("admobUnitIdNativeAdvancedVideo");

        }


        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this.getActivity(), this.admobAppId);
    }


    private void execActionCreateInterstitial(JSONObject args, CallbackContext callbackContext) {

        this.createInterstitial();
        callbackContext.success("");

    }

    private void createInterstitial() {
        if (mInterstitialAd == null) {
            AdMobPlugin self = this;
            mInterstitialAd = new InterstitialAd(this.getActivity());
            mInterstitialAd.setAdUnitId(this.admobUnitIdInterstitial);
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    // Code to be executed when when the interstitial ad is closed.
                    // Load the next interstitial.
//                    self.loadInterstitial();
                    emitWindowEvent(EVENT_ON_AD_CLOSED, ADTYPE_INTERSTITIAL);
                }

                @Override
                public void onAdFailedToLoad(int errCode) {
                    // Code to be executed when an ad request fails.
                    emitWindowEvent(EVENT_ON_AD_FAILED_TO_LOAD, ADTYPE_INTERSTITIAL, errCode);
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                    emitWindowEvent(EVENT_ON_AD_LEFT_APPLICATION, ADTYPE_INTERSTITIAL);
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed.
                    emitWindowEvent(EVENT_ON_AD_OPENED, ADTYPE_INTERSTITIAL);
                }

                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    emitWindowEvent(EVENT_ON_AD_LOADED, ADTYPE_INTERSTITIAL);
                }

                @Override
                public void onAdClicked() {
                    // Clicked
                    emitWindowEvent(EVENT_ON_AD_CLICKED, ADTYPE_INTERSTITIAL);
                }

                @Override
                public void onAdImpression() {
                    // Impression
                    emitWindowEvent(EVENT_ON_AD_IMPRESSION, ADTYPE_INTERSTITIAL);
                }

            });
        }


    }


    private void execActionLoadInterstitial(JSONObject args, CallbackContext callbackContext) {
        this.loadInterstitial();
        callbackContext.success("");
    }


    private void loadInterstitial() {
        if (mInterstitialAd != null && !mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest.Builder builder = new AdRequest.Builder();
            if (this.runType.equals(RUN_TYPE_TEST_WITH_DEVICE)) {
                builder.addTestDevice(this.testDevice);
            }
            AdRequest adRequest = builder.build();

            mInterstitialAd.loadAd(adRequest);
        } else {
            Log.d(TAG, "loadInterstitial: isloading or is loaded");
        }

    }

    private void execActionIsInterstitialLoading(JSONObject args, CallbackContext callbackContext) {

        boolean isLoading = mInterstitialAd.isLoading();

        callbackContext.success(isLoading + "");

    }

    private void execActionIsInterstitialLoaded(JSONObject args, CallbackContext callbackContext) {
        boolean isLoaded = mInterstitialAd.isLoaded();
        callbackContext.success(isLoaded + "");
    }


    private void execActionShowInterstitial(JSONObject args, CallbackContext callbackContext) {
        this.showInterstitial();
        callbackContext.success("");
    }

    private void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void execActionCreateRewardedVideo(JSONObject args, CallbackContext callbackContext) {
        this.createRewardedVideo();
        callbackContext.success("");
    }


    private void createRewardedVideo() {
        if (mRewardedVideoAd == null) {
            // Use an activity context to get the rewarded video instance.
            mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this.getActivity());
            mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
                @Override
                public void onRewardedVideoAdLoaded() {
                    emitWindowEvent(EVENT_ON_AD_LOADED, ADTYPE_REWARDVIDEO);
                }

                @Override
                public void onRewardedVideoAdOpened() {
                    emitWindowEvent(EVENT_ON_AD_OPENED, ADTYPE_REWARDVIDEO);
                }

                @Override
                public void onRewardedVideoStarted() {
                    emitWindowEvent(EVENT_ON_REWARDED_VIDEO_STARTED, ADTYPE_REWARDVIDEO);
                }

                @Override
                public void onRewardedVideoAdClosed() {
                    emitWindowEvent(EVENT_ON_AD_CLOSED, ADTYPE_REWARDVIDEO);

                }

                @Override
                public void onRewardedVideoCompleted() {
                    emitWindowEvent(EVENT_ON_REWARDED_VIDEO_COMPLETED, ADTYPE_REWARDVIDEO);
                }

                @Override
                public void onRewarded(RewardItem rewardItem) {
                    JSONObject jsonObj = new JSONObject();
                    try {
                        jsonObj.putOpt("rewardItemType", rewardItem.getType());
                        jsonObj.putOpt("rewardItemAmount", rewardItem.getAmount());
                    } catch (JSONException e) {
                        Log.d(TAG, "onRewarded: e: ", e);
                    }


                    emitWindowEvent(EVENT_ON_REWARDED, ADTYPE_REWARDVIDEO, jsonObj);
                }

                @Override
                public void onRewardedVideoAdLeftApplication() {
                    emitWindowEvent(EVENT_ON_AD_LEFT_APPLICATION, ADTYPE_REWARDVIDEO);
                }

                @Override
                public void onRewardedVideoAdFailedToLoad(int i) {
                    emitWindowEvent(EVENT_ON_AD_FAILED_TO_LOAD, ADTYPE_REWARDVIDEO, i);
                }


            });
        }
    }

    private void execActionLoadRewardedVideo(JSONObject args, CallbackContext callbackContext) {

        this.loadRewardedVideo();
        callbackContext.success("");

    }

    private void loadRewardedVideo() {
        if (mRewardedVideoAd != null && !mRewardedVideoAd.isLoaded()) {
            AdRequest.Builder builder = new AdRequest.Builder();
            if (this.runType.equals(RUN_TYPE_TEST_WITH_DEVICE)) {
                builder.addTestDevice(this.testDevice);
            }
            AdRequest adRequest = builder.build();
            mRewardedVideoAd.loadAd(this.admobUnitIdRewardedVideo, adRequest);
        }
    }


    private void execActionShowRewardedVideo(JSONObject args, CallbackContext callbackContext) {

        this.showRewardedVideo();
        callbackContext.success("");

    }

    private void showRewardedVideo() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }


    private void createBannerView(AdSize size) {
        mBannerView = new AdView(this.getActivity());
        mBannerView.setAdSize(size);

    }


    private void execActionCreateBanner(JSONObject args, final CallbackContext callbackContext) throws JSONException {
        final String size = args.getString("size");
        final int w = args.getInt("w");
        final int h = args.getInt("h");


        final AdMobPlugin self = this;
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                self.createBanner(getBannerSize(size, w, h));
                callbackContext.success("");
            }
        });


    }


    private void createBanner(AdSize size) {
        createBannerView(size);
        mBannerView.setAdUnitId(this.admobUnitIdBanner);
        mBannerView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                // Load the next interstitial.
//                    self.loadBanner();
                emitWindowEvent(EVENT_ON_AD_CLOSED, ADTYPE_BANNER);
            }

            @Override
            public void onAdFailedToLoad(int errCode) {
                // Code to be executed when an ad request fails.
                emitWindowEvent(EVENT_ON_AD_FAILED_TO_LOAD, ADTYPE_BANNER, errCode);
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                emitWindowEvent(EVENT_ON_AD_LEFT_APPLICATION, ADTYPE_BANNER);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                emitWindowEvent(EVENT_ON_AD_OPENED, ADTYPE_BANNER);
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                emitWindowEvent(EVENT_ON_AD_LOADED, ADTYPE_BANNER);
            }

            @Override
            public void onAdClicked() {
                // Clicked
                emitWindowEvent(EVENT_ON_AD_CLICKED, ADTYPE_BANNER);
            }

            @Override
            public void onAdImpression() {
                // Impression
                emitWindowEvent(EVENT_ON_AD_IMPRESSION, ADTYPE_BANNER);
            }

        });
    }


    private void execActionLoadBanner(JSONObject args, final CallbackContext callbackContext) {

        final AdMobPlugin self = this;
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                self.loadBanner();
                callbackContext.success("");
            }
        });


    }

    private void loadBanner() {
        if (mBannerView != null) {

            _loadBanner();

        } else {
            Log.e(TAG, "loadBanner: mBannerView is null");
        }
    }


    private void _loadBanner() {
        AdRequest.Builder builder = new AdRequest.Builder();
        if (this.runType.equals(RUN_TYPE_TEST_WITH_DEVICE)) {
            builder.addTestDevice(this.testDevice);
        }
        AdRequest adRequest = builder.build();
        mBannerView.loadAd(adRequest);
    }


    private void execActionShowBanner(JSONObject args,final CallbackContext callbackContext) throws JSONException {
        final int pos = args.getInt("pos");
        final int x = args.getInt("x");
        final int y = args.getInt("y");


        final AdMobPlugin self = this;
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {


                self.showBanner(pos, x, y);
                callbackContext.success("");
            }
        });


    }


    private void showBanner(int argPos, int argX, int argY) {
        // TODO
        Log.d(TAG, "showBanner: ......." + argPos + " " + argX + " " + argY);

        if (mBannerView != null) {

            View mainView = this.getView();
            if (mainView == null) {
                Log.e(TAG, "Error: could not get main view");
            } else {
                Log.d(TAG, "webview class: " + mainView.getClass());
                Log.d(TAG, "showBanner: ....bannerVisible:" + mBannerVisible);
                if (mBannerVisible) {
                    _detachBanner();
                }


                ViewGroup rootView = (ViewGroup) mainView.getRootView();
//                int rw = rootView.getWidth();
//                int rh = rootView.getHeight();

                int ww = mainView.getWidth();
                int wh = mainView.getHeight();

//                int bw = this.bannerView.getWidth();
//                int bh = this.bannerView.getHeight();
                int bw = mBannerView.getWidth();
                int bh = mBannerView.getHeight();
                mBannerPosition = (argPos == 0) ? mBannerPosition : argPos;
                Log.d(TAG, String.format("show banner: x,y: (%d x %d)", bw, bh));

                Log.w(TAG, "show banner, mOverlap: " + mOverlap + ", position: " + mBannerPosition);
                if (mOverlap) {
                    int x = 0, y = 0;
//                    int ww = mainView.getWidth();
//                    int wh = mainView.getHeight();
                    if (mBannerPosition >= 1 && mBannerPosition <= 9) {
                        switch ((mBannerPosition - 1) % 3) {
                            case 0:
                                x = 0;
                                break;
                            case 1:
                                x = (ww - bw) / 2;
                                break;
                            case 2:
                                x = ww - bw;
                        }

                        switch ((mBannerPosition - 1) / 3) {
                            case 0:
                                y = 0;
                                break;
                            case 1:
                                y = (wh - bh) / 2;
                                break;
                            case 2:
                                y = wh - bh;
                        }
                    } else if (mBannerPosition == 10) {
                        x = argX;
                        y = argY;
                    } else {
                        x = this.posX;
                        y = this.posY;
                    }

                    this.posX = x;
                    this.posY = y;

                    Log.d(TAG, "showBanner: bannerView   111111 x,y,w,h:" + x + "," + y + "," + bw + "," + bh);

                    int[] offsetRootView = new int[]{0, 0};
                    int[] offsetWebView = new int[]{0, 0};
                    rootView.getLocationOnScreen(offsetRootView);
                    mainView.getLocationOnScreen(offsetWebView);
                    x += offsetWebView[0] - offsetRootView[0];
                    y += offsetWebView[1] - offsetRootView[1];
                    Log.d(TAG, "showBanner: bannerView       222222 x,y,w,h:" + x + "," + y + "," + bw + "," + bh);
                    if (mOverlapLayout == null) {
                        mOverlapLayout = new RelativeLayout(this.getActivity());
//                        this.overlapLayout.setBackgroundColor(Color.parseColor("#FF00FF"));
//                        this.overlapLayout.
                        rootView.addView(mOverlapLayout, new RelativeLayout.LayoutParams(-1, -1));
                        Log.d(TAG, "showBanner: overlaplayout add to rootView....");
                        mOverlapLayout.bringToFront();
                    }

//                    bw = rw;
//                    bh = 200;
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(bw, bh);
//                    x = y = 0;
                    Log.d(TAG, "showBanner: bannerView x,y,w,h:" + x + "," + y + "," + bw + "," + bh);


                    params.leftMargin = x;
                    params.topMargin = y;
//                    this.bannerView.setBackgroundColor(Color.parseColor("#FF00FF"));
                    mOverlapLayout.addView(mBannerView, params);
                    mBannerView.bringToFront();
                    mParentView = mOverlapLayout;
                    Log.d(TAG, "showBanner: overlaplayout........");
                } else {
                    mParentView = (ViewGroup) mainView.getParent();
                    if (!(mParentView instanceof LinearLayout)) {
                        mParentView.removeView(mainView);
                        LinearLayout splitLayout = new LinearLayout(this.getActivity());
                        splitLayout.setOrientation(LinearLayout.VERTICAL);
                        splitLayout.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-1, -1, 0.0F));
                        mainView.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-1, -1, 1.0F));
                        splitLayout.addView(mainView);
                        this.getActivity().setContentView(splitLayout);
                        mParentView = splitLayout;
                    }

                    if (mBannerPosition <= 3) {
                        mParentView.addView(mBannerView, 0);
                    } else {
                        mParentView.addView(mBannerView);
                    }
                }

                mParentView.bringToFront();
                mParentView.requestLayout();
                mBannerView.setVisibility(View.VISIBLE);
                mBannerVisible = true;
                mBannerView.resume();
                mainView.requestFocus();


                Log.d(TAG, "showBanner: .......end.....");

            }
        }
    }

    private void execActionHideBanner(JSONObject args, final CallbackContext callbackContext) {


        final AdMobPlugin self = this;
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {


                self.hideBanner();
                callbackContext.success("");


            }
        });


    }


    private void hideBanner() {
        Log.d(TAG, "hideBanner: ");
        if (mBannerView != null) {
            _detachBanner();
            mBannerView.pause();
        }
    }


    private void _detachBanner() {
        Log.d(TAG, "_detachBanner: ");
        if (mBannerView != null) {
            mBannerView.setVisibility(View.INVISIBLE);
            mBannerVisible = false;
            ViewGroup parentView = (ViewGroup) mBannerView.getParent();
            if (parentView != null) {
                parentView.removeView(mBannerView);
            }

        }
    }


    private void _removeBanner() {
        Log.d(TAG, "_removeBanner: ");
        if (mBannerView != null) {
            hideBanner();
            _destroyBanner();
        }

        mBannerVisible = false;
    }


    private void _destroyBanner() {
        Log.d(TAG, "_destroyBanner: ");
        if (mBannerView != null) {
            mBannerView.destroy();
            mBannerView = null;
        }
    }


}
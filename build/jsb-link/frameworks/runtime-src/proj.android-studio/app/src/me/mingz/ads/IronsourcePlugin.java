package me.mingz.ads;


import android.app.Activity;
import android.util.Log;
import android.text.TextUtils;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ironsource.adapters.supersonicads.SupersonicConfig;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.ironsource.mediationsdk.sdk.OfferwallListener;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;
import com.ironsource.mediationsdk.sdk.BannerListener;


public class IronsourcePlugin extends AdsPlugin implements RewardedVideoListener, OfferwallListener, InterstitialListener {
    private final String FALLBACK_USER_ID = "userId";

    private static final String TAG = "[IronsourcePlugin]";

    private static final String EVENT_INTERSTITIAL_LOADED = "interstitialLoaded";
    private static final String EVENT_INTERSTITIAL_SHOWN = "interstitialShown";
    private static final String EVENT_INTERSTITIAL_SHOW_FAILED = "interstitialShowFailed";
    private static final String EVENT_INTERSTITIAL_CLICKED = "interstitialClicked";
    private static final String EVENT_INTERSTITIAL_CLOSED = "interstitialClosed";
    private static final String EVENT_INTERSTITIAL_WILL_OPEN = "interstitialWillOpen";
    private static final String EVENT_INTERSTITIAL_FAILED_TO_LOAD = "interstitialFailedToLoad";

    private static final String EVENT_OFFERWALL_CLOSED = "offerwallClosed";
    private static final String EVENT_OFFERWALL_CREDIT_FAILED = "offerwallCreditFailed";
    private static final String EVENT_OFFERWALL_CREDITED = "offerwallCreditReceived";
    private static final String EVENT_OFFERWALL_SHOW_FAILED = "offerwallShowFailed";
    private static final String EVENT_OFFERWALL_SHOWN = "offerwallShown";
    private static final String EVENT_OFFERWALL_AVAILABILITY_CHANGED = "offerwallAvailabilityChanged";

    private static final String EVENT_REWARDED_VIDEO_FAILED = "rewardedVideoFailed";
    private static final String EVENT_REWARDED_VIDEO_REWARDED = "rewardedVideoRewardReceived";
    private static final String EVENT_REWARDED_VIDEO_ENDED = "rewardedVideoEnded";
    private static final String EVENT_REWARDED_VIDEO_STARTED = "rewardedVideoStarted";
    private static final String EVENT_REWARDED_VIDEO_AVAILABILITY_CHANGED = "rewardedVideoAvailabilityChanged";
    private static final String EVENT_REWARDED_VIDEO_CLOSED = "rewardedVideoClosed";
    private static final String EVENT_REWARDED_VIDEO_OPENED = "rewardedVideoOpened";

    private static final String EVENT_BANNER_DID_LOAD = "bannerDidLoad";
    private static final String EVENT_BANNER_FAILED_TO_LOAD = "bannerFailedToLoad";
    private static final String EVENT_BANNER_DID_CLICK = "bannerDidClick";
    private static final String EVENT_BANNER_WILL_PRESENT_SCREEN = "bannerWillPresentScreen";
    private static final String EVENT_BANNER_DID_DISMISS_SCREEN = "bannerDidDismissScreen";
    private static final String EVENT_BANNER_WILL_LEAVE_APPLICATION = "bannerWillLeaveApplication";


    private IronSourceBannerLayout bannerView = null;
    protected boolean bannerVisible = false;
    protected boolean autoShowBanner = true;
    protected boolean logVerbose = false;

    protected boolean overlap = true;
    protected boolean orientationRenew = true;
    protected int adPosition = 8;
    protected int posX = 0;
    protected int posY = 0;
    protected int adWidth = 0;
    protected int adHeight = 0;


    protected RelativeLayout overlapLayout = null;
    protected LinearLayout splitLayout = null;

    protected ViewGroup parentView = null;


    final private int NO_CHANGE = 0;
    final private int TOP_LEFT = 1;
    final private int TOP_CENTER = 2;
    final private int TOP_RIGHT = 3;
    final private int LEFT = 4;
    final private int CENTER = 5;
    final private int RIGHT = 6;
    final private int BOTTOM_LEFT = 7;
    final private int BOTTOM_CENTER = 8;
    final private int BOTTOM_RIGHT = 9;
    final private int POS_XY = 10;


    @Override
    public boolean exec(String action, JSONObject args, final CallbackContext callbackContext) throws JSONException{
        Log.d(TAG, "exec: ");
        if (action.equals("init")) {
            this.initAction(args, callbackContext);
            return true;
        } else if (action.equals("setDynamicUserId")) {
            this.setDynamicUserIdAction(args, callbackContext);
            return true;
        } else if (action.equals("setConsent")) {
            this.setConsentAction(args, callbackContext);
            return true;
        } else if (action.equals("validateIntegration")) {
            this.validateIntegrationAction(args, callbackContext);
            return true;
        } else if (action.equals("showRewardedVideo")) {
            this.showRewardedVideoAction(args, callbackContext);
            return true;
        } else if (action.equals("hasRewardedVideo")) {
            this.hasRewardedVideoAction(args, callbackContext);
            return true;
        } else if (action.equals("isRewardedVideoCappedForPlacement")) {
            this.isRewardedVideoCappedForPlacementAction(args, callbackContext);
            return true;
        } else if (action.equals("loadBanner")) {
            this.loadBannerAction(args, callbackContext);
            return true;
        } else if (action.equals("showBanner")) {
            this.showBannerAction(args, callbackContext);
            return true;
        } else if (action.equals("hideBanner")) {
            this.hideBannerAction(args, callbackContext);
            return true;
        } else if (action.equals("hasOfferwall")) {
            this.hasOfferwallAction(args, callbackContext);
            return true;
        } else if (action.equals("showOfferwall")) {
            this.showOfferwallAction(args, callbackContext);
            return true;
        } else if (action.equals("loadInterstitial")) {
            this.loadInterstitialAction(args, callbackContext);
            return true;
        } else if (action.equals("hasInterstitial")) {
            this.hasInterstitialAction(args, callbackContext);
            return true;
        } else if (action.equals("showInterstitial")) {
            this.showInterstitialAction(args, callbackContext);
            return true;
        }

        return super.exec(action, args, callbackContext);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        IronSource.onPause(this.getActivity());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IronSource.onResume(this.getActivity());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    /**
     * ----------------------- UTILS ---------------------------
     */

    private JSONObject createErrorJSON(IronSourceError ironSourceError) {
        JSONObject data = new JSONObject();
        JSONObject errorData = new JSONObject();
        try {
            errorData.put("code", ironSourceError.getErrorCode());
            errorData.put("message", ironSourceError.getErrorMessage());
            data.put("error", errorData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }



    /** ----------------------- INITIALIZATION --------------------------- */

    /**
     * Intilization action Initializes IronSource
     */
    private void initAction(JSONObject args, final CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, "initAction: " + args.toString());
        final String appKey = args.getString("appKey");
        final String providedUserId = args.getString("providedUserId");
        final Boolean isDebug = args.getBoolean("debug");
        final int position = args.getInt("position");
        final int x = args.getInt("x");
        final int y = args.getInt("y");
        final int w = args.getInt("w");
        final int h = args.getInt("h");
        this.adPosition = position;
        this.posX = x;
        this.posY = y;
        this.adWidth = (w == 0) ? this.getView().getWidth() : w;
        this.adHeight = (h == 0) ? 200 : h;

//        final String appKey = "82ed924d";

        final IronsourcePlugin self = this;

        Log.d(TAG, "initAction: .................");

        // getting advertiser id should be done on a background thread
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                return IronSource.getAdvertiserId(self.getActivity());
            }

            @Override
            protected void onPostExecute(String advertisingId) {

                String userId = providedUserId;

                if (TextUtils.isEmpty(userId)) {
                    userId = advertisingId;
                }

                if (TextUtils.isEmpty(userId)) {
                    userId = FALLBACK_USER_ID;
                }

                // we're using an advertisingId as the 'userId'
                init(appKey, userId, isDebug);

                callbackContext.success("");

            }
        };

        task.execute();
        Log.d(TAG, "initAction: end....");
    }

    /**
     * Initializes IronSource
     *
     * @todo Provide
     */
    private void init(String appKey, String userId, Boolean isDebug) {
        Log.d(TAG, "init: ...." + appKey + ".." + userId + ".." + isDebug);
        // Be sure to set a listener to each product that is being initiated
        // set the IronSource rewarded video listener
        IronSource.setRewardedVideoListener(this);

        // set the IronSource offerwall listener
        IronSource.setOfferwallListener(this);

        // set the IronSource interstitial listener;
        IronSource.setInterstitialListener(this);

        // set client side callbacks for the offerwall
        SupersonicConfig.getConfigObj().setClientSideCallbacks(true);

        // Set user id
        IronSource.setUserId(userId);

        // init the IronSource SDK
        IronSource.init(this.getActivity(), appKey);

        if (isDebug) {
            IronSource.setAdaptersDebug(true);
        }


        Log.d(TAG, "init: end .....");
    }

    /**
     * ----------------------- SET DYNAMIC USER ID ---------------------------
     */

    private void setDynamicUserIdAction(JSONObject args, final CallbackContext callbackContext) throws JSONException {

        final String userId = args.getString("userId");

        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                IronSource.setDynamicUserId(userId);
                callbackContext.success("");
            }
        });
    }

    /**
     * ----------------------- SET CONSENT ---------------------------
     */

    private void setConsentAction(JSONObject args, final CallbackContext callbackContext) throws JSONException {

        final boolean consent = args.getBoolean("consent");

        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                IronSource.setConsent(consent);
                callbackContext.success("");
            }
        });
    }

    /**
     * ----------------------- VALIDATION INTEGRATION ---------------------------
     */

    /**
     * Validates integration action
     */
    private void validateIntegrationAction(JSONObject args, final CallbackContext callbackContext) {
        final IronsourcePlugin self = this;
        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                IntegrationHelper.validateIntegration(self.getActivity());
                callbackContext.success("");
            }
        });
    }

    /**
     * ----------------------- REWARDED VIDEO ---------------------------
     */

    private void showRewardedVideoAction(JSONObject args, final CallbackContext callbackContext) throws JSONException {

        final String placementName = args.getString("placementName");

        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {

                IronSource.showRewardedVideo(placementName);
                callbackContext.success("");
            }
        });
    }

    private void hasRewardedVideoAction(JSONObject args, final CallbackContext callbackContext) throws JSONException {
        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                boolean available = IronSource.isRewardedVideoAvailable();
                callbackContext.success("" + available);
            }
        });
    }

    private void isRewardedVideoCappedForPlacementAction(JSONObject args, final CallbackContext callbackContext)
            throws JSONException {

        final String placementName = args.getString("placementName");

        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                boolean capped = IronSource.isRewardedVideoPlacementCapped(placementName);
                callbackContext.success("" + capped);
            }
        });
    }

    @Override
    public void onRewardedVideoAdOpened() {
        this.emitWindowEvent(EVENT_REWARDED_VIDEO_OPENED);
    }

    @Override
    public void onRewardedVideoAdClosed() {
        this.emitWindowEvent(EVENT_REWARDED_VIDEO_CLOSED);
    }

    @Override
    public void onRewardedVideoAvailabilityChanged(boolean available) {
        JSONObject data = new JSONObject();
        try {
            data.put("available", available);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.emitWindowEvent(EVENT_REWARDED_VIDEO_AVAILABILITY_CHANGED, data);
    }

    @Override
    public void onRewardedVideoAdStarted() {
        this.emitWindowEvent(EVENT_REWARDED_VIDEO_STARTED);
    }

    @Override
    public void onRewardedVideoAdEnded() {
        this.emitWindowEvent(EVENT_REWARDED_VIDEO_ENDED);
    }

    @Override
    public void onRewardedVideoAdRewarded(Placement placement) {

        String rewardName = placement.getRewardName();
        int rewardAmount = placement.getRewardAmount();

        JSONObject placementData = new JSONObject();
        try {
            placementData.put("name", placement.getPlacementName());
            placementData.put("reward", placement.getRewardName());
            placementData.put("amount", placement.getRewardAmount());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject data = new JSONObject();

        try {
            data.put("placement", placementData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.emitWindowEvent(EVENT_REWARDED_VIDEO_REWARDED, data);
    }

    @Override
    public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {
        this.emitWindowEvent(EVENT_REWARDED_VIDEO_FAILED, createErrorJSON(ironSourceError));
    }

    @Override
    public void onRewardedVideoAdClicked(Placement placement) {

    }

    /**
     * ----------------------- INTERSTITIAL ---------------------------
     */

    private void hasInterstitialAction(JSONObject args, final CallbackContext callbackContext) {
        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                boolean ready = IronSource.isInterstitialReady();
                callbackContext.success(ready + "");
            }
        });
    }

    private void loadInterstitialAction(JSONObject args, final CallbackContext callbackContext) {
        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                IronSource.loadInterstitial();
                callbackContext.success("");
            }
        });
    }

    private void showInterstitialAction(JSONObject args, final CallbackContext callbackContext) throws JSONException {

        final String placement = args.getString("placement");

        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                IronSource.showInterstitial(placement);
                callbackContext.success("");
            }
        });
    }

    @Override
    public void onInterstitialAdReady() {
        this.emitWindowEvent(EVENT_INTERSTITIAL_LOADED);
    }

    @Override
    public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
        this.emitWindowEvent(EVENT_INTERSTITIAL_FAILED_TO_LOAD, createErrorJSON(ironSourceError));
    }

    @Override
    public void onInterstitialAdOpened() {
        this.emitWindowEvent(EVENT_INTERSTITIAL_WILL_OPEN, new JSONObject());
    }

    @Override
    public void onInterstitialAdClosed() {
        this.emitWindowEvent(EVENT_INTERSTITIAL_CLOSED, new JSONObject());
    }

    @Override
    public void onInterstitialAdShowSucceeded() {
        this.emitWindowEvent(EVENT_INTERSTITIAL_SHOWN, new JSONObject());
    }

    @Override
    public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
        this.emitWindowEvent(EVENT_INTERSTITIAL_SHOW_FAILED, createErrorJSON(ironSourceError));
    }

    @Override
    public void onInterstitialAdClicked() {
        Log.d(TAG, "onInterstitialAdClicked: ");
        this.emitWindowEvent(EVENT_INTERSTITIAL_CLICKED, new JSONObject());
    }

    /**
     * ----------------------- OFFERWALL ---------------------------
     */

    private void showOfferwallAction(JSONObject args, final CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, "showOfferwallAction: ");
        final String placementName = args.getString("placementName");

        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                IronSource.showOfferwall(placementName);
                callbackContext.success("");
            }
        });
    }

    private void hasOfferwallAction(JSONObject args, final CallbackContext callbackContext) {
        Log.d(TAG, "hasOfferwallAction: ");
        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                boolean available = IronSource.isOfferwallAvailable();
                callbackContext.success("" + available);
            }
        });

    }

    @Override
    public void onOfferwallAvailable(boolean available) {
        Log.d(TAG, "onOfferwallAvailable: ");
        JSONObject data = new JSONObject();
        try {
            data.put("available", available);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.emitWindowEvent(EVENT_OFFERWALL_AVAILABILITY_CHANGED, data);
    }

    @Override
    public void onOfferwallOpened() {
        Log.d(TAG, "onOfferwallOpened: ");
        this.emitWindowEvent(EVENT_OFFERWALL_SHOWN, new JSONObject());
    }

    @Override
    public void onOfferwallShowFailed(IronSourceError ironSourceError) {
        Log.d(TAG, "onOfferwallShowFailed: ");
        this.emitWindowEvent(EVENT_OFFERWALL_SHOW_FAILED, createErrorJSON(ironSourceError));
    }

    @Override
    public boolean onOfferwallAdCredited(int credits, int totalCredits, boolean totalCreditsFlag) {
        Log.d(TAG, "onOfferwallAdCredited: ");
        JSONObject data = new JSONObject();

        try {
            data.put("credits", credits);
            data.put("totalCredits", totalCredits);
            data.put("totalCreditsFlag", totalCreditsFlag);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.emitWindowEvent(EVENT_OFFERWALL_CREDITED, data);

        return true;
    }

    @Override
    public void onGetOfferwallCreditsFailed(IronSourceError ironSourceError) {
        Log.d(TAG, "onGetOfferwallCreditsFailed: ");
        this.emitWindowEvent(EVENT_OFFERWALL_CREDIT_FAILED, createErrorJSON(ironSourceError));
    }

    @Override
    public void onOfferwallClosed() {
        Log.d(TAG, "onOfferwallClosed: ");
        this.emitWindowEvent(EVENT_OFFERWALL_CLOSED);
    }


    /**
     * ----------------------- BANNER ---------------------------
     */
    private void loadBannerAction(JSONObject args, final CallbackContext callbackContext) {
        Log.d(TAG, "loadBannerAction: ");
        final IronsourcePlugin self = this;

        this.getActivity().runOnUiThread(new Runnable() {

            public void run() {

                self.loadBanner();

                callbackContext.success("");
            }
        });

    }

    private void showBannerAction(JSONObject args, final CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, "showBannerAction: ");
//        final String pos = args.getString(0);
//        if (args.length() == 0){
//            int pos = 10;
//            int x = 0;
//            int y = 0;
//        }else if (args.length() == 1){
        final int position = args.getInt("position");
        final int x = args.getInt("x");
        final int y = args.getInt("y");
//            int w = args.getInt(3);
//            int h = args.getInt(4);
//        }
//
        // int x = args.optInt(1);
        //int y = args.optInt(2);
//

        Log.d(TAG, "showBannerAction: " + position);


        final IronsourcePlugin self = this;

        this.getActivity().runOnUiThread(new Runnable() {

            public void run() {
                self.showBanner(position, x, y);
                callbackContext.success("");

            }
        });

    }

    /**
     * Destroys IronSource Banner and removes it from the container
     */
    private void hideBannerAction(JSONObject args, final CallbackContext callbackContext) {
        Log.d(TAG, "hideBannerAction: ");
        final IronsourcePlugin self = this;

        this.getActivity().runOnUiThread(new Runnable() {

            public void run() {
                self.hideBanner();
                callbackContext.success("");
            }
        });
    }

    ;


    private void hideBanner() {
        Log.d(TAG, "hideBanner: ");
        if (bannerView != null) {
            this.autoShowBanner = false;
            _detachBanner();
//            this.pauseAdView(bannerView);
        }

    }


    private void pauseAdView() {
        Log.d(TAG, "pauseAdView: ");
    }


    private void loadBanner() {
        Log.d(TAG, "loadBanner: ");
        if (bannerView == null) {
            _createBanner();
            bannerVisible = false;

        } else {
            _detachBanner();
        }

        IronSource.loadBanner(bannerView);

    }






    private void showBanner(final int argPos, final int argX, final int argY) {
        Log.d(TAG, "showBanner: ......." + argPos + " " + argX + " " + argY);
//        Log.d(TAG, "showBanner: bannerView x, y, w, h:" + bannerView.getX() +","+ bannerView.getY() +","+ bannerView.getWidth() + "," + bannerView.getHeight());

        if (bannerView != null) {
            final Activity activity = this.getActivity();


            View mainView = this.getView();
            if (mainView == null) {
                Log.e(TAG, "Error: could not get main view");
            } else {
                Log.d(TAG, "webview class: " + mainView.getClass());
                Log.d(TAG, "showBanner: ....bannerVisible:" + this.bannerVisible);
                if (this.bannerVisible) {
                    _detachBanner();
                }


                ViewGroup rootView = (ViewGroup) mainView.getRootView();
//                int rw = rootView.getWidth();
//                int rh = rootView.getHeight();

                int ww = mainView.getWidth();
                int wh = mainView.getHeight();

//                int bw = this.bannerView.getWidth();
//                int bh = this.bannerView.getHeight();
                int bw = this.adWidth;
                int bh = this.adHeight;
                this.adPosition = (argPos == 0) ? this.adPosition : argPos;
                Log.d(TAG, String.format("show banner: x,y: (%d x %d)", bw, bh));

                Log.w(TAG, "show banner, overlap: " + this.overlap + ", position: " + this.adPosition);
                if (this.overlap) {
                    int x = 0, y = 0;
//                    int ww = mainView.getWidth();
//                    int wh = mainView.getHeight();
                    if (this.adPosition >= 1 && this.adPosition <= 9) {
                        switch ((this.adPosition - 1) % 3) {
                            case 0:
                                x = 0;
                                break;
                            case 1:
                                x = (ww - bw) / 2;
                                break;
                            case 2:
                                x = ww - bw;
                        }

                        switch ((this.adPosition - 1) / 3) {
                            case 0:
                                y = 0;
                                break;
                            case 1:
                                y = (wh - bh) / 2;
                                break;
                            case 2:
                                y = wh - bh;
                        }
                    } else if (this.adPosition == 10) {
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
                    if (this.overlapLayout == null) {
                        this.overlapLayout = new RelativeLayout(activity);
//                        this.overlapLayout.setBackgroundColor(Color.parseColor("#FF00FF"));
//                        this.overlapLayout.
                        rootView.addView(this.overlapLayout, new RelativeLayout.LayoutParams(-1, -1));
                        Log.d(TAG, "showBanner: overlaplayout add to rootView....");
                        this.overlapLayout.bringToFront();
                    }

//                    bw = rw;
//                    bh = 200;
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(bw, bh);
//                    x = y = 0;
                    Log.d(TAG, "showBanner: bannerView x,y,w,h:" + x + "," + y + "," + bw + "," + bh);


                    params.leftMargin = x;
                    params.topMargin = y;
//                    this.bannerView.setBackgroundColor(Color.parseColor("#FF00FF"));
                    this.overlapLayout.addView(this.bannerView, params);
                    this.bannerView.bringToFront();
                    this.parentView = this.overlapLayout;
                    Log.d(TAG, "showBanner: overlaplayout........");
                } else {
                    this.parentView = (ViewGroup) mainView.getParent();
                    if (!(this.parentView instanceof LinearLayout)) {
                        this.parentView.removeView(mainView);
                        this.splitLayout = new LinearLayout(this.getActivity());
                        this.splitLayout.setOrientation(LinearLayout.VERTICAL);
                        this.splitLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 0.0F));
                        mainView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 1.0F));
                        this.splitLayout.addView(mainView);
                        this.getActivity().setContentView(this.splitLayout);
                        this.parentView = this.splitLayout;
                    }

                    if (this.adPosition <= 3) {
                        this.parentView.addView(this.bannerView, 0);
                    } else {
                        this.parentView.addView(this.bannerView);
                    }
                }

                this.parentView.bringToFront();
                this.parentView.requestLayout();
                this.bannerView.setVisibility(View.VISIBLE);
                this.bannerVisible = true;
                this.resumeBannerView(this.bannerView);
                mainView.requestFocus();


                Log.d(TAG, "showBanner: .......end.....");

            }
        }
    }

    private void resumeBannerView(View view) {
        Log.d(TAG, "resumeBannerView: ");
//        view.resume();
    }

    private void _detachBanner() {
        Log.d(TAG, "_detachBanner: ");
        if (bannerView != null) {
            bannerView.setVisibility(View.INVISIBLE);
            this.bannerVisible = false;
            ViewGroup parentView = (ViewGroup) this.bannerView.getParent();
            if (parentView != null) {
                parentView.removeView(this.bannerView);
            }

        }
    }


    private void _createBanner() {
        Log.d(TAG, "_createBanner: ");
        final IronsourcePlugin self = this;

        // choose banner size
        ISBannerSize size = ISBannerSize.BANNER;

        _destroyBanner();

        // instantiate IronSourceBanner object, using the IronSource.createBanner API
        bannerView = IronSource.createBanner(self.getActivity(), size);

        Log.d(TAG, "_createBanner: bannerView x, y, w, h:" + bannerView.getX() + "," + bannerView.getY() + "," + bannerView.getWidth() + "," + bannerView.getHeight());

        if (bannerView != null) {

            bannerView.setBannerListener(new BannerListener() {

                @Override
                public void onBannerAdLoaded() {
                    Log.d(TAG, "onBannerAdLoaded: ");
                    Log.d(TAG, "onBannerAdLoaded: bannerView x, y, w, h:" + bannerView.getX() + "," + bannerView.getY() + "," + bannerView.getWidth() + "," + bannerView.getHeight());
//                    self.showBanner(8, 0, 0);
                    self.emitWindowEvent(EVENT_BANNER_DID_LOAD);
                }

                @Override
                public void onBannerAdLoadFailed(IronSourceError ironSourceError) {
                    Log.d(TAG, "onBannerAdLoadFailed: ");
                    self.emitWindowEvent(EVENT_BANNER_FAILED_TO_LOAD, createErrorJSON(ironSourceError));
//                    self.showBanner(8, 0, 0);

                }

                @Override
                public void onBannerAdClicked() {
                    Log.d(TAG, "onBannerAdClicked: ");
                    self.emitWindowEvent(EVENT_BANNER_DID_CLICK);
                }

                @Override
                public void onBannerAdScreenPresented() {
                    Log.d(TAG, "onBannerAdScreenPresented: ");
                    self.emitWindowEvent(EVENT_BANNER_WILL_PRESENT_SCREEN);
                }

                @Override
                public void onBannerAdScreenDismissed() {
                    Log.d(TAG, "onBannerAdScreenDismissed: ");
                    self.emitWindowEvent(EVENT_BANNER_DID_DISMISS_SCREEN);
                }

                @Override
                public void onBannerAdLeftApplication() {
                    Log.d(TAG, "onBannerAdLeftApplication: ");
                    self.emitWindowEvent(EVENT_BANNER_WILL_LEAVE_APPLICATION);
                }
            });
        }
    }


    private void _removeBanner() {
        Log.d(TAG, "_removeBanner: ");
        if (bannerView != null) {
            hideBanner();
            this._destroyBanner();
        }

        bannerVisible = false;
    }


    /**
     * Destory Banner
     */
    private void _destroyBanner() {
        Log.d(TAG, "_destroyBanner: ");
        if (bannerView != null) {
            IronSource.destroyBanner(bannerView);
            bannerView = null;
        }
    }

}

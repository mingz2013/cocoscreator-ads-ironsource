#import "AdMobPlugin.h"



// adtype use with event
static NSString *const ADTYPE_BANNER = @"banner";
static NSString *const ADTYPE_INTERSTITIAL = @"interstitial";
static NSString *const ADTYPE_NATIVE = @"native";
static NSString *const ADTYPE_REWARDVIDEO = @"rewardedvideo";

// events emit to js
static NSString *const EVENT_ON_AD_CLOSED = @"onAdClosed";
static NSString *const EVENT_ON_AD_FAILED_TO_LOAD = @"onAdFailedToLoad";
static NSString *const EVENT_ON_AD_LEFT_APPLICATION = @"onAdLeftApplication";
static NSString *const EVENT_ON_AD_OPENED = @"onAdOpened";
static NSString *const EVENT_ON_AD_LOADED = @"onAdLoaded";
static NSString *const EVENT_ON_AD_CLICKED = @"onAdClicked";
static NSString *const EVENT_ON_AD_IMPRESSION = @"onAdImpression";
static NSString *const EVENT_ON_REWARDED_VIDEO_STARTED = @"onRewardedVideoStarted";
static NSString *const EVENT_ON_REWARDED_VIDEO_COMPLETED = @"onRewardedVideoCompleted";
static NSString *const EVENT_ON_REWARDED = @"onRewarded";

 // adsize, banner
static NSString *const ADSIZE_BANNER = @"BANNER";//new AdSize(320, 50, "320x50_mb");
static NSString *const ADSIZE_FULL_BANNER = @"FULL_BANNER";//new AdSize(468, 60, "468x60_as");
static NSString *const ADSIZE_LARGE_BANNER = @"LARGE_BANNER";//new AdSize(320, 100, "320x100_as");
static NSString *const ADSIZE_LEADERBOARD = @"LEADERBOARD";//new AdSize(728, 90, "728x90_as");
static NSString *const ADSIZE_MEDIUM_RECTANGLE = @"MEDIUM_RECTANGLE";//new AdSize(300, 250, "300x250_as");
static NSString *const ADSIZE_WIDE_SKYSCRAPER = @"WIDE_SKYSCRAPER";//new AdSize(160, 600, "160x600_as");
static NSString *const ADSIZE_SMART_BANNER = @"SMART_BANNER";//new AdSize(-1, -2, "smart_banner");


// admob offical example test id
static NSString *const TEST_ADMOB_APP_ID = @"ca-app-pub-3940256099942544~3347511713";
static NSString *const TEST_ADMOB_UNIT_ID_BANNER = @"ca-app-pub-3940256099942544/6300978111";
static NSString *const TEST_ADMOB_UNIT_ID_INTERSTITIAL = @"ca-app-pub-3940256099942544/1033173712";
static NSString *const TEST_ADMOB_UNIT_ID_INTERSTITIAL_VIDEO = @"ca-app-pub-3940256099942544/8691691433";
static NSString *const TEST_ADMOB_UNIT_ID_REWARDED_VIDEO = @"ca-app-pub-3940256099942544/5224354917";
static NSString *const TEST_ADMOB_UNIT_ID_NATIVE_ADVANCED = @"ca-app-pub-3940256099942544/2247696110";
static NSString *const TEST_ADMOB_UNIT_ID_NATIVE_ADVANCED_VIDEO = @"ca-app-pub-3940256099942544/1044960115";


static NSString *const RUN_TYPE_PROD = @"RUN_TYPE_PROD";
// two ways to get test ads, use one of test units id. or enable test devices
static NSString *const RUN_TYPE_TEST_WITH_TEST_ID = @"RUN_TYPE_TEST_WITH_TEST_ID";
static NSString *const RUN_TYPE_TEST_WITH_DEVICE = @"RUN_TYPE_TEST_WITH_DEVICE";



// banner position
static  int BANNER_POSITION_NO_CHANGE = 0;
static  int BANNER_POSITION_TOP_LEFT = 1;
static  int BANNER_POSITION_TOP_CENTER = 2;
static  int BANNER_POSITION_TOP_RIGHT = 3;
static  int BANNER_POSITION_LEFT = 4;
static  int BANNER_POSITION_CENTER = 5;
static  int BANNER_POSITION_RIGHT = 6;
static  int BANNER_POSITION_BOTTOM_LEFT = 7;
static  int BANNER_POSITION_BOTTOM_CENTER = 8;
static  int BANNER_POSITION_BOTTOM_RIGHT = 9;
static  int BANNER_POSITION_POS_XY = 10;



@implementation AdMobPlugin



- (void) init : (NSDictionary *) args : (CallbackContext*)callbackContext{
    NSDictionary* params = args;
    self.testDevice = params[@"testDevice"];
//    self.mOverlap = params[@"overlap"];
    self.runType = params[@"runType"];
    if([self.runType isEqualToString:@"USER_COMPLETED_LEVEL"]){
//    if (self.runType.equals(RUN_TYPE_TEST_WITH_TEST_ID)) {
        self.admobAppId = TEST_ADMOB_APP_ID;
        self.admobUnitIdBanner = TEST_ADMOB_UNIT_ID_BANNER;
        self.admobUnitIdInterstitial = TEST_ADMOB_UNIT_ID_INTERSTITIAL;
        self.admobUnitIdInterstitialVideo = TEST_ADMOB_UNIT_ID_INTERSTITIAL_VIDEO;
        self.admobUnitIdRewardedVideo = TEST_ADMOB_UNIT_ID_REWARDED_VIDEO;
        self.admobUnitIdNativeAdvanced = TEST_ADMOB_UNIT_ID_NATIVE_ADVANCED;
        self.admobUnitIdNativeAdvancedVideo = TEST_ADMOB_UNIT_ID_NATIVE_ADVANCED_VIDEO;
    } else {
        self.admobAppId = params[@"admobAppId"];
        self.admobUnitIdBanner = params[@"admobUnitIdBanner"];
        self.admobUnitIdInterstitial = params[@"admobUnitIdInterstitial"];
        self.admobUnitIdInterstitialVideo = params[@"admobUnitIdInterstitialVideo"];
        self.admobUnitIdRewardedVideo = params[@"admobUnitIdRewardedVideo"];
        self.admobUnitIdNativeAdvanced = params[@"admobUnitIdNativeAdvanced"];
        self.admobUnitIdNativeAdvancedVideo = params[@"admobUnitIdNativeAdvancedVideo"];
    }


    // Initialize Google Mobile Ads SDK
    // Sample AdMob app ID: ca-app-pub-3940256099942544~1458002511
    [GADMobileAds configureWithApplicationID : self.admobAppId];


}



- (void) createInterstitial : (NSDictionary *)args : (CallbackContext*)callbackContext{

    self.interstitial = [[GADInterstitial alloc]
                         initWithAdUnitID:self.admobUnitIdInterstitial];
    self.interstitial.delegate = self;



}



- (void) loadInterstitial : (NSDictionary *)args : (CallbackContext*)callbackContext{
    GADRequest *request = [GADRequest request];
    [self.interstitial loadRequest:request];
}


- (void) isInterstitialLoading : (NSDictionary *)args : (CallbackContext*)callbackContext{

}
- (void) isInterstitialLoaded : (NSDictionary *)args : (CallbackContext*)callbackContext{

}
- (void) showInterstitial : (NSDictionary *)args : (CallbackContext*)callbackContext{
    if (self.interstitial.isReady) {
        [self.interstitial presentFromRootViewController:self.viewController];
    } else {
        NSLog(@"Ad wasn't ready");
    }
}


#pragma mark - interstitial delegate funtions

/// Tells the delegate an ad request succeeded.
- (void)interstitialDidReceiveAd : (GADInterstitial *)ad {
    NSLog(@"interstitialDidReceiveAd");
}

/// Tells the delegate an ad request failed.
- (void)interstitial : (GADInterstitial *)ad
didFailToReceiveAdWithError : (GADRequestError *)error {
    NSLog(@"interstitial:didFailToReceiveAdWithError: %@", [error localizedDescription]);
}

/// Tells the delegate that an interstitial will be presented.
- (void)interstitialWillPresentScreen : (GADInterstitial *)ad {
    NSLog(@"interstitialWillPresentScreen");
}

/// Tells the delegate the interstitial is to be animated off the screen.
- (void)interstitialWillDismissScreen : (GADInterstitial *)ad {
    NSLog(@"interstitialWillDismissScreen");
}

/// Tells the delegate the interstitial had been animated off the screen.
- (void)interstitialDidDismissScreen : (GADInterstitial *)ad {
    NSLog(@"interstitialDidDismissScreen");
}

/// Tells the delegate that a user click will open another app
/// (such as the App Store), backgrounding the current app.
- (void)interstitialWillLeaveApplication : (GADInterstitial *)ad {
    NSLog(@"interstitialWillLeaveApplication");
}



- (void) createRewardedVideo : (NSDictionary *)args : (CallbackContext*)callbackContext{
    [GADRewardBasedVideoAd sharedInstance].delegate = self;
}
- (void) loadRewardedVideo : (NSDictionary *)args : (CallbackContext*)callbackContext{
    [[GADRewardBasedVideoAd sharedInstance] loadRequest:[GADRequest request]
                                           withAdUnitID:self.admobUnitIdRewardedVideo];
}
- (void) showRewardedVideo : (NSDictionary *)args : (CallbackContext*)callbackContext{
    if ([[GADRewardBasedVideoAd sharedInstance] isReady]) {
        [[GADRewardBasedVideoAd sharedInstance] presentFromRootViewController:self.viewController];
    }
}

#pragma mark - reward video functions

- (void)rewardBasedVideoAd:(GADRewardBasedVideoAd *)rewardBasedVideoAd
   didRewardUserWithReward:(GADAdReward *)reward {
    NSString *rewardMessage =
    [NSString stringWithFormat:@"Reward received with currency %@ , amount %lf",
     reward.type,
     [reward.amount doubleValue]];
    NSLog(rewardMessage);
}

- (void)rewardBasedVideoAdDidReceiveAd:(GADRewardBasedVideoAd *)rewardBasedVideoAd {
    NSLog(@"Reward based video ad is received.");
}

- (void)rewardBasedVideoAdDidOpen:(GADRewardBasedVideoAd *)rewardBasedVideoAd {
    NSLog(@"Opened reward based video ad.");
}

- (void)rewardBasedVideoAdDidStartPlaying:(GADRewardBasedVideoAd *)rewardBasedVideoAd {
    NSLog(@"Reward based video ad started playing.");
}

- (void)rewardBasedVideoAdDidCompletePlaying:(GADRewardBasedVideoAd *)rewardBasedVideoAd {
    NSLog(@"Reward based video ad has completed.");
}

- (void)rewardBasedVideoAdDidClose:(GADRewardBasedVideoAd *)rewardBasedVideoAd {
    NSLog(@"Reward based video ad is closed.");
}

- (void)rewardBasedVideoAdWillLeaveApplication:(GADRewardBasedVideoAd *)rewardBasedVideoAd {
    NSLog(@"Reward based video ad will leave application.");
}

- (void)rewardBasedVideoAd:(GADRewardBasedVideoAd *)rewardBasedVideoAd
    didFailToLoadWithError:(NSError *)error {
    NSLog(@"Reward based video ad failed to load.");
}





- (void) createBanner : (NSDictionary *)args : (CallbackContext*)callbackContext{
    // In this case, we instantiate the banner with desired ad size.
    self.bannerView = [[GADBannerView alloc]
                       initWithAdSize:kGADAdSizeBanner];

    self.bannerView.adUnitID = @"ca-app-pub-3940256099942544/2934735716";
    self.bannerView.rootViewController = self.viewController;
    self.bannerView.delegate = self;


}

- (void)addBannerViewToView:(UIView *)bannerView {
    bannerView.translatesAutoresizingMaskIntoConstraints = NO;
    [self.viewController.view addSubview:bannerView];
    [self.viewController.view addConstraints:@[
                                [NSLayoutConstraint constraintWithItem:bannerView
                                                             attribute:NSLayoutAttributeBottom
                                                             relatedBy:NSLayoutRelationEqual
                                                                toItem:self.viewController.bottomLayoutGuide
                                                             attribute:NSLayoutAttributeTop
                                                            multiplier:1
                                                              constant:0],
                                [NSLayoutConstraint constraintWithItem:bannerView
                                                             attribute:NSLayoutAttributeCenterX
                                                             relatedBy:NSLayoutRelationEqual
                                                                toItem:self.viewController.view
                                                             attribute:NSLayoutAttributeCenterX
                                                            multiplier:1
                                                              constant:0]
                                ]];
}


- (void) loadBanner:(NSDictionary *)args : (CallbackContext*)callbackContext{
    [self.bannerView loadRequest:[GADRequest request]];
}
- (void) showBanner:(NSDictionary *)args : (CallbackContext*)callbackContext{
    [self addBannerViewToView:self.bannerView];
}
- (void) hideBanner:(NSDictionary *)args : (CallbackContext*)callbackContext{

}


#pragma banner functions

/// Tells the delegate an ad request loaded an ad.
- (void)adViewDidReceiveAd:(GADBannerView *)adView {
    NSLog(@"adViewDidReceiveAd");
}

/// Tells the delegate an ad request failed.
- (void)adView:(GADBannerView *)adView
didFailToReceiveAdWithError:(GADRequestError *)error {
    NSLog(@"adView:didFailToReceiveAdWithError: %@", [error localizedDescription]);
}

/// Tells the delegate that a full-screen view will be presented in response
/// to the user clicking on an ad.
- (void)adViewWillPresentScreen:(GADBannerView *)adView {
    NSLog(@"adViewWillPresentScreen");
}

/// Tells the delegate that the full-screen view will be dismissed.
- (void)adViewWillDismissScreen:(GADBannerView *)adView {
    NSLog(@"adViewWillDismissScreen");
}

/// Tells the delegate that the full-screen view has been dismissed.
- (void)adViewDidDismissScreen:(GADBannerView *)adView {
    NSLog(@"adViewDidDismissScreen");
}

/// Tells the delegate that a user click will open another app (such as
/// the App Store), backgrounding the current app.
- (void)adViewWillLeaveApplication:(GADBannerView *)adView {
    NSLog(@"adViewWillLeaveApplication");
}




//- (void) fireEvent:(NSString *)obj event:(NSString *)eventName withData:(NSString *)jsonStr {
//  NSString* js;
//  if(obj && [obj isEqualToString:@"window"]) {
//    js = [NSString stringWithFormat:@"var evt=document.createEvent(\"UIEvents\");evt.initUIEvent(\"%@\",true,false,window,0);window.dispatchEvent(evt);", eventName];
//  } else if(jsonStr && [jsonStr length]>0) {
//    js = [NSString stringWithFormat:@"javascript:cordova.fireDocumentEvent('%@',%@);", eventName, jsonStr];
//  } else {
//    js = [NSString stringWithFormat:@"javascript:cordova.fireDocumentEvent('%@');", eventName];
//  }
//  [self.commandDelegate evalJs:js];
//}

@end


#import "AdsPlugin.h"
//#import <AppLovinSDK/AppLovinSDK.h>
#import <GoogleMobileAds/GoogleMobileAds.h>
//#import GoogleMobileAds;
//#import <GoogleMobileAds>;

@interface AdMobPlugin : AdsPlugin <GADInterstitialDelegate, GADRewardBasedVideoAdDelegate, GADBannerViewDelegate>


// real ids
@property (copy, nonatomic) NSString *admobAppId;
@property (copy, nonatomic) NSString* admobUnitIdBanner;
@property (copy, nonatomic) NSString*admobUnitIdInterstitial;
@property (copy, nonatomic) NSString* admobUnitIdInterstitialVideo;
@property (copy, nonatomic) NSString* admobUnitIdRewardedVideo;
@property (copy, nonatomic) NSString*admobUnitIdNativeAdvanced;
@property (copy, nonatomic) NSString* admobUnitIdNativeAdvancedVideo;
// test device, use to test with device
@property (copy, nonatomic)  NSString* testDevice;// = "33BE2250B43518CCDA7DE426D04EE231";

// run type
@property (copy, nonatomic)  NSString* runType;// = RUN_TYPE_PROD;


// interstitial
@property(nonatomic, strong) GADInterstitial *interstitial;
// banner
@property(nonatomic, strong) GADBannerView *bannerView;



- (void) init : (NSDictionary *)args : (CallbackContext*)callbackContext;
- (void) createInterstitial : (NSDictionary *)args : (CallbackContext*)callbackContext;
- (void) loadInterstitial : (NSDictionary *)args : (CallbackContext*)callbackContext;
- (void) isInterstitialLoading : (NSDictionary *)args : (CallbackContext*)callbackContext;
- (void) isInterstitialLoaded : (NSDictionary *)args : (CallbackContext*)callbackContext;
- (void) showInterstitial : (NSDictionary *)args : (CallbackContext*)callbackContext;

- (void) createRewardedVideo : (NSDictionary *)args : (CallbackContext*)callbackContext;
- (void) loadRewardedVideo : (NSDictionary *)args : (CallbackContext*)callbackContext;
- (void) showRewardedVideo : (NSDictionary *)args : (CallbackContext*)callbackContext;

- (void) createBanner : (NSDictionary *)args : (CallbackContext*)callbackContext;
- (void) loadBanner : (NSDictionary *)args : (CallbackContext*)callbackContext;
- (void) showBanner :(NSDictionary *)args :(CallbackContext*)callbackContext;
- (void) hideBanner :(NSDictionary *)args :(CallbackContext*)callbackContext;



@end

#import <Cordova/CDV.h>
//#import <AppLovinSDK/AppLovinSDK.h>
//#import <GoogleMobileAds/GoogleMobileAds.h>
@import GoogleMobileAds;

@interface AdMobPlugin : CDVPlugin <GADInterstitialDelegate, GADRewardBasedVideoAdDelegate, GADBannerViewDelegate>


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



- (void) init:(CDVInvokedUrlCommand*)command;
- (void) createInterstitial:(CDVInvokedUrlCommand*)command;
- (void) loadInterstitial:(CDVInvokedUrlCommand*)command;
- (void) isInterstitialLoading:(CDVInvokedUrlCommand*)command;
- (void) isInterstitialLoaded:(CDVInvokedUrlCommand*)command;
- (void) showInterstitial:(CDVInvokedUrlCommand*)command;

- (void) createRewardedVideo:(CDVInvokedUrlCommand*)command;
- (void) loadRewardedVideo:(CDVInvokedUrlCommand*)command;
- (void) showRewardedVideo:(CDVInvokedUrlCommand*)command;

- (void) createBanner:(CDVInvokedUrlCommand*)command;
- (void) loadBanner:(CDVInvokedUrlCommand*)command;
- (void) showBanner:(CDVInvokedUrlCommand*)command;
- (void) hideBanner:(CDVInvokedUrlCommand*)command;



@end

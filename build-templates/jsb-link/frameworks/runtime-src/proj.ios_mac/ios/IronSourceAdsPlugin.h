#import <Foundation/Foundation.h>
//#import <Cordova/CDV.h>
#import <IronSource/IronSource.h>
#import "AdsPlugin.h"

@interface IronSourceAdsPlugin : AdsPlugin <ISRewardedVideoDelegate, ISBannerDelegate, ISOfferwallDelegate, ISInterstitialDelegate>

@property(nonatomic, strong) ISBannerView *bannerView;
@property(nonatomic) bool loadingBanner;
@property(nonatomic, strong) NSString *bannerPosition;
@property(nonatomic, strong) UIViewController *bannerController;

- (Boolean)exec : (NSString*) action : (NSDictionary*)args : (CallbackContext*)callbackContext;


- (void)initAction : (NSDictionary *)args : (CallbackContext*)callbackContext;

- (void)setDynamicUserId : (NSDictionary *)args : (CallbackContext*)callbackContext;

- (void)setConsent : (NSDictionary *)args : (CallbackContext*)callbackContext;

- (void)validateIntegration : (NSDictionary *)args : (CallbackContext*)callbackContext;

- (void)showRewardedVideo : (NSDictionary *)args : (CallbackContext*)callbackContext;

- (void)hasRewardedVideo : (NSDictionary *)args : (CallbackContext*)callbackContext;

- (void)loadBanner : (NSDictionary *)args : (CallbackContext*)callbackContext;

- (void)showBanner : (NSDictionary *)args : (CallbackContext*)callbackContext;

- (void)hideBanner : (NSDictionary *)args : (CallbackContext*)callbackContext;

- (void)showOfferwall : (NSDictionary *)args : (CallbackContext*)callbackContext;

- (void)hasOfferwall : (NSDictionary *)args : (CallbackContext*)callbackContext;

- (void)loadInterstitial : (NSDictionary *)args : (CallbackContext*)callbackContext;

- (void)hasInterstitial : (NSDictionary *)args : (CallbackContext*)callbackContext;

- (void)showInterstitial : (NSDictionary *)args : (CallbackContext*)callbackContext;

@end

#import "IronSourceAdsPlugin.h"
//#import <Cordova/CDVViewController.h>
#import "JsbCall.h"
static NSString *const EVENT_INTERSTITIAL_LOADED = @"interstitialLoaded";
static NSString *const EVENT_INTERSTITIAL_SHOWN = @"interstitialShown";
static NSString *const EVENT_INTERSTITIAL_SHOW_FAILED = @"interstitialShowFailed";
static NSString *const EVENT_INTERSTITIAL_CLICKED = @"interstitialClicked";
static NSString *const EVENT_INTERSTITIAL_CLOSED = @"interstitialClosed";
static NSString *const EVENT_INTERSTITIAL_WILL_OPEN = @"interstitialWillOpen";
static NSString *const EVENT_INTERSTITIAL_FAILED_TO_LOAD = @"interstitialFailedToLoad";

static NSString *const EVENT_OFFERWALL_CLOSED = @"offerwallClosed";
static NSString *const EVENT_OFFERWALL_CREDIT_FAILED = @"offerwallCreditFailed";
static NSString *const EVENT_OFFERWALL_CREDITED = @"offerwallCreditReceived";
static NSString *const EVENT_OFFERWALL_SHOW_FAILED = @"offerwallShowFailed";
static NSString *const EVENT_OFFERWALL_SHOWN = @"offerwallShown";
static NSString *const EVENT_OFFERWALL_AVAILABILITY_CHANGED = @"offerwallAvailabilityChanged";

static NSString *const EVENT_REWARDED_VIDEO_FAILED = @"rewardedVideoFailed";
static NSString *const EVENT_REWARDED_VIDEO_REWARDED = @"rewardedVideoRewardReceived";
static NSString *const EVENT_REWARDED_VIDEO_ENDED = @"rewardedVideoEnded";
static NSString *const EVENT_REWARDED_VIDEO_STARTED = @"rewardedVideoStarted";
static NSString *const EVENT_REWARDED_VIDEO_AVAILABILITY_CHANGED = @"rewardedVideoAvailabilityChanged";
static NSString *const EVENT_REWARDED_VIDEO_CLOSED = @"rewardedVideoClosed";
static NSString *const EVENT_REWARDED_VIDEO_OPENED = @"rewardedVideoOpened";

static NSString *const EVENT_BANNER_DID_LOAD = @"bannerDidLoad";
static NSString *const EVENT_BANNER_FAILED_TO_LOAD = @"bannerFailedToLoad";
static NSString *const EVENT_BANNER_DID_CLICK = @"bannerDidClick";
static NSString *const EVENT_BANNER_WILL_PRESENT_SCREEN = @"bannerWillPresentScreen";
static NSString *const EVENT_BANNER_DID_DISMISS_SCREEN = @"bannerDidDismissScreen";
static NSString *const EVENT_BANNER_WILL_LEAVE_APPLICATION = @"bannerWillLeaveApplication";


#define USERID @"demoapp"

@implementation IronSourceAdsPlugin

#pragma mark - IronSourceAdsPlugin

- (Boolean)exec : (NSString*) action : (NSDictionary*)args : (CallbackContext*)callbackContext{
    NSLog(@"IronSourceAdsPlugin.exec: %@", action);

    if([action isEqualToString: @"init"]){
        [self initAction: args:callbackContext];
    }else if([action isEqualToString: @"setDynamicUserId"]){
        [self setDynamicUserId:args:callbackContext];
    }else if([action isEqualToString: @"setConsent"]){
        [self setConsent:args:callbackContext];
    }else if([action isEqualToString: @"showRewardedVideo"]){
        [self showRewardedVideo:args:callbackContext];
    }else if([action isEqualToString: @"hasRewardedVideo"]){
        [self hasRewardedVideo:args:callbackContext];
    }else if([action isEqualToString: @"loadBanner"]){
        [self loadBanner:args:callbackContext];
    }else if([action isEqualToString: @"showBanner"]){
        [self showBanner:args:callbackContext];
    }else if([action isEqualToString: @"hideBanner"]){
        [self hideBanner:args:callbackContext];
//    }else if([action isEqualToString: @"destroyBanner"]){
//        [self destroyBanner:args:callbackContext];
    }else if([action isEqualToString: @"hasOfferwall"]){
        [self hasOfferwall:args:callbackContext];
    }else if([action isEqualToString: @"showOfferwall"]){
        [self showOfferwall:args:callbackContext];
    }else if([action isEqualToString: @"showInterstitial"]){
        [self showInterstitial:args:callbackContext];
    }else if([action isEqualToString: @"loadInterstitial"]){
        [self loadInterstitial:args:callbackContext];
    }else if([action isEqualToString: @"hasInterstitial"]){
        [self hasInterstitial:args :callbackContext];
    }else{

        NSLog(@"IronSourceAdsPlugin.exec, Unknown action: %s", action);
        return false;
    }
    return true;
}


/**
 * Init
 * @params {CDVInvokedUrlCommand} command
 */
- (void)initAction : (NSDictionary *)args : (CallbackContext*)callbackContext
{
    NSString *appKey = [args objectForKey:@"appKey"];
    NSString *userId = [args objectForKey:@"userId"];

    [ISSupersonicAdsConfiguration configurations].useClientSideCallbacks = @(YES);

    [IronSource setRewardedVideoDelegate:self];
    [IronSource setOfferwallDelegate:self];
    [IronSource setBannerDelegate:self];
    [IronSource setInterstitialDelegate:self];

    NSLog(@"initWithKey....before....");
    [IronSource initWithAppKey:appKey];
    NSLog(@"initWithKey...end...");

    if ([userId length] == 0)
    {
        userId = [IronSource advertiserId];
    }

    if ([userId length] == 0)
    {
        userId = USERID;
    }

    self.bannerController = [[UIViewController alloc] init];

    // After setting the delegates you can go ahead and initialize the SDK.
    [IronSource setUserId:userId];


    // Send callback successfull
    [callbackContext success];
}

- (void)setDynamicUserId : (NSDictionary *)args : (CallbackContext*)callbackContext
{
    NSString *userId = [args objectForKey:@"userId"];

    [IronSource setDynamicUserId:userId];

    // Send callback successfull
    [callbackContext success];
}

- (void)setConsent : (NSDictionary *)args :  (CallbackContext*)callbackContext
{
    BOOL consent = [args objectForKey:@"consent"];

    [IronSource setConsent:consent];

    // Send callback successfull
    [callbackContext success];
}



- (void)listSubviewsOfView : (UIView *)view {

    // Get the subviews of the view
    NSArray *subviews = [view subviews];

    // Return if there are no subviews
    if ([subviews count] == 0) return; // COUNT CHECK LINE

    for (UIView *subview in subviews) {

        // Do what you want to do with the subview
        NSLog(@"%@", subview);

        // List the subviews of subview
        [self listSubviewsOfView:subview];
    }
}

/**
 * Validates integration
 * @param {CDVInvokedUrlCommand} command
 */
- (void)validateIntegration : (NSDictionary *)args : (CallbackContext*)callbackContext
{

    [ISIntegrationHelper validateIntegration];

    // Send callback successfull
    [callbackContext success];
}

#pragma mark - Rewarded Video Delegate Functions


/**
 * Checks for if rewarded video placement is capped
 */
- (void)isRewardedVideoCappedForPlacement : (NSDictionary *)args : (CallbackContext*)callbackContext
{
    NSString *placement = [args objectForKey:@"placement"];
    BOOL capped = [IronSource isRewardedVideoCappedForPlacement:placement];

    NSDictionary *data = @{
                           @"capped" : @(capped)
                           };

    [callbackContext success : data];
}


/**
 * Show rewarded video
 */
- (void)showRewardedVideo : (NSDictionary *)args : (CallbackContext*)callbackContext{
    NSString *placement = [args objectForKey:@"placement"];

    if( placement == nil || [placement length] == 0)
    {
        [IronSource showRewardedVideoWithViewController:self.viewController];
    }
    else
    {
        [IronSource showRewardedVideoWithViewController:self.viewController placement:placement];
    }


    // Send callback successfull
   [callbackContext success];
}

// This method lets you know whether or not there is a video
// ready to be presented. It is only after this method is invoked
// with 'hasAvailableAds' set to 'YES' that you can should 'showRV'.
- (void)rewardedVideoHasChangedAvailability : (BOOL)available
{

    NSLog(@"rewardedVideoHasChangedAvailability: %s", available ? "true" : "false");
    NSLog(@"%s", __PRETTY_FUNCTION__);

    NSDictionary *data = @{
                           @"available" : @(available)
                           };

    [self emitWindowEvent : EVENT_REWARDED_VIDEO_AVAILABILITY_CHANGED withData : data];
}

// This method checks if rewarde video is available
- (void)hasRewardedVideo : (NSDictionary *)args : (CallbackContext*)callbackContext
{

    BOOL available = [IronSource hasRewardedVideo];
    NSDictionary *data = @{
                           @"available" : @(available)
                           };
    // Send callback successfull
    [callbackContext success : data];
}

// This method gets invoked after the user has been rewarded.
- (void)didReceiveRewardForPlacement:(ISPlacementInfo *)placementInfo
{
    NSLog(@"%s", __PRETTY_FUNCTION__);

    NSDictionary *data = @{
                           @"placement" : @{
                                   @"name" : placementInfo.placementName,
                                   @"reward" : placementInfo.rewardName,
                                   @"amount" : placementInfo.rewardAmount
                                   }
                           };

    [self emitWindowEvent : EVENT_REWARDED_VIDEO_REWARDED withData : data];
}

// This method gets invoked when there is a problem playing the video.
// If it does happen, check out 'error' for more information and consult
// our knowledge center for help.
- (void)rewardedVideoDidFailToShowWithError : (NSError *)error
{

    NSLog(@"%s", __PRETTY_FUNCTION__);

    NSDictionary *data = @{
                           @"error" : @{
                                   @"code" : @(error.code),
                                   @"message" : error.description
                                   }
                           };

    [self emitWindowEvent : EVENT_REWARDED_VIDEO_FAILED withData : data];
}

// This method gets invoked when we take control, but before
// the video has started playing.
- (void)rewardedVideoDidOpen
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent : EVENT_REWARDED_VIDEO_OPENED];
}

// This method gets invoked when we return controlback to your hands.
// We chose to notify you about rewards here and not in 'didReceiveRewardForPlacement'.
// This is because reward can occur in the middle of the video.
- (void)rewardedVideoDidClose
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent : EVENT_REWARDED_VIDEO_CLOSED];
}

// This method gets invoked when the video has started playing.
- (void)rewardedVideoDidStart
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent : EVENT_REWARDED_VIDEO_STARTED];
}

// This method gets invoked when the video has stopped playing.
- (void)rewardedVideoDidEnd
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent:EVENT_REWARDED_VIDEO_ENDED];
}

//Invoked when the end user clicked on the RewardedVideo ad
- (void)didClickRewardedVideo:(ISPlacementInfo *)placementInfo
{

}


- (void)loadBanner : (NSDictionary *)args : (CallbackContext*)callbackContext
{
    NSString *placement = [args objectForKey:@"placement"];
    NSString *size = [args objectForKey:@"size"];
    NSString *position = [args objectForKey:@"position"];
    ISBannerSize *adSize = ISBannerSize_SMART;

    // We call destroy banner before loading a new banner
    if (self.bannerView) {
        [self destroyBanner];
    }

    if([size isEqualToString:@"large"])
    {
        adSize = ISBannerSize_LARGE;
    }
    else if([size isEqualToString:@"rectangle"])
    {
        adSize = ISBannerSize_RECTANGLE;
    }
    else if([size isEqualToString:@"tablet"])
    {
        adSize = ISBannerSize_LARGE;
    }

    self.bannerPosition = position;

    if( placement == nil || [placement length] == 0)
    {
        [IronSource loadBannerWithViewController:self.bannerController size:adSize];
    }
    else
    {
        [IronSource loadBannerWithViewController:self.bannerController size:adSize placement:placement];
    }

    // Send callback successfull
    [callbackContext success];
}

#pragma mark - Banner Delegate Functions

// Show banner
- (void)showBanner : (NSDictionary *)args : (CallbackContext*)callbackContext
{
    if(self.bannerView)
    {
        [self.viewController.view addSubview:self.bannerView];
        [self.viewController.view bringSubviewToFront:self.bannerView];
    }

    // Send callback successfull
    [callbackContext success];
}

- (void)hideBanner : (NSDictionary *)args : (CallbackContext*)callbackContext
{
    if(self.bannerView)
    {
        [self.bannerView removeFromSuperview];
    }

    // Send callback successfull
    [callbackContext success];
}

- (void)destroyBanner
{
    if (self.bannerView) {
        [IronSource destroyBanner:self.bannerView];
        self.bannerView = nil;
    }
}

// Banner dismissed screen
- (void)bannerDidDismissScreen
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent : EVENT_BANNER_DID_DISMISS_SCREEN];
}

//
- (void)bannerDidFailToLoadWithError : (NSError *)error
{

    NSLog(@"%s", __PRETTY_FUNCTION__);

    NSDictionary *data = @{
                           @"error" : @{
                                   @"code" : @(error.code),
                                   @"message" : error.description
                                   }
                           };


    for (UIView *subUIView in self.bannerController.view.subviews ) {
        [subUIView removeFromSuperview];
    }

    [self listSubviewsOfView:self.bannerController.view];


    [self emitWindowEvent : EVENT_BANNER_FAILED_TO_LOAD withData:data];
}

- (void)bannerDidLoad : (ISBannerView *)bannerView
{
    // We call destroy banner before loading a new banner
    if (self.bannerView) {
        [self destroyBanner];
    }

    self.bannerView = bannerView;

    CGFloat xOffset = .0f;
    CGFloat yOffset = .0f;

    CGFloat bannerHeight    = bannerView.frame.size.height;
    CGFloat bannerWidth     = bannerView.frame.size.width;

    UIScreen* mainScreen = [UIScreen mainScreen];

    CGFloat screenHeight = mainScreen.bounds.size.height;
    CGFloat screenWidth = mainScreen.bounds.size.width;

    if ([self.bannerPosition isEqualToString:@"top"])
    {
        yOffset = 0;
    }
    else if([self.bannerPosition isEqualToString:@"bottom"])
    {
        yOffset = screenHeight - bannerHeight;
    }
    else if([self.bannerPosition isEqualToString:@"center"])
    {
        yOffset = (screenHeight - bannerHeight) / 2;
    }

    xOffset = (screenWidth - bannerWidth) / 2;

    CGRect bannerRect = CGRectMake(xOffset, yOffset, bannerWidth, bannerHeight);

    bannerView.frame = bannerRect;

    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent:EVENT_BANNER_DID_LOAD];
}


- (void)bannerWillLeaveApplication
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent:EVENT_BANNER_WILL_LEAVE_APPLICATION];
}

- (void)bannerWillPresentScreen
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent:EVENT_BANNER_WILL_PRESENT_SCREEN];
}

- (void)didClickBanner
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent:EVENT_BANNER_DID_CLICK];
}

#pragma mark - Offerwall Delegate Functions

// This method checks if rewarde video is available
- (void)hasOfferwall : (NSDictionary *)args : (CallbackContext*)callbackContext
{

    BOOL available = [IronSource hasOfferwall];
    NSDictionary *data = @{
                           @"available" : @(available)
                           };
    // Send callback successfull
    [callbackContext success : data];
}

- (void)showOfferwall : (NSDictionary *)args : (CallbackContext*)callbackContext
{

    NSString *placement = [args objectForKey:@"placement"];

    if( placement == nil || [placement length] == 0)
    {
        [IronSource showOfferwallWithViewController:self.viewController];
    }
    else
    {
        [IronSource showOfferwallWithViewController:self.viewController placement:placement];
    }

    // Send callback successfull
    [callbackContext success];
}

- (void)didFailToReceiveOfferwallCreditsWithError:(NSError *)error
{

    NSLog(@"%s", __PRETTY_FUNCTION__);

    NSDictionary *data = @{
                           @"error" : @{
                                   @"code" : @(error.code),
                                   @"message" : error.description
                                   }
                           };

    [self emitWindowEvent : EVENT_OFFERWALL_CREDIT_FAILED withData:data];
}

- (BOOL)didReceiveOfferwallCredits:(NSDictionary *)creditInfo
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent : EVENT_OFFERWALL_CREDITED withData : creditInfo];
    return YES;
}

- (void)offerwallDidClose
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent:EVENT_OFFERWALL_CLOSED];
}

- (void)offerwallDidFailToShowWithError:(NSError *)error
{
    NSLog(@"%s", __PRETTY_FUNCTION__);

    NSDictionary *data = @{
                           @"error" : @{
                                   @"code" : @(error.code),
                                   @"message" : error.description
                                   }
                           };

    [self emitWindowEvent : EVENT_OFFERWALL_SHOW_FAILED withData:data];
}

- (void)offerwallDidShow
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent:EVENT_OFFERWALL_SHOWN];
}

- (void)offerwallHasChangedAvailability:(BOOL)available
{
    NSLog(@"%s", __PRETTY_FUNCTION__);

    NSDictionary *data = @{
                           @"available" : @(available)
                           };

    [self emitWindowEvent:EVENT_OFFERWALL_AVAILABILITY_CHANGED withData:data];
}

#pragma mark - Intersitial Delegate Functions

- (void)hasInterstitial : (NSDictionary *)args : (CallbackContext*)callbackContext
{

    BOOL available = [IronSource hasInterstitial];


    NSDictionary *data = @{
                           @"available" : @(available)
                           };

    NSLog(@"IronSourceAdsPlugin hasInterstitial %s", available? "true" : "false");
    // Send callback successfull
    [callbackContext success:data];
}

- (void)loadInterstitial : (NSDictionary *)args : (CallbackContext*)callbackContext
{

    NSLog(@"%s", __PRETTY_FUNCTION__);
    [IronSource loadInterstitial];

    // Send callback successfull
    [callbackContext success];
}

- (void)showInterstitial:(NSDictionary *)args :  (CallbackContext*)callbackContext
{

    NSString *placement = [args objectForKey:@"placement"];

    if( placement == nil || [placement length] == 0)
    {
        [IronSource showInterstitialWithViewController:self.viewController];
    }
    else
    {
        [IronSource showInterstitialWithViewController:self.viewController placement:placement];
    }

    // Send callback successfull
    [callbackContext success];
}

- (void)didClickInterstitial
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent:EVENT_INTERSTITIAL_CLICKED];
}

- (void)interstitialDidClose
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent:EVENT_INTERSTITIAL_CLOSED];
}

- (void)interstitialDidFailToLoadWithError:(NSError *)error
{
    NSLog(@"%s", __PRETTY_FUNCTION__);

    NSDictionary *data = @{
                           @"error" : @{
                                   @"code" : @(error.code),
                                   @"message" : error.description
                                   }
                           };

    [self emitWindowEvent:EVENT_INTERSTITIAL_FAILED_TO_LOAD withData:data];
}

- (void)interstitialDidFailToShowWithError:(NSError *)error
{
    NSLog(@"%s", __PRETTY_FUNCTION__);

    NSDictionary *data = @{
                           @"error" : @{
                                   @"code" : @(error.code),
                                   @"message" : error.description
                                   }
                           };

    [self emitWindowEvent:EVENT_INTERSTITIAL_SHOW_FAILED withData:data];
}

- (void)interstitialDidLoad
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent:EVENT_INTERSTITIAL_LOADED];
}

- (void)interstitialDidOpen
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent:EVENT_INTERSTITIAL_WILL_OPEN];
}

- (void)interstitialDidShow
{
    NSLog(@"%s", __PRETTY_FUNCTION__);
    [self emitWindowEvent:EVENT_INTERSTITIAL_SHOWN];
}

@end


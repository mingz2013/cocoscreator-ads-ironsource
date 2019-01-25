//
//  NSObject+AdsPlugin.h
//  hello_world-mobile
//
//  Created by zhaojm on 2019/1/25.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface AdsPluginManager: NSObject


-(void) initAD : (UIViewController*)view;

- (void)showRewardedVideo;
- (void)showOfferwall;
- (void) showInterstitial;
- (void)loadBanner;


@end

NS_ASSUME_NONNULL_END

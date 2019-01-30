//
//  NSObject+AdsPlugin.h
//  hello_world-mobile
//
//  Created by zhaojm on 2019/1/25.
//

#import <Foundation/Foundation.h>
#include "CallbackContext.h"
NS_ASSUME_NONNULL_BEGIN

@interface AdsPlugin: NSObject

@property(nonatomic, strong) UIViewController *viewController;


- (void) emitWindowEvent : (NSString*) event;
-(void) emitWindowEvent : (NSString*)event withData : (NSDictionary*)data;

- (Boolean)exec : (NSString*) action : (NSDictionary*)args : (CallbackContext*)callbackContext;
- (void)initPlugin : (UIViewController*) viewController;

// iOS应用生命周期
- (void)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions;
- (void)applicationDidBecomeActive:(UIApplication *)application;
- (void)applicationWillResignActive:(UIApplication *)application;
- (void)applicationDidEnterBackground:(UIApplication *)application;
- (void)applicationWillEnterForeground:(UIApplication *)application;
- (void)applicationWillTerminate:(UIApplication *)application;

@end

NS_ASSUME_NONNULL_END

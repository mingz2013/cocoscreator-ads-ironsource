
#import "AdsPluginManager.h"


#include "platform/CCApplication.h"
#include "platform/ios/CCEAGLView-ios.h"
#include "IronSourceAdsPlugin.h"
//#include "AdMobPlugin.h"

@implementation AdsPluginManager


#pragma mark -
#pragma mark Singleton

static AdsPluginManager *mInstace = nil;

+ (instancetype)getInstance {
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        mInstace = [[super allocWithZone:NULL] init];
//        [mInstace initPlugins];
    });
    return mInstace;
}
+ (id)allocWithZone:(struct _NSZone *)zone {
    return [AdsPluginManager getInstance];
}

+ (id)copyWithZone:(struct _NSZone *)zone {
    return [AdsPluginManager getInstance];
}

-(void) initPlugins: (UIViewController*)viewController{
    NSLog(@"AdsPluginManager.initPlugins...");
    self.viewController = viewController;

    self.plugins = [NSMutableDictionary dictionaryWithCapacity:10];
    [self.plugins setObject:[[IronSourceAdsPlugin alloc] init] forKey:@"IronsourcePlugin"];
//    [self.plugins setObject:[AdMobPlugin new] forKey:@"AdMobPlugin"];

    NSEnumerator * enumeratorValue = [self.plugins objectEnumerator];
    for (AdsPlugin * plugin in enumeratorValue) {
        [plugin initPlugin : viewController];
    }
    return;
}


- (void)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions{

        NSEnumerator * enumeratorValue = [self.plugins objectEnumerator];
        for (AdsPlugin * plugin in enumeratorValue) {
            [plugin application:application didFinishLaunchingWithOptions:launchOptions];
        }
}
- (void)applicationDidBecomeActive:(UIApplication *)application{

    NSEnumerator * enumeratorValue = [self.plugins objectEnumerator];
    for (AdsPlugin * plugin in enumeratorValue) {
        [plugin applicationDidBecomeActive: application];
    }
}
- (void)applicationWillResignActive:(UIApplication *)application{

    NSEnumerator * enumeratorValue = [self.plugins objectEnumerator];
    for (AdsPlugin * plugin in enumeratorValue) {
        [plugin applicationWillResignActive: application];
    }
}
- (void)applicationDidEnterBackground:(UIApplication *)application{

    NSEnumerator * enumeratorValue = [self.plugins objectEnumerator];
    for (AdsPlugin * plugin in enumeratorValue) {
        [plugin applicationDidEnterBackground: application];
    }
}
- (void)applicationWillEnterForeground:(UIApplication *)application{

    NSEnumerator * enumeratorValue = [self.plugins objectEnumerator];
    for (AdsPlugin * plugin in enumeratorValue) {
        [plugin applicationWillEnterForeground: application];
    }
}
- (void)applicationWillTerminate:(UIApplication *)application{

    NSEnumerator * enumeratorValue = [self.plugins objectEnumerator];
    for (AdsPlugin * plugin in enumeratorValue) {
        [plugin applicationWillTerminate: application];
    }
}



- (Boolean)exec: (NSString*) service : (NSString*) action : (NSDictionary*)args : (CallbackContext*)callbackContext{
    NSLog(@"AdsPluginManager.exec...%@, %@", service, action);
//    NSEnumerator * enumeratorValue = [plugins objectEnumerator];
//    for (AdsPlugin * plugin in enumeratorValue) {
//        [plugin exec: service:action:args:callbackContext];
//    }

    AdsPlugin*plugin = [self.plugins objectForKey:service];
    [plugin exec:action :args : callbackContext];
    return true;

}

@end

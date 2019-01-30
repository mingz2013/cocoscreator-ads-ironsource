
#import "AdsPlugin.h"


#include "platform/CCApplication.h"
#include "platform/ios/CCEAGLView-ios.h"
#include "JsbCall.h"

@implementation AdsPlugin

-(void) emitWindowEvent : (NSString*) event{

    NSMutableDictionary *data = [NSMutableDictionary dictionary];

    [self emitWindowEvent : event  withData : data];
}

-(void) emitWindowEvent : (NSString*)event withData : (NSDictionary*)data{
    [JsbCall emitWindowEvent:event :data];
}


- (Boolean)exec : (NSString*) action : (NSDictionary*)args : (CallbackContext*)callbackContext{
    return false;
}


- (void)initPlugin : (UIViewController*) viewController{
    self.viewController = viewController;
}

// iOS应用生命周期
- (void)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions{

}
- (void)applicationDidBecomeActive:(UIApplication *)application{

}
- (void)applicationWillResignActive:(UIApplication *)application{

}
- (void)applicationDidEnterBackground:(UIApplication *)application{

}
- (void)applicationWillEnterForeground:(UIApplication *)application{

}
- (void)applicationWillTerminate:(UIApplication *)application{

}

@end

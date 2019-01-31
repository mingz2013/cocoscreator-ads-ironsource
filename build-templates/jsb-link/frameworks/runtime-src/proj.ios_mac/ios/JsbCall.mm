
#import "JsbCall.h"
#include <string.h>
#import "cocos2d.h"
#include "cocos/scripting/js-bindings/jswrapper/SeApi.h"
#include <stdarg.h>
#include <stdlib.h>
#include <stdio.h>
#include <string>
#include "AdsPluginManager.h"
@implementation JsbCall




+ (BOOL) exec : (NSString*) service  withAction : (NSString*) action  withParams : (NSString*) params  withCallbackId : (NSString*) callbackId{
    NSLog(@"JsbCall.exec :---> %@, %@, %@, %@", service, action, params, callbackId);
    CallbackContext* callbackContext = [[CallbackContext alloc] init];
//    [callbackContext setCallbackId:callbackId];
    callbackContext.callbackId = callbackId;

    NSData * jsonData = [params dataUsingEncoding:NSUTF8StringEncoding];
    NSError*err = nil;
    NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:jsonData options:NSJSONReadingMutableContainers error:&err];
    if(err){
        NSLog(@"err:->%@", [err localizedDescription]);

        NSDictionary *data = @{
                               @"error" : @{
                                       @"code" : @"",
                                       @"message" : @"params error"
                                       }
                               };
        [callbackContext failure:data];
        return false;
    }

    return [self _exec : service : action : dict : callbackContext];

}
+ (Boolean)_exec : (NSString*) service : (NSString*) action : (NSDictionary*)args : (CallbackContext*)callbackContext{
    NSLog(@"JsbCall._exec...");
    AdsPluginManager *adsPluginManager = [AdsPluginManager getInstance];
    return [adsPluginManager exec: service:action:args:callbackContext];
}

+ (void) emitWindowEvent : (NSString*)event : (NSDictionary*) data{

    
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:data options : 0 error : 0];
    NSString *dataStr = [[NSString alloc] initWithData : jsonData encoding : NSUTF8StringEncoding];

    std::string jsCallStr = cocos2d::StringUtils::format("window.JsbNativeCall.emitWindowEvent('%s', '%s');",[event UTF8String], [dataStr UTF8String]);
    
    NSLog(@"JsbCall.emitWindowEvent: %s", jsCallStr.c_str());

    se::ScriptEngine* se = se::ScriptEngine::getInstance();
    se->evalString(jsCallStr.c_str());
}


+ (void) successCall : (NSString*)callbackId : (NSDictionary*) params{
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:params options : 0 error : 0];
    NSString *dataStr = [[NSString alloc] initWithData : jsonData encoding : NSUTF8StringEncoding];

    std::string jsCallStr = cocos2d::StringUtils::format("window.JsbNativeCall.callBackCallSuccess('%s', '%s');",[callbackId UTF8String], [dataStr UTF8String]);
    
    
    NSLog(@"JsbCall.successCall: %s", jsCallStr.c_str());

    se::ScriptEngine* se = se::ScriptEngine::getInstance();
    se->evalString(jsCallStr.c_str());
}
+ (void) failureCall : (NSString*)callbackId : (NSDictionary*) params{
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:params options : 0 error : 0];
    NSString *dataStr = [[NSString alloc] initWithData : jsonData encoding : NSUTF8StringEncoding];
    std::string jsCallStr = cocos2d::StringUtils::format("window.JsbNativeCall.callBackCallFailure('%s', '%s');",[callbackId UTF8String], [dataStr UTF8String]);
    
    NSLog(@"JsbCall.failureCall: %s", jsCallStr.c_str());

    se::ScriptEngine* se = se::ScriptEngine::getInstance();
    se->evalString(jsCallStr.c_str());
}

@end

#import <Foundation/Foundation.h>
#import "CallbackContext.h"

NS_ASSUME_NONNULL_BEGIN

@interface JsbCall: NSObject
+ (void) successCall : (NSString*)callbackId : (NSDictionary*) params;
+ (void) failureCall : (NSString*)callbackId : (NSDictionary*) params;


+ (BOOL) exec : (NSString*) service  withAction : (NSString*) action  withParams : (NSString*) params  withCallbackId : (NSString*) callbackId;
+ (Boolean)_exec : (NSString*) service : (NSString*) action : (NSDictionary*)args : (CallbackContext*)callbackContext;

+ (void) emitWindowEvent : (NSString*)event : (NSDictionary*) data;

@end

NS_ASSUME_NONNULL_END

//
//  NSObject+CallbackContext.h
//  BrixSaga-mobile
//
//  Created by zhaojm on 2019/1/28.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface CallbackContext : NSObject

@property (nonatomic, strong) NSString *callbackId;


- (void)success;
- (void)failure;
- (void)success:(NSDictionary *)params;
- (void)failure:(NSDictionary *)params;
//- (void) setCallbackId:(NSString *)pCallbackId;
@end

NS_ASSUME_NONNULL_END

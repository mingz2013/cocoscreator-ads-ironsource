#import "CallbackContext.h"
#import "JsbCall.h"


@implementation CallbackContext

- (void)success{

    NSMutableDictionary *params = [NSMutableDictionary dictionary];

    [JsbCall successCall: [self callbackId] : params];
}
- (void)failure{
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    [JsbCall failureCall: [self callbackId] : params];
}

- (void)success:(NSDictionary *)params{
    [JsbCall successCall:[self callbackId] : params];
}

- (void)failure:(NSDictionary *)params{
    [JsbCall failureCall:[self callbackId] : params];
}

//- (void) setCallbackId: (NSString *) pCallbackId {
//    self.callbackId = pCallbackId;
//}


@end

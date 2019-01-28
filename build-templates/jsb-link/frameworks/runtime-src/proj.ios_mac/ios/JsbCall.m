
#import "JsbCall.h"
#include <string.h>
#import "cocos2d.h"
#include "cocos/scripting/js-bindings/jswrapper/SeApi.h"



@implementation JsbCall

+ (void)JSFuctionCall:(NSString *)jsonData{
    NSDictionary *tempDict = [self dictionaryWithJsonString:jsonData];
    NSString *cmdStr = (NSString*)[tempDict objectForKey :@"cmd"];
    cmdStr = [cmdStr stringByAppendingString:[NSString stringWithFormat:@"%@",@":"]];
    SEL sel = NSSelectorFromString(cmdStr);
    [self performSelector:sel withObject:jsonData];
}

+ (void)ADCallBack:(NSMutableDictionary *)dict{
    NSString *cmdStr = dict[@"type"];
    NSString *errCode = dict[@"code"];
    NSMutableDictionary *temp = [NSMutableDictionary dictionaryWithObjectsAndKeys:errCode,@"errCode",cmdStr, @"cmdStr",nil];
    NSString * contentStr = [self convertToJsonData:temp];
    [self callJsEngineCallBack:contentStr];
}

+ (void)callJsEngineCallBack: (NSString*) contentStr{
    NSLog(@"callJsEngineCallBack...");
    std::string param001 = [contentStr UTF8String];
    std::string jsCallStr = cocos2d::StringUtils::format("%s('%s');","JsToOcBridge.onJsToOcBridgeCallback", param001.c_str());
    NSLog(@"jsCallStr = %s", jsCallStr.c_str());

    se::ScriptEngine* se = se::ScriptEngine::getInstance();
    se->evalString(jsCallStr.c_str());
}

@end

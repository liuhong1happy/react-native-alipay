#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>
#import <AlipaySDK/AlipaySDK.h>

#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif

@interface RNAlipayApi : NSObject <RCTBridgeModule>

@end
  

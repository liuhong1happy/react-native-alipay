//
//  RCTAlipayAPI.h
//  RCTAlipayAPI
//
//  Created by 刘红 on 2017/6/2.
//  Copyright © 2017年 Holly Liu. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AlipaySDK/AlipaySDK.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTLog.h>

@interface RCTAlipayAPI : NSObject <RCTBridgeModule>

@property (nonatomic ,strong) RCTResponseSenderBlock mCallback;

@end

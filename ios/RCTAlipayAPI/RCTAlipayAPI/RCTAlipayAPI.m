//
//  RCTAlipay.m
//  RCTAlipay
//
//  Created by Holly Liu on 2017/6/3.
//  Copyright © 2017年 Holly Liu. All rights reserved.
//

#import "RCTAlipayAPI.h"

@implementation RCTAlipayAPI {
    RCTPromiseResolveBlock mResolve;
    RCTPromiseRejectBlock mReject;
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(pay:(NSString *)orderString resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    if (mResolve) {
        NSMutableDictionary *body = @{@"status":@"busy"}.mutableCopy;
        body[@"msg"] = @"当前已经有支付在调用";
        mResolve(@[body]);
        return;
    }
    NSString *appScheme = @"alisdkdemo";
    mResolve = resolve;
    mReject = reject;
    // NOTE: 调用支付结果开始支付
    [[AlipaySDK defaultService] payOrder:orderString fromScheme:appScheme callback:^(NSDictionary *resultDic) {
        NSLog(@"reslut = %@",resultDic);
        NSString* resultStatus = [resultDic objectForKey:@"resultStatus"];
        NSString* memo = [resultDic objectForKey:@"memo"];
        Boolean isSuccessed =  [resultStatus isEqualToString:@"9000"];
        
        NSMutableDictionary *body = @{@"status": isSuccessed ? @"success": @"error"}.mutableCopy;
        body[@"isSuccess"] = isSuccessed ? @"1" : @"0";
        body[@"msg"] = isSuccessed ? @"支付成功" : memo;
        body[@"data"] = resultDic;
        self->mResolve(@[body]);
    }];
}

@end

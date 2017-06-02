//
//  RCTAlipayAPI.m
//  RCTAlipayAPI
//
//  Created by 刘红 on 2017/6/2.
//  Copyright © 2017年 Holly Liu. All rights reserved.
//

#import "RCTAlipayAPI.h"

@implementation RCTAlipayAPI

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(pay:(NSString *)orderString callback:(RCTResponseSenderBlock)callback)
{
    if (mCallback) {
        NSMutableDictionary *body = @{@"status":@"busy"}.mutableCopy;
        body[@"msg"] = @"当前已经有支付在调用";
        callback(body);
        return;
    }
    NSString *appScheme = @"alisdkdemo";
    // NOTE: 调用支付结果开始支付
    [[AlipaySDK defaultService] payOrder:orderString fromScheme:appScheme callback:^(NSDictionary *resultDic) {
        NSLog(@"reslut = %@",resultDic);
        NSString* resultStatus = [resultDic objectForKey:@"resultStatus"];
        NSString* memo = [resultDic objectForKey:@"memo"];
        Boolean isSuccessed =  [resultStatus isEqualToString:@"9000"];
        
        NSMutableDictionary *body = @{@"status": isSuccessed ? @"success": @"error"}.mutableCopy;
        body[@"msg"] = isSuccessed ? @"支付成功" : memo;
        body["@data"] = resultDic;
        callback(body);
    }];
}
@end

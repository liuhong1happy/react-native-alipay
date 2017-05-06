package cn.reactnative.modules.alipay;


import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.widget.Toast;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;

import com.tencent.connect.UserInfo; 
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.Date;

public class AlipayModule extends ReactContextBaseJavaModule {

    public AlipayModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "RCTAlipayAPI";
    }

    @ReactMethod
    public void pay(String info, Promise promise){
        final Activity activity = getCurrentActivity();
        final String orderInfo = info;   // 订单信息
        private Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                Result result = new Result((String) msg.obj);
                Toast.makeText( activity, result.getResult(),
                Toast.LENGTH_LONG).show();

                promise.resolve(result);
            };
        };

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                String result = alipay.payV2(orderInfo,true);
 
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
         // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


}

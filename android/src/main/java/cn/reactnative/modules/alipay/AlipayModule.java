package cn.reactnative.modules.alipay;


import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.facebook.react.bridge.Callback;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;

import org.json.JSONObject;

import com.alipay.sdk.app.PayTask;

import java.util.Date;
import java.util.Map;

public class AlipayModule extends ReactContextBaseJavaModule {

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private Activity mActivity = null;
    private Callback mCallback = null;

    public AlipayModule(ReactApplicationContext context) {
        super(context);
        mActivity = getCurrentActivity();
    }

    @Override
    public String getName() {
        return "RCTAlipayAPI";
    }

    @ReactMethod
    public void pay(String info, final Callback callback){
        if(mCallback!=null) {
            // 已经有支付在调用了
            WritableMap map = Arguments.createMap();
            map.putString("status", "busy");
            map.putString("msg", "当前已经有支付在调用");
            callback.invoke(map);
            return;
        }
        final Activity activity = getCurrentActivity();
        final String orderInfo = info;   // 订单信息

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo,true);

                PayResult payResult = new PayResult(result);

                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                String memo = payResult.getMemo();
                Boolean isSuccessed = TextUtils.equals(resultStatus, "9000");

                WritableMap map = Arguments.createMap();
                map.putString("status",  isSuccessed ? "success" : "error");
                map.putString("msg", isSuccessed ? "支付成功" : memo);
                map.putString("data", resultInfo);
                mCallback.invoke(map);
                mCallback = null;
            }
        };
         // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
        mCallback = callback;
    }
}

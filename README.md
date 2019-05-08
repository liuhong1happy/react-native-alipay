# react-native-alipay

安卓支付宝react-native支付插件

## 安装下载
```js
npm install --save @17ued/react-native-alipay

react-native link @17ued/react-native-alipay
```
## 调用
```js
import RNAlipayApi from 'reactnative-alipay'
RNAlipayApi.pay(orderInfo, (res)=> {
    // res.status 可能是三个值'success/error/busy'
    // res.msg  消息为"支付成功"或者支付宝返回的memo
    // res.data 数据根据实际情况确定
})
```
## 联系

Email: [liuhong1.happy@163.com](mailto:liuhong1.happy@163.com)
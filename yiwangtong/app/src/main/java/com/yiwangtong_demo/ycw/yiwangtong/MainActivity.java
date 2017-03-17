package com.yiwangtong_demo.ycw.yiwangtong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private YWTWebView webview;
    /**签约地址*/
    private static final String signUrl = "http://121.15.180.66:801/mobilehtml/DebitCard/M_NetPay/OneNetRegister/NP_BindCard.aspx";
    /**支付地址地址*/
    private static final String payUrl = "http://121.15.180.66:801/NetPayment/BaseHttp.dll?MB_EUserPay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        webview = (YWTWebView) findViewById(R.id.webview);
        LoadUrl();
    }

    private void LoadUrl() {
        String jsondata = "jsonRequestData="+getJsonRequestData("2017031700","10.00","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","20170317001","20170317002");
        webview.postUrl(payUrl,jsondata.getBytes());
    }

    /**
     * 获得支付请求的参数
     * @param orderId 订单号
     * @param due 金额
     * @param signInformUrl 签约成功通知后台的地址
     * @param payInformUrl 支付成功通知后台的地址
     * @param returnUrl 支付成功的返回商户的地址
     * @param agrNo 协议号
     * @param merchantSerialNo 协议开通的流水号
     * @return
     */
    private String getJsonRequestData(String orderId,String due,String signInformUrl,String payInformUrl,String returnUrl,String agrNo,String merchantSerialNo){
        YWTBaseJsonRequestData<YWTPayReqData> requestData = new YWTBaseJsonRequestData<>();
        YWTPayReqData ywtPayReqData = new YWTPayReqData(
                YWTUtil.getCurrentTime(),
                YWTConfig.branchNo,
                YWTConfig.merchentNo,
                YWTUtil.getYWTOrderTime(),
                orderId,
                due,
                signInformUrl,
                payInformUrl,
                agrNo,
                merchantSerialNo,
                returnUrl
        );
        requestData.setSign(YWTUtil.getSign(ywtPayReqData));
        requestData.setReqData(ywtPayReqData);
        String json = new Gson().toJson(requestData);
        System.out.println(json);
        return json;
    }

}

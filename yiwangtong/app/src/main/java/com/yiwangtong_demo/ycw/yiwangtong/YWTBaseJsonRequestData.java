package com.yiwangtong_demo.ycw.yiwangtong;

import java.io.Serializable;

/**
 * Created by ycw on 2017/3/16.
 */

public class YWTBaseJsonRequestData<T> implements Serializable{

    String version = "1.0";//接口版本号
    String charset = "UTF-8";//参数编码
    String sign;//报文签名
    String signType = "SHA-256";//签名算法
    T reqData;//请求数据

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

    public void setReqData(T reqData) {
        this.reqData = reqData;
    }
}

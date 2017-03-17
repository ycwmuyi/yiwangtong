package com.yiwangtong_demo.ycw.yiwangtong;

/**
 * Created by ycw on 2017/3/16.
 */

public class YWTSignReqData{

    /**
     * 请求时间,商户发起该请求的当前时间，精确到秒。格式：yyyyMMddHHmmss
     */
    public String dateTime = "";

    /**
     * 协议开通请求流水号,商户生成，同一交易日期唯一，长度不超过20位，数字字母都可以，建议纯数字
     */
    public String merchantSerialNo = "";//

    /**
     * 客户协议号。必须为纯数字串，不超过30位。
     *未签约（首次支付）客户，填写新协议号，用于协议开通；
     *已签约（再次支付）客户，填写该客户已有的协议号。商户必须对协议号进行管理，确保客户与协议号一一对应。
     */
    public String agrNo = "";

    /**
     * 商户分行号，4位数字
     */
    public String branchNo = "";

    /**
     * 商户号，6位数字
     */
    public String merchantNo = "";

    /**
     * 商户用户的手机号
     */
    public String mobile = "";

    /**
     * 用于标识商户用户的唯一ID。
     * 商户系统内用户唯一标识，不超过20位，数字字母都可以，建议纯数字
     */
    public String userID = "";


    /**
     * 经度，商户app获取的手机定位数据
     */
    public String lon = "";
    public String lat = "";

    /**
     * 用户在商户系统内风险等级标识
     */
    public String riskLevel = "";

    /**
     * 商户接收成功签约结果通知的地址。
     */
    public String noticeUrl = "";

    /**
     * 成功签约结果通知附加参数,该参数在发送成功签约结果通知时，将原样返回商户
     * 注意：该参数可为空，商户如果需要不止一个参数，可以自行把参数组合、拼装，但组合后的结果不能带有‘&’字符。
     */
    public String noticePara = "";

    /**
     * 返回商户地址,签约成功页面上“返回商户”按钮跳转地址，默认值：http://CMBNPRM，采用默认值的需要商户app拦截该请求，自行决定跳转交互
     */
    public String returnUrl = "";


    public YWTSignReqData(String dateTime, String merchantSerialNo, String agrNo, String branchNo, String merchantNo, String noticeUrl, String returnUrl, String noticePara) {
        this.dateTime = dateTime;
        this.merchantSerialNo = merchantSerialNo;
        this.agrNo = agrNo;
        this.branchNo = branchNo;
        this.merchantNo = merchantNo;
        this.noticeUrl = noticeUrl;
        this.returnUrl = returnUrl;
        this.noticePara = noticePara;
    }
}

package com.yiwangtong_demo.ycw.yiwangtong;

/**
 * Created by ycw on 2017/3/16.
 */

public class YWTPayReqData {

    /**
     * 请求时间,
     *格式：yyyyMMddHHmmss
     *含义：商户发起该请求的当前时间，精确到秒
     */
    public String dateTime="";

    /**
     * 商户分行号，4位数字
     */
    public String branchNo="";

    /**
     * 商户号，6位数字
     */
    public String merchantNo="";

    /**
     * 订单日期,格式：yyyyMMdd
     */
    public String date="";

    /**
     * 订单号,
     * 10位数字，由商户生成，一天内不能重复。
     * 订单日期+订单号唯一定位一笔订单
     */
    public String orderNo="";

    /**
     * 金额,
     * 格式：xxxx.xx
     * 固定两位小数，最大11位整数
     */
    public String amount="";


    /**
     * (选填)
     * 过期时间跨度,
     * 必须为大于零的整数，单位为分钟。
     * 该参数指定当前支付请求必须在指定时间跨度内完成（从系统收到支付请求开始计时），否则按过期处理。一般适用于航空客票等对交易完成时间敏感的支付请求
     */
    public String expireTimeSpan="";

    /**
     * 成功支付结果通知地址,商户接收成功支付结果通知的地址。
     */
    public String payNoticeUrl="";

    /**
     * (选填)
     *成功支付结果通知附加参数,
     *该参数在发送成功支付结果通知时，将原样返回商户
     *注意：该参数可为空，商户如果需要不止一个参数，可以自行把参数组合、拼装，但组合后的结果不能带有’&’字符。
     */
    public String payNoticePara = "";


    /**
     *  (选填)
     * 成功页返回商户地址,支付成功页面上“返回商户”按钮跳转地址。为空则不显示“返回商户”按钮。原生APP可传入一个特定地址（例如:Http://CMBNPRM），并拦截处理自行决定跳转交互。
     */
    public String returnUrl = "";


    /**
     *  (选填)
     *商户取得的客户IP，如果有多个IP用逗号”,”分隔。
     */
    public String clientIP = "";

    /**
     * （选填）
     * 允许支付的卡类型,
     *默认对支付卡种不做限制，储蓄卡和信用卡均可支付
     *A:储蓄卡支付，即禁止信用卡支付
     */
    public String cardType = "";

    /**
     * 客户协议号。必须为纯数字串，不超过30位。
     * 未签约（首次支付）客户，填写新协议号，用于协议开通；
     * 已签约（再次支付）客户，填写该客户已有的协议号。商户必须对协议号进行管理，确保客户与协议号一一对应。
     */
    public String agrNo = "";


    /**
     * 协议开通请求流水号，开通协议时必填。
     */
    public String merchantSerialNo = "";

    /**
     *（选填）
     *用于标识商户用户的唯一ID。
     *商户系统内用户唯一标识，不超过20位，数字字母都可以，建议纯数字
     */
    public String userID = "";

    /**
     * （选填）
     * 商户用户的手机号
     */
    public String mobile = "";

    /**
     * （选填）
     * 经度，商户app获取的手机定位数据，如30.949505
     */
    public String lon = "";
    public String lat = "";

    /**
     * （选填）
     * 风险等级:用户在商户系统内风险等级标识
     */
    public String riskLevel = "";

    /**
     * (C)
     * 成功签约结果通知地址:
     * 首次签约，必填。
     * 商户接收成功签约结果通知的地址。
     */
    public String signNoticeUrl = "";


    /**
     * （选填）
     * 成功签约结果通知附加参数:
     * 该参数在发送成功签约结果通知时，将原样返回商户
     * 注意：该参数可为空，商户如果需要不止一个参数，可以自行把参数组合、拼装，但组合后的结果不能带有’&’字符。
     */
    public String signNoticePara="";

    /**
     * （选填）
     * json格式写入的扩展信息，并使用extendInfoEncrypType指定的算法加密
     * 使用详见扩展信息
     * 注注意：1.加密后的密文必须转换为16进制字符串
     * 2.如果扩展信息字段非空，该字段必填
     */
    public String extendInfo = "";


    /**
     * （选填）
     * 扩展信息的加密算法
     * 扩展信息加密类型，取值为RC4或DES
     * 加密密钥：取值为RC4时，密钥为商户支付密钥；
     * 取值为DES时，密钥为商户支付密钥的前8位，不足8位则右补0
     * 注意：如果扩展信息字段非空，该字段必填
     */
    public String extendInfoEncrypType = "";


    public YWTPayReqData(String dateTime, String branchNo, String merchantNo, String date, String orderNo, String amount, String payNoticeUrl, String returnUrl, String agrNo, String merchantSerialNo, String signNoticeUrl) {
        this.dateTime = dateTime;
        this.branchNo = branchNo;
        this.merchantNo = merchantNo;
        this.date = date;
        this.orderNo = orderNo;
        this.amount = amount;
        this.payNoticeUrl = payNoticeUrl;
        this.returnUrl = returnUrl;
        this.agrNo = agrNo;
        this.merchantSerialNo = merchantSerialNo;
        this.signNoticeUrl = signNoticeUrl;
    }
}

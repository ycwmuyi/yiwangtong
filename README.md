# yiwangtongDemo
招商银行一网通支付demo

博客地址：<http://www.jianshu.com/p/add98309716d>
### 使用方法
只要把YWTConfig内的参数换成你自己的就可以了
    
    public class YWTConfig {

    /**
     * 一网通支付的秘钥
     */
    public static final String privateKey = "xxxxx";

    /**商户分行号*/
    public static final String branchNo = "xxxx";

    /**商户号*/
    public static final String merchentNo = "xxxx";


    }
    

        
### “一网通”与支付宝支付，微信支付的差异 ###

已支付宝为例解释一下第三方app是如何调取支付宝完成支付动作的，微信和支付宝大体逻辑相似，有些不一样的地方后面会做说明。

“支付宝”支付分两种情况：
#### 你的手机已安装支付宝app ######
当第三方app发起支付动作，第三方app内集成的支付宝的sdk会通过进程间的通讯把相关的支付信息传给支付宝app,支付宝app完成支付动作后，再通过进程间的通讯告诉第三方app支付的结果。（支付宝实现进程间的通讯是通过handler实现，而微信是通过aidl实现，个人觉得支付宝的实现方式更利于我们程序员开发）
#### 你的手机没有安装支付宝ap ######
那这个时候怎么办呢？当然不用担心，支付宝的sdk里集成了一个h5页面，如果发现你的手机没有安装支付宝app,那这个时候它就会唤起这个h5页面让用户通过这个h5页面完成支付动作，当然这种方式肯定是没有直接用支付宝app方便的。

说了这么多好像跟“一网通”没有半毛钱关系，先别急，现在开始来说说“一网通”。其实“一网通”和我上面说的两种情况中的第二种是一样的，不同的是支付宝已经把他封装成sdk,可以很方便的提供给开发者使用，而“一网通”只是提供了一个键盘的sdk其他的相关逻辑（如：webview的加载，签名，加密，支付结果的回调出里）都要开发者自行解决。说道这是不是感觉有点坑。

## “一网通”开发步骤 ##

#### 1.接入“一网通”的键盘
这里其实没什么好讲的官方有提供demo<http://121.15.180.72/OpenAPI2/Download.aspx>
原理是通过重写WebViewClient的shouldOverrideUrlLoading(WebView view, String url)的方法（这个在 android5.0以后就被shouldOverrideUrlLoading(WebViewWebResourceRequest)取代了）。

     
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        // 使用当前的WebView加载页面
        CMBKeyboardFunc kbFunc = new CMBKeyboardFunc((Activity) view.getContext());
        if(kbFunc.HandleUrlCall(view, request.getUrl().toString()) == false) {
            return super.shouldOverrideUrlLoading(view, view.getUrl());
        }else {
            return true;
        }
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        DebugLog.e("shouldOverrideUrlLoading: "+url);
        // 使用当前的WebView加载页面
        CMBKeyboardFunc kbFunc = new CMBKeyboardFunc((Activity) view.getContext());
        if(kbFunc.HandleUrlCall(view, url) == false) {
            return super.shouldOverrideUrlLoading(view, url);
        }else{
            return true;
        }
    }

#### 2,如何加载WebView
因为现在有很多app开发是h5和native的混合开发，加载的“一网通”支付链接是通过form请求
 
    <form action="请求地址" method="post" >
    <input type="hidden" name="jsonRequestData" value='以上json字符串' />
    </form>
如果是h5跳转用上面的方式
如果是native加载webview使用下面的方式
  
    String jsondata =   "jsonRequestData="+josn参数;
    webview.postUrl(payUrl,jsondata.getBytes());

payUrl是“一网通”的接口链接
 
#### 3，签名的生成
“一网通”要求将所有的请求参数按英文字母的升序排列拼接再加上私钥后生成签名，然后将所有参数生成json格式请求。这边我是将所有参数放到一个javabean中，然后通过反射的方式获取所有的字段和字段值的list,然后对list进行排序，最后遍历拼接，看代码吧。
    
     /**
     * 获得一网通接口的相关参数的签名
     * @param obj
     * @return
     */
    public static String getSign(Object obj){
        Field[] fields =obj.getClass().getDeclaredFields();
        List<String> valueNameList = new ArrayList<String>();
        Map<String,String> valueMap = new HashMap<>();
        for(int i= 0;i<fields.length;i++){
            try {
                System.out.println("name:"+fields[i].getName()+" value:"+fields[i].get(obj));
                valueNameList.add(fields[i].getName());
                valueMap.put(fields[i].getName(),  fields[i].get(obj)==null?"":(String)fields[i].get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println("参数个数："+valueNameList.size());
        //按英文字母升序排序
        Collections.sort(valueNameList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i<valueNameList.size();i++) {
            String valueName = valueNameList.get(i);
            String value = valueMap.get(valueName);
            sb.append(valueName+"="+value+"&");
        }
        sb.append(YWTConfig.privateKey);
        System.out.println(sb.toString());
        // 创建加密对象
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 传入要加密的字符串,按指定的字符集将字符串转换为字节流
        try {
            messageDigest.update(sb.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte byteBuffer[] = messageDigest.digest();
        // 將 byte数组转换为16进制string
        String sign = hexString(byteBuffer);
        System.out.println(sign.toString());
        return sign;

    }

最后通过gson输出对应json格式的数据就好了。

#### 4.监听支付成功

![1489738192(1).png](http://upload-images.jianshu.io/upload_images/3977236-125a5df6d99e16ce.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

再请求支付的参数中有这样一个参数returnUrl,这是一个跳转链接，当完成支付时“一网通”会跳转到这个链接，这对webApp固然是很好的，但是如果是原生app意味着我必须通过监听webview的链接跳转，当监听到的跳转链接是returnUrl时就意味着支付成功了。（个人建议可以监听onPageStart()方法来进行判断时候支付完成，当然如果有大神还有更好的方法欢迎指教）

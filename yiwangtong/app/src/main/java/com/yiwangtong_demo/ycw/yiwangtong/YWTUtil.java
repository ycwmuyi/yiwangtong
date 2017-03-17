package com.yiwangtong_demo.ycw.yiwangtong;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ycw on 2017/3/16.
 */

public class YWTUtil {

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

    /**
     * 将byte转换成16进制的字符串
     * @param baSrc
     * @return
     */
    public static String hexString(byte[] baSrc) {
        if (baSrc == null) {
            return "";
        }
        int nByteNum = baSrc.length;
        StringBuilder sbResult = new StringBuilder(nByteNum * 2);

        for (int i = 0; i < nByteNum; i++){
            char chHex;

            byte btHigh = (byte)((baSrc[i] & 0xF0) >> 4);
            if (btHigh < 10)
            {
                chHex = (char)('0' + btHigh);
            }
            else
            {
                chHex = (char)('A' + (btHigh - 10));
            }
            sbResult.append(chHex);

            byte btLow = (byte)(baSrc[i] & 0x0F);
            if (btLow < 10)
            {
                chHex = (char)('0' + btLow);
            }
            else
            {
                chHex = (char)('A' + (btLow - 10));
            }
            sbResult.append(chHex);
        }

        return sbResult.toString();
    }


    /**
     * 根据指定格式输出 时间
     * @return
     */
    public static String getCurrentTime(){
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf0.format(new Date());
    }

    /**
     * 获取订单日期
     * @return
     */
    public static String getYWTOrderTime(){
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyyMMdd");
        return sdf0.format(new Date());
    }


}

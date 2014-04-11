package com.gxx.oa.utils;

import com.gxx.oa.interfaces.BaseInterface;
import org.apache.log4j.Logger;

import java.net.URLEncoder;

/**
 * 短信服务工具类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-30 12:09
 */
public class SMSUtil implements BaseInterface {
    /**
     * 日志处理器
     */
    static Logger logger = Logger.getLogger(SMSUtil.class);

    /**
     * 发送短信
     * @param phone
     * @param content
     * @return
     * @throws Exception
     */
    public static int sendSMS(String phone, String content) throws Exception {
        if(true){
            return 1;//写死成功 todo
        }
        String smsUid = PropertyUtil.getInstance().getProperty(SMS_UID);
        String smsKey = PropertyUtil.getInstance().getProperty(SMS_KEY);
        String url = "http://gbk.sms.webchinese.cn/?Uid=" + URLEncoder.encode(smsUid, "GBK") + "&Key=" +
                smsKey + "&smsMob=" + phone + "&smsText=" + URLEncoder.encode(content, "GBK");
        String result = HttpClientUtils.postUrl(url, "GBK", "GBK");
        return Integer.parseInt(result);
    }

    /**
     * 根据结果判断发送是否成功
     * @param result
     * @return
     */
    public static boolean checkSuccess(int result){
        if(result > 0){
            return true;
        }
        return false;
    }
}

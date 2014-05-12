package com.gxx.oa;

import com.gxx.oa.dao.SMSDao;
import com.gxx.oa.entities.SMS;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.PropertyUtil;
import com.gxx.oa.utils.SMSUtil;
import com.gxx.oa.utils.TokenUtil;

/**
 * 操作短信action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-11 11:07
 */
public class OperateSMSAction extends BaseAction {
    /**
     * 操作类型
     */
    private static final String TYPE_SEND = "send";//发送短信

    /**
     * 操作类型
     */
    String type;//操作类型
    String phone;//手机号
    String content;//内容

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        //权限校验
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0013_SMS);
        logger.info("type:" + type + ",phone=" + phone + ",content=" + content);
        //ajax结果
        String resp;
        if(TYPE_SEND.equals(type)){//加载下一页
            //短信日数量限制
            int limit = Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.SMS_DAY_LIMIT));
            int countToday = SMSDao.countSMSByUserIdAndStateAndDate(getUser().getId(), SMSInterface.STATE_SUCCESS, date);
            if(limit <= countToday){
                resp = "{isSuccess:" + false + ",message:'你发送短信已达上限，请联系管理员，或者明天再试！'," +
                        "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            } else {
                String[] phoneArray = phone.split(SymbolInterface.SYMBOL_COMMA);
                boolean isAllSuccess = true;//是否都成功
                for(String phone : phoneArray){
                    //发送短信
                    int result = SMSUtil.sendSMS(phone, content);
                    boolean isSuccess = SMSUtil.checkSuccess(result);
                    if(!isSuccess){
                        isAllSuccess = false;
                    }
                    //保存记录
                    SMS sms = new SMS(getUser().getId(), phone, content,
                            isSuccess?SMSInterface.STATE_SUCCESS:SMSInterface.STATE_FAIL, date, time, getIp());
                    SMSDao.insertSMS(sms);
                }
                resp = "{isSuccess:" + isAllSuccess + ",message:'" + (isAllSuccess?"发送完成！":"未全部发送成功!") +
                        "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            }
        } else {
            resp = "{isSuccess:false,message:'操作类型有误！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        }
        write(resp);
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

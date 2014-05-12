package com.gxx.oa;

import com.gxx.oa.dao.RemindDao;
import com.gxx.oa.entities.Remind;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

/**
 * 操作提醒action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-9 16:14
 */
public class OperateRemindAction extends BaseAction {
    /**
     * 操作类型
     */
    private static final String TYPE_QUERY = "query";//查询
    private static final String TYPE_INSERT_REMIND = "insertRemind";//创建提醒
    private static final String TYPE_UPDATE_REMIND = "updateRemind";//修改提醒
    private static final String TYPE_DELETE_REMIND = "deleteRemind";//删除提醒

    /**
     * 操作类型
     */
    String type;//操作类型
    String date;//日期
    String content;//提醒内容
    String remindType;//提醒类型
    String remindDateTime;//提醒时间
    String updateRemindId;//修改提醒id
    String deleteRemindId;//删除提醒id

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        //权限校验
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0011_CALENDAR);
        logger.info("type:" + type + ",date=" + date);
        //ajax结果
        String resp;
        if(TYPE_QUERY.equals(type)){//查询
            //根据用户id和日期查提醒
            String remindsJson = BaseUtil.getJsonArrayFromReminds(RemindDao.queryRemindsByUserIdAndDate
                    (getUser().getId(), date)).replaceAll("\\\'", "\\\\\\\'").replaceAll("\\\"", "\\\\\\\"");
            //返回结果
            resp = "{isSuccess:true,message:'查询提醒成功！',remindsJson:'" + remindsJson +
                    "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
        } else if(TYPE_INSERT_REMIND.equals(type)){//创建提醒
            //创建提醒
            Remind remind = new Remind(getUser().getId(), date, content, Integer.parseInt(remindType),
                    remindDateTime, StringUtils.EMPTY, super.date, time, getIp(), StringUtils.EMPTY,
                    StringUtils.EMPTY, StringUtils.EMPTY);
            RemindDao.insertRemind(remind);
            //根据用户id和日期查提醒
            String remindsJson = BaseUtil.getJsonArrayFromReminds(RemindDao.queryRemindsByUserIdAndDate
                    (getUser().getId(), date)).replaceAll("\\\'", "\\\\\\\'").replaceAll("\\\"", "\\\\\\\"");
            //返回结果
            resp = "{isSuccess:true,message:'创建提醒成功！',remindsJson:'" + remindsJson +
                    "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
        } else if(TYPE_UPDATE_REMIND.equals(type)){//修改提醒
            //创建提醒
            Remind remind = RemindDao.getRemindById(Integer.parseInt(updateRemindId));
            if(null == remind || remind.getUserId() != getUser().getId()){
                //返回结果
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                remind.setDate(date);
                remind.setContent(content);
                remind.setRemindType(Integer.parseInt(remindType));
                remind.setRemindDateTime(remindDateTime);
                remind.setUpdateDate(super.date);
                remind.setUpdateTime(time);
                remind.setUpdateIp(getIp());
                RemindDao.updateRemind(remind);
                //根据用户id和日期查提醒
                String remindsJson = BaseUtil.getJsonArrayFromReminds(RemindDao.queryRemindsByUserIdAndDate
                        (getUser().getId(), date)).replaceAll("\\\'", "\\\\\\\'").replaceAll("\\\"", "\\\\\\\"");
                //返回结果
                resp = "{isSuccess:true,message:'修改提醒成功！',remindsJson:'" + remindsJson +
                        "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_DELETE_REMIND.equals(type)){//删除提醒
            //创建提醒
            Remind remind = RemindDao.getRemindById(Integer.parseInt(deleteRemindId));
            if(null == remind || remind.getUserId() != getUser().getId()){
                //返回结果
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                RemindDao.deleteRemind(remind);
                //根据用户id和日期查提醒
                String remindsJson = BaseUtil.getJsonArrayFromReminds(RemindDao.queryRemindsByUserIdAndDate
                        (getUser().getId(), remind.getDate())).replaceAll("\\\'", "\\\\\\\'").replaceAll("\\\"", "\\\\\\\"");
                //返回结果
                resp = "{isSuccess:true,message:'删除提醒成功！',remindsJson:'" + remindsJson +
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

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemindType() {
        return remindType;
    }

    public void setRemindType(String remindType) {
        this.remindType = remindType;
    }

    public String getRemindDateTime() {
        return remindDateTime;
    }

    public void setRemindDateTime(String remindDateTime) {
        this.remindDateTime = remindDateTime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUpdateRemindId() {
        return updateRemindId;
    }

    public void setUpdateRemindId(String updateRemindId) {
        this.updateRemindId = updateRemindId;
    }

    public String getDeleteRemindId() {
        return deleteRemindId;
    }

    public void setDeleteRemindId(String deleteRemindId) {
        this.deleteRemindId = deleteRemindId;
    }
}

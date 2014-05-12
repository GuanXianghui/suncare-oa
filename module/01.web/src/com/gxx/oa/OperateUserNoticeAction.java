package com.gxx.oa;

import com.gxx.oa.dao.NoticeDao;
import com.gxx.oa.dao.UserNoticeDao;
import com.gxx.oa.entities.Notice;
import com.gxx.oa.entities.UserNotice;
import com.gxx.oa.interfaces.BaseInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.interfaces.UserNoticeInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.PropertyUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.List;

/**
 * 操作用户公告action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-3 22:22
 */
public class OperateUserNoticeAction extends BaseAction {
    /**
     * 操作类型
     */
    private static final String TYPE_READ = "read";
    private static final String TYPE_DELETE = "delete";
    private static final String TYPE_NEXT_PAGE = "nextPage";

    /**
     * 操作类型
     */
    String type;
    String noticeId;
    String countNow;


    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        //权限校验
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0006_NOTICE);
        logger.info("type:" + type + ",noticeId=" + noticeId + ",countNow=" + countNow);
        //ajax结果
        String resp;
        if(TYPE_READ.equals(type)){
            UserNotice notice = UserNoticeDao.getUserNoticeByUserIdAndNoticeId(getUser().getId(), Integer.parseInt(noticeId));
            if(null == notice){//没有记录则新增一条已读记录
                notice = new UserNotice(getUser().getId(), Integer.parseInt(noticeId),
                        UserNoticeInterface.STATE_READED, date, time, getIp());
                UserNoticeDao.insertUserNotice(notice);
            }//有记录说明不是已读就是已删除，不做修改
            resp = "{isSuccess:true,message:'阅读公告成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(TYPE_DELETE.equals(type)){
            UserNotice notice = UserNoticeDao.getUserNoticeByUserIdAndNoticeId(getUser().getId(), Integer.parseInt(noticeId));
            if(null == notice){//没有记录则新增一条删除记录
                notice = new UserNotice(getUser().getId(), Integer.parseInt(noticeId),
                        UserNoticeInterface.STATE_DELETED, date, time, getIp());
                UserNoticeDao.insertUserNotice(notice);
            } else {//有记录说明不是已读就是已删除，重复做修改
                notice.setState(UserNoticeInterface.STATE_DELETED);
                notice.setDate(date);
                notice.setTime(time);
                notice.setIp(getIp());
                UserNoticeDao.updateUserNotice(notice);
            }
            resp = "{isSuccess:true,message:'删除公告成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(TYPE_NEXT_PAGE.equals(type)) {
            //已删除的ids
            String deleteIds = UserNoticeDao.queryUserNoticeIdsByUserIdAndState(getUser().getId(),
                    UserNoticeInterface.STATE_DELETED);
            //下一页公告 limit countNow~countNow+pageSize
            List<Notice> nextPageNotices = NoticeDao.queryNoticesByFromToAndWithoutIds(Integer.parseInt(countNow),
                    Integer.parseInt(PropertyUtil.getInstance().
                            getProperty(BaseInterface.NOTICE_PAGE_SIZE)), deleteIds);
            /**
             * 下一页公告Json串
             * replaceAll("\\\'", "\\\\\\\'")，转换单引号
             * replaceAll("\\\"", "\\\\\\\"")，转换双引号
             * replaceAll("\r\n", uuid)，转换换行符成uuid
             */
            String nextPageJson = BaseUtil.getJsonArrayFromNotices(nextPageNotices).replaceAll("\\\'", "\\\\\\\'").
                    replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                    PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID));
            //返回结果
            resp = "{isSuccess:true,message:'加载下一页公告成功！',nextPageJson:'" + nextPageJson +
                    "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

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

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getCountNow() {
        return countNow;
    }

    public void setCountNow(String countNow) {
        this.countNow = countNow;
    }
}

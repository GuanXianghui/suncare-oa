package com.gxx.oa;

import com.gxx.oa.dao.NoticeDao;
import com.gxx.oa.entities.Notice;
import com.gxx.oa.utils.BaseUtil;
import org.apache.commons.lang.StringUtils;

/**
 * 公告管理action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-3 12:38
 */
public class ConfigNoticeAction extends BaseAction {
    /**
     * 管理类型
     */
    private static final String TYPE_ADD = "add";
    private static final String TYPE_UPDATE = "update";
    private static final String TYPE_DELETE = "delete";

    //公告id
    private String noticeId;
    //管理类型
    private String type;
    //标题
    private String title;
    //内容
    private String content;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        //权限校验
        BaseUtil.checkRightWithException(getUser().getId(), RIGHT_0007_CONFIG_NOTICE);
        logger.info("noticeId:" + noticeId + ",type:" + type + ",title:" + title + ",content:" + content);
        //公告类型判断
        if(StringUtils.equals(TYPE_ADD, type)) {
            Notice notice = new Notice(title, content, date, time, getIp(),
                    StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
            NoticeDao.insertNotice(notice);
            message = "新增公告成功！";
        } else if(StringUtils.equals(TYPE_UPDATE, type)) {
            Notice notice = NoticeDao.getNoticeById(Integer.parseInt(noticeId));
            notice.setTitle(title);
            notice.setContent(content);
            notice.setUpdateDate(date);
            notice.setUpdateTime(time);
            notice.setUpdateIp(getIp());
            NoticeDao.updateNotice(notice);
            message = "修改公告成功！";
        } else if(StringUtils.equals(TYPE_DELETE, type)) {
            Notice notice = NoticeDao.getNoticeById(Integer.parseInt(noticeId));
            NoticeDao.deleteNotice(notice);
            message = "删除公告成功！";
        } else {
            message = "公共管理类型有误！";
            return ERROR;
        }
        return SUCCESS;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}

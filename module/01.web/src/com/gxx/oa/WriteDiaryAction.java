package com.gxx.oa;

import com.gxx.oa.dao.DiaryDao;
import com.gxx.oa.entities.Diary;
import com.gxx.oa.utils.BaseUtil;
import org.apache.commons.lang.StringUtils;

/**
 * 写工作日志action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-7 22:16
 */
public class WriteDiaryAction extends BaseAction {
    //日期
    private String date;
    //内容
    private String content;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        //权限校验
        BaseUtil.checkRightWithException(getUser().getId(), RIGHT_0010_DIARY);
        logger.info("date:" + date + ",content:" + content);
        Diary diary = new Diary(getUser().getId(), date, content, super.date, time, getIp(), StringUtils.EMPTY,
                StringUtils.EMPTY, StringUtils.EMPTY);
        DiaryDao.insertDiary(diary);
        message = "写工作日志成功！";
        return SUCCESS;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

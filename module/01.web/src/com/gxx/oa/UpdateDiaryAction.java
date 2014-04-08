package com.gxx.oa;

import com.gxx.oa.dao.DiaryDao;
import com.gxx.oa.entities.Diary;

/**
 * 修改工作日志action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-8 11:45
 */
public class UpdateDiaryAction extends BaseAction {
    //工作日志id
    private String diaryId;
    //日期
    private String date;
    //内容
    private String content;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("diaryId:" + diaryId + ",date:" + date + ",content:" + content);
        Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
        //判不存在该工作日志，或者，该工作日志不属于该用户的不能修改
        if(diary == null || diary.getUserId() != getUser().getId()){
            message = "你的操作有误！";
            return ERROR;
        }
        diary.setDate(date);
        diary.setContent(content);
        diary.setUpdateDate(super.date);
        diary.setUpdateTime(time);
        diary.setUpdateIp(getIp());
        DiaryDao.updateDiary(diary);
        message = "修改工作日志成功！";
        return SUCCESS;
    }

    public String getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(String diaryId) {
        this.diaryId = diaryId;
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

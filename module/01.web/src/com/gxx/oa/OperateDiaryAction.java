package com.gxx.oa;

import com.gxx.oa.dao.DiaryDao;
import com.gxx.oa.dao.DiaryReviewDao;
import com.gxx.oa.dao.MessageDao;
import com.gxx.oa.entities.Diary;
import com.gxx.oa.entities.DiaryReview;
import com.gxx.oa.entities.Message;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.PropertyUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 操作工作日志action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-7 21:53
 */
public class OperateDiaryAction extends BaseAction {
    /**
     * 操作类型
     */
    private static final String TYPE_NEXT_PAGE = "nextPage";//加载下一页
    private static final String TYPE_DELETE = "delete";//删除
    private static final String TYPE_ZAN = "zan";//点赞
    private static final String TYPE_CANCEL_ZAN = "cancelZan";//取消赞
    private static final String TYPE_CREATE_REVIEW = "createReview";//新增评论
    private static final String TYPE_UPDATE_REVIEW = "updateReview";//修改评论
    private static final String TYPE_DELETE_REVIEW = "deleteReview";//删除评论
    private static final String TYPE_REPLY_REVIEW = "replyReview";//回复评论

    /**
     * 操作类型
     */
    String type;//操作类型
    String countNow;//目前的量
    String userId;//塞选用户id
    String date;//塞选日期
    String diaryId;//工作日志id
    String content;//评论内容
    String updateReviewId;//修改评论id
    String deleteReviewId;//删除评论id

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("type:" + type + ",countNow=" + countNow + ",userId=" + userId + ",date=" + date +
                ",diaryId=" + diaryId);
        //ajax结果
        String resp;
        if(TYPE_NEXT_PAGE.equals(type)){//加载下一页
            int userIdInt = Integer.parseInt(userId);//没有选择人则为0
            int countNowInt = Integer.parseInt(countNow);
            String rightUserWithComma = BaseUtil.getLowerLevelPositionUserIdWithComma(getUser().getPosition());//有权限看的下级用户
            //根据用户id，日期，范围查工作日志
            String nextPageDiaries = BaseUtil.getJsonArrayFromDiaries(DiaryDao.queryDiariesByFromTo(userIdInt, date,
                    countNowInt, Integer.parseInt(PropertyUtil.getInstance().
                    getProperty(BaseInterface.DIARY_PAGE_SIZE)), rightUserWithComma)).replaceAll("\\\'", "\\\\\\\'").
                    replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                    PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID));
            //返回结果
            resp = "{isSuccess:true,message:'加载下一页工作日志成功！',nextPageJson:'" + nextPageDiaries +
                    "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
        } else if(TYPE_DELETE.equals(type)){//删除
            Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
            //判不存在该工作日志，或者，该工作日志不属于该用户的不能修改
            if(diary == null || diary.getUserId() != getUser().getId()){
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                DiaryDao.deleteDiary(diary);
                //删除所有的评论
                List<DiaryReview> diaryReviewList = DiaryReviewDao.queryDiaryReviews(diary.getId());
                for(DiaryReview diaryReview : diaryReviewList){
                    DiaryReviewDao.deleteDiaryReview(diaryReview);
                }
                resp = "{isSuccess:true,message:'删除工作日志成功！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_ZAN.equals(type)){//点赞
            Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
            //判不存在该工作日志
            if(diary == null){
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                //判是否已点赞过
                boolean hasAllReadyZan = DiaryReviewDao.hasAllReadyZan(diary.getId(), getUser().getId());
                if(hasAllReadyZan){
                    resp = "{isSuccess:false,message:'你已经点过赞！',hasNewToken:true,token:'" +
                            TokenUtil.createToken(request) + "'}";
                } else {
                    DiaryReview diaryReview = new DiaryReview(getUser().getId(), diary.getId(),
                            DiaryReviewInterface.TYPE_ZAN, 0, StringUtils.EMPTY, super.date, time, getIp(),
                            StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
                    DiaryReviewDao.insertDiaryReview(diaryReview);
                    //给写日志的人发送消息
                    Message message2 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                            diary.getUserId(), "有个人给你的日志点赞啦！请见<a target=\"_blank\" " +
                            "href=\"/showDiary.jsp?id=" + diary.getId() + "\">链接</>",
                            MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                    MessageDao.insertMessage(message2);
                    resp = "{isSuccess:true,message:'点赞成功！',hasNewToken:true,token:'" +
                            TokenUtil.createToken(request) + "'}";
                }
            }
        } else if(TYPE_CANCEL_ZAN.equals(type)){//取消赞
            Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
            //判不存在该工作日志
            if(diary == null){
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                //判是否已点赞过
                boolean hasAllReadyZan = DiaryReviewDao.hasAllReadyZan(diary.getId(), getUser().getId());
                if(!hasAllReadyZan){
                    resp = "{isSuccess:false,message:'你未点过赞！',hasNewToken:true,token:'" +
                            TokenUtil.createToken(request) + "'}";
                } else {
                    DiaryReview diaryReview = DiaryReviewDao.getZan(diary.getId(), getUser().getId());
                    DiaryReviewDao.deleteDiaryReview(diaryReview);
                    //给写日志的人发送消息
                    Message message2 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                            diary.getUserId(), "有个人从你的日志取消赞啦！请见<a target=\"_blank\" " +
                            "href=\"/showDiary.jsp?id=" + diary.getId() + "\">链接</>",
                            MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                    MessageDao.insertMessage(message2);
                    resp = "{isSuccess:true,message:'取消赞成功！',hasNewToken:true,token:'" +
                            TokenUtil.createToken(request) + "'}";
                }
            }
        } else if(TYPE_CREATE_REVIEW.equals(type)){//新增评论
            Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
            //判不存在该工作日志不能评论
            if(diary == null){
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                DiaryReview diaryReview = new DiaryReview(getUser().getId(), diary.getId(),
                        DiaryReviewInterface.TYPE_MESSAGE, 0, content, super.date, time, getIp(),
                        StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
                DiaryReviewDao.insertDiaryReview(diaryReview);
                //给写日志的人发送消息
                Message message2 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                        diary.getUserId(), "有个人评论了你的日志啦！请见<a target=\"_blank\" " +
                        "href=\"/showDiary.jsp?id=" + diary.getId() + "\">链接</>",
                        MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                MessageDao.insertMessage(message2);
                resp = "{isSuccess:true,message:'评论工作日志成功！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_UPDATE_REVIEW.equals(type)){//修改评论
            Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
            DiaryReview diaryReview = DiaryReviewDao.getDiaryReviewById(Integer.parseInt(updateReviewId));
            //判不存在该工作日志，或者，不存在该工作日志评论，或者，该工作日志评论的作者不是该请求用户，不能修改评论
            if(diary == null || diaryReview == null || diaryReview.getUserId() != getUser().getId()){
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                diaryReview.setContent(content);
                diaryReview.setUpdateDate(super.date);
                diaryReview.setUpdateTime(time);
                diaryReview.setUpdateIp(getIp());
                DiaryReviewDao.updateDiaryReview(diaryReview);
                resp = "{isSuccess:true,message:'修改工作日志评论成功！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_DELETE_REVIEW.equals(type)){//删除评论
            Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
            DiaryReview diaryReview = DiaryReviewDao.getDiaryReviewById(Integer.parseInt(deleteReviewId));
            //判不存在该工作日志，或者，不存在该工作日志评论，或者，该工作日志评论的作者不是该请求用户，不能删除评论
            if(diary == null || diaryReview == null || diaryReview.getUserId() != getUser().getId()){
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                DiaryReviewDao.deleteDiaryReview(diaryReview);
                //给写日志的人发送消息
                Message message2 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                        diary.getUserId(), "有个人从你的日志删除评论啦！请见<a target=\"_blank\" " +
                        "href=\"/showDiary.jsp?id=" + diary.getId() + "\">链接</>",
                        MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                MessageDao.insertMessage(message2);
                resp = "{isSuccess:true,message:'删除工作日志评论成功！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_REPLY_REVIEW.equals(type)){//回复评论
            Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
            DiaryReview diaryReview = DiaryReviewDao.getDiaryReviewById(Integer.parseInt(updateReviewId));
            //判不存在该工作日志，或者，不存在该工作日志评论，不能回复评论
            if(diary == null || diaryReview == null){
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                DiaryReview diaryReview2 = new DiaryReview(getUser().getId(), diary.getId(),
                        DiaryReviewInterface.TYPE_REPLY, diaryReview.getUserId(), content, super.date, time,
                        getIp(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
                DiaryReviewDao.insertDiaryReview(diaryReview2);
                //给写日志的人发送消息
                Message message2 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                        diary.getUserId(), "有个人评论了你的日志啦！请见<a target=\"_blank\" " +
                        "href=\"/showDiary.jsp?id=" + diary.getId() + "\">链接</>",
                        MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                MessageDao.insertMessage(message2);
                //给发原始日志评论的人发送消息
                Message message3 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                        diaryReview.getUserId(), "有个人回复了你的日志评论啦！请见<a target=\"_blank\" " +
                        "href=\"/showDiary.jsp?id=" + diary.getId() + "\">链接</>",
                        MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                MessageDao.insertMessage(message3);
                resp = "{isSuccess:true,message:'回复评论工作日志成功！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
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

    public String getCountNow() {
        return countNow;
    }

    public void setCountNow(String countNow) {
        this.countNow = countNow;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(String diaryId) {
        this.diaryId = diaryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdateReviewId() {
        return updateReviewId;
    }

    public void setUpdateReviewId(String updateReviewId) {
        this.updateReviewId = updateReviewId;
    }

    public String getDeleteReviewId() {
        return deleteReviewId;
    }

    public void setDeleteReviewId(String deleteReviewId) {
        this.deleteReviewId = deleteReviewId;
    }
}

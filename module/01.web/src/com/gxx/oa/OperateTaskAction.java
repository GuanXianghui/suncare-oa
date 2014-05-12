package com.gxx.oa;

import com.gxx.oa.dao.*;
import com.gxx.oa.entities.*;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.PropertyUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 操作任务action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 16:47
 */
public class OperateTaskAction extends BaseAction {
    /**
     * 操作类型
     */
    private static final String TYPE_NEXT_PAGE = "nextPage";//加载下一页
    private static final String TYPE_UPDATE_TASK_STATE = "updateTaskState";//修改任务状态
    private static final String TYPE_CUI = "cui";//催
    private static final String TYPE_DELETE = "delete";//删除
    private static final String TYPE_CREATE_REVIEW = "createReview";//新增评论
    private static final String TYPE_UPDATE_REVIEW = "updateReview";//修改评论
    private static final String TYPE_DELETE_REVIEW = "deleteReview";//删除评论
    private static final String TYPE_REPLY_REVIEW = "replyReview";//回复评论

    /**
     * 操作类型
     */
    String type;//操作类型
    String countNow;//目前的量
    String fromUserId;//任务来源用户id
    String toUserId;//任务接受用户id
    String state;//状态
    String taskId;//任务id
    String newState;//新的任务状态
    String updateReviewId;//修改评论id
    String content;//评论内容
    String deleteReviewId;//删除评论id

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        //权限校验
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0012_TASK);
        logger.info("type:" + type + ",countNow=" + countNow + ",fromUserId=" + fromUserId + ",toUserId=" +
                toUserId + ",state=" + state + ",taskId=" + taskId + ",newState=" + newState);
        //ajax结果
        String resp;
        if(TYPE_NEXT_PAGE.equals(type)){//加载下一页
            int countNowInt = Integer.parseInt(countNow);
            int fromUserIdInt = Integer.parseInt(fromUserId);//没有则为0
            int toUserIdInt = Integer.parseInt(toUserId);//没有则为0
            int stateInt = Integer.parseInt(state);//没有则为0
            //根据任务来源用户id,任务接受用户id,状态,范围查任务
            String nextPageTasks = BaseUtil.getJsonArrayFromTasks(TaskDao.queryTasksByFromTo(fromUserIdInt,
                    toUserIdInt, stateInt, countNowInt, Integer.parseInt(PropertyUtil.getInstance().
                    getProperty(BaseInterface.TASK_PAGE_SIZE)))).replaceAll("\\\'", "\\\\\\\'").
                    replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                    PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID));
            //返回结果
            resp = "{isSuccess:true,message:'加载下一页任务成功！',nextPageJson:'" + nextPageTasks +
                    "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
        } else if(TYPE_UPDATE_TASK_STATE.equals(type)){//修改任务状态
            Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
            //判不存在该任务，或者，!(任务来源和接受任务的人可以修改任务) 不能修改
            if(task == null || (task.getFromUserId() != getUser().getId() && task.getToUserId() != getUser().getId())){
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                task.setState(Integer.parseInt(newState));
                task.setUpdateDate(date);
                task.setUpdateTime(time);
                task.setUpdateIp(getIp());
                TaskDao.updateTaskState(task);
                //返回结果
                resp = "{isSuccess:true,message:'修改任务状态成功！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_CUI.equals(type)){//催
            Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
            //判不存在该任务不能催(任何人都可以催进度)
            if(task == null){
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                TaskReview taskReview = new TaskReview(getUser().getId(), Integer.parseInt(taskId),
                        TaskReviewInterface.TYPE_CUI, 0, StringUtils.EMPTY, date, time, getIp(), StringUtils.EMPTY,
                        StringUtils.EMPTY, StringUtils.EMPTY);
                TaskReviewDao.insertTaskReview(taskReview);
                //给任务接受的人发送消息
                Message message2 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                        task.getToUserId(), "有个人催你的任务进度啦！请见<a target=\"_blank\" " +
                        "href=\"/showTask.jsp?id=" + task.getId() + "\">链接</>",
                        MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                MessageDao.insertMessage(message2);
                resp = "{isSuccess:true,message:'催任务进度成功！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_DELETE.equals(type)){//删除
            Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
            //判不存在该任务，或者，(只有创建的人可以删除任务) 不能删除
            if(task == null || task.getFromUserId() != getUser().getId()){
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                //删除任务
                TaskDao.deleteTask(task);
                //删除所有被催任务评论
                List<TaskReview> taskReviewList = TaskReviewDao.queryCuiTaskReviews(task.getId());
                for(TaskReview taskReview : taskReviewList){
                    TaskReviewDao.deleteTaskReview(taskReview);
                }
                //删除所有非被催任务评论
                taskReviewList = TaskReviewDao.queryNotCuiTaskReviews(task.getId());
                for(TaskReview taskReview : taskReviewList){
                    TaskReviewDao.deleteTaskReview(taskReview);
                }
                resp = "{isSuccess:true,message:'删除任务成功！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_CREATE_REVIEW.equals(type)){//新增评论
            Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
            //判不存在该任务不能评论
            if(task == null){
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                TaskReview taskReview = new TaskReview(getUser().getId(), task.getId(),
                        TaskReviewInterface.TYPE_MESSAGE, 0, content, super.date, time, getIp(),
                        StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
                TaskReviewDao.insertTaskReview(taskReview);
                //给任务接受的人发送消息
                Message message2 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                        task.getToUserId(), "有个人评论了你的任务啦！请见<a target=\"_blank\" " +
                        "href=\"/showTask.jsp?id=" + task.getId() + "\">链接</>",
                        MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                MessageDao.insertMessage(message2);
                resp = "{isSuccess:true,message:'评论任务成功！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_UPDATE_REVIEW.equals(type)){//修改评论
            Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
            TaskReview taskReview = TaskReviewDao.getTaskReviewById(Integer.parseInt(updateReviewId));
            //判不存在该任务，或者，不存在该任务评论，或者，该任务评论的作者不是该请求用户，不能修改评论
            if(task == null || taskReview == null || taskReview.getUserId() != getUser().getId()){
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                taskReview.setContent(content);
                taskReview.setUpdateDate(super.date);
                taskReview.setUpdateTime(time);
                taskReview.setUpdateIp(getIp());
                TaskReviewDao.updateTaskReview(taskReview);
                resp = "{isSuccess:true,message:'修改任务评论成功！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_DELETE_REVIEW.equals(type)){//删除评论
            Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
            TaskReview taskReview = TaskReviewDao.getTaskReviewById(Integer.parseInt(deleteReviewId));
            //判不存在该任务，或者，不存在该任务评论，或者，该任务评论的作者不是该请求用户，不能修改评论
            if(task == null || taskReview == null || taskReview.getUserId() != getUser().getId()){
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                TaskReviewDao.deleteTaskReview(taskReview);
                //给任务接受的人发送消息
                Message message2 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                        task.getToUserId(), "有个人删除了你的任务评论啦！请见<a target=\"_blank\" " +
                        "href=\"/showTask.jsp?id=" + task.getId() + "\">链接</>",
                        MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                MessageDao.insertMessage(message2);
                resp = "{isSuccess:true,message:'删除任务评论成功！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_REPLY_REVIEW.equals(type)){//回复评论
            Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
            TaskReview taskReview = TaskReviewDao.getTaskReviewById(Integer.parseInt(updateReviewId));
            //判不存在该任务，或者，不存在该任务评论，不能回复评论
            if(task == null || taskReview == null){
                resp = "{isSuccess:false,message:'你的操作有误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                TaskReview taskReview2 = new TaskReview(getUser().getId(), task.getId(),
                        TaskReviewInterface.TYPE_REPLY, taskReview.getUserId(), content, super.date, time,
                        getIp(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
                TaskReviewDao.insertTaskReview(taskReview2);
                //给任务接受的人发送消息
                Message message2 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                        task.getToUserId(), "有个人回复了你的任务评论啦！请见<a target=\"_blank\" " +
                        "href=\"/showTask.jsp?id=" + task.getId() + "\">链接</>",
                        MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                MessageDao.insertMessage(message2);
                resp = "{isSuccess:true,message:'回复评论任务成功！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
                //给发原始任务评论的人发送消息
                Message message3 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                        taskReview.getUserId(), "有个人回复了你的任务评论啦！请见<a target=\"_blank\" " +
                        "href=\"/showTask.jsp?id=" + task.getId() + "\">链接</>",
                        MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                MessageDao.insertMessage(message3);
                resp = "{isSuccess:true,message:'回复评论任务成功！',hasNewToken:true,token:'" +
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

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUpdateReviewId() {
        return updateReviewId;
    }

    public void setUpdateReviewId(String updateReviewId) {
        this.updateReviewId = updateReviewId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeleteReviewId() {
        return deleteReviewId;
    }

    public void setDeleteReviewId(String deleteReviewId) {
        this.deleteReviewId = deleteReviewId;
    }
}

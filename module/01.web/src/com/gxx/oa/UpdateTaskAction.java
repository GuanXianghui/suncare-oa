package com.gxx.oa;

import com.gxx.oa.dao.TaskDao;
import com.gxx.oa.entities.Task;
import com.gxx.oa.utils.BaseUtil;

/**
 * 修改任务action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 21:16
 */
public class UpdateTaskAction extends BaseAction {
    //任务id
    private String taskId;
    //任务接受用户id
    private String toUserId;
    //任务名称
    private String title;
    //开始日期
    private String beginDate;
    //结束日期
    private String endDate;
    //任务内容
    private String content;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        //权限校验
        BaseUtil.checkRightWithException(getUser().getId(), RIGHT_0012_TASK);
        logger.info("taskId:" + taskId + ",toUserId:" + toUserId + ",title:" + title + ",beginDate:" +
                beginDate + ",endDate:" + endDate + ",content:" + content);
        Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
        //判不存在该任务，或者，!(任务来源和接受任务的人可以修改任务) 不能修改
        if(task == null || (task.getFromUserId() != getUser().getId() && task.getToUserId() != getUser().getId())){
            message = "你的操作有误！";
            return ERROR;
        }
        task.setToUserId(Integer.parseInt(toUserId));
        task.setTitle(title);
        task.setBeginDate(beginDate);
        task.setEndDate(endDate);
        task.setContent(content);
        task.setUpdateDate(date);
        task.setUpdateTime(time);
        task.setUpdateIp(getIp());
        TaskDao.updateTask(task);
        message = "修改任务成功！";
        return SUCCESS;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
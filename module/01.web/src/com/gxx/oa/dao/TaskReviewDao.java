package com.gxx.oa.dao;

import com.gxx.oa.entities.TaskReview;
import com.gxx.oa.interfaces.TaskReviewInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务评论实体操作类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 15:26
 */
public class TaskReviewDao {
    /**
     * 根据任务id查被催任务评论
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    public static List<TaskReview> queryCuiTaskReviews(int taskId) throws Exception {
        List<TaskReview> list = new ArrayList<TaskReview>();
        String sql = "SELECT id,user_id,type,replied_user_id,content,create_date,create_time," +
                "create_ip,update_date,update_time,update_ip FROM task_review WHERE task_id=" + taskId +
                " AND type=" + TaskReviewInterface.TYPE_CUI + " ORDER BY ID DESC";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                int type = rs.getInt("type");
                int repliedUerId = rs.getInt("replied_user_id");
                String content = rs.getString("content");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                TaskReview taskReview = new TaskReview(id, userId, taskId, type, repliedUerId, content, createDate,
                        createTime, createIp, updateDate, updateTime, updateIp);
                list.add(taskReview);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据任务id查非被催任务评论
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    public static List<TaskReview> queryNotCuiTaskReviews(int taskId) throws Exception {
        List<TaskReview> list = new ArrayList<TaskReview>();
        String sql = "SELECT id,user_id,type,replied_user_id,content,create_date,create_time," +
                "create_ip,update_date,update_time,update_ip FROM task_review WHERE task_id=" + taskId +
                " AND type!=" + TaskReviewInterface.TYPE_CUI + " ORDER BY ID DESC";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                int type = rs.getInt("type");
                int repliedUerId = rs.getInt("replied_user_id");
                String content = rs.getString("content");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                TaskReview taskReview = new TaskReview(id, userId, taskId, type, repliedUerId, content, createDate,
                        createTime, createIp, updateDate, updateTime, updateIp);
                list.add(taskReview);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据id查任务评论
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static TaskReview getTaskReviewById(int id) throws Exception {
        TaskReview taskReview = null;
        String sql = "SELECT user_id,task_id,type,replied_user_id,content,create_date,create_time," +
                "create_ip,update_date,update_time,update_ip FROM task_review WHERE id=" + id;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                int taskId = rs.getInt("task_id");
                int type = rs.getInt("type");
                int repliedUerId = rs.getInt("replied_user_id");
                String content = rs.getString("content");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                taskReview = new TaskReview(id, userId, taskId, type, repliedUerId, content, createDate, createTime,
                        createIp, updateDate, updateTime, updateIp);
            }
            return taskReview;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 新增任务评论
     *
     * @param taskReview
     * @throws Exception
     */
    public static void insertTaskReview(TaskReview taskReview) throws Exception {
        String sql = "insert into task_review(id,user_id,task_id,type,replied_user_id,content,create_date," +
                "create_time,create_ip,update_date,update_time,update_ip) values (null," +
                taskReview.getUserId() + "," + taskReview.getTaskId() + "," + taskReview.getType() + "," +
                taskReview.getRepliedUserId() + ",'" + taskReview.getContent() + "','" + taskReview.getCreateDate() +
                "','" + taskReview.getCreateTime() + "','" + taskReview.getCreateIp() + "','" +
                taskReview.getUpdateDate() + "','" + taskReview.getUpdateTime() + "','" +
                taskReview.getUpdateIp() + "')";
        DB.executeUpdate(sql);
    }

    /**
     * 更新任务评论
     *
     * @param taskReview
     * @throws Exception
     */
    public static void updateTaskReview(TaskReview taskReview) throws Exception {
        String sql = "update task_review set content='" + taskReview.getContent() + "',update_date='" +
                taskReview.getUpdateDate() + "',update_time='" + taskReview.getUpdateTime() + "',update_ip='" +
                taskReview.getUpdateIp() + "' where id=" + taskReview.getId();
        DB.executeUpdate(sql);
    }

    /**
     * 删除任务评论
     *
     * @param taskReview
     * @throws Exception
     */
    public static void deleteTaskReview(TaskReview taskReview) throws Exception {
        String sql = "delete from task_review where id=" + taskReview.getId();
        DB.executeUpdate(sql);
    }
}
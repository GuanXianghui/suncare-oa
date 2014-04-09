package com.gxx.oa.dao;

import com.gxx.oa.entities.Remind;
import com.gxx.oa.interfaces.RemindInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 提醒实体操作类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-9 15:19
 */
public class RemindDao {
    /**
     * 根据起止提醒时间查提醒
     *
     * @param beginDateTime
     * @param endDateTime
     * @return
     * @throws Exception
     */
    public static List<Remind> queryRemindsBetweenRemindDateTime(String beginDateTime, String endDateTime) throws Exception {
        List<Remind> list = new ArrayList<Remind>();
        String sql = "SELECT id,user_id,date,content,remind_type,remind_date_time,remind_target," +
                "create_date,create_time,create_ip,update_date,update_time,update_ip FROM remind " +
                "WHERE remind_type!=" +  RemindInterface.REMIND_TYPE_NO_REMIND + " AND remind_date_time " +
                ">= '" + beginDateTime + "' AND remind_date_time < '" + endDateTime + "' ORDER BY id DESC";
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
                String date = rs.getString("date");
                String content = rs.getString("content");
                int remindType = rs.getInt("remind_type");
                String remindDateTime = rs.getString("remind_date_time");
                String remindTarget = rs.getString("remind_target");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                Remind remind = new Remind(id, userId, date, content, remindType, remindDateTime, remindTarget,
                        createDate, createTime, createIp, updateDate, updateTime, updateIp);
                list.add(remind);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据用户id和日期查提醒
     *
     * @param userId
     * @param date
     * @return
     * @throws Exception
     */
    public static List<Remind> queryRemindsByUserIdAndDate(int userId, String date) throws Exception {
        List<Remind> list = new ArrayList<Remind>();
        String sql = "SELECT id,content,remind_type,remind_date_time,remind_target,create_date," +
                "create_time,create_ip,update_date,update_time,update_ip FROM remind WHERE user_id=" +
                userId + " AND date='" + date + "' ORDER BY id DESC";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                String content = rs.getString("content");
                int remindType = rs.getInt("remind_type");
                String remindDateTime = rs.getString("remind_date_time");
                String remindTarget = rs.getString("remind_target");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                Remind remind = new Remind(id, userId, date, content, remindType, remindDateTime, remindTarget,
                        createDate, createTime, createIp, updateDate, updateTime, updateIp);
                list.add(remind);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据提醒id查提醒
     * @param id
     * @return
     * @throws Exception
     */
    public static Remind getRemindById(int id) throws Exception {
        Remind remind = null;
        String sql = "SELECT user_id,date,content,remind_type,remind_date_time,remind_target,create_date," +
                "create_time,create_ip,update_date,update_time,update_ip FROM remind WHERE id=" + id;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String date = rs.getString("date");
                String content = rs.getString("content");
                int remindType = rs.getInt("remind_type");
                String remindDateTime = rs.getString("remind_date_time");
                String remindTarget = rs.getString("remind_target");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                remind = new Remind(id, userId, date, content, remindType, remindDateTime, remindTarget,
                        createDate, createTime, createIp, updateDate, updateTime, updateIp);
            }
            return remind;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 新增提醒
     *
     * @param remind
     * @throws Exception
     */
    public static void insertRemind(Remind remind) throws Exception {
        String sql = "INSERT INTO remind(id,user_id,date,content,remind_type,remind_date_time," +
                "remind_target,create_date,create_time,create_ip,update_date,update_time,update_ip) " +
                "VALUES(NULL," + remind.getUserId() + ",'" + remind.getDate() + "','" + remind.getContent() + "'," +
                remind.getRemindType() + ",'" + remind.getRemindDateTime() + "','" + remind.getRemindTarget() + "','" +
                remind.getCreateDate() + "','" + remind.getCreateTime() + "','" + remind.getCreateIp() + "','" +
                remind.getUpdateDate() + "','" + remind.getUpdateTime() + "','" + remind.getUpdateIp()+ "')";
        DB.executeUpdate(sql);
    }

    /**
     * 修改提醒
     *
     * @param remind
     * @throws Exception
     */
    public static void updateRemind(Remind remind) throws Exception {
        String sql = "UPDATE remind SET date='" + remind.getDate() + "',content='" + remind.getContent() +
                "',remind_type=" + remind.getRemindType() + ",remind_date_time='" + remind.getRemindDateTime() +
                "',remind_target='" + remind.getRemindTarget() + "',update_date='" + remind.getUpdateDate() +
                "',update_time='" + remind.getUpdateTime() + "',update_ip='" + remind.getUpdateIp() + "' WHERE id=" +
                remind.getId();
        DB.executeUpdate(sql);
    }

    /**
     * 删除提醒
     *
     * @param remind
     * @throws Exception
     */
    public static void deleteRemind(Remind remind) throws Exception {
        String sql = "DELETE FROM remind WHERE id=" + remind.getId();
        DB.executeUpdate(sql);
    }
}

package com.gxx.oa.dao;

import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.interfaces.CloudKnowAskInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 申成知道提问实体操作类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 13:22
 */
public class CloudKnowAskDao implements CloudKnowAskInterface {
    /**
     * 根据 用户id 查 申成知道提问
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public static List<CloudKnowAsk> queryCloudKnowAsksByUserId(int userId) throws Exception {
        List<CloudKnowAsk> list = new ArrayList<CloudKnowAsk>();
        String sql = "SELECT id,user_id,question,description,type,urgent,state,create_date,create_time," +
                "create_ip,update_date,update_time,update_ip FROM cloud_know_ask WHERE user_id=" + userId +
                " AND state=" + STATE_NORMAL + " ORDER BY id DESC";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                String question = rs.getString("question");
                String description = rs.getString("description");
                String type = rs.getString("type");
                int urgent = rs.getInt("urgent");
                int state = rs.getInt("state");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                CloudKnowAsk cloudKnowAsk = new CloudKnowAsk(id, userId, question, description, type, urgent, state,
                        createDate, createTime, createIp, updateDate, updateTime, updateIp);
                list.add(cloudKnowAsk);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据id查申成知道提问
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static CloudKnowAsk getCloudKnowAskById(int id) throws Exception {
        CloudKnowAsk cloudKnowAsk = null;
        String sql = "SELECT id,user_id,question,description,type,urgent,state,create_date,create_time," +
                "create_ip,update_date,update_time,update_ip FROM cloud_know_ask WHERE id=" + id;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String question = rs.getString("question");
                String description = rs.getString("description");
                String type = rs.getString("type");
                int urgent = rs.getInt("urgent");
                int state = rs.getInt("state");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                cloudKnowAsk = new CloudKnowAsk(id, userId, question, description, type, urgent, state,
                        createDate, createTime, createIp, updateDate, updateTime, updateIp);
            }
            return cloudKnowAsk;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 新增申成知道提问
     *
     * @param cloudKnowAsk
     * @throws Exception
     */
    public static void insertCloudKnowAsk(CloudKnowAsk cloudKnowAsk) throws Exception {
        String sql = "insert into cloud_know_ask(id,user_id,question,description,type,urgent,state," +
                "create_date,create_time,create_ip,update_date,update_time,update_ip) values " +
                "(null," + cloudKnowAsk.getUserId() + ",'" + cloudKnowAsk.getQuestion() + "','" +
                cloudKnowAsk.getDescription() + "','" + cloudKnowAsk.getType() + "'," +
                cloudKnowAsk.getUrgent() + "," + cloudKnowAsk.getState() + ",'" +
                cloudKnowAsk.getCreateDate() + "','" + cloudKnowAsk.getCreateTime() + "','" +
                cloudKnowAsk.getCreateIp() + "','" + cloudKnowAsk.getUpdateDate() + "','" +
                cloudKnowAsk.getUpdateTime() + "','" + cloudKnowAsk.getUpdateIp() + "')";
        DB.executeUpdate(sql);
    }

    /**
     * 更新申成知道提问
     *
     * @param cloudKnowAsk
     * @throws Exception
     */
    public static void updateCloudKnowAsk(CloudKnowAsk cloudKnowAsk) throws Exception {
        String sql = "update cloud_know_ask set question='" + cloudKnowAsk.getQuestion() + "',description='" +
                cloudKnowAsk.getDescription() + "',type='" + cloudKnowAsk.getType() + "',urgent=" +
                cloudKnowAsk.getUrgent() + ",state=" + cloudKnowAsk.getState() + ",update_date='" +
                cloudKnowAsk.getUpdateDate() + "',update_time='" + cloudKnowAsk.getUpdateTime() +
                "',update_ip='" + cloudKnowAsk.getUpdateIp() + "' where id=" + cloudKnowAsk.getId();
        DB.executeUpdate(sql);
    }

    /**
     * 查询申成知道提问
     * @param question
     * @return
     */
    public static List<CloudKnowAsk> queryCloudKnowAsksByQuestion(String question) throws Exception {
        List<CloudKnowAsk> list = new ArrayList<CloudKnowAsk>();
        String sql = "SELECT id,user_id,question,description,type,urgent,state," +
                "create_date,create_time,create_ip,update_date,update_time,update_ip FROM" +
                " cloud_know_ask WHERE question like '%" + question + "%' ORDER BY id DESC";//AND state=" + STATE_NORMAL +" 前面有or这里用and好像无效
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
                String newQuestion = rs.getString("question");
                String description = rs.getString("description");
                String type = rs.getString("type");
                int urgent = rs.getInt("urgent");
                int state = rs.getInt("state");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");

                /**
                 * SQL 语句总 AND state=" + STATE_NORMAL +" 前面有or这里用and好像无效
                 */
                if(state != STATE_NORMAL){
                    continue;
                }

                CloudKnowAsk cloudKnowAsk = new CloudKnowAsk(id, userId, newQuestion, description, type, urgent, state,
                        createDate, createTime, createIp, updateDate, updateTime, updateIp);
                list.add(cloudKnowAsk);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }
}
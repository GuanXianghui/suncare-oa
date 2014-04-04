package com.gxx.oa.dao;

import com.gxx.oa.entities.Message;
import com.gxx.oa.entities.UserNotice;
import com.gxx.oa.interfaces.SymbolInterface;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息实体操作类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-4 19:44
 */
public class MessageDao {
    /**
     * 根据用户id查总共消息的量
     *
     * @return
     * @throws Exception
     */
    public static int countAllMessagesByUserId(int userId) throws Exception {
        int countNum = 0;
        String sql = "SELECT count(1) count_num FROM message WHERE to_user_id=" + userId;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                countNum = rs.getInt("count_num");
            }
            return countNum;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据消息接受用户id和范围查消息
     *
     * @return
     * @throws Exception
     */
    public static List<Message> queryMessagesByUserIdAndFromTo(int userId, int from, int to) throws Exception {
        List<Message> list = new ArrayList<Message>();
        String sql = "SELECT id,from_user_id,from_user_type,content,state,date,time,ip FROM " +
                "message WHERE to_user_id=" + userId + " ORDER BY id DESC limit " + from + "," + to;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                int fromUserId = rs.getInt("from_user_id");
                int fromUserType = rs.getInt("from_user_type");
                String content = rs.getString("content");
                int state = rs.getInt("state");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String ip = rs.getString("ip");
                Message message = new Message(id, fromUserId, fromUserType, userId,  content, state, date, time, ip);
                list.add(message);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据id查消息
     *
     * @return
     * @throws Exception
     */
    public static Message getMessageById(int id) throws Exception {
        Message message = null;
        String sql = "SELECT from_user_id,from_user_type,to_user_id,content,state,date,time,ip FROM " +
                "message WHERE id=" + id;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int fromUserId = rs.getInt("from_user_id");
                int fromUserType = rs.getInt("from_user_type");
                int toUserId = rs.getInt("to_user_id");
                String content = rs.getString("content");
                int state = rs.getInt("state");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String ip = rs.getString("ip");
                message = new Message(id, fromUserId, fromUserType, toUserId,  content, state, date, time, ip);
            }
            return message;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 新增消息
     *
     * @param message
     * @throws Exception
     */
    public static void insertMessage(Message message) throws Exception {
        String sql = "INSERT INTO message(id,from_user_id,from_user_type,to_user_id,content,state,date," +
                "time,ip) VALUES(NULL," + message.getFromUserId() + "," + message.getFromUserType() + "," +
                message.getToUserId() + ",'" + message.getContent() + "'," + message.getState() + ",'" +
                message.getDate() + "','" + message.getTime() + "','" + message.getIp() + "')";
        DB.executeUpdate(sql);
    }

    /**
     * 修改消息
     *
     * @param message
     * @throws Exception
     */
    public static void updateMessage(Message message) throws Exception {
        String sql = "UPDATE message SET state=" + message.getState() + ",date='" + message.getDate() + "'," +
                "time='" + message.getTime() + "',ip='" + message.getIp() + "' WHERE id=" + message.getId();
        DB.executeUpdate(sql);
    }

    /**
     * 删除消息
     *
     * @param message
     * @throws Exception
     */
    public static void deleteMessage(Message message) throws Exception {
        String sql = "DELETE FROM message WHERE id=" + message.getId();
        DB.executeUpdate(sql);
    }
}

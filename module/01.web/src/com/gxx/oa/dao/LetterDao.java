package com.gxx.oa.dao;

import com.gxx.oa.entities.Letter;
import com.gxx.oa.interfaces.LetterInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 站内信实体操作类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-5 18:13
 */
public class LetterDao implements LetterInterface {
    /**
     * 根据用户id和用户类型和查询类型 查总共站内信的量
     *
     * @param userId
     * @param userType
     * @param type
     * @return
     * @throws Exception
     */
    public static int countLettersByType(int userId, int userType, int type) throws Exception {
        int countNum = 0;
        String sql = "SELECT count(1) count_num FROM letter WHERE user_id=" + userId + " AND " +
                "user_type=" + userType;
        if(type == TYPE_RECEIVED){
            sql += " AND send_or_receive=" + RECEIVE + " AND delete_state=" + DELETE_STATE_NOT_DELETED;
        } else if(type == TYPE_SENT){
            sql += " AND send_or_receive=" + SEND + " AND delete_state=" + DELETE_STATE_NOT_DELETED;
        } else if(type == TYPE_DELETED){
            sql += " AND delete_state=" + DELETE_STATE_DELETED;
        }
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
     * 根据用户id，用户类型，查询类型，范围查站内信
     *
     * @param userId
     * @param userType
     * @param type
     * @param from
     * @param to
     * @return
     * @throws Exception
     */
    public static List<Letter> queryLettersByTypeAndFromTo(int userId, int userType, int type, int from, int to)
            throws Exception {
        List<Letter> list = new ArrayList<Letter>();
        String sql = "SELECT id,send_or_receive,from_user_id,from_user_type,to_user_ids,to_user_types," +
                "cc_user_ids,cc_user_types,read_state,delete_state,title,content,create_date,create_time," +
                "create_ip,operate_date,operate_time,operate_ip FROM letter WHERE user_id=" + userId +
                " AND user_type=" + userType;
        if(type == TYPE_RECEIVED){
            sql += " AND send_or_receive=" + RECEIVE + " AND delete_state=" + DELETE_STATE_NOT_DELETED;
        } else if(type == TYPE_SENT){
            sql += " AND send_or_receive=" + SEND + " AND delete_state=" + DELETE_STATE_NOT_DELETED;
        } else if(type == TYPE_DELETED){
            sql += " AND delete_state=" + DELETE_STATE_DELETED;
        }
        sql += " ORDER BY id DESC limit " + from + "," + to;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                int sendOrReceive = rs.getInt("send_or_receive");
                int fromUserId = rs.getInt("from_user_id");
                int fromUserType = rs.getInt("from_user_type");
                String toUserIds = rs.getString("to_user_ids");
                String toUserTypes = rs.getString("to_user_types");
                String ccUserIds = rs.getString("cc_user_ids");
                String ccUserTypes = rs.getString("cc_user_types");
                int readState = rs.getInt("read_state");
                int deleteState = rs.getInt("delete_state");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String operateDate = rs.getString("operate_date");
                String operateTime = rs.getString("operate_time");
                String operateIp = rs.getString("operate_ip");
                Letter letter = new Letter(id, userId, userType, sendOrReceive, fromUserId, fromUserType, toUserIds,
                        toUserTypes, ccUserIds, ccUserTypes, readState, deleteState, title, content, createDate,
                        createTime, createIp, operateDate, operateTime, operateIp);
                list.add(letter);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据id查站内信
     *
     * @return
     * @throws Exception
     */
    public static Letter getLetterById(int id) throws Exception {
        Letter letter = null;
        String sql = "SELECT user_id,user_type,send_or_receive,from_user_id,from_user_type,to_user_ids," +
                "to_user_types,cc_user_ids,cc_user_types,read_state,delete_state,title,content," +
                "create_date,create_time,create_ip,operate_date,operate_time,operate_ip FROM letter " +
                "WHERE id=" + id;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                int userType = rs.getInt("user_Type");
                int sendOrReceive = rs.getInt("send_or_receive");
                int fromUserId = rs.getInt("from_user_id");
                int fromUserType = rs.getInt("from_user_type");
                String toUserIds = rs.getString("to_user_ids");
                String toUserTypes = rs.getString("to_user_types");
                String ccUserIds = rs.getString("cc_user_ids");
                String ccUserTypes = rs.getString("cc_user_types");
                int readState = rs.getInt("read_state");
                int deleteState = rs.getInt("delete_state");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String operateDate = rs.getString("operate_date");
                String operateTime = rs.getString("operate_time");
                String operateIp = rs.getString("operate_ip");
                letter = new Letter(id, userId, userType, sendOrReceive, fromUserId, fromUserType, toUserIds,
                        toUserTypes, ccUserIds, ccUserTypes, readState, deleteState, title, content, createDate,
                        createTime, createIp, operateDate, operateTime, operateIp);
            }
            return letter;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 新增站内信
     *
     * @param letter
     * @throws Exception
     */
    public static void insertLetter(Letter letter) throws Exception {
        String sql = "INSERT INTO LETTER(id,user_id,user_type,send_or_receive,from_user_id," +
                "from_user_type,to_user_ids,to_user_types,cc_user_ids,cc_user_types,read_state," +
                "delete_state,title,content,create_date,create_time,create_ip,operate_date,operate_time," +
                "operate_ip)VALUES(NULL," + letter.getUserId() + "," + letter.getUserType() + "," +
                letter.getSendOrReceive() + "," + letter.getFromUserId() + "," + letter.getFromUserType() + ",'" +
                letter.getToUserIds() + "','" + letter.getToUserTypes() + "','" + letter.getCcUserIds() + "','" +
                letter.getCcUserTypes() + "'," + letter.getReadState() + "," + letter.getDeleteState() + ",'" +
                letter.getTitle() + "','" + letter.getContent() + "','" + letter.getCreateDate() + "','" +
                letter.getCreateTime() + "','" + letter.getCreateIp() + "','" + letter.getOperateDate() + "','" +
                letter.getOperateTime() + "','" + letter.getOperateIp() + "')";
        DB.executeUpdate(sql);
    }

    /**
     * 修改站内信
     *
     * @param letter
     * @throws Exception
     */
    public static void updateLetter(Letter letter) throws Exception {
        String sql = "UPDATE letter SET read_state=" + letter.getReadState() + ",delete_state=" +
                letter.getDeleteState() + ",operate_date='" + letter.getOperateDate() + "',operate_time='" +
                letter.getOperateTime() + "',operate_ip='" + letter.getOperateIp() + "' WHERE id=" + letter.getId();
        DB.executeUpdate(sql);
    }

    /**
     * 删除站内信
     *
     * @param letter
     * @throws Exception
     */
    public static void deleteLetter(Letter letter) throws Exception {
        String sql = "DELETE FROM letter WHERE id=" + letter.getId();
        DB.executeUpdate(sql);
    }
}

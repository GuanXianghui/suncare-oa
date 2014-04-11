package com.gxx.oa.dao;

import com.gxx.oa.entities.SMS;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 短信实体操作类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-11 10:19
 */
public class SMSDao {
    /**
     * 根据用户id，状态和日期查短信量
     * 注意：userId>0,state>0,date非空才带上条件
     *
     * @param userId
     * @param state
     * @param date
     * @return
     * @throws Exception
     */
    public static int countSMSByUserIdAndStateAndDate(int userId, int state, String date) throws Exception {
        int countNum = 0;
        String sql = "SELECT count(1) count_num FROM sms WHERE 1=1";
        if(userId > 0){
            sql += " AND user_id=" + userId;
        }
        if(state > 0){
            sql += " AND state=" + state;
        }
        if(StringUtils.isNotBlank(date)){
            sql += " AND date=" + date;
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
     * 根据用户id，状态和日期查短信
     * 注意：userId>0,state>0,date非空才带上条件
     *
     * @param userId
     * @param state
     * @param date
     * @return
     * @throws Exception
     */
    public static List<SMS> querySMSByUserIdAndStateAndDate(int userId, int state, String date) throws Exception {
        List<SMS> list = new ArrayList<SMS>();
        String sql = "SELECT id,user_id,phone,content,state,date,time,ip FROM sms WHERE 1=1";
        if(userId > 0){
            sql += " AND user_id=" + userId;
        }
        if(state > 0){
            sql += " AND state=" + state;
        }
        if(StringUtils.isNotBlank(date)){
            sql += " AND date=" + date;
        }
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                userId = rs.getInt("user_id");
                String phone = rs.getString("phone");
                String content = rs.getString("content");
                state = rs.getInt("state");
                date = rs.getString("date");
                String time = rs.getString("time");
                String ip = rs.getString("ip");
                list.add(new SMS(id, userId, phone, content, state, date, time, ip));
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据id查短信
     *
     * @return
     * @throws Exception
     */
    public static SMS getSMSById(int id) throws Exception {
        SMS sms = null;
        String sql = "SELECT user_id,phone,content,state,date,time,ip FROM sms where id=" + id;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String phone = rs.getString("phone");
                String content = rs.getString("content");
                int state = rs.getInt("state");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String ip = rs.getString("ip");
                sms = new SMS(id, userId, phone, content, state, date, time, ip);
            }
            return sms;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 新增短信
     *
     * @param sms
     * @throws Exception
     */
    public static void insertSMS(SMS sms) throws Exception {
        String sql = "insert into sms(id,user_id,phone,content,state,date,time,ip) values (null," +
                sms.getUserId() + ",'" + sms.getPhone() + "','" + sms.getContent() + "'," + sms.getState() + ",'" +
                sms.getDate() + "','" + sms.getTime() + "','" + sms.getIp() + "')";
        DB.executeUpdate(sql);
    }
}

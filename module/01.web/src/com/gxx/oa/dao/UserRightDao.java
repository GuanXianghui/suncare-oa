package com.gxx.oa.dao;

import com.gxx.oa.entities.UserRight;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 权限实体操作类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-31 08:41
 */
public class UserRightDao {
    /**
     * 查询权限
     *
     * @return
     * @throws Exception
     */
    public static UserRight getUserRightByUserId(int userId) throws Exception {
        String sql = "SELECT user_right FROM user_right WHERE user_id=" + userId;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                String userRight = rs.getString("user_right");
                UserRight userRightEntity = new UserRight(userId, userRight);
                return userRightEntity;
            }
            return null;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 创建权限
     *
     * @param userRight
     * @throws Exception
     */
    public static void insertUserRight(UserRight userRight) throws Exception {
        String sql = "insert into user_right(user_id,user_right) VALUES (" + userRight.getUserId() + ",'" + userRight.getUserRight() + "')";
        DB.executeUpdate(sql);
    }

    /**
     * 更新权限
     *
     * @param userRight
     * @throws Exception
     */
    public static void updateUserRight(UserRight userRight) throws Exception {
        String sql = "update user_right set user_right='" + userRight.getUserRight() + "' where user_id=" + userRight.getUserId();
        DB.executeUpdate(sql);
    }
}

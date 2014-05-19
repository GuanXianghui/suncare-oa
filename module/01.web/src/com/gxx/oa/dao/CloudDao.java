package com.gxx.oa.dao;

import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * �����ʵ�������
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 13:22
 */
public class CloudDao implements CloudInterface {
    /**
     * ���� ���ļ���id ���� ״̬Ϊ����
     *
     * @param pid
     * @return
     * @throws Exception
     */
    public static List<Cloud> queryCloudsByPid(int userId, int pid)
            throws Exception {
        List<Cloud> list = new ArrayList<Cloud>();
        String sql = "SELECT id,user_id,type,pid,name,state,dir,route,size,create_date,create_time,create_ip FROM" +
                " cloud WHERE user_id=" + userId + " AND pid=" + pid + " AND state=" + STATE_NORMAL + " ORDER BY id";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                int type = rs.getInt("type");
                String name = rs.getString("name");
                int state = rs.getInt("state");
                String dir = rs.getString("dir");
                String route = rs.getString("route");
                long size = rs.getLong("size");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                Cloud cloud = new Cloud(id, userId, type, pid, name, state, dir, route, size, createDate, createTime, createIp);
                list.add(cloud);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����id����
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static Cloud getCloudById(int id) throws Exception {
        Cloud cloud = null;
        String sql = "SELECT id,user_id,type,pid,name,state,dir,route,size,create_date,create_time,create_ip" +
                " FROM cloud WHERE id=" + id;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                int type = rs.getInt("type");
                int pid = rs.getInt("pid");
                String name = rs.getString("name");
                int state = rs.getInt("state");
                String dir = rs.getString("dir");
                String route = rs.getString("route");
                long size = rs.getLong("size");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                cloud = new Cloud(id, userId, type, pid, name, state, dir, route, size, createDate, createTime, createIp);
            }
            return cloud;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �����û�ID��·������
     *
     * @param userId
     * @param route
     * @return
     * @throws Exception
     */
    public static Cloud getCloudByUserIdAndRoute(int userId, String route) throws Exception {
        Cloud cloud = null;
        String sql = "SELECT id,user_id,type,pid,name,state,dir,route,size,create_date,create_time,create_ip" +
                " FROM cloud WHERE user_id=" + userId + " AND route='" + route + "'";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                int type = rs.getInt("type");
                int pid = rs.getInt("pid");
                String name = rs.getString("name");
                int state = rs.getInt("state");
                String dir = rs.getString("dir");
                long size = rs.getLong("size");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                cloud = new Cloud(id, userId, type, pid, name, state, dir, route, size, createDate, createTime, createIp);
            }
            return cloud;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �����û�ID��Ŀ¼���ļ������ļ����Ͳ���
     *
     * @param userId
     * @param dir
     * @param name
     * @return
     * @throws Exception
     */
    public static Cloud getCloudByUserIdAndDirAndNameAndType(int userId, String dir, String name, int type) throws Exception {
        Cloud cloud = null;
        String sql = "SELECT id,user_id,type,pid,name,state,dir,route,size,create_date,create_time,create_ip" +
                " FROM cloud WHERE user_id=" + userId + " AND dir='" + dir + "' AND name='" + name + "' AND type=" + type;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                int pid = rs.getInt("pid");
                int state = rs.getInt("state");
                String route = rs.getString("route");
                long size = rs.getLong("size");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                cloud = new Cloud(id, userId, type, pid, name, state, dir, route, size, createDate, createTime, createIp);
            }
            return cloud;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ������
     *
     * @param cloud
     * @throws Exception
     */
    public static void insertCloud(Cloud cloud) throws Exception {
        String sql = "insert into cloud(id,user_id,type,pid,name,state,dir,route,size,create_date,create_time," +
                "create_ip) values " +
                "(null," + cloud.getUserId() + "," + cloud.getType() + "," + cloud.getPid() + ",'" +
                cloud.getName() + "'," + cloud.getState() + ",'" + cloud.getDir() + "','" + cloud.getRoute() +
                "'," + cloud.getSize() + ",'" + cloud.getCreateDate() + "','" + cloud.getCreateTime() + "','" +
                cloud.getCreateIp() + "')";
        DB.executeUpdate(sql);
    }

    /**
     * ������
     *
     * @param cloud
     * @throws Exception
     */
    public static void updateCloud(Cloud cloud) throws Exception {
        String sql = "update cloud set pid=" + cloud.getPid() + ",name='" + cloud.getName() +
                "',state=" + cloud.getState() + ",dir='" + cloud.getDir() + "',route='" +
                cloud.getRoute() + "' where id=" + cloud.getId();
        DB.executeUpdate(sql);
    }
}
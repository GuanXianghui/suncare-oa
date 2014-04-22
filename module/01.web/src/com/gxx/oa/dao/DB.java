package com.gxx.oa.dao;

import com.gxx.oa.interfaces.BaseInterface;
import com.gxx.oa.utils.PropertyUtil;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * 数据库操作基础类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 18:21
 */
public class DB implements BaseInterface {
    /**
     * 日志处理器
     */
    static Logger logger = Logger.getLogger(DB.class);

    public static Connection getConn() {

        Connection c = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }

        try {
            c = DriverManager.getConnection(PropertyUtil.getInstance().getProperty(MYSQL_CONNECTION));
        } catch (SQLException e) {
            e.printStackTrace();
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                c = null;
            }
        }
        return c;
    }

    public static Statement createStatement(Connection c) {

        Statement stmt = null;
        try {
            stmt = c.createStatement();
        } catch (SQLException e) {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                stmt = null;
            }
        }
        return stmt;
    }

    public static ResultSet executeQuery(Connection c, Statement stmt, String sql) {
        logger.info("query sql:[" + sql + "]");
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                stmt = null;
            }
        }
        return rs;
    }

    public static void executeUpdate(String sql) throws Exception {
        logger.info("update sql:[" + sql + "]");
        Connection conn = DB.getConn();
        Statement stmt = DB.createStatement(conn);
        try {
            stmt.executeUpdate(sql);
        } finally {
            DB.close(stmt);
            DB.close(conn);
        }
    }

    public static void close(ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            rs = null;
        }
    }

    public static void close(Statement stmt) {

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            stmt = null;
        }
    }

    public static void close(Connection c) {

        if (c != null) {
            try {
                c.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            c = null;
        }
    }

    public static PreparedStatement prepareStatement(Connection c, String sql) {

        PreparedStatement pstmt = null;
        try {
            pstmt = c.prepareStatement(sql);
        } catch (SQLException e) {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                pstmt = null;
            }
        }
        return pstmt;
    }

    public static void main(String[] asdf) throws Exception {
        Connection connection;
        Statement stmt;
        ResultSet rs;
        String sql;
        try {
            /*****1. 填写数据库相关信息(请查找数据库详情页)*****/
            String databaseName = "HAZRplpephWPvKPxuxLN";
            String host = "sqld.duapp.com";
            String port = "4050";
            String username = "TXPYv03bDC11icFdFHhUTCxp";//用户名(api key);
            String password = "zk4pw6n2yrCaT25x3TsPK0xkecZRLXoT";//密码(secret key)

//            databaseName = "sq_guanxxoa";
//            host = "mysql.sql61.cdncenter.net";
//            port = "3306";
//            username = "sq_guanxxoa";//用户名(api key);
//            password = "guanxxoa";//密码(secret key)

            String driverName = "com.mysql.jdbc.Driver";
            String dbUrl = "jdbc:mysql://";
            String serverName = host + ":" + port + "/";
            String connName = dbUrl + serverName + databaseName;

            /******2. 接着连接并选择数据库名为databaseName的服务器******/
            Class.forName(driverName);
            connection = DriverManager.getConnection(connName, username, password);
            stmt = connection.createStatement();
            /*至此连接已完全建立，就可对当前数据库进行相应的操作了*/
            /* 3. 接下来就可以使用其它标准mysql函数操作进行数据库操作*/
            //创建一个数据库表
            sql = "select count(1) count_num from user";
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                int countNum = rs.getInt("count_num");
                System.out.println(countNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

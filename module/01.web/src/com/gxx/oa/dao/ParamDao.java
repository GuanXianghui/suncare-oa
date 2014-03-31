package com.gxx.oa.dao;

import com.gxx.oa.entities.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 启动参数实体操作类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-31 08:41
 */
public class ParamDao {
    /**
     * 查询所有启动参数
     *
     * @return
     * @throws Exception
     */
    public static List<Param> queryAllParams() throws Exception {
        List<Param> list = new ArrayList<Param>();
        String sql = "SELECT name,value,info FROM param";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                String name = rs.getString("name");
                String value = rs.getString("value");
                String info = rs.getString("info");
                Param param = new Param(name, value, info);
                list.add(param);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 更新启动参数
     *
     * @param param
     * @throws Exception
     */
    public static void updateParam(Param param) throws Exception {
        String sql = "update param set value='" + param.getValue() + "',info='" + param.getInfo() +
                "' where name='" + param.getName() + "'";
        DB.executeUpdate(sql);
    }
}

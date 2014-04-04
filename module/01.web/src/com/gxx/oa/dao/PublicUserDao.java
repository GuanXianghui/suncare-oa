package com.gxx.oa.dao;

import com.gxx.oa.entities.PublicUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 公众账号实体操作类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-4 19:05
 */
public class PublicUserDao {
    /**
     * 查询所有公众账号
     *
     * @return
     * @throws Exception
     */
    public static List<PublicUser> queryAllPublicUsers() throws Exception {
        List<PublicUser> list = new ArrayList<PublicUser>();
        String sql = "SELECT ID,NAME,SHORT_NAME,ENGLISH_NAME,HEAD_PHOTO,URL FROM PUBLIC_USER ORDER BY ID";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String shortName = rs.getString("short_name");
                String englishName = rs.getString("english_name");
                String headPhoto = rs.getString("head_photo");
                String url = rs.getString("url");
                PublicUser publicUser = new PublicUser(id, name, shortName, englishName, headPhoto, url);
                list.add(publicUser);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }
}
package com.gxx.oa.dao;

import com.gxx.oa.entities.Structure;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 公司结构实体操作类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-31 16:06
 */
public class StructureDao {
    /**
     * 查询所有公司结构
     *
     * @return
     * @throws Exception
     */
    public static List<Structure> queryAllStructures() throws Exception {
        List<Structure> list = new ArrayList<Structure>();
        String sql = "SELECT id,type,name,pid,index_id FROM structure order by index_id";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                int type = rs.getInt("type");
                String name = rs.getString("name");
                int pid = rs.getInt("pid");
                int indexId = rs.getInt("index_id");
                Structure structure = new Structure(id, type, name, pid, indexId);
                list.add(structure);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 查询pid的子公司结构
     *
     * @return
     * @throws Exception
     */
    public static List<Structure> queryStructuresByPid(int pid) throws Exception {
        List<Structure> list = new ArrayList<Structure>();
        String sql = "SELECT id,type,name,index_id FROM structure where pid=" + pid + " order by index_id";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                int type = rs.getInt("type");
                String name = rs.getString("name");
                int indexId = rs.getInt("index_id");
                Structure structure = new Structure(id, type, name, pid, indexId);
                list.add(structure);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据id查询公司结构
     *
     * @return
     * @throws Exception
     */
    public static Structure getStructureById(int id) throws Exception {
        String sql = "SELECT type,name,pid,index_id FROM structure where id=" + id;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int type = rs.getInt("type");
                String name = rs.getString("name");
                int pid = rs.getInt("pid");
                int indexId = rs.getInt("index_id");
                Structure structure = new Structure(id, type, name, pid, indexId);
                return structure;
            }
            return null;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据pid,indexId查询公司结构
     *
     * @return
     * @throws Exception
     */
    public static Structure getStructureByPidAndIndexId(int pid, int indexId) throws Exception {
        String sql = "SELECT id,type,name FROM structure where pid=" + pid + " AND index_id=" + indexId;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                int type = rs.getInt("type");
                String name = rs.getString("name");
                Structure structure = new Structure(id, type, name, pid, indexId);
                return structure;
            }
            return null;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 新增公司结构
     *
     * @param structure
     * @throws Exception
     */
    public static void insertStructure(Structure structure) throws Exception {
        String sql = "insert structure(id,type,name,pid,index_id) values (null," + structure.getType() +
                ",'" + structure.getName() + "'," + structure.getPid() + "," + structure.getIndexId() + ")";
        DB.executeUpdate(sql);
    }

    /**
     * 更新公司结构
     *
     * @param structure
     * @throws Exception
     */
    public static void updateStructure(Structure structure) throws Exception {
        String sql = "update structure set type=" + structure.getType() + ",name='" + structure.getName() +
                "',pid=" + structure.getPid() + ",index_id=" + structure.getIndexId() + " where id=" +
                structure.getId();
        DB.executeUpdate(sql);
    }

    /**
     * 删除公司结构
     *
     * @param id
     * @throws Exception
     */
    public static void deleteStructure(int id) throws Exception {
        String sql = "delete from structure where id=" + id;
        DB.executeUpdate(sql);
    }

    /**
     * 根据pid查最大的indexId
     * @param pid
     * @return
     */
    public static int getMaxIndexIdByPid(int pid) throws Exception{
        int maxIndexId = 0;
        String sql = "SELECT MAX(index_id) max_index_id FROM structure WHERE pid=" + pid;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                maxIndexId = rs.getInt("max_index_id");
            }
            return maxIndexId;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 得到左边那个公司架构
     * @param structure
     * @return
     */
    public static Structure getLeftOne(Structure structure) throws Exception{
        int maxIndexId = 0;
        String sql = "SELECT MAX(index_id) max_index_id FROM structure WHERE pid=" + structure.getPid() +
                " AND index_id<" + structure.getIndexId();
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                maxIndexId = rs.getInt("max_index_id");
            }
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }

        // 说明没有左侧的公司结构
        if(maxIndexId == 0) {
            return null;
        }

        return getStructureByPidAndIndexId(structure.getPid(), maxIndexId);
    }

    /**
     * 得到右边那个公司架构
     * @param structure
     * @return
     */
    public static Structure getRightOne(Structure structure) throws Exception{
        int minIndexId = 0;
        String sql = "SELECT MIN(index_id) min_index_id FROM structure WHERE pid=" + structure.getPid() +
                " AND index_id>" + structure.getIndexId();
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next()) {
                minIndexId = rs.getInt("min_index_id");
            }
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }

        // 说明没有右侧的公司结构
        if(minIndexId == 0) {
            return null;
        }

        return getStructureByPidAndIndexId(structure.getPid(), minIndexId);
    }
}

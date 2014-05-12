package com.gxx.oa;

import com.gxx.oa.dao.StructureDao;
import com.gxx.oa.entities.Structure;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.List;

/**
 * 管理组织架构action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-31 20:10
 */
public class ManageOrgStructureAction extends BaseAction {
    /**
     * 操作类型静态变量
     */
    private static String  CONFIG_TYPE_MOVE2LEFT = "move2Left";
    private static String  CONFIG_TYPE_MOVE2RIGHT = "move2Right";
    private static String  CONFIG_TYPE_ADD_NODE = "addNode";
    private static String  CONFIG_TYPE_UPDATE_NODE = "updateNode";
    private static String  CONFIG_TYPE_DELETE_NODE = "deleteNode";

    /**
     * 管理类型
     */
    String configType;
    /**
     * id
     */
    int id;
    /**
     * 组织结构类型
     */
    int type;
    /**
     * 组织结构名称
     */
    String name;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        //权限校验
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0005_ORG_STRUCTURE_MANAGE);
        logger.info("configType:" + configType + ",id:" + id + ",type:" + type + ",name:" + name);
        String resp;
        if(CONFIG_TYPE_MOVE2LEFT.equals(configType)) {//左移
            Structure structure = StructureDao.getStructureById(id);
            Structure leftOne = StructureDao.getLeftOne(structure);
            if(null != leftOne) {
                int tempIndexId = structure.getIndexId();
                structure.setIndexId(leftOne.getIndexId());
                StructureDao.updateStructure(structure);
                leftOne.setIndexId(tempIndexId);
                StructureDao.updateStructure(leftOne);
            }
            resp = "{isSuccess:true,message:'左移节点成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "', newStructureJsonStr:\"" +
                    BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures()) + "\"}";
        } else if(CONFIG_TYPE_MOVE2RIGHT.equals(configType)) {//右移
            Structure structure = StructureDao.getStructureById(id);
            Structure rightOne = StructureDao.getRightOne(structure);
            if(null != rightOne) {
                int tempIndexId = structure.getIndexId();
                structure.setIndexId(rightOne.getIndexId());
                StructureDao.updateStructure(structure);
                rightOne.setIndexId(tempIndexId);
                StructureDao.updateStructure(rightOne);
            }
            resp = "{isSuccess:true,message:'右移节点成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "', newStructureJsonStr:\"" +
                    BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures()) + "\"}";
        } else if(CONFIG_TYPE_ADD_NODE.equals(configType)) {//新增节点
            Structure structure = new Structure(type, name, id, StructureDao.getMaxIndexIdByPid(id) + 1);
            StructureDao.insertStructure(structure);
            resp = "{isSuccess:true,message:'新增节点成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "', newStructureJsonStr:\"" +
                    BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures()) + "\"}";
        } else if(CONFIG_TYPE_UPDATE_NODE.equals(configType)) {//更新节点
            Structure structure = StructureDao.getStructureById(id);
            structure.setType(type);
            structure.setName(name);
            StructureDao.updateStructure(structure);
            resp = "{isSuccess:true,message:'更新节点成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "', newStructureJsonStr:\"" +
                    BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures()) + "\"}";
        } else if(CONFIG_TYPE_DELETE_NODE.equals(configType)) {//删除节点
            deleteStructure(id);
            resp = "{isSuccess:true,message:'删除节点成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "', newStructureJsonStr:\"" +
                    BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures()) + "\"}";
        } else {//异常分支
            resp = "{isSuccess:true,message:'异常发生！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        }
        write(resp);
        return null;
    }

    /**
     * 循环删除节点
     * @param deleteId
     */
    private void deleteStructure(int deleteId) throws Exception{
        List<Structure> list = StructureDao.queryStructuresByPid(deleteId);
        for(Structure tempStructure : list) {
            deleteStructure(tempStructure.getId());
        }
        StructureDao.deleteStructure(deleteId);
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

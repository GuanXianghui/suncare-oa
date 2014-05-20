package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.List;

/**
 * 重命名action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudRenameAction extends BaseAction implements CloudInterface {
    //修改对象id
    String id;
    //新文件名
    String newName;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("id:" + id + ",newName:" + newName);

        Cloud cloud = CloudDao.getCloudById(Integer.parseInt(id));
        if(cloud == null || cloud.getUserId() != getUser().getId() || cloud.getState() != CloudInterface.STATE_NORMAL){
            message = "你的操作有误，请重试!";
            String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                    "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            write(resp);
            return null;
        }

        /**
         * 根据用户id和目录 判目录合法性
         * 1.如果dir为左斜杠/则允许
         * 2.其他则dir根据/截取，每段判dir是否存在而且状态正常
         */
        BaseUtil.checkCloudDir(getUser().getId(), cloud.getDir());

        /**
         * 判当前目录下新的名字是否已经被用
         * 根据用户ID和目录和文件名和文件类型查云 状态为正常
         */
        Cloud newNameCloud = CloudDao.getCloudByUserIdAndDirAndNameAndType(getUser().getId(), cloud.getDir(), newName, cloud.getType());
        if(null != newNameCloud){
            message = "该目录[" + cloud.getDir() + "]下已存在相同名字[" + newName + "]的" + BaseUtil.getCloudTypeDesc(cloud.getType()) + "!";
            String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                    "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            write(resp);
            return null;
        }

        //修改名字
        cloud.setName(newName);
        /**
         * route varchar(1100) comment '相对路径 可为空
         * 如果是文件夹则有文件夹路径route=dir+name+/ 如果是文件则为真实文件相对路径'
         * route=dir+name+/
         */
        if(cloud.getType() == TYPE_DIR){
            cloud.setRoute(cloud.getDir() + newName + SymbolInterface.SYMBOL_SLASH);
        }
        CloudDao.updateCloud(cloud);

        /**
         * 修改文件夹名字时，该文件夹的下级所有对象的dir都做修改
         */
        if(cloud.getType() == TYPE_DIR){
            List<Cloud> clouds = CloudDao.queryCloudsByPid(getUser().getId(), cloud.getId());
            for(Cloud tempCloud : clouds){
                tempCloud.setDir(cloud.getRoute());
                CloudDao.updateCloud(tempCloud);
            }
        }

        //从申成云集合得到Json数组
        List<Cloud> clouds = CloudDao.queryCloudsByPid(getUser().getId(), cloud.getPid());
        String filesJsonStr = BaseUtil.getJsonArrayFromClouds(clouds).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");

        //返回结果
        String resp = "{isSuccess:true,message:'重命名成功！',filesJsonStr:'" + filesJsonStr + "'," +
                "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

        write(resp);
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}

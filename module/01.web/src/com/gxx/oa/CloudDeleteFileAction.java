package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 删除文件action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudDeleteFileAction extends BaseAction implements CloudInterface {
    //所处目录
    String dir;
    //删除文件ID
    String deleteIds;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("dir:" + dir + ",deleteIds:" + deleteIds);

        /**
         * 根据用户id和目录 判目录合法性
         * 1.如果dir为左斜杠/则允许
         * 2.其他则dir根据/截取，每段判dir是否存在而且状态正常
         */
        BaseUtil.checkCloudDir(getUser().getId(), dir);

        //删除的申成云
        List<Cloud> deleteClouds = new ArrayList<Cloud>();
        String[] fileIds = deleteIds.split(SymbolInterface.SYMBOL_COMMA);
        //逐个校验是否都存在
        for(int i=0;i<fileIds.length;i++){
            int id = Integer.parseInt(fileIds[i]);
            Cloud cloud = CloudDao.getCloudById(id);
            if(cloud == null){
                message = "该目录[" + dir + "]下不存在ID为[" + id + "]的对象!";
                String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                        "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
                write(resp);
                return null;
            }
            deleteClouds.add(cloud);
        }

        //逐个删除
        for(Cloud cloud : deleteClouds){
            cloud.setState(STATE_DELETE);
            CloudDao.updateCloud(cloud);
        }

        int pid = FRONT_DIR_PID;
        if(!StringUtils.equals(FRONT_DIR, dir)){
            Cloud cloud = CloudDao.getCloudByUserIdAndRoute(getUser().getId(), dir);
            pid = cloud.getId();
        }

        //从申成云集合得到Json数组
        List<Cloud> clouds = CloudDao.queryCloudsByPid(getUser().getId(), pid);
        String filesJsonStr = BaseUtil.getJsonArrayFromClouds(clouds).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");

        //返回结果
        String resp = "{isSuccess:true,message:'删除文件成功！',filesJsonStr:'" + filesJsonStr + "'," +
                "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

        write(resp);
        return null;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteIds(String deleteIds) {
        this.deleteIds = deleteIds;
    }
}

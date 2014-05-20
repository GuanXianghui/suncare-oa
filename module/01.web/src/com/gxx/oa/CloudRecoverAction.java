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
 * 还原文件action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudRecoverAction extends BaseAction implements CloudInterface {
    //还原对象id
    String recoverIds;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("recoverIds:" + recoverIds);

        //还原的申成云
        List<Cloud> recoverClouds = new ArrayList<Cloud>();
        String[] fileIds = recoverIds.split(SymbolInterface.SYMBOL_COMMA);
        //逐个校验是否都存在
        for(int i=0;i<fileIds.length;i++){
            int id = Integer.parseInt(fileIds[i]);
            Cloud cloud = CloudDao.getCloudById(id);
            if(cloud == null || cloud.getState() != STATE_DELETE){
                message = "你的操作有误，请刷新页面重试!";
                String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                        "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
                write(resp);
                return null;
            }
            recoverClouds.add(cloud);
        }

        //逐个还原
        for(Cloud cloud : recoverClouds){
            //根据用户ID和目录和文件名和文件类型查云 状态为正常
            Cloud existCloud = CloudDao.getCloudByUserIdAndDirAndNameAndType(getUser().getId(), cloud.getDir(), cloud.getName(), cloud.getType());
            if(existCloud != null){
                message = "目录[" + cloud.getDir() + "]下已经存在名字为[" + cloud.getName() + "]的" +
                        BaseUtil.getCloudTypeDesc(cloud.getType()) + "!";
                String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                        "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
                write(resp);
                return null;
            }

            try{
                //根据用户id和目录 判目录合法性 如果不合法 则逐层创建目录
                BaseUtil.checkCloudDir(getUser().getId(), cloud.getDir());
            } catch (Exception e){
                //逐层创建目录
                BaseUtil.createCloudDir(getUser().getId(), cloud.getDir(), date, time, getIp());
            }

            int pid = 0;
            if(!StringUtils.equals(FRONT_DIR, cloud.getDir())){
                //更新pid和正常状态
                Cloud pidCloud = CloudDao.getCloudByUserIdAndRoute(getUser().getId(), cloud.getDir());
                pid = pidCloud.getId();
            }
            cloud.setPid(pid);
            cloud.setState(STATE_NORMAL);
            CloudDao.updateCloud(cloud);
        }

        //从申成云集合得到Json数组
        List<Cloud> clouds = CloudDao.queryRecycleClouds(getUser().getId());
        String filesJsonStr = BaseUtil.getJsonArrayFromClouds(clouds).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");

        //返回结果
        String resp = "{isSuccess:true,message:'还原文件成功！',filesJsonStr:'" + filesJsonStr + "'," +
                "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

        write(resp);
        return null;
    }

    public String getRecoverIds() {
        return recoverIds;
    }

    public void setRecoverIds(String recoverIds) {
        this.recoverIds = recoverIds;
    }
}

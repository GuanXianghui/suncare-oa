package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 加载文件夹action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudLoadDirAction extends BaseAction implements CloudInterface {
    //目录
    String dir;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("dir:" + dir);

        /**
         * 根据用户id和目录 判目录合法性
         * 1.如果dir为左斜杠/则允许
         * 2.其他则dir根据/截取，每段判dir是否存在而且状态正常
         */
        BaseUtil.checkCloudDir(getUser().getId(), dir);

        //父目录id
        int pid = FRONT_DIR_PID;
        if(!StringUtils.equals(FRONT_DIR, dir)){
            Cloud dirCloud = CloudDao.getCloudByUserIdAndRoute(getUser().getId(), dir);
            pid = dirCloud.getId();
        }

        //从申成云集合得到Json数组
        List<Cloud> clouds = CloudDao.queryCloudsByPid(getUser().getId(), pid);
        String filesJsonStr = BaseUtil.getJsonArrayFromClouds(clouds).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");

        //返回结果
        String resp = "{isSuccess:true,message:'加载文件夹[" + dir + "]成功！',filesJsonStr:'" + filesJsonStr + "'," +
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
}

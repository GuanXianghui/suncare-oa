package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 新建文件夹action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudNewDirDirAction extends BaseAction implements CloudInterface {
    //所处目录
    String dir;
    //新建目录
    String newDir;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("dir:" + dir + ",newDir:" + newDir);

        /**
         * 根据用户id和目录 判目录合法性
         * 1.如果dir为左斜杠/则允许
         * 2.其他则dir根据/截取，每段判dir是否存在而且状态正常
         */
        BaseUtil.checkDir(getUser().getId(), dir);

        //根据用户ID和目录和文件名和文件类型查云
        Cloud cloud = CloudDao.getCloudByUserIdAndDirAndNameAndType(getUser().getId(), dir, newDir, TYPE_DIR);
        if(cloud != null && cloud.getState() == STATE_NORMAL){
            message = "该目录[" + dir + "]下已存在相同名字[" + newDir + "]的文件夹!";
            String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                    "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            write(resp);
            return null;
        }

        //父目录id
        int pid = FRONT_DIR_PID;
        if(!StringUtils.equals(FRONT_DIR, dir)){
            Cloud dirCloud = CloudDao.getCloudByUserIdAndRoute(getUser().getId(), dir);
            pid = dirCloud.getId();
        }

        //新增云文件夹
        cloud = new Cloud(getUser().getId(), TYPE_DIR, pid, newDir, STATE_NORMAL, dir,
                dir+newDir+ SymbolInterface.SYMBOL_SLASH, 0, date, time, getIp());
        CloudDao.insertCloud(cloud);

        //从申成云集合得到Json数组
        List<Cloud> clouds = CloudDao.queryCloudsByPid(getUser().getId(), pid);
        String filesJsonStr = BaseUtil.getJsonArrayFromClouds(clouds).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");

        //返回结果
        String resp = "{isSuccess:true,message:'新建文件夹成功！',filesJsonStr:'" + filesJsonStr + "'," +
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

    public String getNewDir() {
        return newDir;
    }

    public void setNewDir(String newDir) {
        this.newDir = newDir;
    }
}

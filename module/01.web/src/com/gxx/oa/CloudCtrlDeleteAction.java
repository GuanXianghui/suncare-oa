package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 彻底删除文件action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudCtrlDeleteAction extends BaseAction implements CloudInterface {
    //彻底删除文件ID
    String ctrlDeleteIds;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("ctrlDeleteIds:" + ctrlDeleteIds);

        //删除的申成云
        List<Cloud> ctrlDeleteClouds = new ArrayList<Cloud>();
        String[] fileIds = ctrlDeleteIds.split(SymbolInterface.SYMBOL_COMMA);
        //逐个校验是否都存在
        for(int i=0;i<fileIds.length;i++){
            int id = Integer.parseInt(fileIds[i]);
            Cloud cloud = CloudDao.getCloudById(id);
            if(cloud == null){
                message = "你的操作不合法，请刷新页面重试!";
                String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                        "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
                write(resp);
                return null;
            }
            ctrlDeleteClouds.add(cloud);
        }

        //逐个彻底删除
        for(Cloud cloud : ctrlDeleteClouds){
            cloud.setState(STATE_CTRL_DELETE);
            CloudDao.updateCloud(cloud);
        }

        //从申成云集合得到Json数组
        List<Cloud> clouds = CloudDao.queryRecycleClouds(getUser().getId());
        String filesJsonStr = BaseUtil.getJsonArrayFromClouds(clouds).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");

        //返回结果
        String resp = "{isSuccess:true,message:'彻底删除文件成功！',filesJsonStr:'" + filesJsonStr + "'," +
                "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

        write(resp);
        return null;
    }

    public String getCtrlDeleteIds() {
        return ctrlDeleteIds;
    }

    public void setCtrlDeleteIds(String ctrlDeleteIds) {
        this.ctrlDeleteIds = ctrlDeleteIds;
    }
}

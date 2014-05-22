package com.gxx.oa;

import com.gxx.oa.dao.CloudDocDao;
import com.gxx.oa.entities.CloudDoc;
import com.gxx.oa.interfaces.CloudDocInterface;
import org.apache.commons.lang.StringUtils;

/**
 * 删除文档action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudDeleteDocAction extends BaseAction implements CloudDocInterface {
    /**
     * 文档id
     */
    private String docId;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("docId:" + docId);
        //判文档id不为空
        if(StringUtils.isBlank(docId)){
            message = "文档id不能为空!";
            return ERROR;
        }
        // 文档idInt类型
        int docInInt = Integer.parseInt(docId);
        //判文档存在
        CloudDoc cloudDoc = CloudDocDao.getCloudDocById(docInInt);
        if(null == cloudDoc || cloudDoc.getUserId() != getUser().getId() || cloudDoc.getState() != STATE_NORMAL){
            message = "你的操作有误，请刷新页面重试!";
            return ERROR;
        }

        cloudDoc.setState(STATE_DELETE);
        cloudDoc.setUpdateDate(date);
        cloudDoc.setUpdateTime(time);
        cloudDoc.setUpdateIp(getIp());
        CloudDocDao.updateCloudDoc(cloudDoc);

        message = "删除文档成功！";
        return SUCCESS;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}

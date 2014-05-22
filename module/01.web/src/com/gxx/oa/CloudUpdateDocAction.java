package com.gxx.oa;

import com.gxx.oa.dao.CloudDocDao;
import com.gxx.oa.entities.CloudDoc;
import com.gxx.oa.interfaces.CloudDocInterface;
import org.apache.commons.lang.StringUtils;

/**
 * 申成文库上传文档action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudUpdateDocAction extends BaseAction implements CloudDocInterface {
    /**
     * 文档id
     */
    private String docId;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 分类
     */
    private String type;
    /**
     * 标签
     */
    private String tags;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("title:" + title + ",description:" + description + ",type:" + type +
                ",tags:" + tags + ",docId:" + docId);
        //判文档id不为空
        if(StringUtils.isBlank(docId)){
            message = "文档id不能为空!";
            return ERROR;
        }
        //判必输字段为空
        if(StringUtils.isBlank(title) || StringUtils.isBlank(type))
        {
            message = "标题和分类不能为空!";
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

        cloudDoc.setTitle(title);
        cloudDoc.setDescription(description);
        cloudDoc.setType(type);
        cloudDoc.setTags(tags);
        cloudDoc.setUpdateDate(date);
        cloudDoc.setUpdateTime(time);
        cloudDoc.setUpdateIp(getIp());
        CloudDocDao.updateCloudDoc(cloudDoc);

        message = "修改文档成功！";
        return SUCCESS;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}

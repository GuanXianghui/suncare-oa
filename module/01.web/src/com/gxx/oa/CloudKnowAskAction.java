package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.interfaces.CloudKnowAskInterface;
import org.apache.commons.lang.StringUtils;

/**
 * 申成文库上传文档action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudKnowAskAction extends BaseAction implements CloudKnowAskInterface {
    /**
     * 问题
     */
    private String question;
    /**
     * 问题补充
     */
    private String description;
    /**
     * 分类
     */
    private String type;
    /**
     * 紧急
     */
    private String urgent;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("question:" + question + ",description:" + description + ",type:" + type +
                ",urgent:" + urgent);
        //判必输字段为空
        if(StringUtils.isBlank(question) || StringUtils.isBlank(type) || StringUtils.isBlank(urgent))
        {
            message = "问题，分类和紧急不能为空!";
            return ERROR;
        }

        //新增申成知道提问
        CloudKnowAsk cloudKnowAsk = new CloudKnowAsk(getUser().getId(), question, description, type,
                Integer.parseInt(urgent), STATE_NORMAL, date, time, getIp(), StringUtils.EMPTY, StringUtils.EMPTY,
                StringUtils.EMPTY);
        CloudKnowAskDao.insertCloudKnowAsk(cloudKnowAsk);

        message = "提问到申成知道成功，我们会找到相应的人帮你解答！";
        return SUCCESS;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }
}

package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.interfaces.CloudKnowAskInterface;
import org.apache.commons.lang.StringUtils;

/**
 * 删除提问action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudKnowDeleteAskAction extends BaseAction implements CloudKnowAskInterface {
    /**
     * 提问id
     */
    private String askId;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("askId:" + askId);
        //判提问id不为空
        if(StringUtils.isBlank(askId)){
            message = "提问id不能为空!";
            return ERROR;
        }
        // 提问idInt类型
        int askIdInt = Integer.parseInt(askId);
        //判文档存在
        CloudKnowAsk cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(askIdInt);
        if(null == cloudKnowAsk || cloudKnowAsk.getUserId() != getUser().getId() || cloudKnowAsk.getState() != STATE_NORMAL){
            message = "你的操作有误，请刷新页面重试!";
            return ERROR;
        }

        //更新申成知道提问
        cloudKnowAsk.setState(STATE_DELETE);
        cloudKnowAsk.setUpdateDate(date);
        cloudKnowAsk.setUpdateTime(time);
        cloudKnowAsk.setUpdateIp(getIp());
        CloudKnowAskDao.updateCloudKnowAsk(cloudKnowAsk);

        message = "删除提问成功！";
        return SUCCESS;
    }

    public String getAskId() {
        return askId;
    }

    public void setAskId(String askId) {
        this.askId = askId;
    }
}

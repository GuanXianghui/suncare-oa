package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAnswerDao;
import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.entities.CloudKnowAnswer;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.interfaces.CloudKnowAnswerInterface;
import com.gxx.oa.interfaces.CloudKnowAskInterface;
import org.apache.commons.lang.StringUtils;

/**
 * 删除回答action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudKnowDeleteAnswerAction extends BaseAction implements CloudKnowAnswerInterface {
    /**
     * 提问id
     */
    private String askId;
    /**
     * 回答id
     */
    private String answerId;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("askId:" + askId + ",answerId:" + answerId);
        //判回答id不为空
        if(StringUtils.isBlank(answerId)){
            message = "回答id不能为空!";
            return ERROR;
        }
        // 回答idInt类型
        int answerIdInt = Integer.parseInt(answerId);
        //判文档存在
        CloudKnowAnswer cloudKnowAnswer = CloudKnowAnswerDao.getCloudKnowAnswerById(answerIdInt);
        if(null == cloudKnowAnswer || cloudKnowAnswer.getUserId() != getUser().getId() || cloudKnowAnswer.getState() != STATE_NORMAL){
            message = "你的操作有误，请刷新页面重试!";
            return ERROR;
        }

        //根据id查申成知道提问
        CloudKnowAsk cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(cloudKnowAnswer.getAskId());
        if(null == cloudKnowAsk || cloudKnowAsk.getState() != CloudKnowAskInterface.STATE_NORMAL){
            message = "你的操作有误，请刷新页面重试!";
            return ERROR;
        }

        //赋值 提问id 用于跳转
        askId = StringUtils.EMPTY + cloudKnowAsk.getId();

        //更新申成知道提问
        cloudKnowAnswer.setState(STATE_DELETE);
        cloudKnowAnswer.setUpdateDate(date);
        cloudKnowAnswer.setUpdateTime(time);
        cloudKnowAnswer.setUpdateIp(getIp());
        CloudKnowAnswerDao.updateCloudKnowAnswer(cloudKnowAnswer);

        message = "删除回答成功！";
        return SUCCESS;
    }

    public String getAskId() {
        return askId;
    }

    public void setAskId(String askId) {
        this.askId = askId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
}

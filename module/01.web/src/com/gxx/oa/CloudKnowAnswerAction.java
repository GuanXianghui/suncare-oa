package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAnswerDao;
import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.entities.CloudKnowAnswer;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.interfaces.CloudKnowAnswerInterface;
import com.gxx.oa.interfaces.CloudKnowAskInterface;
import org.apache.commons.lang.StringUtils;

/**
 * 申成文库上传文档action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudKnowAnswerAction extends BaseAction implements CloudKnowAnswerInterface {
    /**
     * 提问id
     */
    private String askId;
    /**
     * 回答
     */
    private String answer;
    /**
     * 回答类型 type: insert新增回答 update修改回答
     */
    private String type;
    /**
     * 修改回答ID
     */
    private String answerId;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("askId:" + askId + ",answer:" + answer + ",type:" + type + ",answerId:" + answerId);
        //判必输字段为空
        if(StringUtils.isBlank(askId) || StringUtils.isBlank(answer) || StringUtils.isBlank(type))
        {
            message = "提问id，回答，回答类型不能为空!";
            return ERROR;
        }

        //判类型为修改回答
        if(StringUtils.equals(type, "update")){
            if(StringUtils.isBlank(answerId)){
                message = "修改回答ID不能为空!";
                return ERROR;
            }
            CloudKnowAnswer cloudKnowAnswer = CloudKnowAnswerDao.getCloudKnowAnswerById(Integer.parseInt(answerId));
            if(cloudKnowAnswer == null || cloudKnowAnswer.getUserId() != getUser().getId() || cloudKnowAnswer.getState() != STATE_NORMAL){
                message = "该回答状态不正常!";
                return ERROR;
            }
            //根据id查申成知道提问
            CloudKnowAsk cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(Integer.parseInt(askId));
            if(cloudKnowAsk == null || cloudKnowAsk.getState() != CloudKnowAskInterface.STATE_NORMAL){
                message = "该提问状态不正常!";
                return ERROR;
            }
            cloudKnowAnswer.setAnswer(answer);
            cloudKnowAnswer.setUpdateDate(date);
            cloudKnowAnswer.setUpdateTime(time);
            cloudKnowAnswer.setUpdateIp(getIp());
            CloudKnowAnswerDao.updateCloudKnowAnswer(cloudKnowAnswer);
            message = "修改回答成功！";
            return SUCCESS;
        }
        //以下类型为新增回答
        //根据id查申成知道提问
        CloudKnowAsk cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(Integer.parseInt(askId));
        if(cloudKnowAsk == null || cloudKnowAsk.getState() != CloudKnowAskInterface.STATE_NORMAL){
            message = "该提问状态不正常!";
            return ERROR;
        }

        //新增申成知道回答
        CloudKnowAnswer cloudKnowAnswer = new CloudKnowAnswer(cloudKnowAsk.getId(), getUser().getId(), answer, 0,
                STATE_NORMAL, date, time, getIp(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        CloudKnowAnswerDao.insertCloudKnowAnswer(cloudKnowAnswer);

        message = "你的回答已提交，申成知道感谢你的贡献！";
        return SUCCESS;
    }

    public String getAskId() {
        return askId;
    }

    public void setAskId(String askId) {
        this.askId = askId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
}

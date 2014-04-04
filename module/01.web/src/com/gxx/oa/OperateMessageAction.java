package com.gxx.oa;

import com.gxx.oa.dao.MessageDao;
import com.gxx.oa.entities.Message;
import com.gxx.oa.interfaces.BaseInterface;
import com.gxx.oa.interfaces.MessageInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.PropertyUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.List;

/**
 * 操作消息action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-5 00:19
 */
public class OperateMessageAction extends BaseAction {
    /**
     * 操作类型
     */
    private static final String TYPE_READ = "read";
    private static final String TYPE_DELETE = "delete";
    private static final String TYPE_NEXT_PAGE = "nextPage";

    /**
     * 操作类型
     */
    String type;
    String messageId;
    String countNow;


    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("type:" + type + ",messageId=" + messageId + ",countNow=" + countNow);
        //ajax结果
        String resp;
        if(TYPE_READ.equals(type)){
            Message message = MessageDao.getMessageById(Integer.parseInt(messageId));
            if(null != message && MessageInterface.STATE_READED != message.getState()){//没有记录则是已被删除
                message.setState(MessageInterface.STATE_READED);
                message.setDate(date);
                message.setTime(time);
                message.setIp(getIp());
                MessageDao.updateMessage(message);
            }
            resp = "{isSuccess:true,message:'阅读消息成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(TYPE_DELETE.equals(type)){
            Message message = MessageDao.getMessageById(Integer.parseInt(messageId));
            if(null != message){//没有记录则是已被删除
                MessageDao.deleteMessage(message);
            }
            resp = "{isSuccess:true,message:'删除消息成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(TYPE_NEXT_PAGE.equals(type)) {
            //下一页消 limit countNow~countNow+pageSize
            List<Message> nextPageMessages = MessageDao.queryMessagesByUserIdAndFromTo(getUser().getId(),
                    Integer.parseInt(countNow), Integer.parseInt(PropertyUtil.getInstance().
                            getProperty(BaseInterface.MESSAGE_PAGE_SIZE)));
            /**
             * 下一页消息Json串
             * replaceAll("\\\'", "\\\\\\\'")，转换单引号
             * replaceAll("\\\"", "\\\\\\\"")，转换双引号
             * replaceAll("\r\n", uuid)，转换换行符成uuid
             */
            String nextPageJson = BaseUtil.getJsonArrayFromMessages(nextPageMessages).replaceAll("\\\'", "\\\\\\\'").
                    replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                    PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID));
            //返回结果
            resp = "{isSuccess:true,message:'加载下一页消息成功！',nextPageJson:'" + nextPageJson +
                    "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

        } else {
            resp = "{isSuccess:false,message:'操作类型有误！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        }
        write(resp);
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getCountNow() {
        return countNow;
    }

    public void setCountNow(String countNow) {
        this.countNow = countNow;
    }
}

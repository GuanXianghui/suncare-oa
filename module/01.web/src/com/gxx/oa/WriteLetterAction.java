package com.gxx.oa;

import com.gxx.oa.dao.LetterDao;
import com.gxx.oa.entities.Letter;
import com.gxx.oa.interfaces.LetterInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.interfaces.UserInterface;
import org.apache.commons.lang.StringUtils;

/**
 * 写信action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-6 20:02
 */
public class WriteLetterAction extends BaseAction {
    //收件人
    private String toUserIds;
    //抄送
    private String ccUserIds;
    //标题
    private String title;
    //内容
    private String content;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("toUserIds:" + toUserIds + ",ccUserIds:" + ccUserIds + ",title:" + title + ",content:" + content);
        // 1 创建一条发送的站内信
        Letter sendLetter = new Letter(getUser().getId(), UserInterface.USER_TYPE_NORMAL, LetterInterface.SEND,
                getUser().getId(), UserInterface.USER_TYPE_NORMAL, toUserIds, getUserTypesFromUserIds(toUserIds),
                ccUserIds, getUserTypesFromUserIds(ccUserIds), LetterInterface.READ_STATE_READED,
                LetterInterface.DELETE_STATE_NOT_DELETED, title, content, date, time, getIp(),
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        LetterDao.insertLetter(sendLetter);

        // 2 创建接受的站内信
        if(StringUtils.isNotBlank(toUserIds)){
            String[] ids = toUserIds.split(SymbolInterface.SYMBOL_COMMA);
            for(int i=0;i<ids.length;i++){
                sendLetter = new Letter(Integer.parseInt(ids[i]), UserInterface.USER_TYPE_NORMAL, LetterInterface.RECEIVE,
                        getUser().getId(), UserInterface.USER_TYPE_NORMAL, toUserIds, getUserTypesFromUserIds(toUserIds),
                        ccUserIds, getUserTypesFromUserIds(ccUserIds), LetterInterface.READ_STATE_NOT_READED,
                        LetterInterface.DELETE_STATE_NOT_DELETED, title, content, date, time, getIp(),
                        StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
                LetterDao.insertLetter(sendLetter);
            }
        }

        // 3 创建抄送的站内信
        if(StringUtils.isNotBlank(ccUserIds)){
            String[] ids = ccUserIds.split(SymbolInterface.SYMBOL_COMMA);
            for(int i=0;i<ids.length;i++){
                sendLetter = new Letter(Integer.parseInt(ids[i]), UserInterface.USER_TYPE_NORMAL, LetterInterface.RECEIVE,
                        getUser().getId(), UserInterface.USER_TYPE_NORMAL, toUserIds, getUserTypesFromUserIds(toUserIds),
                        ccUserIds, getUserTypesFromUserIds(ccUserIds), LetterInterface.READ_STATE_NOT_READED,
                        LetterInterface.DELETE_STATE_NOT_DELETED, title, content, date, time, getIp(),
                        StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
                LetterDao.insertLetter(sendLetter);
            }
        }

        message = "写信成功！";
        return SUCCESS;
    }

    /**
     * 根据用户ids(逗号分隔)，得到用户类型(逗号分隔)
     * todo 临时先这么写，因为前段没有传过来用户类型
     * @param userIds
     * @return
     */
    private String getUserTypesFromUserIds(String userIds){
        String userTypes = StringUtils.EMPTY;
        if(StringUtils.isBlank(userIds)){
            return userTypes;
        }
        for(int i=0;i<userIds.split(SymbolInterface.SYMBOL_COMMA).length;i++){
            if(StringUtils.isNotBlank(userTypes)){
                userTypes += SymbolInterface.SYMBOL_COMMA;
            }
            userTypes += UserInterface.USER_TYPE_NORMAL;
        }
        return userTypes;
    }

    public String getToUserIds() {
        return toUserIds;
    }

    public void setToUserIds(String toUserIds) {
        this.toUserIds = toUserIds;
    }

    public String getCcUserIds() {
        return ccUserIds;
    }

    public void setCcUserIds(String ccUserIds) {
        this.ccUserIds = ccUserIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

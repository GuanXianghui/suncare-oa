package com.gxx.oa;

import com.gxx.oa.dao.LetterDao;
import com.gxx.oa.entities.Letter;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.PropertyUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

/**
 * 操作站内信action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-6 22:06
 */
public class OperateLetterAction extends BaseAction {
    /**
     * 操作类型
     */
    private static final String TYPE_NEXT_PAGE = "nextPage";//加载下一页
    private static final String TYPE_DELETE = "delete";//删除
    private static final String TYPE_CTRL_DELETE = "ctrlDelete";//彻底删除
    private static final String TYPE_SET_READED = "setReaded";//标记成已读
    private static final String TYPE_RESTORE = "restore";//还原

    /**
     * 操作类型
     */
    String type;//操作类型
    String ids;//选中站内信ids
    String box;//站内信箱类型
    String countNow;//目前的量

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("type:" + type + ",box=" + box + ",countNow=" + countNow);
        //ajax结果
        String resp;
        if(TYPE_NEXT_PAGE.equals(type)){//加载下一页
            //根据用户id，用户类型，查询类型，范围查站内信
            String nextPageLetters = BaseUtil.getJsonArrayFromLetters(LetterDao.queryLettersByTypeAndFromTo(getUser().getId(),
                    UserInterface.USER_TYPE_NORMAL, getTypeFromBox(box), Integer.parseInt(countNow),
                    Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.LETTER_PAGE_SIZE))), box).
                    replaceAll("\\\'", "\\\\\\\'").replaceAll("\\\"", "\\\\\\\"").
                    replaceAll(SymbolInterface.SYMBOL_NEW_LINE, PropertyUtil.getInstance().
                    getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID));

            //返回结果
            resp = "{isSuccess:true,message:'加载下一页消息成功！',nextPageJson:'" + nextPageLetters +
                    "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
        } else if(TYPE_DELETE.equals(type)){//删除
            //循环删除站内信
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(SymbolInterface.SYMBOL_COMMA);
                for(int i=0;i<idArray.length;i++){
                    Letter letter = LetterDao.getLetterById(Integer.parseInt(idArray[i]));
                    if(letter.getUserId() != getUser().getId()){
                        logger.error("该站内信不是该用户的！userId=" + getUser().getId() + ",letterId=" + letter.getId());
                        continue;
                    }
                    letter.setDeleteState(LetterInterface.DELETE_STATE_DELETED);
                    letter.setOperateDate(date);
                    letter.setOperateTime(time);
                    letter.setOperateIp(getIp());
                    LetterDao.updateLetter(letter);
                }
            }
            //返回结果
            resp = "{isSuccess:true,message:'删除站内信成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(TYPE_CTRL_DELETE.equals(type)){//彻底删除
            //循环彻底删除站内信
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(SymbolInterface.SYMBOL_COMMA);
                for(int i=0;i<idArray.length;i++){
                    Letter letter = LetterDao.getLetterById(Integer.parseInt(idArray[i]));
                    if(letter.getUserId() != getUser().getId()){
                        logger.error("该站内信不是该用户的！userId=" + getUser().getId() + ",letterId=" + letter.getId());
                        continue;
                    }
                    LetterDao.deleteLetter(letter);
                }
            }
            //返回结果
            resp = "{isSuccess:true,message:'彻底删除站内信成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(TYPE_SET_READED.equals(type)){//标记成已读
            //循环标记站内信成已读
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(SymbolInterface.SYMBOL_COMMA);
                for(int i=0;i<idArray.length;i++){
                    Letter letter = LetterDao.getLetterById(Integer.parseInt(idArray[i]));
                    if(letter.getUserId() != getUser().getId()){
                        logger.error("该站内信不是该用户的！userId=" + getUser().getId() + ",letterId=" + letter.getId());
                        continue;
                    }
                    letter.setReadState(LetterInterface.READ_STATE_READED);
                    letter.setOperateDate(date);
                    letter.setOperateTime(time);
                    letter.setOperateIp(getIp());
                    LetterDao.updateLetter(letter);
                }
            }
            //返回结果
            resp = "{isSuccess:true,message:'标记站内信成已读成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(TYPE_RESTORE.equals(type)){//还原
            //循环还原站内信
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(SymbolInterface.SYMBOL_COMMA);
                for(int i=0;i<idArray.length;i++){
                    Letter letter = LetterDao.getLetterById(Integer.parseInt(idArray[i]));
                    if(letter.getUserId() != getUser().getId()){
                        logger.error("该站内信不是该用户的！userId=" + getUser().getId() + ",letterId=" + letter.getId());
                        continue;
                    }
                    letter.setDeleteState(LetterInterface.DELETE_STATE_NOT_DELETED);
                    letter.setOperateDate(date);
                    letter.setOperateTime(time);
                    letter.setOperateIp(getIp());
                    LetterDao.updateLetter(letter);
                }
            }
            //返回结果
            resp = "{isSuccess:true,message:'还原站内信成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else {
            resp = "{isSuccess:false,message:'操作类型有误！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        }
        write(resp);
        return null;
    }

    /**
     * 从box得到type
     * @param box
     * @return
     */
    private int getTypeFromBox(String box){
        int type;
        if(StringUtils.equals(box, LetterInterface.BOX_SENT)){
            type = LetterInterface.TYPE_SENT;
        } else if(StringUtils.equals(box, LetterInterface.BOX_DELETED)){
            type = LetterInterface.TYPE_DELETED;
        } else {
            type = LetterInterface.TYPE_RECEIVED;
        }
        return type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getCountNow() {
        return countNow;
    }

    public void setCountNow(String countNow) {
        this.countNow = countNow;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}

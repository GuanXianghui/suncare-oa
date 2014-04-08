package com.gxx.oa.utils;

import com.gxx.oa.dao.StructureDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.*;
import com.gxx.oa.interfaces.*;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ����������
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-30 12:09
 */
public class BaseUtil implements SymbolInterface {
    /**
     * �е�¼
     * @param request
     */
    public static void checkLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //��������
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
        if(request.getSession().getAttribute(BaseInterface.USER_KEY) == null) {
            response.sendRedirect(baseUrl + "index.jsp");
        }
    }

    /**
     * ����dao+session�û�������Ϣ
     * @param request
     */
    public static void refreshUserVisit(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(BaseInterface.USER_KEY);
        user.setVisitDate(DateUtil.getNowDate());
        user.setVisitTime(DateUtil.getNowTime());
        user.setVisitIp(IPAddressUtil.getIPAddress(request));
        try {
            UserDao.updateUserVisitInfo(user);
            request.getSession().setAttribute(BaseInterface.USER_KEY, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * �ӹ�˾�ṹ���ϵõ�Json����
     * @param list
     * @return
     */
    public static String getJsonArrayFromStructures(List<Structure> list) {
        String result = StringUtils.EMPTY;
        for(Structure structure : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_BIT_AND;
            }
            result += "{id:" + structure.getId() + ",type:" + structure.getType() + ",name:'" +
                    structure.getName() + "',pid:" + structure.getPid() + ",indexId:" + structure.getIndexId() + "}";
        }
        return result;
    }

    /**
     * �����û��Ա�
     * @param sex
     * @return
     */
    public static String translateUserSex(int sex) {
        if(UserInterface.SEX_X == sex) {
            return "��";
        }
        if(UserInterface.SEX_O == sex) {
            return "Ů";
        }
        return StringUtils.EMPTY;
    }

    /**
     * ���û����ϵõ�Json����
     * @param list
     * @return
     */
    public static String getJsonArrayFromUsers(List<User> list) {
        String result = StringUtils.EMPTY;
        for(User user : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_BIT_AND;
            }
            result += "{id:" + user.getId() + ",name:'" + user.getName() + "',letter:'" +
                    user.getLetter() + "',state:" + user.getState() + ",company:" + user.getCompany() +
                    ",dept:" + user.getDept() + ",position:" + user.getPosition() + ",desk:'" + user.getDesk() +
                    "',sex:" + user.getSex() +",birthday:'" + user.getBirthday() +"',officeTel:'" + user.getOfficeTel() +
                    "',mobileTel:'" + user.getMobileTel() +"',email:'" + user.getEmail() +"',qq:'" + user.getQq() +
                    "',msn:'" + user.getMsn() +"',address:'" + user.getAddress() +"',headPhoto:'" + user.getHeadPhoto() +
                    "',website:'" + user.getWebsite() + "'}";
        }
        return result;
    }

    /**
     * �ӹ��漯�ϵõ�Json����
     * @param list
     * @return
     */
    public static String getJsonArrayFromNotices(List<Notice> list) {
        String result = StringUtils.EMPTY;
        for(Notice notice : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            result += "{id:" + notice.getId() + ",title:'" + notice.getTitle() + "',content:'" +
                    notice.getContent() + "',createDate:'" + notice.getCreateDate() + "',createTime:'" +
                    notice.getCreateTime() + "',createIp:'" + notice.getCreateIp() + "',updateDate:'" +
                    notice.getUpdateDate() + "',updateTime:'" + notice.getUpdateTime() + "',updateIp:'" +
                    notice.getUpdateIp() +"'}";
        }
        return result;
    }

    /**
     * ����Ϣ���ϵõ�Json����
     * @param list
     * @return
     */
    public static String getJsonArrayFromMessages(List<Message> list) throws Exception {
        String result = StringUtils.EMPTY;
        for(Message message : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            String fromUserName;
            String headPhoto;
            String url;
            if(UserInterface.USER_TYPE_NORMAL == message.getFromUserType()){//��ͨ�û�
                User user = UserDao.getUserById(message.getFromUserId());
                fromUserName = user.getName();
                headPhoto = user.getHeadPhoto();
                url = "/user.jsp?id=" + message.getFromUserId();
            } else {//�����˺�
                PublicUser user = PublicUserUtil.getInstance().getPublicUserById(message.getFromUserId());
                fromUserName = user.getName();
                headPhoto = user.getHeadPhoto();
                url = user.getUrl();
            }
            result += "{id:" + message.getId() + ",fromUserId:" + message.getFromUserId() + ",fromUserType:" +
                    message.getFromUserType() + ",toUserId:" + message.getToUserId() + ",content:'" +
                    message.getContent() + "',state:" + message.getState() + ",date:'" + message.getDate() +
                    "',time:'" + message.getTime() + "',ip:'" + message.getIp() + "',fromUserName:'" +
                    fromUserName + "',headPhoto:'" + headPhoto + "',url:'" + url + "'}";
        }
        return result;
    }

    /**
     * ��վ���ż��ϵõ�Json����
     * @param list
     * @param box
     * @return
     * @throws Exception
     */
    public static String getJsonArrayFromLetters(List<Letter> list, String box) throws Exception {
        String result = StringUtils.EMPTY;
        for(Letter letter : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            result += getJsonStrFromLetter(letter, box);
        }
        return result;
    }

    /**
     * ��վ���ŵõ�Json��
     * @param letter
     * @param box
     * @return
     * @throws Exception
     */
    public static String getJsonStrFromLetter(Letter letter, String box) throws Exception{
        String result = StringUtils.EMPTY;
        /**
         * �ѷ�����ʾ�ռ���
         * �ռ������ɾ����ʾ������
         */
        int displayUserId = 0;//��ʾ�û�id
        int displayUserType = 0;//��ʾ�û�����
        if(StringUtils.equals(LetterInterface.BOX_SENT, box)){
            int commaIdIndex = letter.getToUserIds().indexOf(SYMBOL_COMMA);
            int commaTypeIndex = letter.getToUserTypes().indexOf(SYMBOL_COMMA);
            if(commaIdIndex == -1){//ֻ��һ���ռ���
                displayUserId = Integer.parseInt(letter.getToUserIds());
                displayUserType = Integer.parseInt(letter.getToUserTypes());
            } else {//�ж���ռ���
                displayUserId = Integer.parseInt(letter.getToUserIds().substring(0, commaIdIndex));
                displayUserType = Integer.parseInt(letter.getToUserTypes().substring(0, commaTypeIndex));
            }
        } else {
            displayUserId = letter.getFromUserId();
            displayUserType = letter.getFromUserType();
        }
        String fromUserName;
        String headPhoto;
        String url;
        if(UserInterface.USER_TYPE_NORMAL == displayUserType){//��ͨ�û�
            User user = UserDao.getUserById(displayUserId);
            fromUserName = user.getName();
            headPhoto = user.getHeadPhoto();
            url = "/user.jsp?id=" + displayUserId;
        } else {//�����˺�
            PublicUser user = PublicUserUtil.getInstance().getPublicUserById(displayUserId);
            fromUserName = user.getName();
            headPhoto = user.getHeadPhoto();
            url = user.getUrl();
        }

        result += "{id:" + letter.getId() + ",userId:" + letter.getUserId() + ",userType:" + letter.getUserType() +
                ",sendOrReceive:" + letter.getSendOrReceive() + ",fromUserId:" + letter.getFromUserId() +
                ",fromUserType:" + letter.getFromUserType() + ",toUserIds:'" + letter.getToUserIds() +
                "',toUserTypes:'" + letter.getToUserTypes() + "',ccUserIds:'" + letter.getCcUserIds() +
                "',ccUserTypes:'" + letter.getCcUserTypes() + "',readState:" + letter.getReadState() +
                ",deleteState:" + letter.getDeleteState() + ",title:'" + letter.getTitle() + "',content:'" +
                letter.getContent() + "',createDate:'" + letter.getCreateDate() + "',createTime:'" +
                letter.getCreateTime() + "',createIp:'" + letter.getCreateIp() + "',operateDate:'" +
                letter.getOperateDate() + "',operateTime:'" + letter.getOperateTime() + "',operateIp:'" +
                letter.getOperateIp() + "',fromUserName:'" + fromUserName + "',headPhoto:'" + headPhoto +
                "',url:'" + url + "'}";
        return result;
    }

    /**
     * �ӹ�����־���ϵõ�Json����
     * @param list
     * @return
     * @throws Exception
     */
    public static String getJsonArrayFromDiaries(List<Diary> list) throws Exception {
        String result = StringUtils.EMPTY;
        for(Diary diary : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            User user = UserDao.getUserById(diary.getUserId());
            result += "{id:" + diary.getId() + ",userId:" + diary.getUserId() + ",date:'" + diary.getDate() +
                    "',createDate:'" + diary.getCreateDate() + "',createTime:'" + diary.getCreateTime() +
                    "',createIp:'" + diary.getCreateIp() + "',updateDate:'" + diary.getUpdateDate() +
                    "',updateTime:'" + diary.getUpdateTime() + "',updateIp:'" + diary.getUpdateIp() +
                    "',userName:'" + user.getName() + "'}";
        }
        return result;
    }
}

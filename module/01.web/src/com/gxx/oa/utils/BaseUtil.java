package com.gxx.oa.utils;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.dao.StructureDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.dao.UserRightDao;
import com.gxx.oa.entities.*;
import com.gxx.oa.exceptions.AjaxException;
import com.gxx.oa.interfaces.*;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础工具类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-30 12:09
 */
public class BaseUtil implements SymbolInterface {
    /**
     * 判登录
     *
     * @param request
     */
    public static boolean isLogin(HttpServletRequest request) throws Exception {
        if(request.getSession().getAttribute(BaseInterface.USER_KEY) == null) {
            return false;
        }
        return true;
    }

    /**
     * 更新dao+session用户访问信息
     *
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
     * 从公司结构集合得到Json数组
     *
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
     * 翻译用户性别
     *
     * @param sex
     * @return
     */
    public static String translateUserSex(int sex) {
        if(UserInterface.SEX_X == sex) {
            return "男";
        }
        if(UserInterface.SEX_O == sex) {
            return "女";
        }
        return StringUtils.EMPTY;
    }

    /**
     * 从用户集合得到Json数组
     *
     * @param list
     * @return
     */
    public static String getJsonArrayFromUsers(List<User> list) throws Exception {
        String result = StringUtils.EMPTY;
        for(User user : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_BIT_AND;
            }
            Structure company = StructureDao.getStructureById(user.getCompany());
            Structure dept = StructureDao.getStructureById(user.getDept());
            Structure position = StructureDao.getStructureById(user.getPosition());
            result += "{id:" + user.getId() + ",name:'" + user.getName() + "',letter:'" +
                    user.getLetter() + "',state:" + user.getState() + ",company:" + user.getCompany() +
                    ",dept:" + user.getDept() + ",position:" + user.getPosition() + ",desk:'" + user.getDesk() +
                    "',sex:" + user.getSex() +",birthday:'" + user.getBirthday() +"',officeTel:'" + user.getOfficeTel() +
                    "',mobileTel:'" + user.getMobileTel() +"',email:'" + user.getEmail() +"',qq:'" + user.getQq() +
                    "',msn:'" + user.getMsn() +"',address:'" + user.getAddress() +"',headPhoto:'" + user.getHeadPhoto() +
                    "',website:'" + user.getWebsite() + "',companyName:'" + (null==company?"无":company.getName()) +
                    "',deptName:'" + (null==dept?"无":dept.getName()) + "',positionName:'" +
                    (null==position?"无":position.getName()) + "'}";
        }
        return result;
    }

    /**
     * 从公告集合得到Json数组
     *
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
     * 从消息集合得到Json数组
     *
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
            if(UserInterface.USER_TYPE_NORMAL == message.getFromUserType()){//普通用户
                User user = UserDao.getUserById(message.getFromUserId());
                fromUserName = user.getName();
                headPhoto = user.getHeadPhoto();
                url = "/user.jsp?id=" + message.getFromUserId();
            } else {//公众账号
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
     * 从站内信集合得到Json数组
     *
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
     * 从站内信得到Json串
     *
     * @param letter
     * @param box
     * @return
     * @throws Exception
     */
    public static String getJsonStrFromLetter(Letter letter, String box) throws Exception{
        String result = StringUtils.EMPTY;
        /**
         * 已发送显示收件人
         * 收件箱和已删除显示发件人
         */
        int displayUserId = 0;//显示用户id
        int displayUserType = 0;//显示用户类型
        if(StringUtils.equals(LetterInterface.BOX_SENT, box)){
            int commaIdIndex = letter.getToUserIds().indexOf(SYMBOL_COMMA);
            int commaTypeIndex = letter.getToUserTypes().indexOf(SYMBOL_COMMA);
            if(commaIdIndex == -1){//只有一个收件人
                displayUserId = Integer.parseInt(letter.getToUserIds());
                displayUserType = Integer.parseInt(letter.getToUserTypes());
            } else {//有多个收件人
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
        if(UserInterface.USER_TYPE_NORMAL == displayUserType){//普通用户
            User user = UserDao.getUserById(displayUserId);
            fromUserName = user.getName();
            headPhoto = user.getHeadPhoto();
            url = "/user.jsp?id=" + displayUserId;
        } else {//公众账号
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
     * 从工作日志集合得到Json数组
     *
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

    /**
     * 从工作日志集合得到Json数组
     *
     * @param list
     * @return
     * @throws Exception
     */
    public static String getJsonArrayFromReminds(List<Remind> list) throws Exception {
        String result = StringUtils.EMPTY;
        for(Remind remind : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            result += "{id:" + remind.getId() + ",userId:" + remind.getUserId() + ",date:'" + remind.getDate() +
                    "',content:'" + remind.getContent() + "',remindType:" + remind.getRemindType() +
                    ",remindDateTime:'" + remind.getRemindDateTime() + "',remindTarget:'" + remind.getRemindTarget() +
                    "',createDate:'" + remind.getCreateDate() + "',createTime:'" + remind.getCreateTime() +
                    "',createIp:'" + remind.getCreateIp() + "',updateDate:'" + remind.getUpdateDate() +
                    "',updateTime:'" + remind.getUpdateTime() + "',updateIp:'" + remind.getUpdateIp() +
                    "',remindTypeDesc:'" + remind.getRemindTypeDesc() + "'}";
        }
        return result;
    }

    /**
     * 从任务集合得到Json数组
     *
     * @param list
     * @return
     * @throws Exception
     */
    public static String getJsonArrayFromTasks(List<Task> list) throws Exception {
        String result = StringUtils.EMPTY;
        for(Task task : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            User fromUser = UserDao.getUserById(task.getFromUserId());
            User toUser = UserDao.getUserById(task.getToUserId());
            result += "{id:" + task.getId() + ",fromUserId:" + task.getFromUserId() + ",toUserId:" +
                    task.getToUserId() + ",title:'" + task.getTitle() + "',content:'" + task.getContent() +
                    "',state:" + task.getState() + ",beginDate:'" + task.getBeginDate() + "',endDate:'" +
                    task.getEndDate() + "',createDate:'" + task.getCreateDate() + "',createTime:'" +
                    task.getCreateTime() + "',createIp:'" + task.getCreateIp() + "',updateDate:'" +
                    task.getUpdateDate() + "',updateTime:'" + task.getUpdateTime() + "',updateIp:'" +
                    task.getUpdateIp() + "',fromUserName:'" + fromUser.getName() + "',toUserName:'" +
                    toUser.getName() + "',stateDesc:'" + task.getStateDesc() + "'}";
        }
        return result;
    }

    /**
     * 从短信集合得到Json数组
     *
     * @param list
     * @return
     */
    public static String getJsonArrayFromSMS(List<SMS> list) throws Exception {
        String result = StringUtils.EMPTY;
        for(SMS sms : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            result += "{id:" + sms.getId() + ",userId:" + sms.getId() + ",phone:'" + sms.getPhone() +
                    "',content:'" + sms.getContent() + "',state:" + sms.getState() + ",date:'" + sms.getDate() +
                    "',time:'" + sms.getTime() + "',ip:'" + sms.getIp() + "',stateDesc:'" + sms.getStateDesc() +
                    "'}";
        }
        return result;
    }

    /**
     * 得到默认密码
     * md5(defaultPwd + md5Key)
     *
     * @return
     */
    public static String generateDefaultPwd(){
        String md5Key = PropertyUtil.getInstance().getProperty(BaseInterface.MD5_KEY);
        String defaultPwd = PropertyUtil.getInstance().getProperty(BaseInterface.DEFAULT_PASSWORD);
        MD5Util md5 = new MD5Util();
        String password = md5.md5(defaultPwd + md5Key);
        return password;
    }

    /**
     * 根据职位id查部门
     * 注意：任何一个职位都会属于某个公司 而不一定会属于哪个部门 比如：董事长
     *
     * @param positionId
     * @return
     */
    public static Structure getDeptByPosition(int positionId) throws Exception{
        // 查看公司，部门，职位信息
        Structure company = null;
        Structure dept = null;
        Structure position = StructureDao.getStructureById(positionId);
        int pid = position.getPid();
        while (pid != 0) {
            Structure temp = StructureDao.getStructureById(pid);
            if(null == temp) {
                break;
            }
            if(temp.getType() == StructureInterface.TYPE_COMPANY) {
                if(null == company) {
                    company = temp;
                }
                break;//有了公司 就不管部门
            }
            if(temp.getType() == StructureInterface.TYPE_DEPT) {
                if(null == dept) {
                    dept = temp;
                }
                pid = dept.getPid();
            }
            if(temp.getType() == StructureInterface.TYPE_POSITION) {
                pid = temp.getPid();//继续往上
            }
        }
        return dept;
    }

    /**
     * 根据职位id查公司
     * 注意：任何一个职位都会属于某个公司 而不一定会属于哪个部门 比如：董事长
     *
     * @param positionId
     * @return
     */
    public static Structure getCompanyByPosition(int positionId) throws Exception{
        // 查看公司，部门，职位信息
        Structure company = null;
        Structure dept = null;
        Structure position = StructureDao.getStructureById(positionId);
        int pid = position.getPid();
        while (pid != 0) {
            Structure temp = StructureDao.getStructureById(pid);
            if(null == temp) {
                break;
            }
            if(temp.getType() == StructureInterface.TYPE_COMPANY) {
                if(null == company) {
                    company = temp;
                }
                break;//有了公司 就不管部门
            }
            if(temp.getType() == StructureInterface.TYPE_DEPT) {
                if(null == dept) {
                    dept = temp;
                }
                pid = dept.getPid();
            }
            if(temp.getType() == StructureInterface.TYPE_POSITION) {
                pid = temp.getPid();//继续往上
            }
        }
        return company;
    }

    /**
     * 根据结构得到下级位置结构用户ID用逗号隔开
     *
     * @param structureId
     * @return
     * @throws Exception
     */
    public static String getLowerLevelPositionUserIdWithComma(int structureId) throws Exception {
        List<User> users = getLowerLevelPositionUsers(structureId);
        String userIdWithComma = StringUtils.EMPTY;
        for(User temp : users){
            if(StringUtils.isNotBlank(userIdWithComma)){
                userIdWithComma += SYMBOL_COMMA;
            }
            userIdWithComma += temp.getId();
        }
        return userIdWithComma;
    }

    /**
     * 根据结构得到下级位置结构用户集合
     *
     * @param structureId
     * @return
     * @throws Exception
     */
    public static List<User> getLowerLevelPositionUsers(int structureId) throws Exception {
        Structure structure = StructureDao.getStructureById(structureId);
        List<Structure> positions = getLowerLevelPositions(structure);
        String positionWithComma = StringUtils.EMPTY;
        for(Structure position : positions){
            if(StringUtils.isNotBlank(positionWithComma)){
                positionWithComma += SYMBOL_COMMA;
            }
            positionWithComma += position.getId();
        }
        List<User> users = UserDao.queryUserByPositionWithComma(positionWithComma);
        return users;
    }

    /**
     * 根据结构ID得到下级位置结构集合
     *
     * @param structureId
     * @return
     */
    public static List<Structure> getLowerLevelPositions(int structureId) throws Exception {
        Structure structure = StructureDao.getStructureById(structureId);
        List<Structure> positions = getLowerLevelPositions(structure);
        return positions;
    }

    /**
     * 根据结构得到下级位置结构集合
     *
     * @param structure
     * @return
     */
    public static List<Structure> getLowerLevelPositions(Structure structure) throws Exception {
        List<Structure> allStructureList = StructureDao.queryAllStructures();
        List<Structure> positions = getLowerLevelPositions(structure, allStructureList);
        if(structure.getType() == StructureInterface.TYPE_POSITION){
            positions.add(structure);
        }
        return positions;
    }

    /**
     * 根据结构，所有公司结构得到下级位置结构
     *
     * @param structure
     * @param allStructureList
     * @return
     */
    public static List<Structure> getLowerLevelPositions(Structure structure, List<Structure> allStructureList){
        List<Structure> positions = new ArrayList<Structure>();
        for(Structure temp : allStructureList){
            if(temp.getPid() == structure.getId()){
                if(temp.getType() == StructureInterface.TYPE_POSITION){
                    positions.add(temp);
                }
                positions.addAll(getLowerLevelPositions(temp, allStructureList));
            }
        }
        return positions;
    }

    /**
     * 根据用户id来展示用户
     *
     * @param request
     * @param userIds
     * @return
     */
    public static String displayUsersByIds(HttpServletRequest request, String userIds) throws Exception {
        //域名链接
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
        String result = StringUtils.EMPTY;
        String[] idArray = userIds.split(SYMBOL_COMMA);
        for(String userId : idArray){
            User user = UserDao.getUserById(Integer.parseInt(userId));
            if(StringUtils.isNotBlank(result)){
                result += SYMBOL_COMMA;
            }
            result += "<a href=\"" + baseUrl + "user.jsp?id=" + userId + "\" target=\"_blank\">" +
                    user.getName() + "</a>";
        }
        return result;
    }

    /**
     * 权限校验
     * @param userId
     * @param rightCode
     * @throws Exception
     */
    public static void checkRightWithException(int userId, String rightCode) throws Exception {
        if(!checkRight(userId,  rightCode)){
            throw new RuntimeException("您无该权限！");
        }
    }

    /**
     * 权限校验
     * @param userId
     * @param rightCode
     * @throws Exception
     */
    public static void checkRightWithAjaxException(int userId, String rightCode) throws Exception {
        if(!checkRight(userId,  rightCode)){
            throw new AjaxException("您无该权限！");
        }
    }

    /**
     * 权限校验
     * @param userId
     * @param rightCode
     * @throws Exception
     */
    public static boolean checkRight(int userId, String rightCode) throws Exception {
        //查询权限
        UserRight userRight = UserRightDao.getUserRightByUserId(userId);
        if(userRight.getUserRight().indexOf(rightCode) == -1){
            return false;
        }
        return true;
    }

    /**
     * 判是否有权限
     * @param rights
     * @param right
     * @return
     */
    public static boolean haveRight(String rights, String right){
        return rights.indexOf(right) > -1;
    }

    /**
     * 从申成云集合得到Json数组
     *
     * @param clouds
     * @return
     * @throws Exception
     */
    public static String getJsonArrayFromClouds(List<Cloud> clouds) throws Exception {
        String result = StringUtils.EMPTY;
        for(Cloud cloud : clouds) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            result += "{id:" + cloud.getId() + ",userId:" + cloud.getUserId() + ",type:" +
                    cloud.getType() + ",pid:" + cloud.getPid() + ",name:'" + cloud.getName() +
                    "',state:" + cloud.getState() + ",dir:'" + cloud.getDir() + "',route:'" +
                    cloud.getRoute() + "',size:" + ((cloud.getType()==CloudInterface.TYPE_FILE)?cloud.getSize():"0") +
                    ",formatSize:'" + ((cloud.getType()==CloudInterface.TYPE_FILE)?FileUtil.formatFileSize(cloud.getSize()):"0") +
                    "',createDate:'" + cloud.getCreateDate() + "',createTime:'" + cloud.getCreateTime() +
                    "',createIp:'" + cloud.getCreateIp() + "'}";
        }
        return result;
    }

    /**
     * 根据申成云类型得到描述
     * @param type
     * @return
     */
    public static String getCloudTypeDesc(int type){
        if(CloudInterface.TYPE_FILE == type){
            return "文件";
        }
        if(CloudInterface.TYPE_DIR == type){
            return "文件夹";
        }
        if(CloudInterface.TYPE_SYSTEM_FILE == type){
            return "系统文件";
        }
        return StringUtils.EMPTY;
    }

    /**
     * 根据用户id和目录 判目录合法性
     * 1.如果dir为左斜杠/则允许
     * 2.其他则dir根据/截取，每段判dir是否存在而且状态正常
     * @param userId
     * @param dir
     * @throws Exception
     */
    public static void checkCloudDir(int userId, String dir) throws Exception {
        if(StringUtils.isBlank(dir)){
            throw new AjaxException("文件目录有误:[" + dir + "]");
        }
        //如果dir为左斜杠/则允许
        if(StringUtils.equals(CloudInterface.FRONT_DIR, dir)){
            return;
        }
        //去掉前后的左斜杠/
        dir = dir.substring(1);
        if(dir.endsWith(SYMBOL_SLASH)){
            dir = dir.substring(0, dir.length() - 1);
        }
        //逐个文件夹校验状态
        String[] dirParts = dir.split(SYMBOL_SLASH);
        for(int i=0;i<dirParts.length;i++){
            String tempDir = SYMBOL_SLASH;
            for(int j=0;j<i+1;j++){
                tempDir += dirParts[j];
                tempDir += SYMBOL_SLASH;
            }
            Cloud tempCloud = CloudDao.getCloudByUserIdAndRoute(userId, tempDir);
            if(null == tempCloud || tempCloud.getType() != CloudInterface.TYPE_DIR){
                throw new AjaxException("文件目录有误:[" + tempDir + "]");
            }
        }
    }

    /**
     * 逐层创建目录
     * @param userId
     * @param dir
     */
    public static void createCloudDir(int userId, String dir, String date, String time, String ip) throws Exception {
        if(StringUtils.isBlank(dir)){
            throw new AjaxException("文件目录有误:[" + dir + "]");
        }
        //如果dir为左斜杠/则允许
        if(StringUtils.equals(CloudInterface.FRONT_DIR, dir)){
            return;
        }
        //去掉前后的左斜杠/
        dir = dir.substring(1);
        if(dir.endsWith(SYMBOL_SLASH)){
            dir = dir.substring(0, dir.length() - 1);
        }
        int pid = 0;
        //是否 找到第一个被删除的目录
        boolean findFirstDeleteDir = false;
        //逐个文件夹校验状态
        String[] dirParts = dir.split(SYMBOL_SLASH);
        for(int i=0;i<dirParts.length;i++){
            String newDir = StringUtils.EMPTY;
            String tempDir = SYMBOL_SLASH;
            for(int j=0;j<i+1;j++){
                newDir = dirParts[j];
                tempDir += dirParts[j];
                tempDir += SYMBOL_SLASH;
            }
            /**
             * 如果没有找到第一个被删除的目录，一直找
             * 一旦找到，下面每个目录都需要手动来创建
             */
            if(!findFirstDeleteDir){
                Cloud tempCloud = CloudDao.getCloudByUserIdAndRoute(userId, tempDir);
                if(null != tempCloud){
                    //更新pid
                    pid = tempCloud.getId();
                    continue;
                } else {
                    findFirstDeleteDir = true;
                }
            }
            //每个目录都需要手动来创建
            Cloud cloud = new Cloud(userId, CloudInterface.TYPE_DIR, pid, newDir, CloudInterface.STATE_NORMAL,
                    tempDir.substring(0, tempDir.length()-newDir.length()-1), tempDir, 0, date, time, ip);
            CloudDao.insertCloud(cloud);
            //更新pid
            cloud = CloudDao.getCloudByUserIdAndRoute(userId, tempDir);
            pid = cloud.getId();
        }
    }
}

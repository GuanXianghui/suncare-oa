package com.gxx.oa;

import com.gxx.oa.dao.StructureDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.Structure;
import com.gxx.oa.entities.User;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 修改用户action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-14 13:09
 */
public class UpdateUserAction extends BaseAction {
    //操作类型
    String updateType;
    //用户id
    String userId;
    //职位id
    String positionId;
    //状态
    String state;

    //修改用户类型 重置密码：initPassword 修改职位：updatePosition 修改状态：updateState
    private static final String UPDATE_TYPE_INIT_PASSWORD = "initPassword";
    private static final String UPDATE_TYPE_UPDATE_POSITION = "updatePosition";
    private static final String UPDATE_TYPE_UPDATE_STATE = "updateState";

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("updateType:" + updateType + ",userId=" + userId + ",positionId=" + positionId);
        String resp;

        if(StringUtils.equals(UPDATE_TYPE_INIT_PASSWORD, updateType)){//重置密码
            //查询用户，重置密码
            User user = UserDao.getUserById(Integer.parseInt(userId));
            user.setPassword(BaseUtil.generateDefaultPwd());
            UserDao.updateUserPassword(user);

            //返回结果
            resp = "{isSuccess:true,message:'重置密码成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(StringUtils.equals(UPDATE_TYPE_UPDATE_POSITION, updateType)){//修改职位
            // 查看公司，部门，职位信息
            Structure position = StructureDao.getStructureById(Integer.parseInt(positionId));
            Structure dept = BaseUtil.getDeptByPosition(Integer.parseInt(positionId));
            Structure company = BaseUtil.getCompanyByPosition(Integer.parseInt(positionId));

            //查询用户，修改职位
            User user = UserDao.getUserById(Integer.parseInt(userId));
            user.setPosition(position==null?0:position.getId());
            user.setDept(dept==null?0:dept.getId());
            user.setCompany(company==null?0:company.getId());
            UserDao.updatePosition(user);

            //返回结果
            resp = "{isSuccess:true,message:'修改职位成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(StringUtils.equals(UPDATE_TYPE_UPDATE_STATE, updateType)){//修改状态
            //查询用户，修改职位
            User user = UserDao.getUserById(Integer.parseInt(userId));
            user.setState(Integer.parseInt(state));
            UserDao.updateUserState(user);

            //返回结果
            resp = "{isSuccess:true,message:'修改职位成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else {
            //返回结果
            resp = "{isSuccess:false,message:'操作类型有误！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        }

        write(resp);
        return null;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

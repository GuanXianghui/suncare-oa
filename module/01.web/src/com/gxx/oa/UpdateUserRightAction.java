package com.gxx.oa;

import com.gxx.oa.dao.UserRightDao;
import com.gxx.oa.entities.UserRight;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

/**
 * 修改用户权限action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class UpdateUserRightAction extends BaseAction {
    //用户Id
    String userId;
    //用户权限
    String userRight;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        //权限校验
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0015_USER_RIGHT);
        logger.info("userId:" + userId + ",userRight:" + userRight);

        //查询权限
        UserRight userRightEntity = UserRightDao.getUserRightByUserId(Integer.parseInt(userId));
        userRightEntity.setUserRight(userRight);
        UserRightDao.updateUserRight(userRightEntity);

        //返回结果
        String resp = "{isSuccess:true,message:'修改用户权限成功！',hasNewToken:true,token:'" +
                TokenUtil.createToken(request) + "'}";

        write(resp);
        return null;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserRight() {
        return userRight;
    }

    public void setUserRight(String userRight) {
        this.userRight = userRight;
    }
}

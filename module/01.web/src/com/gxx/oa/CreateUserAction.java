package com.gxx.oa;

import com.gxx.oa.dao.StructureDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.dao.UserRightDao;
import com.gxx.oa.entities.Structure;
import com.gxx.oa.entities.User;
import com.gxx.oa.entities.UserRight;
import com.gxx.oa.interfaces.BaseInterface;
import com.gxx.oa.interfaces.ParamInterface;
import com.gxx.oa.interfaces.StructureInterface;
import com.gxx.oa.interfaces.UserInterface;
import com.gxx.oa.utils.*;
import org.apache.commons.lang.StringUtils;

/**
 * 创建用户action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-14 10:51
 */
public class CreateUserAction extends BaseAction {
    String name;
    String letter;
    String positionId;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        //权限校验
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0003_USER_OPERATE);
        logger.info("name:" + name + ",letter=" + letter + ",positionId=" + positionId);
        String resp;

        //根据名字查是否已有用户使用
        User user = UserDao.getUserByName(name);
        if(null != user){
            //返回结果
            resp = "{isSuccess:false,message:'该用户名已被用，请更换用户名！',hasNewToken:true," +
                    "token:'" + TokenUtil.createToken(request) + "'}";
        } else {
            // 查看公司，部门，职位信息
            Structure position = StructureDao.getStructureById(Integer.parseInt(positionId));
            Structure dept = BaseUtil.getDeptByPosition(Integer.parseInt(positionId));
            Structure company = BaseUtil.getCompanyByPosition(Integer.parseInt(positionId));

            int companyInt = company==null?0:company.getId();
            int deptInt = dept==null?0:dept.getId();
            int positionInt = position==null?0:position.getId();

            String defaultPhoto = PropertyUtil.getInstance().getProperty(BaseInterface.DEFAULT_HEAD_PHOTO);
            user = new User(name, BaseUtil.generateDefaultPwd(), letter, UserInterface.STATE_NORMAL, companyInt, deptInt,
                    positionInt, StringUtils.EMPTY, UserInterface.SEX_X, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                    StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, defaultPhoto, StringUtils.EMPTY,
                    date, time, getIp(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
            UserDao.insertUser(user);

            //创建权限
            user = UserDao.getUserByName(name);
            UserRight userRight = new UserRight(user.getId(), ParamUtil.getInstance().getValueByName(ParamInterface.DEFAULT_RIGHT));
            UserRightDao.insertUserRight(userRight);

            //返回结果
            resp = "{isSuccess:true,message:'创建用户成功！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        }
        write(resp);
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }
}

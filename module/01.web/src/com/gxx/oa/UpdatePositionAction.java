package com.gxx.oa;

import com.gxx.oa.dao.StructureDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.Structure;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.StructureInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

/**
 * 修改职位action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-31 23:07
 */
public class UpdatePositionAction extends BaseAction implements StructureInterface {
    /**
     * 公司架构id
     */
    int id;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        //权限校验
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0001_USER_MANAGE);
        logger.info("id:" + id);

        // 查看公司，部门，职位信息
        Structure position = StructureDao.getStructureById(id);
        Structure dept = BaseUtil.getDeptByPosition(id);
        Structure company = BaseUtil.getCompanyByPosition(id);

        //更新用户
        User user = getUser();
        user.setCompany(company==null?0:company.getId());
        user.setDept(dept==null?0:dept.getId());
        user.setPosition(position==null?0:position.getId());
        UserDao.updateUserInfo(user);

        String companyName = company==null?"无":company.getName();
        String deptName = dept==null?"无":dept.getName();
        String positionName = position==null?"无":position.getName();

        //返回结果
        String resp = "{isSuccess:true,message:'更新职位成功！',companyName:'" + companyName +
                "',deptName:'" + deptName + "',positionName:'" + positionName + "',hasNewToken:true,token:'" +
                TokenUtil.createToken(request) + "'}";
        write(resp);
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

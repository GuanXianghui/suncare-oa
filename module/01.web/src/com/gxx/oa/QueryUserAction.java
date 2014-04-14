package com.gxx.oa;

import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.User;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.List;

/**
 * 查询用户action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-14 13:09
 */
public class QueryUserAction extends BaseAction {
    String name;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("name:" + name);

        //根据姓名或者拼音查用户
        List<User> list = UserDao.queryUserByNameOrLetter(name);
        String json = BaseUtil.getJsonArrayFromUsers(list).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");
        //返回结果
        String resp = "{isSuccess:true,message:'查询用户成功！',jsonStr:'" + json + "',hasNewToken:true," +
                "token:'" + TokenUtil.createToken(request) + "'}";
        write(resp);
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

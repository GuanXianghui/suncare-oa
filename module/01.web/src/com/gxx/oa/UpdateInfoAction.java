package com.gxx.oa;

import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.User;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.DateUtil;
import com.gxx.oa.utils.TokenUtil;

/**
 * 修改信息action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 14:25
 */
public class UpdateInfoAction extends BaseAction {
    int sex;
    String birthday;
    String officeTel;
    String mobileTel;
    String desk;
    String email;
    String qq;
    String msn;
    String address;
    String website;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("sex:" + sex + ",birthday=" + birthday + ",officeTel=" + officeTel + ",mobileTel=" +
                mobileTel + ",desk=" + desk + ",email=" + email + ",qq=" + qq + ",msn=" + msn +
                ",address=" + address + ",website=" + website);
        User user = getUser();
        user.setSex(sex);
        user.setBirthday(birthday);
        user.setOfficeTel(officeTel);
        user.setMobileTel(mobileTel);
        user.setDesk(desk);
        user.setEmail(email);
        user.setQq(qq);
        user.setMsn(msn);
        user.setAddress(address);
        user.setWebsite(website);
        UserDao.updateUserInfo(user);

        //返回结果
        String resp = "{isSuccess:true,message:'修改信息成功！',sex:'" + BaseUtil.translateUserSex(user.getSex()) +
                "',birthday:'" + DateUtil.getCNDate(DateUtil.getDate(user.getBirthday())) +
                "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
        write(resp);
        return null;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public String getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}

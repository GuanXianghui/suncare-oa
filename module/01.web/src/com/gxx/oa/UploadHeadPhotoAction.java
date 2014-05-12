package com.gxx.oa;

import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.User;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.FileUtil;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.util.Date;

/**
 * 上传头像action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class UploadHeadPhotoAction extends BaseAction {
    /**
     * 头像文件
     */
    private File headPhoto;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        //权限校验
        BaseUtil.checkRightWithException(getUser().getId(), RIGHT_0001_USER_MANAGE);
        logger.info("headPhoto:" + headPhoto);
        //判头像为空
        if(null == headPhoto)
        {
            message = "服务器未收到图片!";
            return ERROR;
        }
        //新的图片名称
        String headPhotoName = getUser().getId() + "_" + new Date().getTime() + ".jpg";
        //服务器上的路径
        String headPhotoPath = ServletActionContext.getServletContext().getRealPath("images/head") + "/" + headPhotoName;
        //页面引用位置 相对路径
        String headPhotoPagePath = "images/head" + "/" + headPhotoName;
        //服务器上的路径对应的文件
        File imageFile = new File(headPhotoPath);
        //拷贝文件
        FileUtil.copy(headPhoto, imageFile);

        //更新用户头像
        User user = getUser();
        user.setHeadPhoto(headPhotoPagePath);
        UserDao.updateUserHeadPhoto(user);

        message = "上传头像成功！";
        return SUCCESS;
    }

    public File getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(File headPhoto) {
        this.headPhoto = headPhoto;
    }
}

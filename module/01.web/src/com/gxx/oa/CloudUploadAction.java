package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.util.Date;

/**
 * 申成云上传文件action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudUploadAction extends BaseAction implements CloudInterface {
    /**
     * 目录
     */
    private String dir;
    /**
     * 文件
     */
    private File file;
    /**
     * 文件名
     */
    private String fileFileName;
    /**
     * 文件类型
     */
    private String fileContentType;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("dir:" + dir + ",file:" + file + ",fileFileName:" + fileFileName +
                ",fileContentType:" + fileContentType + ",size:" +
                FileUtil.formatFileSize(file.length()));
        //判头像为空
        if(null == file)
        {
            message = "服务器未收到文件!";
            return ERROR;
        }

        /**
         * 根据用户id和目录 判目录合法性
         * 1.如果dir为左斜杠/则允许
         * 2.其他则dir根据/截取，每段判dir是否存在而且状态正常
         */
        BaseUtil.checkCloudDir(getUser().getId(), dir);

        //根据用户ID和目录和文件名查云 状态为正常
        Cloud cloud = CloudDao.getCloudByUserIdAndDirAndNameAndType(getUser().getId(), dir, fileFileName, TYPE_FILE);
        if(cloud != null){
            message = "该目录[" + dir + "]下已存在相同名字[" + fileFileName + "]的文件!";
            return ERROR;
        }

        //新的文件类型
        String fileType = StringUtils.EMPTY;
        int dotIndex = fileFileName.indexOf(SymbolInterface.SYMBOL_DOT);
        if(dotIndex > -1){
            fileType = fileFileName.substring(dotIndex);
        }
        //新的文件名称
        String fileName = getUser().getId() + "_" + new Date().getTime() + fileType;
        //服务器上的路径
        String filePath = ServletActionContext.getServletContext().getRealPath("files/cloud") + "/" + fileName;
        //页面引用位置 相对路径
        String filePagePath = "files/cloud/" + fileName;
        //服务器上的路径对应的文件
        File newFile = new File(filePath);
        //拷贝文件
        FileUtil.copy(file, newFile);

        //父目录id
        int pid = FRONT_DIR_PID;
        if(!StringUtils.equals(FRONT_DIR, dir)){
            Cloud dirCloud = CloudDao.getCloudByUserIdAndRoute(getUser().getId(), dir);
            pid = dirCloud.getId();
        }

        //新增云
        cloud = new Cloud(getUser().getId(), TYPE_FILE, pid, fileFileName, STATE_NORMAL, dir, filePagePath,
                file.length(), date, time, getIp());
        CloudDao.insertCloud(cloud);

        message = "上传申成云文件成功！";
        return SUCCESS;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }
}

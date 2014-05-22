package com.gxx.oa;

import com.gxx.oa.dao.CloudDocDao;
import com.gxx.oa.entities.CloudDoc;
import com.gxx.oa.interfaces.CloudDocInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.util.Date;

/**
 * 申成文库上传文档action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudUploadDocAction extends BaseAction implements CloudDocInterface {
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 分类
     */
    private String type;
    /**
     * 标签
     */
    private String tags;
    /**
     * 文档
     */
    private File doc;
    /**
     * 文档名
     */
    private String docFileName;
    /**
     * 文档类型
     */
    private String docContentType;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("title:" + title + ",description:" + description + ",type:" + type +
                ",tags:" + tags + ",doc:" + doc + ",docFileName:" + docFileName + ",docContentType:" +
                docContentType + ",size:" + (doc==null?"":FileUtil.formatFileSize(doc.length())));
        //判必输字段为空
        if(StringUtils.isBlank(title) || StringUtils.isBlank(type))
        {
            message = "标题和分类不能为空!";
            return ERROR;
        }

        //判文档为空
        if(doc == null){
            message = "服务器未收到文件!";
            return ERROR;
        }

        //文件为空
        if(doc.length() == 0){
            message = "文件不能为空!";
            return ERROR;
        }

        //新的文件类型
        String fileType = StringUtils.EMPTY;
        int dotIndex = docFileName.lastIndexOf(SymbolInterface.SYMBOL_DOT);
        if(dotIndex > -1){
            fileType = docFileName.substring(dotIndex);
        }

        /**
         * 是否是支持的文件类型
         */
        if(!BaseUtil.isSupportCloudDocType(
                fileType.startsWith(SymbolInterface.SYMBOL_DOT)?fileType.substring(1):fileType)){
            message = "不支持的文件类型" + (StringUtils.isBlank(fileType)?"":"[" + fileType + "]") + "!";
            return ERROR;
        }

        //新的文件名称
        String fileName = getUser().getId() + "_" + new Date().getTime() + fileType;
        //服务器上的路径
        String filePath = ServletActionContext.getServletContext().getRealPath("files/cloud_doc") + "/" + fileName;
        //页面引用位置 相对路径
        String filePagePath = "files/cloud_doc/" + fileName;
        //服务器上的路径对应的文件
        File newFile = new File(filePath);
        //拷贝文件
        FileUtil.copy(doc, newFile);

        //根据用户ID和目录和文件名查云 状态为正常
        CloudDoc cloudDoc = new CloudDoc(getUser().getId(), title, STATE_NORMAL, description, type, tags, filePagePath,
                doc.length(), 0, 0, date, time, getIp(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        CloudDocDao.insertCloudDoc(cloudDoc);

        message = "上传文档到申成文库成功！";
        return SUCCESS;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public File getDoc() {
        return doc;
    }

    public void setDoc(File doc) {
        this.doc = doc;
    }

    public String getDocFileName() {
        return docFileName;
    }

    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName;
    }

    public String getDocContentType() {
        return docContentType;
    }

    public void setDocContentType(String docContentType) {
        this.docContentType = docContentType;
    }
}

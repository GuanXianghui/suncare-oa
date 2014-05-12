package com.gxx.oa;

import com.gxx.oa.dao.NoticeDao;
import com.gxx.oa.entities.Notice;
import com.gxx.oa.interfaces.BaseInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.PropertyUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.List;

/**
 * 加载下一页公告action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-3 14:25
 */
public class ShowNextPageNoticesAction extends BaseAction {
    /**
     * 目前公告数
     */
    int countNow;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        //权限校验
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0007_CONFIG_NOTICE);
        logger.info("countNow:" + countNow);
        //算当前页书
        int nowPage = countNow / Integer.parseInt(PropertyUtil.getInstance().
                getProperty(BaseInterface.NOTICE_PAGE_SIZE));
        //下一页
        int nextPage = nowPage + 1;
        //下一页公告
        List<Notice> nextPageNotices = NoticeDao.queryNoticesByPage(nextPage,
                Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.NOTICE_PAGE_SIZE)));
        /**
         * 下一页公告Json串
         * replaceAll("\\\'", "\\\\\\\'")，转换单引号
         * replaceAll("\\\"", "\\\\\\\"")，转换双引号
         * replaceAll("\r\n", uuid)，转换换行符成uuid
         */
        String nextPageJson = BaseUtil.getJsonArrayFromNotices(nextPageNotices).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID));

        //返回结果
        String resp = "{isSuccess:true,message:'加载下一页公告成功！',nextPageJson:'" + nextPageJson +
                "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
        write(resp);
        return null;
    }

    public int getCountNow() {
        return countNow;
    }

    public void setCountNow(int countNow) {
        this.countNow = countNow;
    }
}

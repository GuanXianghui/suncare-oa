package com.gxx.oa;

import com.gxx.oa.interfaces.ParamInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.ParamUtil;
import com.gxx.oa.utils.TokenUtil;

/**
 * 修改默认权限action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class UpdateDefaultRightAction extends BaseAction {
    //权限代码
    String rightCodes;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        //权限校验
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0014_DEFAULT_RIGHT);
        logger.info("rightCodes:" + rightCodes);

        //查询描述
        String info = ParamUtil.getInstance().getInfoByName(ParamInterface.DEFAULT_RIGHT);

        //根据name修改value和info
        ParamUtil.getInstance().updateParam(ParamInterface.DEFAULT_RIGHT, rightCodes, info);

        //返回结果
        String resp = "{isSuccess:true,message:'修改默认权限成功！',hasNewToken:true,token:'" +
                TokenUtil.createToken(request) + "'}";

        write(resp);
        return null;
    }

    public String getRightCodes() {
        return rightCodes;
    }

    public void setRightCodes(String rightCodes) {
        this.rightCodes = rightCodes;
    }
}

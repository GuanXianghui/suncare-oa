package com.gxx.oa.utils;

import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.Structure;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.BaseInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 基础工具类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-30 12:09
 */
public class BaseUtil implements SymbolInterface {
    /**
     * 判登录
     * @param request
     */
    public static void checkLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //域名链接
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
        if(request.getSession().getAttribute(BaseInterface.USER_KEY) == null) {
            response.sendRedirect(baseUrl + "index.jsp");
        }
    }

    /**
     * 更新dao+session用户访问信息
     * @param request
     */
    public static void refreshUserVisit(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(BaseInterface.USER_KEY);
        user.setVisitDate(DateUtil.getNowDate());
        user.setVisitTime(DateUtil.getNowTime());
        user.setVisitIp(IPAddressUtil.getIPAddress(request));
        try {
            UserDao.updateUserVisitInfo(user);
            request.getSession().setAttribute(BaseInterface.USER_KEY, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从公司结构集合得到Json数组
     * @param list
     * @return
     */
    public static String getJsonArrayFromStructures(List<Structure> list) {
        String result = StringUtils.EMPTY;
        for(Structure structure : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_BIT_AND;
            }
            result += "{id:" + structure.getId() + ",type:" + structure.getType() + ",name:'" +
                    structure.getName() + "',pid:" + structure.getPid() + ",indexId:" + structure.getIndexId() + "}";
        }
        return result;
    }
}

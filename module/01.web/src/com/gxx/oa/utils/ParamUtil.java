package com.gxx.oa.utils;

import com.gxx.oa.dao.ParamDao;
import com.gxx.oa.entities.Param;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 启动参数工具类
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-31 08:39
 */
public class ParamUtil {
    private static ParamUtil instance;

    public static ParamUtil getInstance() throws Exception {
        if (null == instance) {
            instance = new ParamUtil();
        }
        return instance;
    }

    static List<Param> params;

    private ParamUtil() throws Exception {
        refresh();
    }

    /**
     * 配置缓存刷新
     */
    public static void refresh() throws Exception{
        // 1 查询所有启动参数
        params = ParamDao.queryAllParams();
    }

    /**
     * 获取值
     *
     * @param name
     * @return
     */
    public String getValueByName(String name) {
        for(Param param : params) {
            if(param.getName().equals(name)) {
                return param.getValue();
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取描述
     *
     * @param name
     * @return
     */
    public String getInfoByName(String name) {
        for(Param param : params) {
            if(param.getName().equals(name)) {
                return param.getInfo();
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 根据name修改value和info
     * @param name
     * @param value
     * @param info
     */
    public void updateParam(String name, String value, String info) throws Exception{
        //找启动参数
        Param param = null;
        for(Param temp : params) {
            if(temp.getName().equals(name)) {
                param = temp;
                break;
            }
        }

        //找不到该启动参数
        if(null == param){
            throw new RuntimeException("找不到该启动参数:[" + name + "]");
        }

        //更新启动参数
        param.setValue(value);
        param.setInfo(info);
        ParamDao.updateParam(param);

        //配置缓存刷新
        refresh();
    }
}

package com.gxx.oa.utils;

import com.gxx.oa.dao.ParamDao;
import com.gxx.oa.entities.Param;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * ��������������
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
     * ���û���ˢ��
     */
    public static void refresh() throws Exception{
        // 1 ��ѯ������������
        params = ParamDao.queryAllParams();
    }

    /**
     * ��ȡֵ
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
     * ��ȡ����
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
}

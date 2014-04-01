package com.gxx.oa;

import com.gxx.oa.dao.StructureDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.Structure;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.StructureInterface;
import com.gxx.oa.utils.TokenUtil;

/**
 * �޸�ְλaction
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-31 23:07
 */
public class UpdatePositionAction extends BaseAction implements StructureInterface {
    /**
     * ��˾�ܹ�id
     */
    int id;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("id:" + id);

        Structure company = null;
        Structure dept = null;
        Structure position = StructureDao.getStructureById(id);
        int pid = position.getPid();
        while (pid != 0) {
            Structure temp = StructureDao.getStructureById(pid);
            if(null == temp) {
                break;
            }
            if(temp.getType() == TYPE_COMPANY) {
                if(null == company) {
                    company = temp;
                }
                break;//���˹�˾ �Ͳ��ܲ���
            }
            if(temp.getType() == TYPE_DEPT) {
                if(null == dept) {
                    dept = temp;
                }
                pid = dept.getPid();
            }
            if(temp.getType() == TYPE_POSITION) {
                pid = temp.getPid();//��������
            }
        }

        //�����û�
        User user = getUser();
        user.setCompany(company==null?0:company.getId());
        user.setDept(dept==null?0:dept.getId());
        user.setPosition(position==null?0:position.getId());
        UserDao.updateUserInfo(user);

        String companyName = company==null?"��":company.getName();
        String deptName = dept==null?"��":dept.getName();
        String positionName = position==null?"��":position.getName();

        //���ؽ��
        String resp = "{isSuccess:true,message:'����ְλ�ɹ���',companyName:'" + companyName +
                "',deptName:'" + deptName + "',positionName:'" + positionName + "',hasNewToken:true,token:'" +
                TokenUtil.createToken(request) + "'}";
        write(resp);
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

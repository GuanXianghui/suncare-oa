package com.gxx.oa.interfaces;

/**
 * �û������ӿ�
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 13:16
 */
public interface UserInterface extends BaseInterface {
    /**
     * Ĭ������
     */
    public static final String DEFAULT_PASSWORD = "default_password";
    /**
     * Ĭ��ͷ��
     */
    public static final String DEFAULT_HEAD_PHOTO = "default_head_photo";
    /**
     * ״̬: 1������2����
     */
    public static final int STATE_NORMAL = 1;
    public static final int STATE_LOCKED = 2;
    /**
     * �Ա�: 1�У�0Ů
     */
    public static final int SEX_X = 1;
    public static final int SEX_O = 0;
    /**
     * �û����� 1:��ͨ�û� 2:�����˺�
     */
    public static final int USER_TYPE_NORMAL = 1;
    public static final int USER_TYPE_PUBLIC = 2;
}

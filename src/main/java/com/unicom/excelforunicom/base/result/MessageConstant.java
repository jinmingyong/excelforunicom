package com.unicom.excelforunicom.base.result;

/**
 *
 * @author GaoLiWie
 * @date 2018/7/26
 */
public enum MessageConstant {

    /**
     * 操作成功
     */
    MESSAGE_ALERT_SUCCESS("操作成功"),
    /**
     * 操作失败
     */
    MESSAGE_ALERT_ERROR("操作失败");

    public String msg;

    MessageConstant(String msg) {
        this.msg = msg;
    }
}

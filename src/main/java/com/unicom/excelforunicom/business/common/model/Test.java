package com.unicom.excelforunicom.business.common.model;

import javax.persistence.*;

public class Test {
    /**
     * 主键
     */
    @Id
    private String 序号;

    /**
     * 姓名
     */
    private String 姓名;

    /**
     * 年龄
     */
    private String 年龄;

    /**
     * 内容
     */
    private String 内容;

    /**
     * 获取主键
     *
     * @return 序号 - 主键
     */
    public String get序号() {
        return 序号;
    }

    /**
     * 设置主键
     *
     * @param 序号 主键
     */
    public void set序号(String 序号) {
        this.序号 = 序号;
    }

    /**
     * 获取姓名
     *
     * @return 姓名 - 姓名
     */
    public String get姓名() {
        return 姓名;
    }

    /**
     * 设置姓名
     *
     * @param 姓名 姓名
     */
    public void set姓名(String 姓名) {
        this.姓名 = 姓名;
    }

    /**
     * 获取年龄
     *
     * @return 年龄 - 年龄
     */
    public String get年龄() {
        return 年龄;
    }

    /**
     * 设置年龄
     *
     * @param 年龄 年龄
     */
    public void set年龄(String 年龄) {
        this.年龄 = 年龄;
    }

    /**
     * 获取内容
     *
     * @return 内容 - 内容
     */
    public String get内容() {
        return 内容;
    }

    /**
     * 设置内容
     *
     * @param 内容 内容
     */
    public void set内容(String 内容) {
        this.内容 = 内容;
    }
}
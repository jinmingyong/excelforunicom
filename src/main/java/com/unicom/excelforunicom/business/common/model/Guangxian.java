package com.unicom.excelforunicom.business.common.model;

import javax.persistence.*;

public class Guangxian {
    @Id
    private Integer id;

    @Column(name = "guangLan")
    private String guanglan;

    @Column(name = "xianXinCount")
    private String xianxincount;

    @Column(name = "xianXinFirst")
    private String xianxinfirst;

    @Column(name = "xianXinLast")
    private String xianxinlast;

    @Column(name = "ODF_Name")
    private String odfName;

    private String pan;

    @Column(name = "panFirst")
    private String panfirst;

    @Column(name = "panLast")
    private String panlast;

    @Column(name = "ruJiaoXinxi")
    private String rujiaoxinxi;

    @Column(name = "ru_Mian")
    private String ruMian;

    @Column(name = "ru_Pan")
    private String ruPan;

    @Column(name = "ru_PanFirst")
    private String ruPanfirst;

    @Column(name = "ru_PanLast")
    private String ruPanlast;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return guangLan
     */
    public String getGuanglan() {
        return guanglan;
    }

    /**
     * @param guanglan
     */
    public void setGuanglan(String guanglan) {
        this.guanglan = guanglan;
    }

    /**
     * @return xianXinCount
     */
    public String getXianxincount() {
        return xianxincount;
    }

    /**
     * @param xianxincount
     */
    public void setXianxincount(String xianxincount) {
        this.xianxincount = xianxincount;
    }

    /**
     * @return xianXinFirst
     */
    public String getXianxinfirst() {
        return xianxinfirst;
    }

    /**
     * @param xianxinfirst
     */
    public void setXianxinfirst(String xianxinfirst) {
        this.xianxinfirst = xianxinfirst;
    }

    /**
     * @return xianXinLast
     */
    public String getXianxinlast() {
        return xianxinlast;
    }

    /**
     * @param xianxinlast
     */
    public void setXianxinlast(String xianxinlast) {
        this.xianxinlast = xianxinlast;
    }

    /**
     * @return ODF_Name
     */
    public String getOdfName() {
        return odfName;
    }

    /**
     * @param odfName
     */
    public void setOdfName(String odfName) {
        this.odfName = odfName;
    }

    /**
     * @return pan
     */
    public String getPan() {
        return pan;
    }

    /**
     * @param pan
     */
    public void setPan(String pan) {
        this.pan = pan;
    }

    /**
     * @return panFirst
     */
    public String getPanfirst() {
        return panfirst;
    }

    /**
     * @param panfirst
     */
    public void setPanfirst(String panfirst) {
        this.panfirst = panfirst;
    }

    /**
     * @return panLast
     */
    public String getPanlast() {
        return panlast;
    }

    /**
     * @param panlast
     */
    public void setPanlast(String panlast) {
        this.panlast = panlast;
    }

    /**
     * @return ruJiaoXinxi
     */
    public String getRujiaoxinxi() {
        return rujiaoxinxi;
    }

    /**
     * @param rujiaoxinxi
     */
    public void setRujiaoxinxi(String rujiaoxinxi) {
        this.rujiaoxinxi = rujiaoxinxi;
    }

    /**
     * @return ru_Mian
     */
    public String getRuMian() {
        return ruMian;
    }

    /**
     * @param ruMian
     */
    public void setRuMian(String ruMian) {
        this.ruMian = ruMian;
    }

    /**
     * @return ru_Pan
     */
    public String getRuPan() {
        return ruPan;
    }

    /**
     * @param ruPan
     */
    public void setRuPan(String ruPan) {
        this.ruPan = ruPan;
    }

    /**
     * @return ru_PanFirst
     */
    public String getRuPanfirst() {
        return ruPanfirst;
    }

    /**
     * @param ruPanfirst
     */
    public void setRuPanfirst(String ruPanfirst) {
        this.ruPanfirst = ruPanfirst;
    }

    /**
     * @return ru_PanLast
     */
    public String getRuPanlast() {
        return ruPanlast;
    }

    /**
     * @param ruPanlast
     */
    public void setRuPanlast(String ruPanlast) {
        this.ruPanlast = ruPanlast;
    }
}
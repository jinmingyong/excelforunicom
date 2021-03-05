package com.unicom.excelforunicom.business.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Alias("Zyfx")
public class Zyfx {


	@Id
   	@Column(name = "duanZiMingCheng" )
	private String duanZiMingCheng;

   	@Column(name = "guangLan" )
	private String guangLan;

   	@Column(name = "xianXin" )
	private String xianXin;

   	@Column(name = "zhuangTai" )
	private String zhuangTai;

   	@Column(name = "dzmc" )
	private String dzmc;

   	@Column(name = "zhuanYe" )
	private String zhuanYe;

   	@Column(name = "yeWuLeiBie" )
	private String yeWuLeiBie;

   	@Column(name = "yeWuHao" )
	private String yeWuHao;

   	@Column(name = "yeWuMing" )
	private String yeWuMing;

   	@Column(name = "biaoQian" )
	private String biaoQian;

   	@Column(name = "duiYingWeizhi" )
	private String duiYingWeizhi;

   	@Column(name = "tiaoJieWeizhi" )
	private String tiaoJieWeizhi;

   	@Column(name = "guangJiaoWeizhi" )
	private String guangJiaoWeizhi;

   	@Column(name = "guangJiaoTiaojie" )
	private String guangJiaoTiaojie;

   	@Column(name = "peiXianGuangLanMingCheng" )
	private String peiXianGuangLanMingCheng;

   	@Column(name = "bianGengRiQi" )
	private Date bianGengRiQi;

   	@Column(name = "sheBeiMingCheng" )
	private String sheBeiMingCheng;

   	@Column(name = "moKuaiMing" )
	private String moKuaiMing;

   	@Column(name = "pan" )
	private String pan;

   	@Column(name = "juZhan" )
	private String juZhan;

   	@Column(name = "jiFang" )
	private String jiFang;

   	@Column(name = "sheiBeiId" )
	private String sheiBeiId;

   	@Column(name = "duanZiId" )
	private String duanZiId;


}

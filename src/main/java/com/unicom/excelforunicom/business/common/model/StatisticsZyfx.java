package com.unicom.excelforunicom.business.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table ( name ="statistics" )
public class StatisticsZyfx {


   	@Column(name = "juZhan" )
	private String juZhan;

   	@Column(name = "dzNumZaiYong" )
	private Integer dzNumZaiYong = 0;

   	@Column(name = "zhengQiEnd" )
	private Integer zhengQiEnd = 0;

   	@Column(name = "wuXianEnd" )
	private Integer wuXianEnd = 0;

   	@Column(name = "jieRuEnd" )
	private Integer jieRuEnd = 0;

   	@Column(name = "dongLiEnd" )
	private Integer dongLiEnd = 0;

   	@Column(name = "zhengQiNotEnd" )
	private Integer zhengQiNotEnd = 0;

   	@Column(name = "wuXianNotEnd" )
	private Integer wuXianNotEnd = 0;

   	@Column(name = "jieRuNotEnd" )
	private Integer jieRuNotEnd = 0;

   	@Column(name = "dongLiNotEnd" )
	private Integer dongLiNotEnd = 0;

	@Column(name = "xianLuNotEnd" )
	private Integer xianLuNotEnd = 0;

   	@Column(name = "unKonw" )
	private Integer unKonw = 0;

   	@Column(name = "statisticsDate" )
	private String statisticsDate;

}

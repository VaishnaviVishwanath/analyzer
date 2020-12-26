package com.analyzer.analyzer.beans;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ReportsRequest {
   @NotNull @NotBlank
   private String dbName;
   @NotNull @NotBlank
   private String index;
   @NotNull @NotBlank
   private String eventCategory;
   @NotNull @NotBlank
   private String eventLabel;
   private String startTs;
   private String endTs;
   @Override
public String toString() {
	return "ReportsRequest [dbName=" + dbName + ", index=" + index + ", eventCategory=" + eventCategory
			+ ", eventLabel=" + eventLabel + ", createTs=" + startTs + ", endTs=" + endTs + ", offset=" + offset
			+ ", limit=" + limit + "]";
}
private Integer offset;
   private Integer limit;
public String getDbName() {
	return dbName;
}
public void setDbName(String dbName) {
	this.dbName = dbName;
}
public String getIndex() {
	return index;
}
public void setIndex(String index) {
	this.index = index;
}
public String getEventCategory() {
	return eventCategory;
}
public void setEventCategory(String eventCategory) {
	this.eventCategory = eventCategory;
}
public String getEventLabel() {
	return eventLabel;
}
public void setEventLabel(String eventLabel) {
	this.eventLabel = eventLabel;
}
public String getStartTs() {
	return startTs;
}
@SuppressWarnings("deprecation")
public void setStartTs(String startTs) {
	
	this.startTs = startTs;
}
public String getEndTs() {
	return endTs;
}
public void setEndTs(String endTs) {
	this.endTs = endTs;
}
public Integer getOffset() {
	return offset;
}
public void setOffset(Integer offset) {
	this.offset = offset;
}
public Integer getLimit() {
	return limit;
}
public void setLimit(Integer limit) {
	this.limit = limit;
}
}

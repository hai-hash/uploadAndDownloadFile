package com.laptrinhweb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;


public class File1 {
	private String fileName;
	private String base64;
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getBase64() {
		return base64.split(",")[1];
	}
	public void setBase64(String base64) {
		this.base64 = base64;
	}
	

}

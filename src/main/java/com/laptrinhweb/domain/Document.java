package com.laptrinhweb.domain;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="document")
@Data
public class Document {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="filename")
	private String fileName;
	
	@Column(name="size")
	private Long sizeFile;
	
	@Column(name="upload_time")
	private Date uploadTime;
	
	@Column(name="filedownloaduri")
	private String fileDownloadUri;

}

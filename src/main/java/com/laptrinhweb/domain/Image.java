package com.laptrinhweb.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
@Entity
@Data
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="name_file")
	private String nameFile;
	
	@Column(name="file_path")
	private String filePath;
	
	@Column(name="time_upload")
	private Date timeUpload;
	
	@Column(name="size")
	private int size;


}

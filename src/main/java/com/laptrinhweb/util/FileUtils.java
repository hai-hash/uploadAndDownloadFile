package com.laptrinhweb.util;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.laptrinhweb.domain.Image;
import com.laptrinhweb.repository.ImageRepository;


@Component
public class FileUtils {
	
	private final String root = "../project-web/src/main/resources/static";
	@Autowired
	private ImageRepository imageRepo;
	
	public void updateOrWrite(byte[] base64,String path,String name) {
		File file = new File(StringUtils.substringBeforeLast(root + path, "/"));
		
		
		if(!file.exists()) {
			file.mkdir();
		}
		File file1 = new File(root + path);
		try(FileOutputStream fileOutPutStream = new FileOutputStream(file1)) {
			fileOutPutStream.write(base64);
			Image image = new Image();
			image.setNameFile(name);
			image.setFilePath(file1.getAbsolutePath());
			image.setSize(base64.length);
			image.setTimeUpload(new Date());
			imageRepo.save(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

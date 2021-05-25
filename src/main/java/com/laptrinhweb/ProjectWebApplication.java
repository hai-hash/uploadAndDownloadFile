package com.laptrinhweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.laptrinhweb.domain.FileStorageProperties;



@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class ProjectWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectWebApplication.class, args);
	}

}

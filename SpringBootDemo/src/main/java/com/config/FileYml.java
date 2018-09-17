package com.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
public class FileYml  {
	
	private String uploadPath;
	
	private String uploadTempPath;
	

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getUploadTempPath() {
		return uploadTempPath;
	}

	public void setUploadTempPath(String uploadTempPath) {
		this.uploadTempPath = uploadTempPath;
	}

}
package com.drop.box;

import org.springframework.web.multipart.MultipartFile;

public class DropBoxDetail {
	
	private String accessToken;
	private MultipartFile [] inputFile;
	
	public MultipartFile[] getInputFile() {
		return inputFile;
	}
	public void setInputFile(MultipartFile[] inputFile) {
		this.inputFile = inputFile;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}

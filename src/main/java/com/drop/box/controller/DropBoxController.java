package com.drop.box.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.drop.box.DropBoxFileUpload;
import com.sun.jersey.multipart.FormDataParam;

@RestController
@CrossOrigin(origins = "*")
public class DropBoxController {

	private static final String ACCESS_TOKEN = "1DIfmppUucAAAAAAAAAACrU6nfK5eLOq8hAY23dmo8Jwa7EZYr6xSxIm83hvrZQj";

	@RequestMapping(value = "/dorpBoxFileUpload", method = RequestMethod.POST)
	public boolean uploadFile(@FormDataParam("file") MultipartFile[] file) {
		return DropBoxFileUpload.uploadDropBoxFile(file, ACCESS_TOKEN);
	}
}

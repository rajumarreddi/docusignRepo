package com.docu.sign.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.docu.sign.common.DocSignAPIObject;
import com.docu.sign.common.DocSignStatus;
import com.docu.sign.utils.DocSignUtils;
import com.dropbox.core.v2.files.FileMetadata;
import com.sun.jersey.multipart.FormDataParam;


@RestController
@CrossOrigin(origins = "*")
public class DocSignController {
	
	@RequestMapping(value = "/docSignFileUpload", method = RequestMethod.POST)
	public DocSignStatus addRecipients(@FormDataParam("file") MultipartFile[] file) {
		

		DocSignAPIObject docSignAPIObject = new DocSignAPIObject();
		docSignAPIObject.setDocumentId("1");
		docSignAPIObject.setDocuSignAccountAPIKey("2efedc36-8cd1-4133-aaec-e2f86912971b");
		docSignAPIObject.setDocuSignAccountUserName("mukesh.cdac.acts@gmail.com");
		docSignAPIObject.setDocuSignAccountUserPwd("brothers@123");
		docSignAPIObject.setSignerEmailId("mdsajid204@gmail.com");
		docSignAPIObject.setSignerName("Sajid");
		docSignAPIObject.setBaseURL("https://demo.docusign.net/restapi");
		docSignAPIObject.setClientUserId("1001");
		docSignAPIObject.setEnvelopeStatus("sent");
		docSignAPIObject.setRecipientId("1");
		docSignAPIObject.setPageNumber("1");
		docSignAPIObject.setxPosition("450");
		docSignAPIObject.setyPosition("400");
		
		List<MultipartFile> list = Arrays.asList(file);
		try {
			for (MultipartFile data : list) {
				docSignAPIObject.setFileBytes(data.getBytes());
				docSignAPIObject.setFileName(data.getName());
				docSignAPIObject.setSize(""+data.getSize());
				System.out.println(docSignAPIObject.getFileName());
			}
			//status = true;
		} catch (Exception e) {
			System.out.println(e);
		}

		return DocSignUtils.addDocSign(docSignAPIObject);
	}
}

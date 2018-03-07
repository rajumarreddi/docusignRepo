package com.docu.sign.common;

public class DocSignAPIObject {
	
	private String docuSignAccountUserName;
	private String docuSignAccountUserPwd;
	private String docuSignAccountAPIKey;
	
	private String fileName;
	private byte[] fileBytes;
	private String size;
	
	private String signerName;
	private String signerEmailId;
	
	private String clientUserId;
	private String recipientId;
	private String documentId;
	
	private String pageNumber;
	private String xPosition;
	private String yPosition;

	private String envelopeStatus;
	//	String BaseUrl = "https://demo.docusign.net/restapi";
	private String baseURL;

	public String getDocuSignAccountUserName() {
		return docuSignAccountUserName;
	}

	public void setDocuSignAccountUserName(String docuSignAccountUserName) {
		this.docuSignAccountUserName = docuSignAccountUserName;
	}

	public String getDocuSignAccountUserPwd() {
		return docuSignAccountUserPwd;
	}

	public void setDocuSignAccountUserPwd(String docuSignAccountUserPwd) {
		this.docuSignAccountUserPwd = docuSignAccountUserPwd;
	}

	public String getDocuSignAccountAPIKey() {
		return docuSignAccountAPIKey;
	}

	public void setDocuSignAccountAPIKey(String docuSignAccountAPIKey) {
		this.docuSignAccountAPIKey = docuSignAccountAPIKey;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileBytes() {
		return fileBytes;
	}

	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSignerName() {
		return signerName;
	}

	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}

	public String getSignerEmailId() {
		return signerEmailId;
	}

	public void setSignerEmailId(String signerEmailId) {
		this.signerEmailId = signerEmailId;
	}

	public String getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getxPosition() {
		return xPosition;
	}

	public void setxPosition(String xPosition) {
		this.xPosition = xPosition;
	}

	public String getyPosition() {
		return yPosition;
	}

	public void setyPosition(String yPosition) {
		this.yPosition = yPosition;
	}

	public String getEnvelopeStatus() {
		return envelopeStatus;
	}

	public void setEnvelopeStatus(String envelopeStatus) {
		this.envelopeStatus = envelopeStatus;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
	
}

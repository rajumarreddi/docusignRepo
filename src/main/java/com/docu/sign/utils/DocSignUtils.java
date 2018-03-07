package com.docu.sign.utils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.docu.sign.common.DocSignAPIObject;
import com.docu.sign.common.DocSignStatus;
import com.docusign.esign.api.AuthenticationApi;
import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.client.Configuration;
import com.docusign.esign.model.Document;
import com.docusign.esign.model.EnvelopeDefinition;
import com.docusign.esign.model.EnvelopeSummary;
import com.docusign.esign.model.LoginAccount;
import com.docusign.esign.model.LoginInformation;
import com.docusign.esign.model.RecipientViewRequest;
import com.docusign.esign.model.Recipients;
import com.docusign.esign.model.SignHere;
import com.docusign.esign.model.Signer;
import com.docusign.esign.model.Tabs;
import com.docusign.esign.model.ViewUrl;

public class DocSignUtils {

	public static DocSignStatus addDocSign(DocSignAPIObject obj) {
		DocSignStatus returnObj = new DocSignStatus();
		List<LoginAccount> loginAccounts = getAccountURL(obj);
		EnvelopeSummary envelopeSummary = createEnevelope(obj, loginAccounts);
		ViewUrl viewURL = createRecipientView(obj, loginAccounts, envelopeSummary);
		returnObj.setDocuSgingURL(viewURL.getUrl());
		returnObj.setStatusComleted(false);
		return returnObj;
	}

	public static List<LoginAccount> getAccountURL(DocSignAPIObject obj) {

		// Enter your DocuSign credentials
		String UserName = obj.getDocuSignAccountUserName();
		String Password = obj.getDocuSignAccountUserPwd();
		String IntegratorKey = obj.getDocuSignAccountAPIKey();

		// for production environment update to "www.docusign.net/restapi"
		//String BaseUrl = "https://demo.docusign.net/restapi";
		String BaseUrl = obj.getBaseURL();

		// initialize the api client for the desired environment
		ApiClient apiClient = new ApiClient();
		apiClient.setBasePath(BaseUrl);

		// create JSON formatted auth header
		String creds = "{\"Username\":\"" + UserName + "\",\"Password\":\"" + Password + "\",\"IntegratorKey\":\""
				+ IntegratorKey + "\"}";
		apiClient.addDefaultHeader("X-DocuSign-Authentication", creds);

		// assign api client to the Configuration object
		Configuration.setDefaultApiClient(apiClient);

		try {
			// login call available off the AuthenticationApi
			AuthenticationApi authApi = new AuthenticationApi();

			// login has some optional parameters we can set
			AuthenticationApi.LoginOptions loginOps = authApi.new LoginOptions();
			loginOps.setApiPassword("true");
			loginOps.setIncludeAccountIdGuid("true");
			LoginInformation loginInfo = authApi.login(loginOps);

			// note that a given user may be a member of multiple accounts
			List<LoginAccount> loginAccounts = loginInfo.getLoginAccounts();

			System.out.println("LoginInformation: " + loginAccounts);
			return loginAccounts;
		} catch (com.docusign.esign.client.ApiException ex) {
			System.out.println("Exception: " + ex);
		}
		return null;
	}

	public static EnvelopeSummary createEnevelope(DocSignAPIObject obj, List<LoginAccount> loginAccounts) {
		// specify a document we want signed
		//String SignTest1File = "[PATH/TO/DOCUMENT/TEST.PDF]";

		// create a byte array that will hold our document bytes
		byte[] fileBytes = obj.getFileBytes();

		/*
		 * try { String currentDir = System.getProperty("user.dir");
		 * 
		 * // read file from a local directory Path path = Paths.get(currentDir
		 * + SignTest1File); fileBytes = Files.readAllBytes(path); } catch
		 * (IOException ioExcp) { // TODO: handle error
		 * System.out.println("Exception: " + ioExcp); return null; }
		 */

		// create an envelope that will store the document(s), field(s), and
		// recipient(s)
		EnvelopeDefinition envDef = new EnvelopeDefinition();
		envDef.setEmailSubject("Please sign this document sent from Java SDK)");

		// add a document to the envelope
		Document doc = new Document();
		String base64Doc = Base64.getEncoder().encodeToString(fileBytes);
		doc.setDocumentBase64(base64Doc);
		doc.setName(obj.getSignerName() + ".pdf"); // can be different from actual
													// file name
		doc.setDocumentId(obj.getDocumentId());

		List<Document> docs = new ArrayList<Document>();
		docs.add(doc);
		envDef.setDocuments(docs);

		// add a recipient to sign the document, identified by name and email we
		// used above
		Signer signer = new Signer();
		signer.setEmail(obj.getSignerEmailId());
		signer.setName(obj.getSignerName());
		signer.setRecipientId(obj.getRecipientId());

		// Must set |clientUserId| for embedded recipients and provide the same
		// value when requesting
		// the recipient view URL in the next step
		signer.setClientUserId(obj.getClientUserId());

		// create a |signHere| tab somewhere on the document for the signer to
		// sign
		// default unit of measurement is pixels, can be mms, cms, inches also
		SignHere signHere = new SignHere();
		signHere.setDocumentId(obj.getDocumentId());
		signHere.setPageNumber(obj.getPageNumber());
		signHere.setRecipientId(obj.getRecipientId());
		signHere.setXPosition(obj.getxPosition());
		signHere.setYPosition(obj.getyPosition());

		// can have multiple tabs, so need to add to envelope as a single
		// element list
		List<SignHere> signHereTabs = new ArrayList<SignHere>();
		signHereTabs.add(signHere);
		Tabs tabs = new Tabs();
		tabs.setSignHereTabs(signHereTabs);
		signer.setTabs(tabs);

		// add recipients (in this case a single signer) to the envelope
		envDef.setRecipients(new Recipients());
		envDef.getRecipients().setSigners(new ArrayList<Signer>());
		envDef.getRecipients().getSigners().add(signer);

		// send the envelope by setting |status| to "sent". To save as a draft
		// set to "created"
		envDef.setStatus(obj.getEnvelopeStatus());

		try {
			// use the |accountId| we retrieved through the Login API to create
			// the Envelope
			String accountId = loginAccounts.get(0).getAccountId();

			// instantiate a new EnvelopesApi object
			EnvelopesApi envelopesApi = new EnvelopesApi();

			// call the createEnvelope() API to make the signature request live
			// and ready to be signed
			EnvelopeSummary envelopeSummary = envelopesApi.createEnvelope(accountId, envDef);
			// envelopeId.append( envelopeSummary.getEnvelopeId() );
			// envelopeId = envelopeSummary.getEnvelopeId();

			System.out.println("EnvelopeSummary: " + envelopeSummary);
			return envelopeSummary;
		} catch (com.docusign.esign.client.ApiException ex) {
			System.out.println("Exception: " + ex);
		}

		return null;
	}

	public static ViewUrl createRecipientView(DocSignAPIObject obj, List<LoginAccount> loginAccounts,
			EnvelopeSummary envelopeSummary) {
		try {
			// use the |accountId| we retrieved through the Login API
			String accountId = loginAccounts.get(0).getAccountId();

			// instantiate a new EnvelopesApi object
			EnvelopesApi envelopesApi = new EnvelopesApi();

			// set the url where you want the recipient to go once they are done
			// signing
			RecipientViewRequest returnUrl = new RecipientViewRequest();
			returnUrl.setReturnUrl("https://www.docusign.com/devcenter");
			returnUrl.setAuthenticationMethod("email");

			// recipient information must match embedded recipient info we
			// provided in step #2
			returnUrl.setEmail(obj.getSignerEmailId());
			returnUrl.setUserName(obj.getSignerName());
			returnUrl.setRecipientId(obj.getRecipientId());
			returnUrl.setClientUserId(obj.getClientUserId());

			// call the CreateRecipientView API then navigate to the URL to
			// start the signing session
			ViewUrl recipientView;

			recipientView = envelopesApi.createRecipientView(accountId, envelopeSummary.getEnvelopeId().toString(),
					returnUrl);
			System.out.println("ViewUrl: " + recipientView);

			return recipientView;
		} catch (ApiException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}

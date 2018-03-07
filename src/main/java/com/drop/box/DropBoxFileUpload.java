package com.drop.box;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

public class DropBoxFileUpload {

	public static boolean uploadDropBoxFile(MultipartFile [] file, String key) {

		@SuppressWarnings("deprecation")
		DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
		DbxClientV2 client = new DbxClientV2(config, key);

		// getDropBoxDetail(config,client);
		return uploadfile(config, client,file);
	}

	public static boolean uploadfile(DbxRequestConfig config, DbxClientV2 client, MultipartFile [] file) {
		boolean status = false;
		List<MultipartFile> list = Arrays.asList(file);
		try {
			for (MultipartFile data : list) {
				FileMetadata metadata = client.files().uploadBuilder("/"+data.getOriginalFilename()).uploadAndFinish(data.getInputStream());
				/*FileMetadata metadata = client.files().uploadBuilder("/"+data.getName()).uploadAndFinish(data.getInputStream());*/
				System.out.println(metadata.toString());
			}
			status = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return status;
	}

	public static void getDropBoxDetail(DbxRequestConfig config, DbxClientV2 client) {
		try {

			// Get current account info
			FullAccount account = client.users().getCurrentAccount();
			System.out.println(account.getName().getDisplayName());

			// Get files and folder metadata from Dropbox root directory
			ListFolderResult result = client.files().listFolder("");
			while (true) {
				for (Metadata metadata : result.getEntries()) {
					System.out.println(metadata.getPathLower());
				}

				if (!result.getHasMore()) {
					break;
				}

				result = client.files().listFolderContinue(result.getCursor());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

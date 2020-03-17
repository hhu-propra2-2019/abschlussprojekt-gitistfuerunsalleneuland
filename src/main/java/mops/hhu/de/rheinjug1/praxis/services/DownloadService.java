package mops.hhu.de.rheinjug1.praxis.services;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

import io.minio.MinioClient;
import io.minio.errors.MinioException;

@Service
public class DownloadService {
	
	public String getURLforObjectDownload()
		      throws IOException, NoSuchAlgorithmException, InvalidKeyException, XmlPullParserException {
			String url = "";
		    try {
		   
		      MinioClient minioClient =
		          new MinioClient("http://localhost:9000/", "minio","minio123");
		     url = minioClient.presignedGetObject("rheinjug", "testfile.adoc");
		      System.out.println(url);
		    } catch (MinioException e) {
		      System.out.println("Error occurred: " + e);
		    }
		    return url;
		  }

}

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.junit.jupiter.api.Test;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinioTest {

    @Test
    void MinioTest() throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        try {
            // Create a minioClient with the MinIO Server name, Port, Access key and Secret key.
            MinioClient minioClient = new MinioClient("https://play.min.io", "Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG");

            // Check if the bucket already exists.
            boolean isExist = minioClient.bucketExists("asiatrip");
            if(isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // Make a new bucket called asiatrip to hold a zip file of photos.
                minioClient.makeBucket("asiatrip");
            }

            // Upload the zip file to the bucket with putObject
            minioClient.putObject("asiatrip","test.txt", "/home/user/Photos/test.txt");
            System.out.println("/home/user/Photos/asiaphotos.zip is successfully uploaded as asiaphotos.zip to `asiatrip` bucket.");
        } catch(MinioException | XmlPullParserException e) {
            System.out.println("Error occurred: " + e);
        }
    }
}

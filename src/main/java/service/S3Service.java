package service;

import repository.S3Repository;
import software.amazon.awssdk.services.s3.model.*;
import java.util.ArrayList;
import java.util.List;

public final class S3Service {
    private static S3Service INSTANCE;

    public static S3Service getInstance() {
        if(INSTANCE == null)
            INSTANCE = new S3Service();
        return INSTANCE;
    }

    // Return the byte[] from object.
    public byte[] getObjectBytes(String bucketName, String keyName) {
        try {
            GetObjectRequest objectRequest = GetObjectRequest
                    .builder()
                    .key(keyName)
                    .bucket(bucketName)
                    .build();

            return S3Repository.getInstance().getObjectAsBytes(objectRequest).asByteArray();
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return null;
    }

    // Returns the names of all images in the given bucket.
    public List<String> listBucketObjects(String bucketName) {
        String keyName;
        List<String> keys = new ArrayList<>();

        try {
            ListObjectsRequest listObjects = ListObjectsRequest
                    .builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsResponse res = S3Repository.getInstance().listObjects(listObjects);
            List<S3Object> objects = res.contents();

            for (S3Object myValue: objects) {
                keyName = myValue.key();
                keys.add(keyName);
            }
            return keys;

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return null;
    }
}

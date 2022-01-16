package repository;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public final class S3Repository {
    private static S3Client INSTANCE;

    public static S3Client getInstance() {
        if(INSTANCE == null)
            INSTANCE = S3Client.builder()
                    .region(Region.US_EAST_1)
                    .build();
        return INSTANCE;
    }
}

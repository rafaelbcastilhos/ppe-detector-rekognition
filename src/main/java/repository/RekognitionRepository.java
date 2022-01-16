package repository;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;

public final class RekognitionRepository {
    private static RekognitionClient INSTANCE;

    public static RekognitionClient getInstance() {
        if(INSTANCE == null)
            INSTANCE = RekognitionClient.builder()
                    .region(Region.US_EAST_1)
                    .build();

        return INSTANCE;
    }
}

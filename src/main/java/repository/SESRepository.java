package repository;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

public final class SESRepository {
    private static SesClient INSTANCE;

    public static SesClient getInstance() {
        if(INSTANCE == null)
            INSTANCE = SesClient.builder()
                    .region(Region.US_EAST_1)
                    .build();

        return INSTANCE;
    }
}

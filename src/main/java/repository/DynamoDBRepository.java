package repository;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public final class DynamoDBRepository {
    private static DynamoDbClient INSTANCE;
    private static DynamoDbEnhancedClient ENHANCED_INSTANCE;

    public static DynamoDbClient getInstance() {
        if(INSTANCE == null)
            INSTANCE = DynamoDbClient.builder()
                    .region(Region.US_EAST_1)
                    .build();
        return INSTANCE;
    }

    public static DynamoDbEnhancedClient getEnhancedInstance(){
        if(ENHANCED_INSTANCE == null)
            ENHANCED_INSTANCE = DynamoDbEnhancedClient.builder()
                    .dynamoDbClient(INSTANCE)
                    .build();

        return ENHANCED_INSTANCE;
    }
}

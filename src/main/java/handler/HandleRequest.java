package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import model.Item;
import service.DynamoDBService;
import service.RekognitionService;
import service.S3Service;
import service.SESService;
import java.util.*;

public class HandleRequest implements RequestHandler<Map<String,String>, String> {
    S3Service s3Service = new S3Service();
    DynamoDBService dynamoService = new DynamoDBService();
    RekognitionService rekognitionService = new RekognitionService();
    SESService sesService = new SESService();

    @Override
    public String handleRequest(Map<String, String> event, Context context) {
        String bucketName = event.get("bucketName");
        List<String> items = s3Service.listBucketObjects(bucketName);
        List<ArrayList<Item>> myList = new ArrayList<>();
        for (String item : items) {
            byte[] keyData = s3Service.getObjectBytes(bucketName, item);

            // Analyze the photo and return a list where each element is a WorkItem.
            ArrayList<Item> gearItem = rekognitionService.detectLabels(keyData, item);

            // Only add a list with items.
            if(gearItem != null)
                myList.add(gearItem);
        }

        dynamoService.persistItem(myList);

        // Create a new list with only unique keys to email.
        Set<String> uniqueKeys = createUniqueList(myList);
        sesService.sendMsg(uniqueKeys);
        return bucketName;
    }

    // Create a list of unique keys.
    private static Set<String> createUniqueList(List<ArrayList<Item>> gearList) {
        List<String> keys = new ArrayList<>();

        // Persist the data into a DynamoDB table.
        for (Object o : gearList) {
            List innerList = (List) o;
            for (Object value : innerList) {
                Item gearItem = (Item) value;
                keys.add(gearItem.getKey());
            }
        }

        // create list without duplicates image names...
        return new HashSet<>(keys);
    }
}

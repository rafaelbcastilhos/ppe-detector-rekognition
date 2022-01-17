package service;

import model.Item;
import repository.RekognitionRepository;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.model.*;
import java.util.ArrayList;
import java.util.List;

public final class RekognitionService {
    private static RekognitionService INSTANCE;

    public static RekognitionService getInstance() {
        if(INSTANCE == null)
            INSTANCE = new RekognitionService();
        return INSTANCE;
    }

    // Returns a list of Item objects that contains PPE information.
    public ArrayList<Item> detectLabels(byte[] bytes, String key) {
        ArrayList<Item> gearList = new ArrayList<>();
        try {
            SdkBytes sourceBytes = SdkBytes.fromByteArray(bytes);

            // Create an Image object for the source image.
            Image souImage = Image.builder()
                    .bytes(sourceBytes)
                    .build();

            ProtectiveEquipmentSummarizationAttributes summarizationAttributes =
                    ProtectiveEquipmentSummarizationAttributes.builder()
                            .minConfidence(80F)
                            .requiredEquipmentTypesWithStrings("FACE_COVER", "HAND_COVER", "HEAD_COVER")
                            .build();

            DetectProtectiveEquipmentRequest request = DetectProtectiveEquipmentRequest.builder()
                    .image(souImage)
                    .summarizationAttributes(summarizationAttributes)
                    .build();

            DetectProtectiveEquipmentResponse result =
                    RekognitionRepository.getInstance().detectProtectiveEquipment(request);
            List<ProtectiveEquipmentPerson> persons = result.persons();

            // Create a Item object.
            Item gear;
            for (ProtectiveEquipmentPerson person : persons) {
                List<ProtectiveEquipmentBodyPart> bodyParts = person.bodyParts();
                if (bodyParts.isEmpty())
                    System.out.println("\tNo body parts detected");
                else{
                    for (ProtectiveEquipmentBodyPart bodyPart : bodyParts) {
                        List<EquipmentDetection> equipmentDetections = bodyPart.equipmentDetections();

                        if (equipmentDetections.isEmpty())
                            System.out.println("\t\tNo PPE Detected on " + bodyPart.name());
                        else {
                            for (EquipmentDetection item : equipmentDetections) {

                                gear = new Item();
                                gear.setKey(key);

                                String itemType = item.type().toString();
                                String confidence = item.confidence().toString();
                                String myDesc = "Item: " + item.type() + ". Confidence: " + item.confidence().toString();
                                String bodyPartDes = "Covers body part: "
                                        + item.coversBodyPart().value().toString() + ". Confidence: "
                                        + item.coversBodyPart().confidence().toString();

                                gear.setName(itemType);
                                gear.setConfidence(confidence);
                                gear.setItemDescription(myDesc);
                                gear.setBodyCoverDescription(bodyPartDes);

                                gearList.add(gear);
                            }
                        }
                    }
                }
            }

            if (gearList.isEmpty())
                return null;
            else
                return gearList;
        } catch (RekognitionException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }
}

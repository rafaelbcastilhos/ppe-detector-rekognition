package model;

public class Item {
    private String key;
    private String name;
    private String itemDescription;
    private String bodyCoverDescription;
    private String confidence;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getBodyCoverDescription() {
        return bodyCoverDescription;
    }

    public void setBodyCoverDescription(String bodyCoverDescription) {
        this.bodyCoverDescription = bodyCoverDescription;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
}

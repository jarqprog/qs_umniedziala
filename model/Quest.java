package model;

public class Quest extends Item {

    private String type;

    public Quest(String name,
                 int value,
                 String description,
                 String status,
                 String type) {

        this.name = name;
        this.value = value;
        this.description = description;
        this.status = status;
        this.type = type;
        this.itemId = ++idCounter;
    }

    public Quest(String name,
                 int value,
                 String description,
                 String status,
                 String type,
                 int itemId) {

        this.name = name;
        this.value = value;
        this.description = description;
        this.status = status;
        this.type = type;
        this.itemId = itemId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

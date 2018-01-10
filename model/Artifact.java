package model;

public class Artifact extends Item {

    public Artifact(String name,
                 int value,
                 String description,
                 String status) {

        this.name = name;
        this.value = value;
        this.description = description;
        this.status = status;
        this.itemId = ++idCounter;
    }

    public Artifact(String name,
                 int value,
                 String description,
                 String status,
                 int itemId) {

        this.name = name;
        this.value = value;
        this.description = description;
        this.status = status;
        this.itemId = itemId;
    }
    
}

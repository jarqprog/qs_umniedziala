package model;

public class Artifact extends Item {

    public Artifact(String name,
                 int value,
                 String description,
                 String status) {

        super(name, value, description, status);
    }

    public Artifact(String name,
                 int value,
                 String description,
                 String status,
                 int itemId) {

        super(name, value, description, status, itemId);
    }

    @Override
    public String toString() {
        return "Artifact: " + super.toString();
    }
    
}

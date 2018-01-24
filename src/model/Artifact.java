package model;

public class Artifact extends Item {

    public Artifact(String name,
                 int value,
                 String description,
                 String type) {

        super(name, value, description, type);
    }

    public Artifact(int itemId,
                String name,
                 int value,
                 String description,
                 String type) {

        super(itemId, name, value, description, type);
    }

    @Override
    public String toString() {
        return "Artifact: " + super.toString();
    }
    
}

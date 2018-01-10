package model;

public class Quest extends Item {

    private String type;

    public Quest(String name,
                 int value,
                 String description,
                 String status,
                 String type) {

        super(name, value, description, status);
        this.type = type;
    }

    public Quest(String name,
                 int value,
                 String description,
                 String status,
                 String type,
                 int itemId) {

        super(name, value, description, status, itemId);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Quest: " + super.toString() + ", type: " + type;
    }

}

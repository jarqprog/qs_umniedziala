package model;

public abstract class Item {

    protected static int idCounter = 0;

    protected String name;
    protected int value;
    protected String description;
    protected String status;
    protected int itemId;

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public int getItemId() {
        return itemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setDescription(String description)  {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public static void setIdCounter(int newIdCounter) {
        idCounter = newIdCounter;
    }

    public String toString() {
        return "name: " + name
                + ", value: " + value
                + ", description: " + description
                + ", status: " + status
                + ", item id: " + itemId;
    }

}

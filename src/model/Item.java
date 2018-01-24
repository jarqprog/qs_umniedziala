package model;

public abstract class Item {

    protected int itemId;
    protected String name;
    protected int value;
    protected String description;
    protected String type;

    public Item(String name,
                int value,
                String description,
                String type) {

        this.name = name;
        this.value = value;
        this.description = description;
        this.type = type;
    }

    public Item(int itemId,
                String name,
                int value,
                String description,
                String type) {

        this.name = name;
        this.value = value;
        this.description = description;
        this.type = type;
        this.itemId = itemId;
    }
    public int getItemId() { return itemId; }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return status;
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

    public void setType(String status) {
        this.status = status;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    
    public String toString() {
        return "name: " + name
                + ", value: " + value
                + ", description: " + description
                + ", status: " + status
                + ", item id: " + itemId;
    }

}

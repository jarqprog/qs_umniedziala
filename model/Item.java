package model;

public abstract class Item{

    protected String name;
    protected int value;
    protected String description;
    protected boolean status;
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

    public boolean getStatus() {
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

}

package model;

public class Quest extends Item {

    private String category;

    public Quest(int itemId,
                 String name,
                 int value,
                 String description,
                 String type,
                 String category) {

        super(itemId, name, value, description, type);
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return super.toString() + ", category: " + this.category;
    }

}

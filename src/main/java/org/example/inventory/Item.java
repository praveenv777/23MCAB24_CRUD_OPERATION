package org.example.inventory;


public class Item {

    private String itemId;
    private String name;
    private String category;
    private double price;
    private int quantityInStock;

    public Item() {
        // Default constructor
    }

    public Item(String itemId, String name, String category, double price, int quantityInStock) {
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantityInStock=" + quantityInStock +
                '}';
    }
}


package model;

/**
 * Base class for all Gadgets.
 * Demonstrates INHERITANCE and ENCAPSULATION.
 */
public class Gadget extends AbstractProduct {
    private String brand;
    private int stockQuantity;

    public Gadget(String id, String name, double price, String brand, int stockQuantity) {
        super(id, name, price);
        this.brand = brand;
        this.stockQuantity = stockQuantity;
    }

    // Encapsulation: Getters and Setters
    public String getBrand() { return brand; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    @Override
    public String getDetails() {
        return String.format("ID: %s | Name: %s | Brand: %s | Price: $%.2f | Stock: %d", 
                getId(), getName(), brand, getPrice(), stockQuantity);
    }
}

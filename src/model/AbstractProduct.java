package model;

/**
 * Abstract class representing a generic product.
 * This demonstrates ABSTRACTION by defining a contract for all products.
 */
public abstract class AbstractProduct {
    private String id;
    private String name;
    private double price;

    public AbstractProduct(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Encapsulation: Accessors
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    // Abstract method to be implemented by detailed subclasses
    public abstract String getDetails();
}

package model;

/**
 * Subclass representing a Laptop.
 * Enhances the base Gadget with Processor and SSD specifications.
 */
public class Laptop extends Gadget {
    private String processor;
    private int ssdCapacity;

    public Laptop(String id, String name, double price, String brand, int stock, String processor, int ssd) {
        super(id, name, price, brand, stock);
        this.processor = processor;
        this.ssdCapacity = ssd;
    }

    public String getProcessor() {
        return processor;
    }

    public int getSsdCapacity() {
        return ssdCapacity;
    }

    @Override
    public String getDetails() {
        return super.getDetails() + String.format(" | CPU: %s | SSD: %dGB", processor, ssdCapacity);
    }
}

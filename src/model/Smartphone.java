package model;

/**
 * Subclass representing a Smartphone.
 * Enhances the base Gadget with OS and RAM specifications.
 */
public class Smartphone extends Gadget {
    private String operatingSystem;
    private int ramSize;

    public Smartphone(String id, String name, double price, String brand, int stock, String os, int ram) {
        super(id, name, price, brand, stock);
        this.operatingSystem = os;
        this.ramSize = ram;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public int getRamSize() {
        return ramSize;
    }

    @Override
    public String getDetails() {
        return super.getDetails() + String.format(" | OS: %s | RAM: %dGB", operatingSystem, ramSize);
    }
}

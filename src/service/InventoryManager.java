package service;

import model.*;
import exception.InsufficientStockException;
import java.io.*;
import java.util.*;

/**
 * Manages the inventory using COLLECTIONS and FILE I/O.
 * Supports FULL POLYMORPHIC loading and saving of subclass data.
 */
public class InventoryManager {
    private List<Gadget> inventory;
    private static final String DATA_FILE = "data/gadgets.txt";

    public InventoryManager() {
        this.inventory = new ArrayList<>();
        ensureDataDirectoryExists();
    }

    private void ensureDataDirectoryExists() {
        File dir = new File("data");
        if (!dir.exists())
            dir.mkdirs();
    }

    public void addGadget(Gadget gadget) {
        inventory.add(gadget);
    }

    public List<Gadget> getInventory() {
        return new ArrayList<>(inventory);
    }

    public void deleteGadget(String id) {
        inventory.removeIf(p -> p.getId().equalsIgnoreCase(id));
    }

    public void processPurchase(String id, int quantity) throws InsufficientStockException {
        for (Gadget g : inventory) {
            if (g.getId().equalsIgnoreCase(id)) {
                if (g.getStockQuantity() < quantity) {
                    throw new InsufficientStockException("Insufficient stock for " + g.getName());
                }
                g.setStockQuantity(g.getStockQuantity() - quantity);
                return;
            }
        }
    }

    /**
     * FULL POLYMORPHISM: Save subclass-specific fields to the file.
     */
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Gadget g : inventory) {
                StringBuilder row = new StringBuilder();
                if (g instanceof Smartphone) {
                    Smartphone s = (Smartphone) g;
                    row.append("Smartphone,").append(s.getId()).append(",").append(s.getName()).append(",")
                            .append(s.getPrice()).append(",").append(s.getBrand()).append(",")
                            .append(s.getStockQuantity()).append(",").append(s.getOperatingSystem())
                            .append(",").append(s.getRamSize());
                } else if (g instanceof Laptop) {
                    Laptop l = (Laptop) g;
                    row.append("Laptop,").append(l.getId()).append(",").append(l.getName()).append(",")
                            .append(l.getPrice()).append(",").append(l.getBrand()).append(",")
                            .append(l.getStockQuantity()).append(",").append(l.getProcessor())
                            .append(",").append(l.getSsdCapacity());
                } else {
                    row.append("Gadget,").append(g.getId()).append(",").append(g.getName()).append(",")
                            .append(g.getPrice()).append(",").append(g.getBrand()).append(",")
                            .append(g.getStockQuantity());
                }
                writer.println(row.toString());
            }
        } catch (IOException e) {
            System.err.println("File Save Error: " + e.getMessage());
        }
    }

    /**
     * FULL POLYMORPHISM: Load subclass-specific fields from the file.
     */
    public void loadFromFile() {
        inventory.clear();
        File file = new File(DATA_FILE);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length < 6)
                    continue;

                String type = p[0];
                String id = p[1];
                String name = p[2];
                double price = Double.parseDouble(p[3]);
                String brand = p[4];
                int stock = Integer.parseInt(p[5]);

                if (type.equalsIgnoreCase("Smartphone") && p.length >= 8) {
                    inventory.add(new Smartphone(id, name, price, brand, stock, p[6], Integer.parseInt(p[7])));
                } else if (type.equalsIgnoreCase("Laptop") && p.length >= 8) {
                    inventory.add(new Laptop(id, name, price, brand, stock, p[6], Integer.parseInt(p[7])));
                } else {
                    inventory.add(new Gadget(id, name, price, brand, stock) {
                        @Override
                        public String getDetails() {
                            return super.getDetails();
                        }
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("Load Error: " + e.getMessage());
        }
    }
}

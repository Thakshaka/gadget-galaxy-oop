package utils;

import service.InventoryManager;

/**
 * Background thread that automatically saves inventory data.
 * Demonstrates MULTITHREADING.
 */
public class AutoSaveThread extends Thread {
    private InventoryManager manager;
    private boolean running = true;

    public AutoSaveThread(InventoryManager manager) {
        this.manager = manager;
        setDaemon(true); // Ensures thread closes when application stops
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(60000); // Save every 60 seconds
                manager.saveToFile();
                System.out.println("[AutoSave] Inventory backed up automatically.");
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public void stopThread() {
        running = false;
        this.interrupt();
    }
}

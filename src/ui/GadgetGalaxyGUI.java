package ui;

import service.InventoryManager;
import model.*;
import exception.InsufficientStockException;
import utils.AutoSaveThread;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

/**
 * Main Management System for Gadget Galaxy.
 * Demonstrates advanced GUI, Global Localization, and CRUD operations.
 */
public class GadgetGalaxyGUI extends JFrame {
    private InventoryManager manager;
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private String userRole;
    private AutoSaveThread autoSave;
    private JTextField searchField;

    public GadgetGalaxyGUI(String role) {
        this.userRole = role;
        this.manager = new InventoryManager();
        manager.loadFromFile();

        this.autoSave = new AutoSaveThread(manager);
        autoSave.start();

        setupUI();
        refreshTable();
    }

    private void setupUI() {
        setTitle("Gadget Galaxy Hub - Role: " + userRole);
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        // Top Panel: Search & Global Toggle
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(41, 128, 185));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Search Bar
        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchBar.setOpaque(false);
        searchField = new JTextField(20);
        searchField.addCaretListener(e -> filterTable());
        searchBar.add(new JLabel("Search: "));
        searchBar.add(searchField);
        topPanel.add(searchBar, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(new Color(52, 73, 94));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        if (userRole.equals("Admin")) {
            addButton(sidebar, "Add Gadget", e -> openAddDialog("Smartphone"));
            addButton(sidebar, "Delete Selected", e -> deleteSelected());
        }
        addButton(sidebar, "Purchase Item", e -> purchaseSelected());
        addButton(sidebar, "Manual Refresh", e -> refreshTable());
        add(sidebar, BorderLayout.WEST);

        // Data Table with Extra Details
        String[] columns = { "Type", "ID", "Name", "Price", "Brand", "Stock", "Specifications" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(35);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            autoSave.stopThread();
            new LoginGUI().setVisible(true);
            this.dispose();
        });
        footer.add(logoutBtn);
        add(footer, BorderLayout.SOUTH);
    }

    private void addButton(JPanel panel, String text, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(listener);
        panel.add(btn);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Gadget g : manager.getInventory()) {
            String type = (g instanceof Smartphone) ? "📱 Smartphone"
                    : (g instanceof Laptop ? "💻 Laptop" : "📦 Gadget");
            String specs = g.getDetails().split("\\|")[g.getDetails().split("\\|").length - 1].trim();
            tableModel.addRow(new Object[] { type, g.getId(), g.getName(), "$" + g.getPrice(), g.getBrand(),
                    g.getStockQuantity(), specs });
        }
    }

    private void filterTable() {
        String input = searchField.getText();
        if (input.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + input));
        }
    }

    private void purchaseSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an item!");
            return;
        }

        String id = (String) table.getValueAt(row, 1);
        try {
            manager.processPurchase(id, 1);
            manager.saveToFile();
            refreshTable();
            JOptionPane.showMessageDialog(this, "Success!");
        } catch (InsufficientStockException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String id = (String) table.getValueAt(row, 1);
            manager.deleteGadget(id);
            manager.saveToFile();
            refreshTable();
        }
    }

    private void openAddDialog(String defaultType) {
        // More comprehensive dialog using a JComboBox for type
        String[] types = { "Smartphone", "Laptop", "Gadget" };
        String type = (String) JOptionPane.showInputDialog(this, "Select Type:", "Add Product",
                JOptionPane.QUESTION_MESSAGE, null, types, types[0]);
        if (type == null)
            return;

        String id = JOptionPane.showInputDialog("ID:");
        String name = JOptionPane.showInputDialog("Name:");
        String brand = JOptionPane.showInputDialog("Brand:");
        double price = Double.parseDouble(JOptionPane.showInputDialog("Price:"));
        int stock = Integer.parseInt(JOptionPane.showInputDialog("Stock:"));

        if (type.equals("Smartphone")) {
            String os = JOptionPane.showInputDialog("OS (Android/iOS):");
            int ram = Integer.parseInt(JOptionPane.showInputDialog("RAM (GB):"));
            manager.addGadget(new Smartphone(id, name, price, brand, stock, os, ram));
        } else if (type.equals("Laptop")) {
            String cpu = JOptionPane.showInputDialog("CPU:");
            int ssd = Integer.parseInt(JOptionPane.showInputDialog("SSD (GB):"));
            manager.addGadget(new Laptop(id, name, price, brand, stock, cpu, ssd));
        } else {
            manager.addGadget(new Gadget(id, name, price, brand, stock) {
                public String getDetails() {
                    return super.getDetails();
                }
            });
        }
        manager.saveToFile();
        refreshTable();
    }
}

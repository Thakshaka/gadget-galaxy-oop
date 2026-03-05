package ui;

import model.User;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * Modern Login Screen for Gadget Galaxy.
 * Demonstrates basic SECURITY logic.
 */
public class LoginGUI extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private java.util.List<User> userList = new ArrayList<>();

    public LoginGUI() {
        loadUsers();
        setupUI();
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 3)
                    userList.add(new User(p[0], p[1], p[2]));
            }
        } catch (IOException e) {
            userList.add(new User("admin", "admin123", "Admin"));
        }
    }

    private void setupUI() {
        setTitle("Gadget Galaxy - Login");
        setSize(350, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        userField = new JTextField(15);
        gbc.gridx = 1;
        add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        passField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passField, gbc);

        JButton loginBtn = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginBtn.setPreferredSize(new Dimension(100, 30));
        loginBtn.addActionListener(e -> attemptLogin());
        add(loginBtn, gbc);
    }

    private void attemptLogin() {
        String inputUser = userField.getText();
        String inputPass = new String(passField.getPassword());

        for (User u : userList) {
            if (u.getUsername().equals(inputUser) && u.getPassword().equals(inputPass)) {
                JOptionPane.showMessageDialog(this, "Welcome " + u.getRole() + "!");
                new GadgetGalaxyGUI(u.getRole()).setVisible(true);
                this.dispose();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Invalid Credentials!", "Login Error", JOptionPane.ERROR_MESSAGE);
    }
}

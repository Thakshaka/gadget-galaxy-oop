# Gadget Galaxy Pro - Object-Oriented Management System

This repository contains the complete, high-distinction implementation of the "Gadget Galaxy" project for the CSE4006 Object Oriented Programming module (ICBT).

## Advanced Features
- **Full Polymorphic Persistence**: Correctly saves and loads hardware-specific fields for `Smartphone` (OS/RAM) and `Laptop` (CPU/SSD) using custom file parsing.
- **Multithreading**: A background `AutoSaveThread` ensures no data is lost by creating persistent backups every 60 seconds.
- **Advanced GUI**: 
  - **Real-time Search**: Filter the inventory instantly as you type.
  - **Role-Based Security**: Admin users can modify the inventory; Customers can only browse and purchase.
- **Custom Exception Handling**: Custom classes like `InsufficientStockException` provide specific, user-friendly feedback.

## How to Run

### Command Line
1. **Compile**:
   ```powershell
   javac -d bin src/model/*.java src/service/*.java src/ui/*.java src/exception/*.java src/utils/*.java src/Main.java
   ```
2. **Launch**:
   ```powershell
   java -cp bin Main
   ```

### Credentials
- **Admin**: `admin` / `admin123`
- **Customer**: `user` / `user123`

## Repository Structure
- `src/model/`: Classes demonstrating Abstraction, Inheritance, and Encapsulation.
- `src/service/`: Business logic and File I/O handling.
- `src/ui/`: Interactive Swing components (Login and Dashboard).
- `src/exception/`: Custom exception hierarchy.
- `src/utils/`: Background thread utilities.
- `data/`: Sample `.txt` data files.

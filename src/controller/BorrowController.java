package controller;
import utils.FileHandler;
import javax.swing.JOptionPane;

public class BorrowController {
    private LibraryManager manager;

    public BorrowController(LibraryManager manager) {
        this.manager = manager;
    }

    public boolean processBorrow(String itemId, String userId) {
        if (itemId.isEmpty() || userId.isEmpty()) return false;
        boolean success = manager.borrowItem(itemId, userId);
        if (success) {
            FileHandler.saveData(manager.getInventory());
        }
        return success;
    }

    public boolean processReturn(String itemId) {
        if (itemId.isEmpty()) return false;
        boolean success = manager.returnItem(itemId);
        if (success) {
            FileHandler.saveData(manager.getInventory());
        }
        return success;
    }
}
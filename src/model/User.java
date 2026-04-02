package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String userId;
    private String name;
    private String email;
    private String role;
    private List<String> borrowedItemIds;

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = "STUDENT"; // Default role
        this.borrowedItemIds = new ArrayList<>();
    }

    public void addBorrowedItem(String itemId) {
        if (!borrowedItemIds.contains(itemId)) {
            borrowedItemIds.add(itemId);
        }
    }

    public void removeBorrowedItem(String itemId) {
        borrowedItemIds.remove(itemId);
    }

    public int getCurrentBorrows() { return borrowedItemIds.size(); }
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public List<String> getBorrowedItemIds() { return borrowedItemIds; }

    @Override
    public String toString() {
        return name + " (" + userId + ")";
    }
}
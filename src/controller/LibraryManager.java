package controller;
import model.*;
import utils.*;
import java.util.*;

public class LibraryManager {
    private List<LibraryItem> inventory = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Map<String, Queue<String>> reservations = new HashMap<>();
    private Stack<String> undoStack = new Stack<>();
    private LibraryItem[] mostFrequentCache = new LibraryItem[5];

    public LibraryManager() {
        // Sample Data for testing
        inventory.add(new Book("B001", "Java Programming", "Deitel", 2024, "Education"));
        inventory.add(new Book("B002", "Data Structures", "Weiss", 2023, "Tech"));
        inventory.add(new Book("B003", "Algorithms", "Sedgewick", 2022, "Tech"));
        inventory.add(new Magazine("M001", "Tech Monthly", "Tech Publisher", 2024, "Technology"));
        inventory.add(new Magazine("M002", "Science Weekly", "Science Publisher", 2023, "Science"));
        inventory.add(new Journal("J001", "AI Research", "AI Institute", 2024, "Science"));
        inventory.add(new Journal("J002", "Quantum Computing", "Quantum Labs", 2023, "Science"));
    }

    public boolean borrowItem(String itemId, String userId) {
        LibraryItem item = findItemById(itemId);
        if (item != null && item.getStatus().equals("Available")) {
            item.setStatus("Borrowed");
            item.setBorrowedBy(userId);
            item.incrementBorrowCount();
            updateCache(item);
            undoStack.push("BORROW:" + itemId);
            return true;
        } else if (item != null) {
            reservations.computeIfAbsent(itemId, k -> new LinkedList<>()).add(userId);
            return false;
        }
        return false;
    }

    public boolean returnItem(String itemId) {
        LibraryItem item = findItemById(itemId);
        if (item != null) {
            item.setStatus("Available");
            item.setBorrowedBy("None");
            
            if (reservations.containsKey(itemId) && !reservations.get(itemId).isEmpty()) {
                System.out.println("Reserved user notified: " + reservations.get(itemId).poll());
            }
            return true;
        }
        return false;
    }

    public void undoLastAction() {
        if (undoStack.isEmpty()) return;
        String[] parts = undoStack.pop().split(":");
        if (parts[0].equals("BORROW")) {
            returnItem(parts[1]);
        } else if (parts[0].equals("ADD_USER")) {
            users.remove(users.size() - 1);
        }
    }

    public void addUser(String name, String email) {
        String id = utils.IDGenerator.generateUserID();
        users.add(new User(id, name, email));
        undoStack.push("ADD_USER:" + id);
    }

    private void updateCache(LibraryItem item) {
        for (int i = 4; i > 0; i--) {
            mostFrequentCache[i] = mostFrequentCache[i - 1];
        }
        mostFrequentCache[0] = item;
    }

    public String[] getFixedCache() {
        return Arrays.stream(mostFrequentCache)
                .filter(Objects::nonNull)
                .map(LibraryItem::getTitle)
                .toArray(String[]::new);
    }

    public LibraryItem findItemById(String id) {
        return inventory.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst().orElse(null);
    }

    // COMPLETED: Fixed the recursive search Exception
    public int recursiveSearch(String query, int index) {
        if (index >= inventory.size()) return -1;
        if (inventory.get(index).getTitle().toLowerCase().contains(query.toLowerCase())) return index;
        return recursiveSearch(query, index + 1);
    }

    // --- Persistence Handlers ---
    public void saveData() {
        utils.FileHandler.saveData(inventory);
    }

    public void loadFromFile() {
        List<LibraryItem> loaded = utils.FileHandler.loadData();
        if(!loaded.isEmpty()) {
            this.inventory = loaded;
        }
    }

    public List<LibraryItem> getInventory() { return inventory; }
    public List<User> getUsers() { return users; }
    public String getUndoStackSize() { return String.valueOf(undoStack.size()); }
}
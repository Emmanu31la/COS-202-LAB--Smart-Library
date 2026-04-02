package controller;
import model.LibraryItem;
import java.util.*;

/**
 * Holds static utility methods for searching and sorting the library inventory.
 */
public class SearchEngine {

    public static LibraryItem linearSearch(List<LibraryItem> inventory, String query) {
        for (LibraryItem item : inventory) {
            if (item.getTitle().toLowerCase().contains(query.toLowerCase())) return item;
        }
        return null;
    }

    public static void selectionSortByYear(List<LibraryItem> inventory) {
        int n = inventory.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (inventory.get(j).getYear() < inventory.get(minIdx).getYear()) {
                    minIdx = j;
                }
            }
            LibraryItem temp = inventory.get(minIdx);
            inventory.set(minIdx, inventory.get(i));
            inventory.set(i, temp);
        }
    }

    // COMPLETED: Implemented Merge Sort for Title sorting
    public static void mergeSort(List<LibraryItem> inventory) {
        if (inventory.size() < 2) return;
        int mid = inventory.size() / 2;
        
        List<LibraryItem> left = new ArrayList<>(inventory.subList(0, mid));
        List<LibraryItem> right = new ArrayList<>(inventory.subList(mid, inventory.size()));
        
        mergeSort(left);
        mergeSort(right);
        merge(inventory, left, right);
    }

    private static void merge(List<LibraryItem> inventory, List<LibraryItem> left, List<LibraryItem> right) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getTitle().compareToIgnoreCase(right.get(j).getTitle()) <= 0) {
                inventory.set(k++, left.get(i++));
            } else {
                inventory.set(k++, right.get(j++));
            }
        }
        while (i < left.size()) inventory.set(k++, left.get(i++));
        while (j < right.size()) inventory.set(k++, right.get(j++));
    }

    // COMPLETED: Binary Search (returns index, assumes list is sorted)
    public static int binarySearchByTitle(List<LibraryItem> inventory, String query) {
        int low = 0;
        int high = inventory.size() - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int res = query.compareToIgnoreCase(inventory.get(mid).getTitle());
            
            if (res == 0) return mid;
            if (res > 0) low = mid + 1;
            else high = mid - 1;
        }
        return -1; // Not found
    }
}
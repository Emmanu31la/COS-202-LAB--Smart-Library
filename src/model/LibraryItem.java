package model;
import java.io.Serializable;

/**
 * Abstract base class for all library resources.
 * Implements Serializable for file saving and Comparable for sorting.
 */
public abstract class LibraryItem implements Serializable, Comparable<LibraryItem>, Borrowable {
    private String id, title, author, category, status, borrowedBy;
    private int year, borrowCount;

    public LibraryItem(String id, String title, String author, int year, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.category = category;
        this.status = "Available";
        this.borrowedBy = "None";
        this.borrowCount = 0;
    }

    public void incrementBorrowCount() {
        this.borrowCount++;
    }

    @Override
    public int compareTo(LibraryItem other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    // --- Borrowable Interface Implementation ---
    @Override
    public void setBorrowedBy(String userId) {
        this.borrowedBy = (userId == null || userId.isEmpty()) ? "None" : userId;
    }

    @Override
    public String getBorrowedBy() { return borrowedBy; }

    @Override
    public void setStatus(String status) { this.status = status; }

    @Override
    public String getStatus() { return status; }
    // -------------------------------------------

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public int getYear() { return year; }
    public int getBorrowCount() { return borrowCount; }

    // Abstract method to force subclasses to define their type
    public abstract String getType();

    @Override
    public String toString() {
        return String.format("[%s] %s by %s (%d)", getType(), title, author, year);
    }
}
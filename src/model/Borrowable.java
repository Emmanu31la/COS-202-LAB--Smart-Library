package model;

/**
 * Interface defining the borrowing behavior for library items.
 */
public interface Borrowable {
    void setBorrowedBy(String userId);
    String getBorrowedBy();
    void setStatus(String status);
    String getStatus();
}
// Tells Java that this file lives inside the 'model' folder
package model;

public abstract class LibraryItem {
    // --- ENCAPSULATION ---
    // We use 'private' so other parts of the program can't mess with these values directly.
    private String id;
    private String title;
    private String author;
    private int publicationYear;
    private boolean isBorrowed;

    /**
     * CONSTRUCTOR
     * This is the setup method. When we create a new book or magazine 
     * this method automatically runs to assign the initial values.
    */

    public LibraryItem(String id, String title, String author, int publicationYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        // A new item is never borrowed by default when first added to the library
        this.isBorrowed = false;
    }

    // --- GETTERS & SETTERS ---
    // Because our variables are private, we need these public methods 
    // to safely read (get) or update (set) the data.


    public String getId () {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    /*
     * POLYMORPHISM PREPARATION
     * This is an abstract method. 
     * It forces every subclass (like Book or Magazine) to write their own specific version of this method.
    */

    public abstract String getDetails();
}

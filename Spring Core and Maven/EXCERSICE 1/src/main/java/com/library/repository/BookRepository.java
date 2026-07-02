package com.library.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * BookRepository class handles data persistence operations for books
 */
public class BookRepository {
    
    private String repositoryName;
    private List<String> books;

    /**
     * Constructor
     */
    public BookRepository() {
        this.books = new ArrayList<>();
    }

    /**
     * Getter for repository name
     */
    public String getRepositoryName() {
        return repositoryName;
    }

    /**
     * Setter for repository name (used for dependency injection)
     */
    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    /**
     * Add a book to the repository
     */
    public void addBook(String bookName) {
        books.add(bookName);
        System.out.println("Book added: " + bookName);
    }

    /**
     * Get all books from the repository
     */
    public List<String> getAllBooks() {
        return new ArrayList<>(books);
    }

    /**
     * Get book count
     */
    public int getBookCount() {
        return books.size();
    }

    /**
     * Search for a book by name
     */
    public boolean searchBook(String bookName) {
        return books.contains(bookName);
    }

    /**
     * Display repository information
     */
    public void displayInfo() {
        System.out.println("Repository: " + repositoryName);
        System.out.println("Total Books: " + books.size());
    }
}

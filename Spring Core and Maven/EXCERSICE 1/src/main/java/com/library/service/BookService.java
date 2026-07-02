package com.library.service;

import com.library.repository.BookRepository;
import java.util.List;

/**
 * BookService class provides business logic for book management
 */
public class BookService {
    
    private BookRepository bookRepository;

    /**
     * Constructor
     */
    public BookService() {
    }

    /**
     * Getter for BookRepository
     */
    public BookRepository getBookRepository() {
        return bookRepository;
    }

    /**
     * Setter for BookRepository (used for dependency injection)
     */
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Add a new book
     */
    public void addBook(String bookName) {
        if (bookName != null && !bookName.isEmpty()) {
            bookRepository.addBook(bookName);
        } else {
            System.out.println("Invalid book name!");
        }
    }

    /**
     * Get all available books
     */
    public List<String> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    /**
     * Get the total number of books
     */
    public int getTotalBooks() {
        return bookRepository.getBookCount();
    }

    /**
     * Search for a specific book
     */
    public boolean searchBook(String bookName) {
        return bookRepository.searchBook(bookName);
    }

    /**
     * Display service information
     */
    public void displayServiceInfo() {
        System.out.println("=== Book Service Information ===");
        bookRepository.displayInfo();
        List<String> books = getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books available in the repository.");
        } else {
            System.out.println("Available Books:");
            for (String book : books) {
                System.out.println("  - " + book);
            }
        }
        System.out.println("================================\n");
    }
}

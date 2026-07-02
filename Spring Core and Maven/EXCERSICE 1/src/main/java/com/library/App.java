package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main Application class to load Spring context and test the configuration
 */
public class App {
    
    public static void main(String[] args) {
        
        System.out.println("========================================");
        System.out.println("Library Management System - Spring Demo");
        System.out.println("========================================\n");

        // Load Spring ApplicationContext from XML configuration
        System.out.println("Loading Spring ApplicationContext...");
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("Spring context loaded successfully!\n");

        // Retrieve BookService bean from the context
        BookService bookService = (BookService) context.getBean("bookService");
        
        // Test the service
        System.out.println("Testing BookService and BookRepository beans:\n");

        // Add some books
        bookService.addBook("Java Programming");
        bookService.addBook("Spring in Action");
        bookService.addBook("Effective Java");
        bookService.addBook("Design Patterns");
        
        System.out.println();

        // Display service information
        bookService.displayServiceInfo();

        // Search for a book
        System.out.println("Searching for 'Spring in Action': " + 
                           bookService.searchBook("Spring in Action"));
        System.out.println("Searching for 'Python Basics': " + 
                           bookService.searchBook("Python Basics"));
        
        System.out.println();

        // Display all books
        System.out.println("=== All Available Books ===");
        for (String book : bookService.getAllBooks()) {
            System.out.println("  ✓ " + book);
        }
        System.out.println("Total books: " + bookService.getTotalBooks());
        
        System.out.println("\n========================================");
        System.out.println("Application Test Completed Successfully!");
        System.out.println("========================================");
    }
}

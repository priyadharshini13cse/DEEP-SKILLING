package com.example;

public class BookService {
    private BookRepository bookRepository;

    public BookService() {
    }

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void displayBooks() {
        System.out.println("BookService using repository: " + bookRepository.fetchBooks());
    }
}

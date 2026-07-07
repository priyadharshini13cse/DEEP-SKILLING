package com.example.library.service;

import com.example.library.repository.BookRepository;

public class BookService {

    private BookRepository bookRepository;

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void printBookDetails() {
        System.out.println("BookService: " + bookRepository.getBookInfo());
    }
}

package com.bikalp.library.Service.Implementation;

import com.bikalp.library.Exception.BookNotFoundException;
import com.bikalp.library.Model.Book;
import com.bikalp.library.Repository.BookRepo;
import com.bikalp.library.Service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public Book addBook(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public Book updateBook(Long id, Book newBook) {

        Book existingBook = bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        existingBook.setTitle(newBook.getTitle());
        existingBook.setAuthor(newBook.getAuthor());
        existingBook.setQuantity(newBook.getQuantity());
        existingBook.setAvailable(newBook.getAvailable());
        return existingBook;
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    public boolean deleteBookById(Long id) {
        bookRepo.deleteById(id);
        return true;
    }
}

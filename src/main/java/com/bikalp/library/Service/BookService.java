package com.bikalp.library.Service;

import com.bikalp.library.Model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    Book addBook(Book book);

    Book updateBook(Long id, Book book);

    Book getBookById(Long id);

    List<Book> getAllBooks();

    boolean deleteBookById(Long id);
}

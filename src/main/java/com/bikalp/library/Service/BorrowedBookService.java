package com.bikalp.library.Service;

import com.bikalp.library.Model.BorrowedBook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BorrowedBookService {

    BorrowedBook borrowedBook(Long userId, Long bookId);

    BorrowedBook returnBook(Long userId, Long bookId);

    List<BorrowedBook> getAllBorrowedBooks();

    List<BorrowedBook> getBorrowedBooksByUserId(Long userId);

}

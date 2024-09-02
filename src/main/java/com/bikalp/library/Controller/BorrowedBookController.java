package com.bikalp.library.Controller;

import com.bikalp.library.Exception.BookNotFoundException;
import com.bikalp.library.Exception.UserNotFoundException;
import com.bikalp.library.Model.BorrowedBook;
import com.bikalp.library.Service.BorrowedBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowed-book")
public class BorrowedBookController {

    private final BorrowedBookService borrowedBookService;

    public BorrowedBookController(BorrowedBookService borrowedBookService) {
        this.borrowedBookService = borrowedBookService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        try {
            BorrowedBook borrowedBook = borrowedBookService.borrowedBook(userId, bookId);
            return new ResponseEntity<>(borrowedBook, HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>("Book not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    @PostMapping("/return")
    public ResponseEntity<BorrowedBook> returnBook(@RequestParam Long userId, @RequestParam Long bookId) {
        try {
            BorrowedBook returnedBook = borrowedBookService.returnBook(userId, bookId);
            return new ResponseEntity<>(returnedBook, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<List<BorrowedBook>> getAllBorrowedBooks() {
        List<BorrowedBook> borrowedBooks = borrowedBookService.getAllBorrowedBooks();
        return new ResponseEntity<>(borrowedBooks, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user-id")
    public ResponseEntity<List<BorrowedBook>> getBorrowedBooksByUserId(@RequestParam Long id) {
        List<BorrowedBook> borrowedBooks = borrowedBookService.getBorrowedBooksByUserId(id);
        return new ResponseEntity<>(borrowedBooks, HttpStatus.OK);
    }
}

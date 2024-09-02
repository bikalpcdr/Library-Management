package com.bikalp.library.Service.Implementation;

import com.bikalp.library.Exception.BookNotFoundException;
import com.bikalp.library.Exception.UserNotFoundException;
import com.bikalp.library.Model.Book;
import com.bikalp.library.Model.BorrowedBook;
import com.bikalp.library.Model.Users;
import com.bikalp.library.Repository.BookRepo;
import com.bikalp.library.Repository.BorrowedBookRepo;
import com.bikalp.library.Repository.UsersRepo;
import com.bikalp.library.Service.BorrowedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowedBookServiceImpl implements BorrowedBookService {

    private final BorrowedBookRepo borrowedBookRepo;
    private final UsersRepo usersRepo;
    private final BookRepo bookRepo;

    public BorrowedBookServiceImpl(BorrowedBookRepo borrowedBookRepo, UsersRepo usersRepo, BookRepo bookRepo) {
        this.borrowedBookRepo = borrowedBookRepo;
        this.usersRepo = usersRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    public BorrowedBook borrowedBook(Long userId, Long bookId) {
        Optional<Book> existingBook = bookRepo.findById(bookId);
        Optional<Users> existingUser = usersRepo.findById(userId);

        if (existingBook.isEmpty()) {
            throw new BookNotFoundException(bookId);
        }

        if (existingUser.isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        Book book = existingBook.get();
        if (!book.getAvailable() || book.getQuantity() <= 0) {
            throw new RuntimeException("Book is not available for borrowing");
        }

        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBook(book);
        borrowedBook.setUsers(existingUser.get());
        borrowedBook.setBorrowDate(LocalDate.now());
        borrowedBook.setDueDate(LocalDate.now().plusWeeks(2));

        borrowedBookRepo.save(borrowedBook);

        book.setQuantity(book.getQuantity() - 1);
        book.setAvailable(book.getQuantity() > 0);
        bookRepo.save(book);

        return borrowedBook;
    }


    @Override
    public BorrowedBook returnBook(Long userId, Long bookId) {
        Optional<BorrowedBook> borrowedBookOptional = borrowedBookRepo.findByUsersIdAndBookId(userId, bookId);

        if (borrowedBookOptional.isEmpty()) {
            throw new RuntimeException("Borrowed book not found for user id: " + userId + " and book id: " + bookId);
        }

        BorrowedBook borrowedBook = borrowedBookOptional.get();
        borrowedBook.setReturnDate(LocalDate.now());

        borrowedBookRepo.save(borrowedBook);

        Book book = borrowedBook.getBook();
        book.setQuantity(book.getQuantity() + 1);
        book.setAvailable(true);
        bookRepo.save(book);

        return borrowedBook;
    }

    @Override
    public List<BorrowedBook> getAllBorrowedBooks() {
        return borrowedBookRepo.findAll();
    }

    @Override
    public List<BorrowedBook> getBorrowedBooksByUserId(Long userId) {
        return borrowedBookRepo.findByUsersId(userId);
    }
}
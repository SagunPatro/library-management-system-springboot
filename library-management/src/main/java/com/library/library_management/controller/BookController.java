package com.library.library_management.controller;

import com.library.library_management.model.Book;
import com.library.library_management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository repository;

    // GET all books
    @GetMapping
    public List<Book> getBooks() {
        return repository.findAll();
    }

    // POST add new book
    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return repository.save(book);
    }

    // GET book by ID
    @GetMapping("/{id}")
    public Object getBookById(@PathVariable int id) {

        Optional<Book> optionalBook = repository.findById(id);

        if(optionalBook.isPresent()) {
            return optionalBook.get();
        }

        return "Book not found";
    }

    // GET book by title
    @GetMapping("/title/{title}")
    public Object getBookByTitle(@PathVariable String title) {

        Book book = repository.findByTitle(title);

        if(book != null) {
            return book;
        }

        return "Book not found";
    }

    // DELETE book
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {

        if(repository.existsById(id)) {

            repository.deleteById(id);

            return "Book deleted successfully";
        }

        return "Book not found";
    }

    // ISSUE book
    @PutMapping("/{id}/issue")
    public String issueBook(@PathVariable int id) {

        Optional<Book> optionalBook = repository.findById(id);

        if(optionalBook.isPresent()) {

            Book book = optionalBook.get();

            if(book.isIssued()) {
                return "Book already issued";
            }

            book.setIssued(true);

            repository.save(book);

            return "Book issued successfully";
        }

        return "Book not found";
    }

    // RETURN book
    @PutMapping("/{id}/return")
    public String returnBook(@PathVariable int id) {

        Optional<Book> optionalBook = repository.findById(id);

        if(optionalBook.isPresent()) {

            Book book = optionalBook.get();

            if(!book.isIssued()) {
                return "Book is already available";
            }

            book.setIssued(false);

            repository.save(book);

            return "Book returned successfully";
        }

        return "Book not found";
    }
}
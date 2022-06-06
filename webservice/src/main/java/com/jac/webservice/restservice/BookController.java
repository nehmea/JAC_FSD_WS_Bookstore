package com.jac.webservice.restservice;

import com.jac.webservice.model.Book;
import com.jac.webservice.service.BookService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin(maxAge = 60000)
public class BookController {

    @Autowired
    public BookService service;

    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            return new ResponseEntity<>(service.getAllBooks(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(String.format("Unable to retrieve books from the database %n" +
                            "Root Error Message: %s",
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/book/id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") int id) {
        try {
            return new ResponseEntity<>(service.getBookById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(String.format("Unable to retrieve book (id=%d) from the database %n" +
                            "Root Error Message: %s",
                    id,
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/book/isbn/{isbn}")
    public ResponseEntity<Book> getBookByISBN(@PathVariable(value = "isbn") String isbn) {
        try {
            return new ResponseEntity<>(service.getBookByISBN(isbn), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(String.format("Unable to retrieve book (isbn=%s) from the database %n" +
                            "Root Error Message: %s",
                    isbn,
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/Author/{authorName}/books")
    public ResponseEntity<List<Book>> getBookByAuthor(@PathVariable(value = "authorName") String authorName) {
        try {
            return new ResponseEntity<>(service.getBookByAuthorName(authorName), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(String.format("Unable to retrieve book (Author includes: %s) from the database %n" +
                            "Root Error Message: %s",
                    authorName,
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/book/id/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable(value = "id") int id, @RequestBody Book book) {
        try {
            return new ResponseEntity<>(service.updateBookById(id, book), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(String.format("Unable to update book (id=%d) in the database %n" +
                            "Root Error Message: %s",
                    id,
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/book/isbn/{isbn}")
    public ResponseEntity<Book> updateBookById(@PathVariable(value = "isbn") String isbn, @RequestBody Book book) {
        try {
            return new ResponseEntity<>(service.updateBookByISBN(isbn, book), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(String.format("Unable to update book (isbn=%s) in the database %n" +
                            "Root Error Message: %s",
                    isbn,
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        try {
            return new ResponseEntity<>(service.saveBook(book), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(String.format(
                    "Unable to save new book into the database %n" +
                            "Root Error Message: %s",
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/book/id/{id}")
    public ResponseEntity deleteBookById(@PathVariable(value = "id") int id) {
        try {
            Book fetchedBook = service.getBookById(id);
            service.deleteBookById(id);
            return new ResponseEntity("Book has been deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(String.format("Unable to DELETE book (id=%d) from the database %n" +
                            "Root Error Message: %s",
                    id,
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/book/isbn/{isbn}")
    public ResponseEntity<String> deleteBookByISBN(@PathVariable(value = "isbn") String isbn) {
        try {
            Book fetchedBook = service.getBookByISBN(isbn);
            service.deleteBookByISBN(isbn);
            return new ResponseEntity("Book has been deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(String.format("Unable to DELETE book (isbn=%s) from the database %n" +
                            "Root Error Message: %s",
                    isbn,
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }
}

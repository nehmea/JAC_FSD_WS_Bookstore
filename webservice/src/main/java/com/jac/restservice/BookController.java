package com.jac.restservice;

import com.jac.exceptions.DatabaseException;
import com.jac.exceptions.ItemExistException;
import com.jac.exceptions.RecordDoesNotExistInDatabaseException;
import com.jac.model.Book;
import com.jac.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    public BookService service;

    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            return new ResponseEntity<>(service.getAllBooks(), HttpStatus.OK);
        }
        catch (DataRetrievalFailureException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/book/id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") int id) {
        try{
            return new ResponseEntity<>(service.getBookById(id), HttpStatus.OK);
        }
        catch (DataRetrievalFailureException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/book/isbn/{isbn}")
    public ResponseEntity<Book> getBookByISBN(@PathVariable(value = "isbn") String isbn) {
        try{
            return new ResponseEntity<>(service.getBookByISBN(isbn), HttpStatus.OK);
        }
        catch (DataRetrievalFailureException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/Author/{authorName}/books")
    public ResponseEntity<List<Book>> getBookByAuthor(@PathVariable(value = "authorName") String authorName) {
        try{
            return new ResponseEntity<>(service.getBookByAuthorName(authorName), HttpStatus.OK);
        }
        catch (DataRetrievalFailureException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/book/id/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable(value = "id") int id, Book book) {
        try{
            return new ResponseEntity<>(service.updateBookById(id, book), HttpStatus.OK);
        }
        catch (IllegalAccessException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (RecordDoesNotExistInDatabaseException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/book/isbn/{isbn}")
    public ResponseEntity<Book> updateBookById(@PathVariable(value = "isbn") String isbn, Book book) {
        try{
            return new ResponseEntity<>(service.updateBookByISBN(isbn, book), HttpStatus.OK);
        }
        catch (IllegalAccessException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (RecordDoesNotExistInDatabaseException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        try{
            return new ResponseEntity<>(service.saveBook(book), HttpStatus.OK);
        }
        catch (ItemExistException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
        }
        catch (DatabaseException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/book/id/{id}")
    public ResponseEntity deleteBookById(@PathVariable(value = "id") int id) {
        try{
            service.deleteBookById(id);
            return new ResponseEntity("Book has been deleted", HttpStatus.OK);
        }
        catch (DatabaseException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/book/isbn/{isbn}")
    public ResponseEntity deleteBookByISBN(@PathVariable(value = "isbn") String isbn) {
        try{
            service.deleteBookByISBN(isbn);
            return new ResponseEntity("Book has been deleted", HttpStatus.OK);
        }
        catch (DatabaseException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

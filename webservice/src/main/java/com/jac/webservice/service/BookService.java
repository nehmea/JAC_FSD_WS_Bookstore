package com.jac.webservice.service;

import com.jac.webservice.exceptions.DatabaseException;
import com.jac.webservice.model.Book;
import com.jac.webservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    //    get all books
    public List<Book> getAllBooks() {
        return repository.getAllBooks();
    }

    //    get book by id
    public Book getBookById(int id) {
      return repository.getBookById(id);

    }

    //    get book by isbn
    public Book getBookByISBN(String isbn) {
            return repository.getBookByISBN(isbn);
    }

    //    get book by authorName
    public List<Book> getBookByAuthorName(String authorName) {
            return repository.getBookByAuthorName(authorName);
    }

    //    update book by id
    public Book updateBookById(int id, Book book) throws IllegalAccessException {
        return repository.updateBookById(id, book);
    }

    //    update book by isbn
    public Book updateBookByISBN(String isbn, Book book) throws IllegalAccessException {
        return repository.updateBookByISBN(isbn, book);
    }

    //    save a new book
    public Book saveBook(Book book) {
        return repository.saveBook(book);

    }

    //    delete a book by id
    public void deleteBookById(int id) {
        repository.deleteBookById(id);
    }

    //    delete a book by isbn
    public void deleteBookByISBN(String isbn) {
        repository.deleteBookByISBN(isbn);
    }
}

package com.jac.repository;

import com.jac.exceptions.DatabaseException;
import com.jac.exceptions.RecordDoesNotExistInDatabaseException;
import com.jac.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.security.sasl.AuthorizeCallback;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.*;

@Repository
public class BookRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    BookRowMapper bookRowMapper;

    //  A function that returns all books in the database
    public List<Book> getAllBooks() {

        try {
            String sql = "SELECT * FROM books";
            return jdbcTemplate.query(sql, new BookRowMapper());

        } catch (Exception exception) {
            throw new DataRetrievalFailureException("An Exception occurred in BookRepository.getAllBooks()");
        }
    }

    //  A function that returns a book from the database by its id
    public Book getBookById(int id) {
        try {
            String sql = "SELECT * FROM books WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, bookRowMapper, id);
        } catch (Exception exc) {
            throw new DataRetrievalFailureException("An Exception occurred in BookRepository.getBookById" + id);
        }
    }

    //  A function that returns a book from the database by its isbn
    public Book getBookByISBN(String isbn) {
        try {

            String sql = (isbn.length() == 13) ? "SELECT * FROM books WHERE isbn13 = ?" : "SELECT * FROM books WHERE isbn10 = ?";
            return jdbcTemplate.queryForObject(sql, bookRowMapper, isbn);
        } catch (Exception exc) {
            throw new DataRetrievalFailureException("An Exception occurred in BookRepository.getBookByISBN" + isbn);
        }
    }

    //  A function that returns a book from the database by an author name
    public Book getBookByAuthorName(String authorName) {
        try {

            String sql = "SELECT * FROM books WHERE authors LIKE '%?%'";
            return jdbcTemplate.queryForObject(sql, bookRowMapper, authorName);
        } catch (Exception exc) {
            throw new DataRetrievalFailureException("An Exception occurred in BookRepository.getBookByAuthorName" + authorName);
        }
    }


    //  A function that updates a book in the database by its id
    public Book updateBookById(int id, Book book) throws IllegalAccessException {
        Book fetchedBook = getBookById(id);

        if(fetchedBook == null) {
            throw new RecordDoesNotExistInDatabaseException("Failed to retrieve a Loan with id " + id);
        }

        // returns the array of Field objects
        for (Field f : book.getClass().getFields()) {
            f.setAccessible(true);
            if (f.get(book) == null) {
                f.set(book, f.get(fetchedBook));
            }
        }

        try {
            jdbcTemplate.update(
                    "UPDATE books set " +
                            "isbn10=?," +
                            "title=?" +
                            "language=?"+
                            "binding=?," +
                            "release_date=? " +
                            "edition=? " +
                            "page=? " +
                            "dimensions=? " +
                            "rating=? " +
                            "publisher=? " +
                            "authors=? " +
                            "copies=? " +
                            "WHERE id=?",
                    book.getIsbn10(), book.getTitle(), book.getLanguage(), book.getBinding(), Date.valueOf(book.getRelease_date()),  book.getEdition(), book.getPages(), book.getDimensions(), book.getRating(), book.getPublisher(), book.getAuthors(), book.getNumberOfCopies(), id);
            return getBookById(id);
        } catch (Exception exc) {
            throw new DatabaseException("An exception occurred in updateBook " + id);
        }
    }

    //  A function that updates a book in the database by its isbn
    public Book updateBookByISBN(String isbn, Book book) throws IllegalAccessException {
        Book fetchedBook = getBookByISBN(isbn);

        if(fetchedBook == null) {
            throw new RecordDoesNotExistInDatabaseException("Failed to retrieve a Loan with id " + isbn);
        }

        // returns the array of Field objects
        for (Field f : book.getClass().getFields()) {
            f.setAccessible(true);
            if (f.get(book) == null) {
                f.set(book, f.get(fetchedBook));
            }
        }

        try {
            jdbcTemplate.update(
                    "UPDATE books set " +
                            "isbn10=?," +
                            "title=?" +
                            "language=?"+
                            "binding=?," +
                            "release_date=? " +
                            "edition=? " +
                            "page=? " +
                            "dimensions=? " +
                            "rating=? " +
                            "publisher=? " +
                            "authors=? " +
                            "copies=? " +
                            "WHERE isbn13=?",
                    book.getIsbn10(), book.getTitle(), book.getLanguage(), book.getBinding(), Date.valueOf(book.getRelease_date()),  book.getEdition(), book.getPages(), book.getDimensions(), book.getRating(), book.getPublisher(), book.getAuthors(), book.getNumberOfCopies(), isbn);
            return getBookByISBN(isbn);
        } catch (Exception exc) {
            throw new DatabaseException("An exception occurred in updateBook " + isbn);
        }
    }

    //  A function that saves a new book in the database
    public Book saveBook(Book book) {
        try {
            jdbcTemplate.update("Insert into books " +
                            "(isbn13, isbn10, title, language, binding, release_date, edition, page, dimensions, rating, publisher, authors, copies, " +
                            "values (?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    book.getIsbn13(), book.getIsbn10(), book.getTitle(), book.getLanguage(), book.getBinding(), Date.valueOf(book.getRelease_date()),  book.getEdition(), book.getPages(), book.getDimensions(), book.getRating(), book.getPublisher(), book.getAuthors(), book.getNumberOfCopies());

            return getBookByISBN(book.getIsbn13());
        } catch (Exception exc) {
            throw new DatabaseException("An exception occurred in saveBook");
        }
    }

    //  A function that deletes a book from the database by its id
    public void deleteBookById(int id) {
        try {
            jdbcTemplate.update("delete from books where id=?", id);
        }
        catch (Exception exc) {
            throw new DatabaseException("an exception occurred in deleteBookById " + id);
        }
    }

    //  A function that deletes a book from the database by its isbn
    public void deleteBookByISBN(String isbn) {
        String sql = (isbn.length() == 13) ? "DELETE FROM books WHERE isbn13 = ?" : "DELETE FROM books WHERE isbn10 = ?";
        try {
            jdbcTemplate.update(sql, isbn);
        }
        catch (Exception exc) {
            throw new DatabaseException("an exception occurred in deleteBookByISBN " + isbn);
        }
    }
}

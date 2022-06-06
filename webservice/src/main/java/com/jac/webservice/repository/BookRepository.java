package com.jac.webservice.repository;

import com.jac.webservice.exceptions.DatabaseException;
import com.jac.webservice.exceptions.ItemExistException;
import com.jac.webservice.exceptions.RecordDoesNotExistInDatabaseException;
import com.jac.webservice.model.Book;
import com.jac.webservice.model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.List;

@Repository
public class BookRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    BookRowMapper bookRowMapper;

    //  A function that returns all books in the database
    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, bookRowMapper);
    }

    //  A function that returns a book from the database by its id
    public Book getBookById(int id) {
        if (!checkIfBookExistByID(id)) {
            throw new RecordDoesNotExistInDatabaseException("A book with id=" + id + " does not exist in the database!");
        }
        String sql = "SELECT * FROM books WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, bookRowMapper, id);
    }

    //  A function that returns a book from the database by its isbn
    public Book getBookByISBN(String isbn) {
        if (!checkIfBookExistByISBN(isbn)) {
            throw new RecordDoesNotExistInDatabaseException("A book with isbn=" + isbn + " does not exist in the database!");
        }
        String sql = (isbn.length() == 13) ? "SELECT * FROM books WHERE isbn13 = ?" : "SELECT * FROM books WHERE isbn10 = ?";
        return jdbcTemplate.queryForObject(sql, bookRowMapper, isbn);
    }

    //  A function that returns books from the database by an author name
    public List<Book> getBookByAuthorName(String authorName) {
        if (!checkIfBookExistByAuthor(authorName)) {
            throw new RecordDoesNotExistInDatabaseException("A book with Author '" + authorName + "' does not exist in the database!");
        }
        String sql = "SELECT * FROM books WHERE authors LIKE ?";
        return jdbcTemplate.query(sql, bookRowMapper, "%" + authorName + "%");
    }

    //  A function that updates a book in the database by its id
    public Book updateBookById(int id, Book book) throws IllegalAccessException {

        if (!checkIfBookExistByID(id)) {
            throw new RecordDoesNotExistInDatabaseException("Failed to retrieve a Loan with id=" + id);
        }

        Book fetchedBook = getBookById(id);

        if (book.getIsbn13() != null && !book.getIsbn13().equals(fetchedBook.getIsbn13())) {
            throw new DatabaseException(
                    String.format("The provided ISBN (%s) is different from the ISBN (%s) in the database!",
                            book.getIsbn13(), fetchedBook.getIsbn13())
            );
        }

        // returns the array of Field objects
        for (Field f : book.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(book) == null) {
                f.set(book, f.get(fetchedBook));
            }
        }

        jdbcTemplate.update(
                "UPDATE books set " +
                        "title=?, " +
                        "language=?, " +
                        "binding=?, " +
                        "release_date=?, " +
                        "edition=?, " +
                        "pages=?, " +
                        "dimensions=?, " +
                        "rating=?, " +
                        "publisher=?, " +
                        "authors=? , " +
                        "copies=? " +
                        "WHERE id=?",
                book.getTitle(), book.getLanguage(), book.getBinding(),
                (book.getRelease_date() == null) ? book.getRelease_date() : Date.valueOf(book.getRelease_date()),
                book.getEdition(), book.getPages(), book.getDimensions(), book.getRating(), book.getPublisher(), book.getAuthors(), book.getNumberOfCopies(), id);
        return getBookById(id);

    }

    //  A function that updates a book in the database by its isbn
    public Book updateBookByISBN(String isbn, Book book) throws IllegalAccessException {

        if (!checkIfBookExistByISBN(isbn)) {
            throw new RecordDoesNotExistInDatabaseException("Failed to retrieve a Book with ISBN=" + isbn);
        }

        Book fetchedBook = getBookByISBN(isbn);

        // replace empty fields with those from database record
        for (Field f : book.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(book) == null) {
                f.set(book, f.get(fetchedBook));
            }
        }

        jdbcTemplate.update(
                "UPDATE books set " +
                        "title=?, " +
                        "language=?, " +
                        "binding=?," +
                        "release_date=?, " +
                        "edition=?, " +
                        "pages=?, " +
                        "dimensions=?, " +
                        "rating=?, " +
                        "publisher=?, " +
                        "authors=?, " +
                        "copies=? " +
                        "WHERE isbn13=?",
                book.getTitle(), book.getLanguage(), book.getBinding(),
                (book.getRelease_date() == null) ? book.getRelease_date() : Date.valueOf(book.getRelease_date()),
                book.getEdition(), book.getPages(), book.getDimensions(), book.getRating(), book.getPublisher(), book.getAuthors(), book.getNumberOfCopies(),
                isbn);
        return getBookByISBN(isbn);

    }

    //  A function that saves a new book in the database
    public Book saveBook(Book book) {

        if (checkIfBookExistByISBN(book.getIsbn13())) {
            throw new ItemExistException("A record already exist with ISBN = " + book.getIsbn13());
        }

        jdbcTemplate.update("Insert into books " +
                        "(isbn13, isbn10, title, language, binding, release_date, edition, pages, dimensions, rating, publisher, authors, copies) " +
                        "values (?,?,?,?,?,?,?,?,?,?,?,?,?)",
                book.getIsbn13(), book.getIsbn10(), book.getTitle(), book.getLanguage(), book.getBinding(),
                (book.getRelease_date() == null) ? book.getRelease_date() : Date.valueOf(book.getRelease_date()),
                book.getEdition(), book.getPages(), book.getDimensions(), book.getRating(), book.getPublisher(), book.getAuthors(), book.getNumberOfCopies());

        return getBookByISBN(book.getIsbn13());

    }

    //  A function that deletes a book from the database by its id
    public void deleteBookById(int id) {
        if (!checkIfBookExistByID(id)) {
            throw new RecordDoesNotExistInDatabaseException("No Book exists with id=" + id);
        }
        jdbcTemplate.update("delete from books where id=?", id);
    }

    //  A function that deletes a book from the database by its isbn
    public void deleteBookByISBN(String isbn) {
        if (!checkIfBookExistByISBN(isbn)) {
            throw new RecordDoesNotExistInDatabaseException("No Book exists with isbn=" + isbn);
        }
        String sql = (isbn.length() == 13) ? "DELETE FROM books WHERE isbn13 = ?" : "DELETE FROM books WHERE isbn10 = ?";
        jdbcTemplate.update(sql, isbn);

    }

    //        check if book exist by id
    public Boolean checkIfBookExistByID(int id) {
        String sql = "SELECT count(*) FROM books WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return count != 0;
    }

    //        check if book exist by isbn
    public Boolean checkIfBookExistByISBN(String isbn) {
        String sql = (isbn.length() == 13) ? "SELECT count(*) FROM books WHERE isbn13 = ?" : "SELECT count(*) FROM books WHERE isbn10 = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, isbn);

        return count != 0;
    }

    public Boolean checkIfBookExistByAuthor(String authorName) {

        String sql = "SELECT count(*) FROM books WHERE authors LIKE ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, "%" + authorName + "%");

        return count != 0;
    }

}

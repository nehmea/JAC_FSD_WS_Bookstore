package com.jac.webservice.repository;

import com.jac.webservice.exceptions.DatabaseException;
import com.jac.webservice.exceptions.ItemExistException;
import com.jac.webservice.exceptions.RecordDoesNotExistInDatabaseException;
import com.jac.webservice.model.Book;
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

        try {
            String sql = "SELECT * FROM books";
            return jdbcTemplate.query(sql, bookRowMapper);

        } catch (Exception exc) {
            throw new DatabaseException(exc.getCause());
        }
    }

    //  A function that returns a book from the database by its id
    public Book getBookById(int id) {
        try {
            String sql = "SELECT * FROM books WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, bookRowMapper, id);
        } catch (Exception exc) {
            throw new DatabaseException(exc.getCause());
        }
    }

    //  A function that returns a book from the database by its isbn
    public Book getBookByISBN(String isbn) {
        try {

            String sql = (isbn.length() == 13) ? "SELECT * FROM books WHERE isbn13 = ?" : "SELECT * FROM books WHERE isbn10 = ?";
            return jdbcTemplate.queryForObject(sql, bookRowMapper, isbn);
        } catch (Exception exc) {
            throw new DatabaseException(exc.getCause());
        }
    }

    //  A function that returns a book from the database by an author name
    public List<Book> getBookByAuthorName(String authorName) {
        try {

            String sql = "SELECT * FROM books WHERE authors LIKE ?";
            return jdbcTemplate.query(sql, bookRowMapper, "%" + authorName + "%");
        } catch (Exception exc) {
            throw new DataRetrievalFailureException("An Exception occurred in BookRepository.getBookByAuthorName " + authorName);
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
                            "title=?, " +
                            "language=?, "+
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
                    (book.getRelease_date() == null) ? book.getRelease_date():Date.valueOf(book.getRelease_date()),
                    book.getEdition(), book.getPages(), book.getDimensions(), book.getRating(), book.getPublisher(), book.getAuthors(), book.getNumberOfCopies(), id);
            return getBookById(id);

        } catch (Exception exc) {
            throw new DatabaseException(exc.getCause());
        }
    }

    //  A function that updates a book in the database by its isbn
    public Book updateBookByISBN(String isbn, Book book) throws IllegalAccessException {

        Book fetchedBook = getBookByISBN(isbn);

        if(fetchedBook == null) {
            throw new RecordDoesNotExistInDatabaseException("Failed to retrieve a Loan with isbn " + isbn);
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
                            "title=?, " +
                            "language=?, "+
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
        } catch (Exception exc) {
            throw new DatabaseException(exc.getCause());
        }
    }

    //  A function that saves a new book in the database
    public Book saveBook(Book book) {
        try {
            Book fetchedBook = getBookByISBN(book.getIsbn13());
            if(fetchedBook != null) {
                throw new ItemExistException("A book with the same ISBN already exists in the repository");
            }
        }
       catch (DatabaseException exception) {
           try {
               jdbcTemplate.update("Insert into books " +
                               "(isbn13, isbn10, title, language, binding, release_date, edition, pages, dimensions, rating, publisher, authors, copies) " +
                               "values (?,?,?,?,?,?,?,?,?,?,?,?,?)",
                       book.getIsbn13(), book.getIsbn10(), book.getTitle(), book.getLanguage(), book.getBinding(),
                       (book.getRelease_date() == null) ? book.getRelease_date():Date.valueOf(book.getRelease_date()),
                       book.getEdition(), book.getPages(), book.getDimensions(), book.getRating(), book.getPublisher(), book.getAuthors(), book.getNumberOfCopies());

               return getBookByISBN(book.getIsbn13());

           } catch (Exception exc) {
               throw new DatabaseException(exc.getCause());
           }
       }
        return book;
    }

    //  A function that deletes a book from the database by its id
    public void deleteBookById(int id) {
        try {
            jdbcTemplate.update("delete from books where id=?", id);
        }
        catch (Exception exc) {
            throw new DatabaseException(exc.getCause());
        }
    }

    //  A function that deletes a book from the database by its isbn
    public void deleteBookByISBN(String isbn) {

            try {
                String sql = (isbn.length() == 13) ? "DELETE FROM books WHERE isbn13 = ?" : "DELETE FROM books WHERE isbn10 = ?";
                jdbcTemplate.update(sql, isbn);
            }
            catch (Exception exc) {
                throw new DatabaseException(exc.getCause());
            }
        }

}

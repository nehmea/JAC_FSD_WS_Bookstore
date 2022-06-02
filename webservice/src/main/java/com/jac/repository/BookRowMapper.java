package com.jac.repository;

import com.jac.model.Book;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setIsbn13(rs.getString("isbn13"));
        book.setIsbn10(rs.getString("isbn10"));
        book.setTitle(rs.getString("title"));
        book.setLanguage(rs.getString("language"));
        book.setBinding(rs.getString("binding"));
        book.setRelease_date(rs.getDate("date").toLocalDate());
        book.setEdition(rs.getString("edition"));
        book.setPages(rs.getInt("pages"));
        book.setDimensions(rs.getString("dimensions"));
        book.setRating(rs.getFloat("rating"));
        book.setPublisher(rs.getString("publisher"));
        book.setAuthors(rs.getString("Authors"));
        book.setNumberOfCopies(rs.getInt("copies"));

        return book;
    }

}

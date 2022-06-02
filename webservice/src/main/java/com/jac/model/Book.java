package com.jac.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {

    private int id;
    private String isbn13;
    private String isbn10;
    private String title;
    private String language;
    private String binding;
    private LocalDate release_date;
    private String Edition;
    private int pages;
    private String dimensions;
    private float rating;
    private String publisher;
    private String authors;
    private int numberOfCopies;

    public Book(String isbn13, String title, String language, int numberOfCopies) {
        this.isbn13 = isbn13;
        this.title = title;
        this.language = language;
        this.numberOfCopies = numberOfCopies;
    }
}

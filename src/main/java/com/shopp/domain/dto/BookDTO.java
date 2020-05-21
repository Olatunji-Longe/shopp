package com.shopp.domain.dto;

import com.shopp.domain.Book;

import java.math.BigDecimal;

public class BookDTO extends EntityDTO<Book> {

    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String language;
    private Integer year;
    private String imageUrl;
    private BigDecimal price;

    public BookDTO(Book book) {
        super(book);
    }

    @Override
    protected void load(Book book) {
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.language = book.getLanguage();
        this.year = book.getYear();
        this.imageUrl = book.getImageUrl();
        this.price = book.getPrice();
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getLanguage() {
        return language;
    }

    public Integer getYear() {
        return year;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }
}

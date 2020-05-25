package com.shopp.domain.dto;

import com.shopp.domain.Book;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@SuperBuilder
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

}

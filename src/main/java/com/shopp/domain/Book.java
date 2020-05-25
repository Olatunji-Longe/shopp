package com.shopp.domain;

import com.shopp.domain.dto.BookDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "books", schema = "public", catalog = "shoppdb")
public class Book extends RootEntity {

    @Column(name = "isbn", nullable = false, unique = true, length = 32)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false, length = 128)
    private String author;

    @Column(name = "language", nullable = false, length = 64)
    private String language;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Override
    public BookDTO toDTO() {
        return new BookDTO(this);
    }

}

package com.example.level2.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @Column(name = "book_id")
    private String id; // BU/MC/PC/PS/TC + 4 digits

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "au_id", referencedColumnName = "au_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "pub_id", referencedColumnName = "pub_id")
    private Publisher publisher;

    @Transient
    private Double price;

    @Column(name = "notes")
    private String notes;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
    public Publisher getPublisher() { return publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}

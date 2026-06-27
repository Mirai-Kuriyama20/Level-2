package com.example.level2.service;

import com.example.level2.model.Author;
import com.example.level2.model.Book;
import com.example.level2.model.Publisher;
import com.example.level2.model.User;
import com.example.level2.repository.BaseRepository;
import com.example.level2.repository.BookRepository;
import com.example.level2.repository.UserRepository;

import java.util.List;

public class BMSService {
    private final BookRepository bookRepo = new BookRepository();
    private final UserRepository userRepo = new UserRepository();
    private final BaseRepository<Author, String> authorRepo = new BaseRepository<>(Author.class);
    private final BaseRepository<Publisher, String> publisherRepo = new BaseRepository<>(Publisher.class);

    // User functions
    public User login(String username, String password) {
        return userRepo.login(username, password);
    }

    // Book functions
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book getBookById(String id) {
        return bookRepo.findById(id);
    }

    public List<Book> searchBooks(String keyword, String type) {
        return bookRepo.search(keyword, type);
    }

    public void addBook(Book book) throws Exception {
        if (bookRepo.findById(book.getId()) != null) {
            throw new Exception("Book ID already exists!");
        }
        bookRepo.save(book);
    }

    public void updateBook(Book book) {
        bookRepo.update(book);
    }

    public void deleteBook(String id) {
        bookRepo.delete(id);
    }

    // Author & Publisher functions
    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    public Author getAuthorById(String id) {
        return authorRepo.findById(id);
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepo.findAll();
    }

    public Publisher getPublisherById(String id) {
        return publisherRepo.findById(id);
    }
    
    // For initial data
    public void saveUser(User user) {
        userRepo.save(user);
    }
    
    public void saveAuthor(Author author) {
        authorRepo.save(author);
    }
    
    public void savePublisher(Publisher publisher) {
        publisherRepo.save(publisher);
    }
}

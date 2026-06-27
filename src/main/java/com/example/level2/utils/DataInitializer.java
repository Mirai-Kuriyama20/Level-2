package com.example.level2.utils;

import com.example.level2.model.Author;
import com.example.level2.model.Publisher;
import com.example.level2.model.User;
import com.example.level2.service.BMSService;

public class DataInitializer {
    public static void main(String[] args) {
        BMSService service = new BMSService();

        // Add sample users
        service.saveUser(new User("sa", "sa"));
        service.saveUser(new User("admin", "123"));

        // Add sample authors
        service.saveAuthor(new Author(null, "Nguyen Nhat Anh"));
        service.saveAuthor(new Author(null, "To Hoai"));
        service.saveAuthor(new Author(null, "Nam Cao"));

        // Add sample publishers
        service.savePublisher(new Publisher(null, "NXB Tre"));
        service.savePublisher(new Publisher(null, "NXB Kim Dong"));
        service.savePublisher(new Publisher(null, "NXB Giao Duc"));

        System.out.println("Sample data initialized successfully!");
        System.exit(0);
    }
}

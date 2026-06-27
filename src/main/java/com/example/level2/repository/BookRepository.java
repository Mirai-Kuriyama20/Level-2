package com.example.level2.repository;

import com.example.level2.model.Book;
import com.example.level2.utils.EntitymanagerUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class BookRepository extends BaseRepository<Book, String> {
    public BookRepository() {
        super(Book.class);
    }

    public List<Book> search(String keyword, String type) {
        EntityManager em = EntitymanagerUtils.getEntityManager();
        try {
            if (type == null || type.equals("Default") || keyword == null || keyword.trim().isEmpty()) {
                return findAll();
            }

            String jpql = "SELECT b FROM Book b WHERE ";
            switch (type) {
                case "Book ID":
                    jpql += "b.id LIKE :keyword";
                    break;
                case "Book Title":
                    jpql += "b.title LIKE :keyword";
                    break;
                case "Authors":
                    jpql += "b.author.name LIKE :keyword";
                    break;
                default:
                    jpql += "1=1";
            }
            TypedQuery<Book> query = em.createQuery(jpql, Book.class);
            query.setParameter("keyword", "%" + keyword.trim() + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}

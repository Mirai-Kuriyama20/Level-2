package com.example.level2.repository;

import com.example.level2.model.User;
import com.example.level2.utils.EntitymanagerUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class UserRepository extends BaseRepository<User, String> {
    public UserRepository() {
        super(User.class);
    }

    public User login(String username, String password) {
        EntityManager em = EntitymanagerUtils.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :user AND u.password = :pass", User.class)
                    .setParameter("user", username)
                    .setParameter("pass", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}

package com.example.level2.jdbc;


import com.example.level2.utils.EntitymanagerUtils;

public class JdbcMain {

    public static void main(String[] args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("BookDBI", "minh", "123456");

//        try (Connection connection = dcm.getConnection()) {
//            System.out.println("Connected...");
//        } catch (SQLException e) {
//            System.out.println("Failed to connect to database");
//            e.printStackTrace();
//        }

        try (var em = EntitymanagerUtils.getEntityManager()) {
            System.out.println("Create table.....");

        } catch (Exception e) {
            System.out.println("Failed to create entity manager");
            e.printStackTrace();
        } finally {
            EntitymanagerUtils.close();
        }
    }
}

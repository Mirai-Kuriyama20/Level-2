package com.example.level2;

import com.example.level2.model.Author;
import com.example.level2.model.Book;
import com.example.level2.model.Publisher;
import com.example.level2.service.BMSService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "BookServlet", value = "/books")
public class BookServlet extends HttpServlet {
    private final BMSService service = new BMSService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAuthenticated(request, response)) {
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listBooks(request, response);
                break;
            case "search":
                searchBooks(request, response);
                break;
            case "add":
                showBookForm(request, response, new Book(), false);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            default:
                listBooks(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAuthenticated(request, response)) {
            return;
        }

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    createBook(request);
                    redirectToList(response, "Book added successfully.");
                    break;
                case "update":
                    updateBook(request);
                    redirectToList(response, "Book updated successfully.");
                    break;
                case "delete":
                    deleteBook(request);
                    redirectToList(response, "Book deleted successfully.");
                    break;
                default:
                    redirectToList(response, null);
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            Book formBook = buildBookFromRequest(request);
            boolean editMode = "update".equals(action);
            showBookForm(request, response, formBook, editMode);
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = service.getAllBooks();
        request.setAttribute("books", books);
        request.setAttribute("message", request.getParameter("message"));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private void searchBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String type = request.getParameter("type");
        List<Book> books = service.searchBooks(keyword, type);
        request.setAttribute("books", books);
        request.setAttribute("keyword", keyword);
        request.setAttribute("type", type);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Book book = service.getBookById(id);
        if (book == null) {
            request.setAttribute("error", "Book not found.");
            listBooks(request, response);
            return;
        }
        showBookForm(request, response, book, true);
    }

    private void showBookForm(HttpServletRequest request, HttpServletResponse response, Book book, boolean editMode)
            throws ServletException, IOException {
        request.setAttribute("formBook", book);
        request.setAttribute("editMode", editMode);
        request.setAttribute("authors", service.getAllAuthors());
        request.setAttribute("publishers", service.getAllPublishers());
        request.setAttribute("books", service.getAllBooks());
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private void createBook(HttpServletRequest request) throws Exception {
        Book book = buildBookFromRequest(request);
        validateBook(book);
        service.addBook(book);
    }

    private void updateBook(HttpServletRequest request) throws Exception {
        Book book = buildBookFromRequest(request);
        validateBook(book);
        if (service.getBookById(book.getId()) == null) {
            throw new Exception("Book not found.");
        }
        service.updateBook(book);
    }

    private void deleteBook(HttpServletRequest request) throws Exception {
        String id = trim(request.getParameter("id"));
        if (id.isEmpty()) {
            throw new Exception("Book ID is required.");
        }
        service.deleteBook(id);
    }

    private Book buildBookFromRequest(HttpServletRequest request) {
        String id = trim(request.getParameter("id"));
        String title = trim(request.getParameter("title"));
        String authorId = trim(request.getParameter("authorId"));
        String publisherId = trim(request.getParameter("publisherId"));
        String notes = trim(request.getParameter("notes"));

        Author author = authorId.isEmpty() ? null : service.getAuthorById(authorId);
        Publisher publisher = publisherId.isEmpty() ? null : service.getPublisherById(publisherId);

        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setNotes(notes);
        return book;
    }

    private void validateBook(Book book) throws Exception {
        if (book.getId() == null || book.getId().isEmpty()) {
            throw new Exception("Book ID is required.");
        }
        if (!book.getId().matches("^(BU|MC|PC|PS|TC)\\d{4}$")) {
            throw new Exception("Book ID must start with BU/MC/PC/PS/TC followed by 4 digits.");
        }
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new Exception("Book title is required.");
        }
        if (book.getAuthor() == null) {
            throw new Exception("Please choose an author.");
        }
        if (book.getPublisher() == null) {
            throw new Exception("Please choose a publisher.");
        }
    }

    private void redirectToList(HttpServletResponse response, String message) throws IOException {
        String url = "books?action=list";
        if (message != null) {
            url += "&message=" + java.net.URLEncoder.encode(message, java.nio.charset.StandardCharsets.UTF_8);
        }
        response.sendRedirect(url);
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private boolean isAuthenticated(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession(false) == null || request.getSession(false).getAttribute("user") == null) {
            response.sendRedirect("login");
            return false;
        }
        return true;
    }
}

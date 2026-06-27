<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Management System (BMS)</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #f4f4f4; }
        .search-container, .form-container { border: 1px solid #ccc; padding: 15px; border-radius: 5px; background-color: #f9f9f9; margin-bottom: 15px; }
        .btn { padding: 5px 15px; cursor: pointer; }
        .btn-link { display: inline-block; padding: 6px 12px; border: 1px solid #888; border-radius: 3px; color: #000; text-decoration: none; background: #eee; }
        .btn-danger { background: #b00020; border-color: #b00020; color: white; }
        .message { padding: 10px; margin-bottom: 15px; border: 1px solid #7cb342; background: #f1f8e9; color: #33691e; }
        .error { padding: 10px; margin-bottom: 15px; border: 1px solid #d32f2f; background: #ffebee; color: #b71c1c; }
        .form-grid { display: grid; grid-template-columns: 140px minmax(240px, 1fr); gap: 10px; max-width: 760px; }
        .form-grid input, .form-grid select, .form-grid textarea { padding: 6px; }
        .form-actions { grid-column: 2; display: flex; gap: 8px; align-items: center; }
        .actions { display: flex; gap: 8px; align-items: center; }
        .actions form { margin: 0; }
        .page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; }
        .user-bar { display: flex; gap: 12px; align-items: center; }
    </style>
</head>
<body>

<c:if test="${empty books and empty param.action and empty param.type}">
    <jsp:forward page="/books?action=list" />
</c:if>

<c:if test="${param.type == 'Default' and empty param.keyword}">
    <jsp:forward page="/books?action=list" />
</c:if>

<div class="page-header">
    <h1>Book Management System</h1>
    <div class="user-bar">
        <span>Welcome ${sessionScope.user.username}</span>
        <a href="logout">Logout</a>
    </div>
</div>

<c:if test="${not empty message}">
    <div class="message">${message}</div>
</c:if>

<c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>

<div class="search-container">
    <form action="books" method="get">
        <input type="hidden" name="action" value="search">
        <label>Search by:</label>
        <select name="type">
            <option value="Default" ${type == 'Default' ? 'selected' : ''}>Default</option>
            <option value="Book ID" ${type == 'Book ID' ? 'selected' : ''}>Book ID</option>
            <option value="Book Title" ${type == 'Book Title' ? 'selected' : ''}>Book Title</option>
            <option value="Authors" ${type == 'Authors' ? 'selected' : ''}>Authors</option>
        </select>
        
        <label>Enter keyword:</label>
        <input type="text" name="keyword" value="${keyword}">
        
        <button type="submit" class="btn">Search</button>
        <a href="books?action=list" style="margin-left: 10px;">Clear</a>
        <a href="books?action=add" class="btn-link" style="margin-left: 10px;">Add new</a>
    </form>
</div>

<c:if test="${not empty formBook}">
    <div class="form-container">
        <h2>${editMode ? 'Edit Book' : 'Add Book'}</h2>
        <form action="books" method="post" class="form-grid">
            <input type="hidden" name="action" value="${editMode ? 'update' : 'create'}">

            <label for="id">Book ID:</label>
            <input id="id" name="id" value="${formBook.id}" ${editMode ? 'readonly' : ''} required>

            <label for="title">Book Title:</label>
            <input id="title" name="title" value="${formBook.title}" required>

            <label for="authorId">Author:</label>
            <select id="authorId" name="authorId" required>
                <option value="">-- Choose author --</option>
                <c:forEach var="author" items="${authors}">
                    <option value="${author.id}" ${formBook.author.id == author.id ? 'selected' : ''}>${author.name}</option>
                </c:forEach>
            </select>

            <label for="publisherId">Publisher:</label>
            <select id="publisherId" name="publisherId" required>
                <option value="">-- Choose publisher --</option>
                <c:forEach var="publisher" items="${publishers}">
                    <option value="${publisher.id}" ${formBook.publisher.id == publisher.id ? 'selected' : ''}>${publisher.name}</option>
                </c:forEach>
            </select>

            <label for="notes">Notes:</label>
            <textarea id="notes" name="notes" rows="4"><c:out value="${formBook.notes}" /></textarea>

            <div class="form-actions">
                <button type="submit" class="btn">${editMode ? 'Update' : 'Add'}</button>
                <a href="books?action=list">Cancel</a>
            </div>
        </form>
    </div>
</c:if>

<table>
    <thead>
    <tr>
        <th>Book ID</th>
        <th>Book Title</th>
        <th>Author</th>
        <th>Publisher</th>
        <th>Notes</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="book" items="${books}">
        <tr>
            <td>${book.id}</td>
            <td>${book.title}</td>
            <td>${book.author.name}</td>
            <td>${book.publisher.name}</td>
            <td>${book.notes}</td>
            <td>
                <div class="actions">
                    <a href="books?action=edit&id=${book.id}">Edit</a>
                    <form action="books" method="post" onsubmit="return confirm('Delete this book?');">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${book.id}">
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </form>
                </div>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty books}">
        <tr>
            <td colspan="6" style="text-align: center;">No books found.</td>
        </tr>
    </c:if>
    </tbody>
</table>

</body>
</html>

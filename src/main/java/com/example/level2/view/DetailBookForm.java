package com.example.level2.view;

import com.example.level2.model.Author;
import com.example.level2.model.Book;
import com.example.level2.model.Publisher;
import com.example.level2.service.BMSService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DetailBookForm extends JFrame {
    private JTextField txtBookID, txtTitle, txtPrice;
    private JTextArea txtNotes;
    private JComboBox<Author> cbAuthors;
    private JComboBox<Publisher> cbPublishers;
    private JButton btnUpdate, btnRemove, btnClear, btnClose;
    private final BMSService service = new BMSService();
    private HomeForm parent;
    private Book currentBook;

    public DetailBookForm(HomeForm parent, Book book) {
        this.parent = parent;
        this.currentBook = book;
        setTitle("Detail Book");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2));

        add(new JLabel("Book ID:"));
        txtBookID = new JTextField(book.getId());
        txtBookID.setEditable(false);
        add(txtBookID);

        add(new JLabel("Book Title:"));
        txtTitle = new JTextField(book.getTitle());
        add(txtTitle);

        add(new JLabel("Author:"));
        cbAuthors = new JComboBox<>();
        service.getAllAuthors().forEach(cbAuthors::addItem);
        if (book.getAuthor() != null) {
            for (int i = 0; i < cbAuthors.getItemCount(); i++) {
                if (cbAuthors.getItemAt(i).getId().equals(book.getAuthor().getId())) {
                    cbAuthors.setSelectedIndex(i);
                    break;
                }
            }
        }
        add(cbAuthors);

        add(new JLabel("Publisher:"));
        cbPublishers = new JComboBox<>();
        service.getAllPublishers().forEach(cbPublishers::addItem);
        if (book.getPublisher() != null) {
            for (int i = 0; i < cbPublishers.getItemCount(); i++) {
                if (cbPublishers.getItemAt(i).getId().equals(book.getPublisher().getId())) {
                    cbPublishers.setSelectedIndex(i);
                    break;
                }
            }
        }
        add(cbPublishers);

        add(new JLabel("Price:"));
        txtPrice = new JTextField(String.valueOf(book.getPrice()));
        add(txtPrice);

        add(new JLabel("Notes:"));
        txtNotes = new JTextArea(book.getNotes());
        add(new JScrollPane(txtNotes));

        btnUpdate = new JButton("Update");
        add(btnUpdate);

        btnRemove = new JButton("Remove");
        add(btnRemove);

        btnClear = new JButton("Clear");
        add(btnClear);

        btnClose = new JButton("Close");
        add(btnClose);

        btnUpdate.addActionListener(e -> {
            if (validateForm()) {
                currentBook.setTitle(txtTitle.getText());
                currentBook.setAuthor((Author) cbAuthors.getSelectedItem());
                currentBook.setPublisher((Publisher) cbPublishers.getSelectedItem());
                currentBook.setPrice(Double.parseDouble(txtPrice.getText()));
                currentBook.setNotes(txtNotes.getText());
                service.updateBook(currentBook);
                JOptionPane.showMessageDialog(this, "Update success!");
                parent.loadBooks();
                parent.setVisible(true);
                dispose();
            }
        });

        btnRemove.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this book?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                service.deleteBook(currentBook.getId());
                JOptionPane.showMessageDialog(this, "Remove success!");
                parent.loadBooks();
                parent.setVisible(true);
                dispose();
            }
        });

        btnClear.addActionListener(e -> clearForm());

        btnClose.addActionListener(e -> {
            parent.setVisible(true);
            dispose();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parent.setVisible(true);
                dispose();
            }
        });
    }

    private boolean validateForm() {
        if (txtTitle.getText().isEmpty() || txtPrice.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title and Price are required!");
            return false;
        }
        try {
            Double.parseDouble(txtPrice.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Price must be a number!");
            return false;
        }
        return true;
    }

    private void clearForm() {
        txtTitle.setText("");
        txtPrice.setText("");
        txtNotes.setText("");
    }
}

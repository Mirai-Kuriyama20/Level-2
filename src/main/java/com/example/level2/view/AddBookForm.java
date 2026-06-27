package com.example.level2.view;

import com.example.level2.model.Author;
import com.example.level2.model.Book;
import com.example.level2.model.Publisher;
import com.example.level2.service.BMSService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddBookForm extends JFrame {
    private JTextField txtBookID, txtTitle, txtPrice;
    private JTextArea txtNotes;
    private JComboBox<Author> cbAuthors;
    private JComboBox<Publisher> cbPublishers;
    private JButton btnAdd, btnClear;
    private final BMSService service = new BMSService();
    private HomeForm parent;

    public AddBookForm(HomeForm parent) {
        this.parent = parent;
        setTitle("Add Book");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2));

        add(new JLabel("Book ID:"));
        txtBookID = new JTextField();
        add(txtBookID);

        add(new JLabel("Book Title:"));
        txtTitle = new JTextField();
        add(txtTitle);

        add(new JLabel("Author:"));
        cbAuthors = new JComboBox<>();
        service.getAllAuthors().forEach(cbAuthors::addItem);
        add(cbAuthors);

        add(new JLabel("Publisher:"));
        cbPublishers = new JComboBox<>();
        service.getAllPublishers().forEach(cbPublishers::addItem);
        add(cbPublishers);

        add(new JLabel("Price:"));
        txtPrice = new JTextField();
        add(txtPrice);

        add(new JLabel("Notes:"));
        txtNotes = new JTextArea();
        add(new JScrollPane(txtNotes));

        btnAdd = new JButton("Add");
        add(btnAdd);

        btnClear = new JButton("Clear");
        add(btnClear);

        btnAdd.addActionListener(e -> {
            if (validateForm()) {
                try {
                    Book book = Book.builder()
                            .id(txtBookID.getText())
                            .title(txtTitle.getText())
                            .author((Author) cbAuthors.getSelectedItem())
                            .publisher((Publisher) cbPublishers.getSelectedItem())
                            .price(Double.parseDouble(txtPrice.getText()))
                            .notes(txtNotes.getText())
                            .build();
                    service.addBook(book);
                    JOptionPane.showMessageDialog(this, "Add success!");
                    parent.loadBooks();
                    parent.setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnClear.addActionListener(e -> clearForm());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parent.setVisible(true);
                dispose();
            }
        });
    }

    private boolean validateForm() {
        if (txtBookID.getText().isEmpty() || txtTitle.getText().isEmpty() || txtPrice.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return false;
        }
        if (!txtBookID.getText().matches("^(BU|MC|PC|PS|TC)\\d{4}$")) {
            JOptionPane.showMessageDialog(this, "Book ID must start with BU/MC/PC/PS/TC followed by 4 digits!");
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
        txtBookID.setText("");
        txtTitle.setText("");
        txtPrice.setText("");
        txtNotes.setText("");
    }
}

package com.example.level2.view;

import com.example.level2.model.Book;
import com.example.level2.service.BMSService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class HomeForm extends JFrame {
    private JLabel lblWelcome;
    private JComboBox<String> cbSearchBy;
    private JTextField txtKeyword;
    private JButton btnSearch, btnAddNew;
    private JTable tblBooks;
    private DefaultTableModel tableModel;
    private final BMSService service = new BMSService();
    private String currentUser;

    public HomeForm(String username) {
        this.currentUser = username;
        setTitle("Home - BMS");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblWelcome = new JLabel("Welcome " + username);
        topPanel.add(lblWelcome);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search book"));
        
        searchPanel.add(new JLabel("Search by:"));
        cbSearchBy = new JComboBox<>(new String[]{"Default", "Book ID", "Book Title", "Authors"});
        searchPanel.add(cbSearchBy);
        
        searchPanel.add(new JLabel("Enter keyword:"));
        txtKeyword = new JTextField(20);
        searchPanel.add(txtKeyword);
        
        btnSearch = new JButton("Search");
        searchPanel.add(btnSearch);
        
        btnAddNew = new JButton("Add new");
        searchPanel.add(btnAddNew);
        
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Book ID", "Book Title", "Author", "Publisher", "Price"}, 0);
        tblBooks = new JTable(tableModel);
        centerPanel.add(new JScrollPane(tblBooks), BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);

        loadBooks();

        btnSearch.addActionListener(e -> {
            String keyword = txtKeyword.getText();
            String type = (String) cbSearchBy.getSelectedItem();
            if (type.equals("Default") && (keyword == null || keyword.trim().isEmpty())) {
                loadBooks();
            } else {
                List<Book> result = service.searchBooks(keyword, type);
                displayBooks(result);
            }
        });

        btnAddNew.addActionListener(e -> {
            new AddBookForm(this).setVisible(true);
            this.setVisible(false);
        });

        tblBooks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = tblBooks.getSelectedRow();
                    String id = (String) tblBooks.getValueAt(row, 0);
                    Book book = service.searchBooks(id, "Book ID").get(0);
                    new DetailBookForm(HomeForm.this, book).setVisible(true);
                    HomeForm.this.setVisible(false);
                }
            }
        });
    }

    public void loadBooks() {
        displayBooks(service.getAllBooks());
    }

    public String getCurrentUser() {
        return currentUser;
    }

    private void displayBooks(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book b : books) {
            tableModel.addRow(new Object[]{
                b.getId(), 
                b.getTitle(), 
                b.getAuthor() != null ? b.getAuthor().getName() : "", 
                b.getPublisher() != null ? b.getPublisher().getName() : "", 
                b.getPrice()
            });
        }
    }
}

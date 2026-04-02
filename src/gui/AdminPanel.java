package gui;
import model.User;
import controller.LibraryManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminPanel extends JPanel {
    private LibraryManager manager;
    private DefaultTableModel userTableModel;
    private JTextField nameField, emailField;
    private JComboBox<String> roleCombo;
    private JButton addBtn, undoBtn;

    public AdminPanel(LibraryManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initComponents();
        layoutComponents();
        setupActions();
        updateUserTable();
    }

    private void initComponents() {
        nameField = new JTextField(15);
        emailField = new JTextField(15);
        roleCombo = new JComboBox<>(new String[]{"Member", "Staff", "Admin"});
        addBtn = new JButton("Add User Account");
        undoBtn = new JButton("Undo Last Action");
        
        String[] columns = {"User ID", "Full Name", "Email Address", "Role"};
        userTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
    }

    private void layoutComponents() {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Registration & Admin Console"));
        inputPanel.add(new JLabel("Name:")); inputPanel.add(nameField);
        inputPanel.add(new JLabel("Email:")); inputPanel.add(emailField);
        inputPanel.add(new JLabel("Role:")); inputPanel.add(roleCombo);
        inputPanel.add(addBtn);
        inputPanel.add(undoBtn);
        add(inputPanel, BorderLayout.NORTH);

        JTable userTable = new JTable(userTableModel);
        userTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Active System Users"));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupActions() {
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            if (name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            manager.addUser(name, email);
            updateUserTable();
            nameField.setText(""); emailField.setText("");
            JOptionPane.showMessageDialog(this, "User added successfully!");
        });

        undoBtn.addActionListener(e -> {
            manager.undoLastAction();
            updateUserTable();
            JOptionPane.showMessageDialog(this, "Last action undone.");
        });
    }

    public void updateUserTable() {
        userTableModel.setRowCount(0);
        for (User user : manager.getUsers()) {
            userTableModel.addRow(new Object[]{
                user.getUserId(), user.getName(), user.getEmail(), user.getRole()
            });
        }
    }
}
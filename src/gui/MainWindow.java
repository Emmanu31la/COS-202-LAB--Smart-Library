package gui;
import controller.*;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainWindow extends JFrame {
    private LibraryManager manager;
    private Color mivaBlack = new Color(0, 0, 0);

    public MainWindow() {
        manager = new LibraryManager();
        manager.loadFromFile(); // Load data on startup
        setupMainFrame();
    }

    private void setupMainFrame() {
        setTitle("Smart Library Circulation & Automation System");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Section
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(mivaBlack);
        JLabel title = new JLabel(" Smart Library Circulation & Automation System", JLabel.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Navigation Tabs - Integrates the modular panels
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("View Items", createViewItemsTab());
        
        // Pass a BorrowController and an empty runnable (or custom refresh logic)
        tabs.addTab("Borrow/Return", new BorrowPanel(new BorrowController(manager), () -> {}));
        
        tabs.addTab("Admin", new AdminPanel(manager));
        tabs.addTab("Search & Sort", new SearchSortPanel(manager));
        tabs.addTab("Reports", createReportsTab());
        
        add(tabs, BorderLayout.CENTER);

        // Status Bar
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.add(new JLabel(" Ready."), BorderLayout.WEST);
        add(statusBar, BorderLayout.SOUTH);
    }

    private JPanel createViewItemsTab() {
        JPanel p = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Filter by Type:"));
        JComboBox<String> filterBox = new JComboBox<>(new String[]{"All", "Book", "Magazine", "Journal"});
        JButton refreshBtn = new JButton("Refresh");
        top.add(filterBox);
        top.add(refreshBtn);

        String[] cols = {"ID", "Type", "Title", "Author", "Year", "Category", "Status", "Borrowed By"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        
        Runnable loadData = () -> {
            model.setRowCount(0);
            for (LibraryItem item : manager.getInventory()) {
                model.addRow(new Object[]{
                    item.getId(), item.getType(), item.getTitle(),
                    item.getAuthor(), item.getYear(), item.getCategory(),
                    item.getStatus(), item.getBorrowedBy()
                });
            }
        };
        
        loadData.run();
        refreshBtn.addActionListener(e -> loadData.run());

        p.add(top, BorderLayout.NORTH);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        return p;
    }

    private JPanel createReportsTab() {
        JPanel main = new JPanel(new BorderLayout());
        JTabbedPane reportTabs = new JTabbedPane();
        
        String[] cols = {"ID", "Title", "Type", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        // Populating basic cache info
        for(String title : manager.getFixedCache()) {
            model.addRow(new Object[]{"CACHE", title, "-", "-"});
        }
        
        reportTabs.addTab("Cache Viewer", new JScrollPane(new JTable(model)));
        main.add(new JLabel(" Top Frequently Borrowed Items (from frequency cache):"), BorderLayout.NORTH);
        main.add(reportTabs, BorderLayout.CENTER);
        return main;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
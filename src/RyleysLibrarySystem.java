import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RyleysLibrarySystem {
    private Inventory inventory = new Inventory();
    private JFrame frame;
    private final Color MILITARY_BG = new Color(53, 66, 48);
    private final Color MILITARY_BTN = new Color(75, 83, 32);
    private final Color KHAKI = new Color(210, 210, 180);
    private final Color TEXT_BLACK = Color.BLACK;

    public RyleysLibrarySystem() {
        inventory.loadData();
        frame = new JFrame("Ryley's Digital Library");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 450);
        frame.setLayout(new BorderLayout());

        JLabel header = new JLabel("📚 RYLEY'S LIBRARY SYSTEM", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(MILITARY_BG);
        header.setForeground(KHAKI);
        header.setFont(new Font("Monospaced", Font.BOLD, 26));
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        frame.add(header, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        menuPanel.setBackground(MILITARY_BG);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

        JButton btnAdd = createStyledButton("➕ Add a Book");
        JButton btnBorrow = createStyledButton("📖 Borrow a Book");
        JButton btnReturn = createStyledButton("🔄 Return a Book");
        JButton btnSearch = createStyledButton("🔍 Search For Title");
        JButton btnList = createStyledButton("📋 List All Books");
        JButton btnExit = createStyledButton("🚪 Save & Exit");

        btnAdd.addActionListener(e -> showAddDialog());
        btnBorrow.addActionListener(e -> showBorrowDialog());
        btnReturn.addActionListener(e -> showReturnDialog());
        btnSearch.addActionListener(e -> showSearchDialog());
        btnList.addActionListener(e -> showListPopup());
        btnExit.addActionListener(e -> {
            inventory.saveData();
            JOptionPane.showMessageDialog(frame, "Congratulations! All the Books are Saved. Goodbye and Thank you for using Ryley's Digital Library! 👋"); 
            System.exit(0);
        });

        menuPanel.add(btnAdd); menuPanel.add(btnBorrow);
        menuPanel.add(btnReturn); menuPanel.add(btnSearch);
        menuPanel.add(btnList); menuPanel.add(btnExit);

        frame.add(menuPanel, BorderLayout.CENTER);
        frame.getContentPane().setBackground(MILITARY_BG);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Monospaced", Font.BOLD, 14));
        btn.setBackground(KHAKI);
        btn.setForeground(TEXT_BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(MILITARY_BTN, 2));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(MILITARY_BTN);
                btn.setForeground(KHAKI);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(KHAKI);
                btn.setForeground(TEXT_BLACK);
            }
        });
        return btn;
    }

    private void showAddDialog() {
        JTextField idF = new JTextField();
        JTextField titF = new JTextField();
        JTextField autF = new JTextField();
        JTextField isbnF = new JTextField();
        JTextField pagF = new JTextField();
        Object[] msg = { "ID:", idF, "Title:", titF, "Author:", autF, "ISBN:", isbnF, "Pages:", pagF };
        int res = JOptionPane.showConfirmDialog(frame, msg, "New Entry", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            if (titF.getText().trim().isEmpty() || autF.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "❌ Error: Please enter a Title and Author.");
                return;
            }
            try {
                inventory.addBook(new Book(Integer.parseInt(idF.getText()), titF.getText(), autF.getText(), isbnF.getText(), Integer.parseInt(pagF.getText())));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Input Error: Please enter valid numbers.");
            }
        }
    }

    private void showListPopup() {
        JTextArea area = new JTextArea(inventory.getTechnicalList(), 15, 60);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setBackground(KHAKI);
        JScrollPane scroll = new JScrollPane(area);
        JOptionPane.showMessageDialog(frame, scroll, "TECHNICAL LIST", JOptionPane.PLAIN_MESSAGE);
    }

    private void showBorrowDialog() {
        String id = JOptionPane.showInputDialog(frame, "Please Enter the ID of the Book You Want to Borrow:");
        if (id != null) try { inventory.borrowBook(Integer.parseInt(id)); } catch(NumberFormatException e) {}
    }

    private void showReturnDialog() {
        String id = JOptionPane.showInputDialog(frame, "Please Enter the ID of the Book You Want to Return:");
        if (id != null) try { inventory.returnBook(Integer.parseInt(id)); } catch(NumberFormatException e) {}
    }

    private void showSearchDialog() {
        String q = JOptionPane.showInputDialog(frame, "Please Enter the Title of the Book You Want to Search For:");
        if (q != null) inventory.searchByTitle(q);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {}
        SwingUtilities.invokeLater(() -> new RyleysLibrarySystem());
    }
}
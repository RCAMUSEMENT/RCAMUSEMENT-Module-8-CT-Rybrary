import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RyleysLibrarySystem {
    private Inventory inventory = new Inventory();
    private JFrame frame;
    // Military Green Palette
    private final Color MILITARY_BG = new Color(53, 66, 48); // Deep Forest Olive Green Color
    private final Color MILITARY_BTN = new Color(75, 83, 32); // Olive Drab Green Color
    private final Color KHAKI = new Color(210, 210, 180); // Faded Khaki Color
    private final Color TEXT_BLACK = Color.BLACK; // Black for words to improve readability on the military background

    public RyleysLibrarySystem() {
        inventory.loadData();
        frame = new JFrame("Ryley's Digital Library");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 450);
        frame.setLayout(new BorderLayout());

        // Header Style: Background of text is Olive Green, Title Text is Khaki Color,
        // and the font is Monospaced Bold to give it a much more technical and militaristic feel.
        // The header also has some padding for better spacing.
        JLabel header = new JLabel("📚 RYLEY'S LIBRARY SYSTEM", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(MILITARY_BG);
        header.setForeground(KHAKI); // Title is Khaki in color
        header.setFont(new Font("Monospaced", Font.BOLD, 26));
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        frame.add(header, BorderLayout.NORTH);

        // Menu Panel Style
        JPanel menuPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        menuPanel.setBackground(MILITARY_BG);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

        // Created Buttons with Black text
        JButton btnAdd = createStyledButton("➕ Add a Book");
        JButton btnBorrow = createStyledButton("📖 Borrow a Book");
        JButton btnReturn = createStyledButton("🔄 Return a Book");
        JButton btnSearch = createStyledButton("🔍 Search For Title");
        JButton btnList = createStyledButton("📋 List All Books");
        JButton btnExit = createStyledButton("🚪 Save & Exit");

        // My Logic Actions for each button
        btnAdd.addActionListener(e -> showAddDialog());
        btnBorrow.addActionListener(e -> showBorrowDialog());
        btnReturn.addActionListener(e -> showReturnDialog());
        btnSearch.addActionListener(e -> showSearchDialog());
        btnList.addActionListener(e -> showListPopup());
        btnExit.addActionListener(e -> {
            inventory.saveData();
            JOptionPane.showMessageDialog(frame, "Archives Saved. Goodbye and Thank you for using Ryley's Digital Library! 👋");
            System.exit(0);
        });

        menuPanel.add(btnAdd);
        menuPanel.add(btnBorrow);
        menuPanel.add(btnReturn);
        menuPanel.add(btnSearch);
        menuPanel.add(btnList);
        menuPanel.add(btnExit);

        frame.add(menuPanel, BorderLayout.CENTER);
        frame.getContentPane().setBackground(MILITARY_BG);
        frame.setLocationRelativeTo(null);

        // Tactical Dynamic Scaling logic to match text to window size
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int headerSize = Math.max(18, frame.getWidth() / 20);
                int buttonSize = Math.max(10, frame.getWidth() / 35);
                header.setFont(new Font("Monospaced", Font.BOLD, headerSize));
                for (Component c : menuPanel.getComponents()) {
                    if (c instanceof JButton) {
                        c.setFont(new Font("Monospaced", Font.BOLD, buttonSize));
                    }
                }
            }
        });

        frame.setVisible(true);
    }

    // Button Styling: Khaki background with Black text To both fit my military theme and improve its overall readability
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Monospaced", Font.BOLD, 14));
        btn.setBackground(KHAKI); // Buttons use a Khaki background
        btn.setForeground(TEXT_BLACK); // Text is Black
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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {}
        SwingUtilities.invokeLater(() -> new RyleysLibrarySystem());
    }
}
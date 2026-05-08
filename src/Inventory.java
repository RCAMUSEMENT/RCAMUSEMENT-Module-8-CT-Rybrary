import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class Inventory {
    private Map<Integer, Book> mainInventory = new HashMap<>();
    private Map<Integer, Book> lendingInventory = new HashMap<>();
    private final String DATA_FILE = "library_data.ser";

    public void addBook(Book book) {
        if (mainInventory.containsKey(book.getId()) || lendingInventory.containsKey(book.getId())) {
            JOptionPane.showMessageDialog(null, "⚠️ Error: The ID " + book.getId() + " already exists. Please use a unique ID.");
        } else {
            mainInventory.put(book.getId(), book);
            JOptionPane.showMessageDialog(null, "✅ Congratulations! The book '" + book.getTitle() + "' has been successfully added to the inventory.");
        }
    }

    public void borrowBook(int id) {
        if (mainInventory.containsKey(id)) {
            Book b = mainInventory.remove(id);
            lendingInventory.put(id, b);
            JOptionPane.showMessageDialog(null, "📖 Congratulations! The book '" + b.getTitle() + "' has been successfully borrowed.");
        } else {
            JOptionPane.showMessageDialog(null, "❌ Error: ID " + id + " is not available. Please check the ID and try again.");
        }
    }

    public void returnBook(int id) {
        if (lendingInventory.containsKey(id)) {
            Book b = lendingInventory.remove(id);
            mainInventory.put(id, b);
            JOptionPane.showMessageDialog(null, "🔄 Congratulations! The book '" + b.getTitle() + "' has been successfully returned.");
        } else {
            JOptionPane.showMessageDialog(null, "❌ Error: Sorry, This book is not in the lending inventory.");
        }
    }

    public void searchByTitle(String keyword) {
        StringBuilder results = new StringBuilder("Search Results:\n");
        boolean found = false;
        for (Book b : mainInventory.values()) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                results.append("- ").append(b.getTitle()).append("\n");
                found = true;
            }
        }
        JOptionPane.showMessageDialog(null, found ? results.toString() : "❌ Error: Sorry, no books found matching your search criteria.");
    }

    public void printAll() {
        if (mainInventory.isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Error: Sorry, the current inventory is empty.");
        } else {
            System.out.println("\n--- RYLEY'S AVAILABLE COLLECTION ---");
            mainInventory.values().forEach(Book::printBookInfo);
            JOptionPane.showMessageDialog(null, "Please check your terminal for the full technical list of available books.");
        }
    }

    // Helper for the List Popup
    public String getTechnicalList() {
        if (mainInventory.isEmpty()) return "❌ Error: Sorry, the current inventory is empty.";
        StringBuilder sb = new StringBuilder("              --- RYLEY'S DIGITAL LIBRARY ---\n         --- AVAILABLE COLLECTION FOR CHECK-OUT ---\n\n");
        mainInventory.values().forEach(b -> sb.append(String.format("ID: %-4d | %-20s | By: %-15s | %d pgs\n",
                b.getId(), b.getTitle(), b.getAuthor(), b.getPages())));
        return sb.toString();
    }

    public void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Map<String, Map<Integer, Book>> data = (Map<String, Map<Integer, Book>>) ois.readObject();
            mainInventory = data.getOrDefault("main", new HashMap<>());
            lendingInventory = data.getOrDefault("lending", new HashMap<>());
        } catch (Exception e) { /* New start */ }
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            Map<String, Map<Integer, Book>> data = new HashMap<>();
            data.put("main", mainInventory);
            data.put("lending", lendingInventory);
            oos.writeObject(data);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "❌ Error saving data: " + e.getMessage());
        }
    }
}
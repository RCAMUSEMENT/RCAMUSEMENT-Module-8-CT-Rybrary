/**
 * Ryley's Library System
 * Student Name: Ryley Carlson
 * CSC372 Module 8 Critical Thinking Assignment
 * Date: 2026-04-26
 * Program: Rybrary.java
 * Description: A digital library management system that allows users to add, borrow, return, search, and list books in a collection.
 * The system uses Java Swing for the user interface and Java serialization for data persistence.
 * The design incorporates a military green color palette to create a unique and engaging user experience.
 */

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String title;
    private String author;
    private String isbn;
    private int pages;

    public Book() {}
    public Book(int id, String title, String author, String isbn, int pages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.pages = pages;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }

    public void printBookInfo() {
        System.out.printf(" ID: %-4d | %-25s | By: %-15s | ISBN: %-13s | %d pgs%n", id, title, author, isbn, pages);
    }
}
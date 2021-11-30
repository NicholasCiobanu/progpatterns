/*
 * The MIT License
 *
 * Copyright 2021 Nicholas Ciobanu.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package finalproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;
import java.sql.PreparedStatement;

/**
 *
 * @author Nicholas Ciobanu
 */
public class Book implements Comparable<Book> {

    private String SN;
    private String title;
    private String author;
    private String publisher;
    private int qte;
    private double price;
    private int issuedQte;
    private String DateOfPurchase;

    public Book(String SN, String title, String author, String publisher, int qte, double price, int issuedQte, String DateOfPurchase) {
        this.SN = SN;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.qte = qte;
        this.price = price;
        this.issuedQte = 0;
        this.DateOfPurchase = DateOfPurchase;
    }

    public Book(String SN) {
        this.SN = SN;
    }

    public void addBook(Book book) {

        try {
            LocalDate date = LocalDate.now();
            Connection con = DriverManager.getConnection("jdbc:sqlite:Database\\ProjectDB.db");
            Statement stmt = con.createStatement();
            String insertInTable1 = "INSERT INTO Books"
                    + "(SN,Title,Author,Publisher,Price,Quantity,Issued,AddedDate)"
                    + "VALUES ('" + SN + "', '" + title + "','" + author + "', '" + publisher + "'," + price + ", " + qte + ", " + issuedQte + ", '" + date.toString() + "');";
            stmt.executeUpdate(insertInTable1);
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public boolean issueBook(Book b, Student s) {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:Database\\ProjectDB.db");
            Statement stmt = con.createStatement();
            String queryTable1 = "select COUNT(*) AS total from Students WHERE StudentId='" + s.getStId() + "';";
            ResultSet rs1 = stmt.executeQuery(queryTable1);
            if (rs1.getInt("total") == 0) {
                System.out.println("Invalid Student information");
                return false;
            }
            String queryTable2 = "select COUNT(*) AS total from Books WHERE SN='" + b.getSN() + "';";
            ResultSet rs2 = stmt.executeQuery(queryTable2);
            if (rs2.getInt("total") == 0) {
                System.out.println("No Such Book");
                return false;
            }
            String queryTable3 = "select * from Books WHERE SN='" + b.getSN() + "';";
            ResultSet rs3 = stmt.executeQuery(queryTable3);
            if (rs3.getInt("Quantity") == 0) {
                System.out.println("No more copies of this book");
                return false;
            }
            String updateBooks1 = "UPDATE Books SET Quantity=Quantity+1 WHERE SN='" + b.getSN() + "';";
            stmt.executeUpdate(updateBooks1);
            String updateBooks2 = "UPDATE Books SET Issued =Issued+1 WHERE SN='" + b.getSN() + "';";
            stmt.executeUpdate(updateBooks2);
            Scanner myObj = new Scanner(System.in);
            System.out.println("Enter current date");
            String date = myObj.nextLine();
            String insertInTable1 = "INSERT INTO IssuedBooks"
                    + "(SN,StId,StName,StudentContact,IssueDate)"
                    + "VALUES ('" + SN + "', '" + s.getStId() + "','" + s.getName() + "', '" + s.getContact() + "', '" + date + "');";
            stmt.executeUpdate(insertInTable1);

            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }

    public boolean returnBook(IssuedBook b, Student s) throws Exception {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:sqlite:Database\\ProjectDB.db");
            Statement stmt = con.createStatement();
            String queryTable1 = "SELECT COUNT() AS total from IssuedBooks WHERE='" + b.getId() + "';";
            ResultSet rs1 = stmt.executeQuery(queryTable1);
            if (rs1.getInt("total") == 0) {
                System.out.println("Invalid Student information");
                return false;
            }
            String queryTable2 = "select COUNT() AS total from Students WHERE StudentId='" + s.getStId() + "';";
            ResultSet rs2 = stmt.executeQuery(queryTable2);
            if (rs2.getInt("total") == 0) {
                System.out.println("Invalid Student information");
                return false;
            }
            String updateBooks1 = "UPDATE Books SET Quantity = Quantity + 1 WHERE SN='" + b.getSN() + "';";
            stmt.executeUpdate(updateBooks1);
            String updateBooks2 = "UPDATE Books SET Issued = Issued - 1 WHERE SN='" + b.getSN() + "';";
            stmt.executeUpdate(updateBooks2);
            //not finished
            String deleteRecord = "DELETE FROM IssuedBooks WHERE id ='" + b.getId() + "';";
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;

    }

    public static Map<String, Book> viewCatalog() {

        Map<String, Book> catalog = new TreeMap<>();

        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:Database\\ProjectDB.db");
            Statement stmt = con.createStatement();
            String queryTable = "select * from Books ";
            ResultSet rs1 = stmt.executeQuery(queryTable);
            while (rs1.next()) {
                catalog.put(rs1.getString("SN"), new Book(rs1.getString("SN"), rs1.getString("Title"), rs1.getString("Author"), rs1.getString("Publisher"), rs1.getInt("Quantity"), rs1.getDouble("Price"), rs1.getInt("Issued"), rs1.getString("AddedDate")));

            }
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return catalog;
    }

    public static Map<String, IssuedBook> viewIssuedBooks() {

        Map<String, IssuedBook> catalog = new TreeMap<>();

        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:Database\\ProjectDB.db");
            Statement stmt = con.createStatement();
            String queryTable = "select * from IssuedBooks ";
            ResultSet rs1 = stmt.executeQuery(queryTable);
            while (rs1.next()) {
                catalog.put(rs1.getString("id"), new IssuedBook(rs1.getInt("id"), rs1.getString("SN"), rs1.getInt("StId"), rs1.getString("StName"), rs1.getString("StudentContact"), rs1.getString("IssueDate")));

            }
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return catalog;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.SN);
        hash = 89 * hash + Objects.hashCode(this.title);
        hash = 89 * hash + Objects.hashCode(this.author);
        hash = 89 * hash + Objects.hashCode(this.publisher);
        hash = 89 * hash + this.qte;
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        hash = 89 * hash + this.issuedQte;
        hash = 89 * hash + Objects.hashCode(this.DateOfPurchase);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Book other = (Book) obj;
        if (this.qte != other.qte)
            return false;
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price))
            return false;
        if (this.issuedQte != other.issuedQte)
            return false;
        if (!Objects.equals(this.SN, other.SN))
            return false;
        if (!Objects.equals(this.title, other.title))
            return false;
        if (!Objects.equals(this.author, other.author))
            return false;
        if (!Objects.equals(this.publisher, other.publisher))
            return false;
        if (!Objects.equals(this.DateOfPurchase, other.DateOfPurchase))
            return false;
        return true;
    }

    @Override
    public int compareTo(Book o) {
        return Integer.parseInt(this.SN) - Integer.parseInt(o.getSN());
    }

    @Override
    public String toString() {
        return "Book{" + "SN=" + SN + ", title=" + title + ", author=" + author + ", publisher=" + publisher + ", qte=" + qte + ", price=" + price + ", issuedQte=" + issuedQte + ", DateOfPurchase=" + DateOfPurchase + '}';
    }

    public String getSN() {
        return SN;
    }

}

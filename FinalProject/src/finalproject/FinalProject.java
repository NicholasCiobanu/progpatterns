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

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *
 * @author Nicholas Ciobanu
 */
public class FinalProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        createDatabases();
        initializeGUI();

    }

    private static void createDatabases() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:Database\\ProjectDB.db");
            Statement stmt = con.createStatement();

            String createTable = "CREATE TABLE Books "
                    + "(SN          TEXT         PRIMARY KEY     NOT NULL,"
                    + " Title       CHAR(50)        NOT NULL, "
                    + " Author      CHAR(50)        NOT NULL, "
                    + " Publisher   CHAR(50)    NOT NULL, "
                    + " Price       DOUBLE     NOT NULL, "
                    + " Quantity    INT         NOT NULL, "
                    + " Issued      INT         NOT NULL, "
                    + " AddedDate   TEXT        NOT NULL) ";
            stmt.executeUpdate("DROP table if exists Books");
            stmt.executeUpdate(createTable);

            String insertInTable1 = "INSERT INTO Books"
                    + "(SN,Title,Author,Publisher,Price,Quantity,Issued,AddedDate)"
                    + "VALUES ('B1', 'Title1','Author1', 'Publisher1',25.50, 1, 0, '2010-01-01');";
            stmt.executeUpdate(insertInTable1);

            String insertInTable2 = "INSERT INTO Books"
                    + "(SN,Title,Author,Publisher,Price,Quantity,Issued,AddedDate)"
                    + "VALUES ('B2', 'Title2','Author2', 'Publisher2',30.0, 3, 0, '2007-02-05');";
            stmt.executeUpdate(insertInTable2);

            String insertInTable4 = "INSERT INTO Books"
                    + "(SN,Title,Author,Publisher,Price,Quantity,Issued,AddedDate)"
                    + "VALUES ('B4', 'Title4','Author4', 'Publisher4',30.0, 2, 0, '2005-05-10');";
            stmt.executeUpdate(insertInTable4);
            String insertInTable5 = "INSERT INTO Books"
                    + "(SN,Title,Author,Publisher,Price,Quantity,Issued,AddedDate)"
                    + "VALUES ('B5', 'Title5','Author3', 'Publisher5',10.75, 1, 0, '2007-01-01');";
            stmt.executeUpdate(insertInTable5);
            String insertInTable3 = "INSERT INTO Books"
                    + "(SN,Title,Author,Publisher,Price,Quantity,Issued,AddedDate)"
                    + "VALUES ('B3', 'Title3','Author3', 'Publisher3',15.25, 5, 0, '2003-01-01');";
            stmt.executeUpdate(insertInTable3);
            String createStudentsTable = "CREATE TABLE Students "
                    + "(StudentId    INT   PRIMARY KEY    NOT NULL,"
                    + " Name         TEXT  NOT NULL, "
                    + " Contact      INT   NOT NULL) ";

            stmt.executeUpdate("DROP table if exists Students");
            stmt.executeUpdate(createStudentsTable);

            String insertInTable = "INSERT INTO Students"
                    + "(StudentId ,Name,Contact)"
                    + "VALUES (1,'John Washington',438-567-8658);";
            stmt.executeUpdate(insertInTable);

            insertInTable = "INSERT INTO Students"
                    + "(StudentId ,Name,Contact)"
                    + "VALUES (2,'Maddison Annemarie',438-534-1456);";
            stmt.executeUpdate(insertInTable);

            insertInTable = "INSERT INTO Students"
                    + "(StudentId ,Name,Contact)"
                    + "VALUES (3,'Valentin Georgiev',438-345-1123);";
            stmt.executeUpdate(insertInTable);

            insertInTable = "INSERT INTO Students"
                    + "(StudentId ,Name,Contact)"
                    + "VALUES (4,'James Smith',438-345-1123);";
            stmt.executeUpdate(insertInTable);

            insertInTable = "INSERT INTO Students"
                    + "(StudentId ,Name,Contact)"
                    + "VALUES (5,'Sebastian Jones ',438-345-1123);";
            stmt.executeUpdate(insertInTable);
            String createIssuedBooksTable = "CREATE TABLE IssuedBooks "
                    + "(id    INTEGER   PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + " SN    TEXT  NOT NULL, "
                    + " StId  INT   NOT NULL, "
                    + " StName  TEXT  NOT NULL, "
                    + " StudentContact  TEXT  NOT NULL, "
                    + " IssueDate  TEXT  NOT NULL, "
                    + " FOREIGN KEY (SN) REFERENCES Books(SN),"
                    + " FOREIGN KEY (StId) REFERENCES Students(StudentId))";

            stmt.executeUpdate("DROP table if exists IssuedBooks");
            stmt.executeUpdate(createIssuedBooksTable);

            String queryTable = "select * from Books ";
            ResultSet rs1 = stmt.executeQuery(queryTable);
            while (rs1.next()) {
                System.out.println(" SN = " + rs1.getString("SN"));
                System.out.println(" Title = " + rs1.getString("Title"));
                System.out.println(" Author = " + rs1.getString("Author"));
                System.out.println(" Publisher = " + rs1.getString("Publisher"));
                System.out.println(" Price = " + rs1.getString("Price"));
                System.out.println(" Quantity = " + rs1.getString("Quantity"));
                System.out.println(" Issued = " + rs1.getString("Issued"));
                System.out.println(" AddedDate = " + rs1.getString("AddedDate"));
                //System.out.println(" AddedDate = " + rs1.getDate("AddedDate"));
                //date needs to be fixed
            }

            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private static void initializeGUI() {
        System.out.println("Enter 1 if librarian or 2 if student");
        Scanner input = new Scanner(System.in);
        int loginInput = input.nextInt();
        if (loginInput == 1) {
            initializeLibrarianGUI();
        } else {
            initializeStudentGUI();
        }
    }

    private static void initializeLibrarianGUI() {
        System.out.println("Enter choice of press 0 to exit program");
        while (true) {
            System.out.println("0. Exit Program");
            System.out.println("1. Add a book");
            System.out.println("2. Issue a book");
            System.out.println("3. Return a book");
            System.out.println("4. View Catalog");
            System.out.println("5. View Issued Books");
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            if (choice == 0) {
                break;
            }
            // put in controller /separate form view
            switch (choice) {
                case 1: {
                    Book book = createBook();
                    book.addBook(book);
                }
                case 2: {
                    System.out.println("Enter Student ID to which to issue a book");
                    int studId = input.nextInt();
                    System.out.println("Enter Book SN of book to be issued");
                    String bookId = input.next();
                    Book book = new Book(bookId);
                    Student student = createStudent(studId);
                    book.issueBook(book, student);
                }
                case 3: {
                    System.out.println("Return a book not yet implemented");
                }
                case 4: {
                    Book.viewCatalog().forEach((key, value) -> System.out.println(key + ":" + value));

                }
                case 5: {
                    Book.viewIssuedBooks().forEach((key, value) -> System.out.println(key + ":" + value));
                }
            }
        }
    }

    private static void initializeStudentGUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static Book createBook() {
        Scanner input = new Scanner(System.in);
        System.out.println("enter SN");
        String SN = input.nextLine();
        System.out.println("enter Title");
        String title = input.nextLine();
        System.out.println("enter Author");
        String author = input.nextLine();
        System.out.println("enter Publisher");
        String publisher = input.nextLine();
        System.out.println("enter Quantity");
        int qte = input.nextInt();
        System.out.println("enter Price");
        double price = input.nextDouble();
        LocalDateTime now = LocalDateTime.now();
        Book book = new Book(SN, title, author, publisher, qte, price, 0, now.toString());
        return book;
    }

    private static Student createStudent(int id) {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:Database\\ProjectDB.db");
            Statement stmt = con.createStatement();
            String queryTable = "select * from Students WHERE StudentId ='" + id + "';";
            ResultSet rs1 = stmt.executeQuery(queryTable);
            Student student = new Student(rs1.getInt("StudentId"), rs1.getString("Name"), rs1.getString("Contact"));

            stmt.close();
            con.close();
            return student;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }

    public IssuedBook createIssuedBook(Book b) throws Exception {
        IssuedBook issuedBook;
        //dasdas
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:sqlite:Database\\ProjectDB.db");
            Statement stmt = con.createStatement();
            String queryTable = "select * from IssuedBooks WHERE SN='" + b.getSN() + "';";
            ResultSet rs1 = stmt.executeQuery(queryTable);
            while (rs1.next()) {
                return issuedBook = new IssuedBook(rs1.getInt("id"), rs1.getString("SN"), rs1.getInt("StId"), rs1.getString("StName"), rs1.getString("StudentContact"), rs1.getString("IssueDate"));

            }
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }

}

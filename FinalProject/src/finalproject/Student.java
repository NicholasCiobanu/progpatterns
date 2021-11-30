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

import java.util.LinkedList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Objects;

/**
 *
 * @author Nicholas Ciobanu
 */
public class Student {

    private int StId;
    private String name;
    private String contact;

    public int getStId() {
        return StId;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public Student(int StId) {
        this.StId = StId;
    }


    public Student(int StId, String name, String contact) {
        this.StId = StId;
        this.name = name;
        this.contact = contact;
    }

    public void SearchBookByTitle(String title) {
        try {
            List<Book> bookTitles = new LinkedList<Book>();

            Class.forName("org.sqlite.JDBC");
            java.sql.Connection con = DriverManager.getConnection("jdbc:sqlite:Database\\ProjectDB.db");
            java.sql.Statement stmt = con.createStatement();

            String queryTable = "select * from Books WHERE Title='" + title + "';";
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

        } catch (Exception e) {            //make general exception
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);

        }

//        }
//        public List<Book> SearchBookByName(String title){
//
//        }
//        public List<Book> SearchBookByYear(String title){
//
//        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.StId;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.contact);
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
        final Student other = (Student) obj;
        if (this.StId != other.StId)
            return false;
        if (!Objects.equals(this.name, other.name))
            return false;
        if (!Objects.equals(this.contact, other.contact))
            return false;
        return true;
    }
}

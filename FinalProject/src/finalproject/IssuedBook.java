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

/**
 *
 * @author Nicholas Ciobanu
 */
public class IssuedBook {

    private int id;
    private String SN;
    private int StId;
    private String StName;
    private String StudentContact;

    public String getSN() {
        return SN;
    }

    public int getStId() {
        return StId;
    }

    public String getStName() {
        return StName;
    }

    public String getStudentContact() {
        return StudentContact;
    }

    public String getIssueDate() {
        return IssueDate;
    }
    private String IssueDate;

    public IssuedBook(int id, String SN, int StId, String StName, String StudentContact, String IssueDate) {
        this.id = id;
        this.SN = SN;
        this.StId = StId;
        this.StName = StName;
        this.StudentContact = StudentContact;
        this.IssueDate = IssueDate;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "IssuedBook{" + "id=" + id + ", SN=" + SN + ", StId=" + StId + ", StName=" + StName + ", StudentContact=" + StudentContact + ", IssueDate=" + IssueDate + '}';
    }

}

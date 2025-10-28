/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.University.CourseCatalog;

/**
 *
 * @author Hammad
 */
public class Course {
    // ADD ALL OF THIS BELOW
    String number;
    String name;
    int credits;
    int price = 1500;

    public Course(String numb, String n, int ch) {
        number = numb;  // Course number first
        name = n;       // Course name second
        credits = ch;
    }

    public String getCourseNumber() {
        return number;
    }

    public int getCoursePrice() {
        return price * credits;
    }

    public int getCredits() {
        return credits;
    }
}

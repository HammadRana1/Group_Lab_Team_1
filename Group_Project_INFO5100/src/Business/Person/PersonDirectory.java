/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Person;

import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class PersonDirectory {

    ArrayList<Person> personlist;

    public PersonDirectory() {

        personlist = new ArrayList();

    }

    //RijurikSaha-RegisterPerson-10/24
    // For Admin or Faculty
    public Person newPerson(String id, String name, String email, String department, String role) {
        Person p = new Person(id, name, email, department, role);
        personlist.add(p);
        return p;
    }
    
    //For Student
    public Person newPerson(String id, String name, String email, String department, String role, String contact, String academicStatus) {
        Person p = new Person(id, name, email, department, role, contact, academicStatus);
        personlist.add(p);
        return p;
    }

    public ArrayList<Person> getPersonList() {
        return personlist;
    }

    public void removePerson(Person p) {
        personlist.remove(p);
    }

    public Person findPerson(String id) {

        for (Person p : personlist) {

            if (p.isMatch(id)) {
                return p;
            }
        }
        return null; //not found after going through the whole list
    }

}

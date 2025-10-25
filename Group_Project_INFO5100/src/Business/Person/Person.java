/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Person;

/**
 *
 * @author kal bugrara
 */
public class Person {

    private String id;
    //RijurikSaha-RegisterPerson-10/24
    private String name;
    private String email;
    private String department;
    private String role;

    public Person(String id) {

        this.id = id;
    }

    public Person(String id, String name, String email, String department, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.role = role;
    }

    public String getPersonId() {
        return id;
    }
    
    //RijurikSaha-RegisterPerson-10/24
    
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getRole() {
        return role;
    }

    public boolean isMatch(String id) {
        if (getPersonId().equals(id)) {
            return true;
        }
        return false;
    }
    
    //RijurikSaha-RegisterPerson-10/24
    
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setRole(String role) {
        this.role = role;
    }
    @Override
    public String toString() {
        return getPersonId();
    }
}

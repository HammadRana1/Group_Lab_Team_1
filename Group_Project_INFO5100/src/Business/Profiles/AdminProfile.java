/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Profiles;

import Business.Person.Person;

/**
 *
 * @author RIO
 */

//RijurikSaha_Create_24/10
public class AdminProfile extends Profile {
    public AdminProfile(Person p) {
        super(p);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}

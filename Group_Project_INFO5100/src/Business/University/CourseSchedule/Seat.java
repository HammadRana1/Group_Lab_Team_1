/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.University.CourseSchedule;

/**
 *
 * @author Hammad
 */
public class Seat {
    
    Boolean occupied; 
    int number;
    SeatAssignment seatassignment; //links back to studentprofile
    CourseOffer courseoffer;
    public Seat (CourseOffer co, int n){
        courseoffer = co;
        number = n;
        occupied = false;
        
    }
    
    public Boolean isOccupied(){
        return occupied;

    }
    public SeatAssignment newSeatAssignment(CourseLoad cl){
        
        seatassignment = new SeatAssignment(cl, this); //links seatassignment to seat
        occupied = true;   
        return seatassignment;
    }
    public CourseOffer getCourseOffer(){
        return courseoffer;
    }
    public int getCourseCredits(){
        return courseoffer.getCreditHours();
    }
    
}

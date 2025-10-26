/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.University.CourseSchedule;

/**
 *
 * @author syrillthevenin
 */
public class Term {
    private final String code;   // e.g., "FALL-2025"
    private final String label;  // e.g., "Fall 2025"
    public Term(String code, String label){ this.code=code; this.label=label; }
    public String getCode(){ return code; }
    public String getLabel(){ return label; }
    @Override public String toString(){ return label; }
    
}

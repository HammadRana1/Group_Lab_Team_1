/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author syrillthevenin
 */

//Thevenin_10-25-_"Tracks_balance_history_and_isClear_for_transcript_lock"
package Business.Billing;
import java.util.*;

public class Account {
    public static class Entry {
        public final Date date = new Date();
        public final String type; // CHARGE, PAYMENT, REFUND
        public final double amount;
        public final String note;
        public Entry(String type, double amount, String note){
            this.type=type; this.amount=amount; this.note=note;
        }
    }

    private double balance;
    private final List<Entry> history = new ArrayList<>();

    public double getBalance(){ return balance; }
    public List<Entry> getHistory(){ return history; }
    public boolean isClear(){ return balance <= 0.0001; }

    //Thevenin_10-25-_"Bill_student_when_enrolling"
    public void charge(double amount, String note){
        if (amount <= 0) return;
        balance += amount; history.add(new Entry("CHARGE", amount, note));
    }
    //Thevenin_10-25-_"Record_payment_from_student"
    public void pay(double amount){
        if (amount <= 0) return;
        balance -= amount; history.add(new Entry("PAYMENT", amount, "Payment"));
    }
    //Thevenin_10-25-_"Refund_on_drop"
    public void refund(double amount, String note){
        if (amount <= 0) return;
        balance -= amount; history.add(new Entry("REFUND", amount, note));
    }
}

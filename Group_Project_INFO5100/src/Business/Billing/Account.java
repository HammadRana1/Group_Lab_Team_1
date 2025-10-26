/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
<<<<<<< Updated upstream
=======
package Business.Billing;

import java.util.*;

>>>>>>> Stashed changes
/**
 *
 * @author syrillthevenin
 */
<<<<<<< Updated upstream

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
=======
public class Account {

    public static class Entry {
        public final Date date = new Date();
        public final String type; public final double amount; public final String note;
        public Entry(String type,double amount,String note){ this.type=type; this.amount=amount; this.note=note; }
    }
    private double balance; private final List<Entry> history = new ArrayList<>();
    public double getBalance(){ return balance; } public List<Entry> getHistory(){ return history; }
    public boolean isClear(){ return balance <= 0.0001; }
    public void charge(double amt,String note){ if(amt>0){ balance+=amt; history.add(new Entry("CHARGE",amt,note)); } }
    public void pay(double amt){ if(amt>0){ balance-=amt; history.add(new Entry("PAYMENT",amt,"Payment")); } }
    public void refund(double amt,String note){ if(amt>0){ balance-=amt; history.add(new Entry("REFUND",amt,note)); } }
}


>>>>>>> Stashed changes

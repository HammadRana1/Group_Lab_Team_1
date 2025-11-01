package Business.Billing;

public class Account {
    private double balance;

    public Account() {
        this.balance = 0.0;
    }

    public Account(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void pay(double amount) {
        if (amount <= balance) {
            balance -= amount;
        }
    }

    public void addCharge(double amount) {
        balance += amount;
    }

    public boolean isPaidInFull() {
        return balance <= 0;
    }
}

import java.util.ArrayList;
import java.util.Scanner;

class InSufficientFundsException extends Exception {
    InSufficientFundsException(String msg) {
        super(msg);
    }
}

class Account {

    private String holder;
    private double bal;
    private ArrayList<Double> trail;

    Account(String holder, double bal) {
        this.holder = holder;
        this.bal = bal;
        trail = new ArrayList<>();
    }

    public void deposit(double amt) {

        if (amt <= 0) {
            throw new IllegalArgumentException("Invalid Amount");
        }

        bal += amt;
        keep(amt);

        System.out.println("Deposit Successful");
    }

    public void withdraw(double amt)
            throws InSufficientFundsException {

        if (amt < 0) {
            throw new IllegalArgumentException("Negative Amount");
        }

        if (amt > bal) {
            throw new InSufficientFundsException("Balance Too Low");
        }

        bal -= amt;
        keep(-amt);

        System.out.println("Withdraw Successful");
    }

    private void keep(double x) {

        if (trail.size() == 5) {
            trail.remove(0);
        }

        trail.add(x);
    }

    public void statement() {

        System.out.println("\n------ Mini Statement ------");
        System.out.println("Holder : " + holder);
        System.out.printf("Balance : %.2f\n", bal);

        if (trail.isEmpty()) {
            System.out.println("No Transactions");
            return;
        }

        System.out.println("Recent Transactions");

        for (double v : trail) {

            if (v >= 0) {
                System.out.printf("+ %.2f\n", v);
            } else {
                System.out.printf("- %.2f\n", Math.abs(v));
            }
        }
    }

    public double balance() {
        return bal;
    }

    public String name() {
        return holder;
    }

}

public class FinSafe {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.print("Account Holder : ");
        String nm = in.nextLine();

        System.out.print("Opening Balance : ");
        double open = in.nextDouble();

        Account obj = new Account(nm, open);

        while (true) {

            System.out.println("\n===== FinSafe =====");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. View Balance");
            System.out.println("4. Mini Statement");
            System.out.println("5. Exit");
            System.out.print("Select : ");

            int op = in.nextInt();

            switch (op) {

                case 1:

                    try {

                        System.out.print("Amount : ");
                        double d = in.nextDouble();

                        obj.deposit(d);

                    } catch (IllegalArgumentException e) {

                        System.out.println(e.getMessage());

                    }

                    break;

                case 2:

                    try {

                        System.out.print("Amount : ");
                        double w = in.nextDouble();

                        obj.withdraw(w);

                    } catch (IllegalArgumentException e) {

                        System.out.println(e.getMessage());

                    } catch (InSufficientFundsException e) {

                        System.out.println(e.getMessage());

                    }

                    break;

                case 3:

                    System.out.printf("Current Balance : %.2f\n", obj.balance());

                    break;

                case 4:

                    obj.statement();

                    break;

                case 5:

                    System.out.println("Session Closed");
                    in.close();
                    return;

                default:

                    System.out.println("Invalid Choice");

            }

        }

    }

}
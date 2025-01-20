package Reservation;

import java.time.LocalDate;

public class Payment {

    private int id;
    private static int idGenerator = 1;
    private double amount;
    private String paymentMethod;
    private String status;
    private LocalDate paymentDate;
    private String receipt;

    public Payment(double amount, String paymentMethod, String status, LocalDate paymentDate) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.paymentDate = paymentDate;
        this.id = idGenerator++;
        this.receipt = "Amount: " + amount + " Method: " + paymentMethod + " Date: " + paymentDate;
    }

    public static void setIdGenerator(int highestId) {
        idGenerator = highestId + 1;
    }

    public void processPayment() {
        this.status = "Processed";
        System.out.println("Payment  successfully.");
    }

    public void refundPayment() {
        this.status = "Refunded";
        System.out.println("Payment refunded successfully.");
    }

    public String generateReceipt() {
        return "Payment{" +
                "amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                ", paymentDate=" + paymentDate +
                ", receipt='" + receipt + '\'' +
                '}';
    }

    public void applyDiscount(String insurance) {
        if (insurance == "Tamin ejtemaee") {
            double discount = (amount / 20);
            amount -= discount;
            System.out.println("Discount applied. New amount: " + amount);
        } else if (insurance == "Farhangian") {
            int discount = (int) (amount / 10);
            amount -= discount;
            System.out.println("Discount applied. New amount: " + amount);
        } else if (insurance == "Bazneshastegi") {
            int discount = (int) (amount / 5);
            amount -= discount;
            System.out.println("Discount applied. New amount: " + amount);
        } else {
            System.out.println("There is no discount! ");
        }
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                ", paymentDate=" + paymentDate +
                ", receipt='" + receipt + '\'' +
                '}';
    }
}
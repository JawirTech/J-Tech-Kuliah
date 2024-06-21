package assignments.assignment3.payment;

public interface DepeFoodPaymentSystem {        //class ini merupakan class interface yang akan diturunkan ke CreditcardPayment dan DebitPayment
    public static long saldo = 0;
    public long processPayment(long amount);
}
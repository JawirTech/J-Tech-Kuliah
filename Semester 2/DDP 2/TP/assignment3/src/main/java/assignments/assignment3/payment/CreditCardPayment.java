package assignments.assignment3.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem {       //class ini merupakan turunan dai class interface DepeFoodPaymentSystem
    public static final double TRANSACTION_FEE_PERCENTAGE = 0.02;       //persentase biaya transaksi yaitu 2% dari total biaya order

    public long processPayment(long amount) {                           //method dari interface yang wajib di override
        return amount + countTransactionFee(amount);                    //total biaya setelah ditambah biaya transaksi
    }
    
    public long countTransactionFee(long amount) {
        return (long) (amount * TRANSACTION_FEE_PERCENTAGE);            //Menghitung biaya transaksi
    }
}
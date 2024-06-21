package assignments.assignment3.payment;

public class DebitPayment implements DepeFoodPaymentSystem {    //class ini merupakan turunan dai class interface DepeFoodPaymentSystem
    public static final double MINIMUM_TOTAL_PRICE = 50000;     //harga minimum pembayaran untuk class debit

    public long processPayment(long amount) {                   //method dari interface yang wajib di override
        if (amount < MINIMUM_TOTAL_PRICE) {
            return -1;                                          //nanti saat di implementasi -1 ini akan menandakan bahwa order tidak bisa < 50000
        }
        return amount;                                          //kalau transaksi >= 50000, maka akan diteruskan
    }
    
    public double getMINIMUM_TOTAL_PRICE() {
        return MINIMUM_TOTAL_PRICE;                             //return harga minimum pembayaran
    }
}
package assignments.assignment3;

import java.util.ArrayList;

public class Order {
    private String orderId;
    private String tanggal;
    private int ongkir;
    private Restaurant resto;
    private boolean orderFinished;
    private ArrayList<Menu> items = new ArrayList<>();

    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, ArrayList<Menu> items){      //membuat constructor berdasarkan UML
        this.orderId = orderId;     
        this.tanggal = tanggal;
        this.ongkir = ongkir;
        this.resto = resto;
        this.items = new ArrayList<>();
        for (Menu item : items) {
            this.items.add(item);
        }
    }   
    //membuat setter dan getter bagi semua atribut dari kelas Order
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getOngkir() {
        return ongkir;
    }

    public void setOngkir(int ongkir) {
        this.ongkir = ongkir;
    }

    public Restaurant getResto() {
        return resto;
    }

    public void setResto(Restaurant resto) {
        this.resto = resto;
    }

    public boolean getOrderFinished() {
        return orderFinished;
    }

    public void setOrderFinished(boolean orderFinished) {
        this.orderFinished = orderFinished;
    }

    public ArrayList<Menu> getItems() {
        return items;
    }

    public void setItems(Menu menu) {
        this.items.add(menu);           //menambahkan menu ke ArrayList items ketika dipanggil
    }

    //return total biaya dari semua menu yang dipesan beserta ongkirnya
    public double getTotalBiaya() {
        double total = 0;
        for (Menu menu : getItems()) {
            total += menu.getHarga();
        }
        return total + getOngkir(); 
    }
}
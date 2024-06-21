package assignments.assignment3;

import java.util.ArrayList;

public class Restaurant {
    private String nama;
    private ArrayList<Menu> menu = new ArrayList<>();   
    private long saldo;

    public Restaurant(String nama){         //membuat constructor berdasarkan UML
        this.nama = nama;
        this.menu = new ArrayList<>();
    }
    //membuat setter dan getter untuk semua atribut di class Restaurant
    public String getNama () {
	    return this.nama;
    }
    
    public void setNama (String nama) {
	    this.nama = nama;
    }

    public ArrayList<Menu> getMenu() {
        return menu;
    }

    public void setMenu (Menu menuInput) {
        this.menu.add(menuInput);            //menambahkan menuInput setiap di set dan dimasukkan ke ArrayList menu
    }

    public long getSaldo() {
        return saldo;
    }

    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }
}
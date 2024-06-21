package  assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
    private String nama;
    private ArrayList<Menu> menu = new ArrayList<>();   

    public Restaurant(String nama){         //membuat constructor berdasarkan UML
        this.nama = nama;
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
}
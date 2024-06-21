package assignments.assignment2;

public class Menu {
    private String namaMakanan;
    private double harga;

    public Menu(String namaMakanan, double harga){      //membuat constructor berdasarkan UML
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }
    //membuat setter dan getter untuk semua atribut kelas Menu
    public String getNamaMakanan() {
        return namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public double getHarga() {
        return harga;
    }

    public  void setHarga(double harga) {
        this.harga = harga;
    }
}
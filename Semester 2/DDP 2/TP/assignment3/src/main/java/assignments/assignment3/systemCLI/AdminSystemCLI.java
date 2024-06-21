package assignments.assignment3.systemCLI;

import java.util.ArrayList;
import java.util.Scanner;

import assignments.assignment3.Menu;
import assignments.assignment3.Restaurant;

//TODO: Extends Abstract yang diberikan
public class AdminSystemCLI extends UserSystemCLI{                      //class ini merupakan subclass dari class abstract UserSystemCLI
    private static final Scanner input = new Scanner(System.in);
    public static ArrayList<Restaurant> restoList = new ArrayList<>();

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    boolean handleMenu(int command){
        switch(command){
            case 1 -> handleTambahRestoran();
            case 2 -> handleHapusRestoran();
            case 3 -> {return false;}
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    void displayMenu() {
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    protected void handleTambahRestoran(){
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
        System.out.println("--------------Tambah Restoran--------------");
        String namaRestoran;
        int jumlahMakanan;
        outer:
        while (true) {
            System.out.print("Nama: ");
            namaRestoran = input.nextLine();
            for (Restaurant resto : restoList) {        //input namaRestoran tidak boleh ada di restoList
                if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
                    System.out.println("Restoran dengan nama " + namaRestoran + " sudah pernah terdaftar. Mohon masukkan nama yang berbeda!\n");
                    continue outer;
                }
            }
            if (namaRestoran.length() < 4) {    //nama restoran harus sepanjang paling tidak 4 char
                System.out.println("Nama restoran tidak valid!\n");
                continue;
            }
            else {
                Restaurant newRestaurant = new Restaurant(namaRestoran);
                System.out.print("Jumlah Makanan: ");
                jumlahMakanan = input.nextInt();
                input.nextLine();
                ArrayList<String> validasiMenu = new ArrayList<>();
                for (int i = 0; i < jumlahMakanan; i++) {       //di input dulu semua menu, lalu nanti akan di validasi
                    String inputLine = input.nextLine();
                    validasiMenu.add(inputLine);
                }
                for (String menu : validasiMenu) {
                    String[] inputSplit = new String[2];    
                    for (int j = 0; j < menu.length(); j++) {
                        if (Character.isDigit(menu.charAt(j))) {    //jika char berupa angka, maka akan di split
                            inputSplit[0] = menu.substring(0, j - 1);
                            inputSplit[1] = menu.substring(j);
                            break;
                        }
                    }
                    String namaMakanan = inputSplit[0];
                    double newHarga;
                    try {
                        newHarga = Double.parseDouble(inputSplit[1].trim());   //di cek apakah semua char di harga tadi semuanya berupa digit
                    }
                    catch (NumberFormatException e) {       //jika ternyata harga tidak semuanya berupa char digit, maka akan mengulang loop
                        System.out.println("Harga menu harus bilangan bulat!\n");
                        continue outer;
                    }
                    newRestaurant.setMenu(new Menu(namaMakanan, newHarga));     //menambah menu ke restoran yang ditambah
                }
                restoList.add(newRestaurant);     //menambah restoran yang sudah jadi ke restoList
                System.out.println("Restaurant " + namaRestoran + " Berhasil terdaftar.");
                break;
            }
        }
    }

    protected void handleHapusRestoran(){
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
        System.out.println("--------------Hapus Restoran--------------");
        String namaRestoran;
        outer:
        while (true) {
            System.out.print("Nama Restoran: ");
            namaRestoran = input.nextLine();
            for (Restaurant resto : restoList) {        //mengecek apakah namaRestoran ada di restoList
                if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
                    restoList.remove(resto);        //jika ada, maka resto itu akan di buang dari restoList
                    System.out.println("Restoran berhasil dihapus.");
                    break outer;
                }
            }
            System.out.println("Restoran tidak terdaftar pada sistem.\n");
            continue;
        }
    }
}
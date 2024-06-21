package assignments.assignment1;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);

    public static void showMenu(){
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

    public static void altShowMenu() {      //method ketika while loop diulang sebagai show menu tanpa header DepeFood
        System.out.println("Pilih menu:");
        System.err.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
        System.out.println("---------------------------------------------");
    }
   
    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {      //method untuk menciptakan OrderID menurut nama restoran, tanggal order, dan nomor telepon menurut input user
        String res = "";                                        //variabel untuk menyimpan String yang akan di return sebagai Order ID
        for (int j = 0; j < namaRestoran.length(); j++) {       //mengecek validasi dari nama restoran
            if (res.length() < 4) {
                if (namaRestoran.charAt(j) != ' ') {    //character yang diambil hanya berupa huruf
                    res += Character.toUpperCase(namaRestoran.charAt(j));   //character tadi otomatis di kapitalkan dan dimasukkan ke res
                }
            }
            else {
                break;
            }
        }

        for (int k = 0; k < tanggalOrder.length(); k++) {
            if (Character.isDigit(tanggalOrder.charAt(k))) {       //mengecek character apakah character tersebut berupa bilangan atau digit
                res += tanggalOrder.charAt(k);                     //res dimasukkan jika character tadi berupa digit
            }
        }

        int sum = 0;       //sum untuk 2 digit setelah tanggal, jadi nomor telepon dicari sum nya lalu di mod 100, jika dibawah 10, maka akan ditambahkan 0 diawal
        for (int l = 0; l < noTelepon.length(); l++) {
            sum += Character.getNumericValue(noTelepon.charAt(l));      //menambahkan sum dengan masing2 character nomor telepon yang berupa nilai numerik
        }
        int mod = sum % 100;    //sum tadi di mod dengan 100
        if (mod < 10) {
            res += "0" + mod;   //jika mod kurang dari 10, maka di res, sebelum angka itu ditambahkan 0
        }
        else {
            res += mod;
        }
        res += checksum(res);
        return res;
    }

    public static String generateBill(String OrderID, String lokasi){       //method untuk menciptakan bill atau struk bagi Order ID dan lokasi yang di input user
        String hargaAkhir = "";
        lokasi = lokasi.toUpperCase();      //lokasi harus diubah ke kapital
        switch (lokasi) {                   //switch case menentukan lokasi serta dan harganya
            case "P": hargaAkhir = "Rp 10.000";
                break;
            case "U": hargaAkhir = "Rp 20.000"; 
                break;
            case "T": hargaAkhir = "Rp 35.000";
                break;
            case "S": hargaAkhir = "Rp 40.000";
                break; 
            case "B": hargaAkhir = "Rp 60.000"; 
                break;
            default:
                break;
        }
        return "Bill:\nOrder ID: " + OrderID + "\nTanggal Pemesanan: " + OrderID.substring(4, 6) + "/" + 
                OrderID.substring(6, 8) + "/" + OrderID.substring(8, 12) + "\nLokasi Pengiriman: " + 
                lokasi + "\nBiaya Ongkos Kirim: " + hargaAkhir + "\n";
    }

    public static boolean isInteger(String s) {     //method yang mengecek apakah suatu character berupa Integer
        for (int n = 0; n < s.length(); n++) {
            if (Character.isDigit(s.charAt(n))) {       //cek melewat loop satu per satu apakah character tersebut berupa digit
                continue;
            }
            else {
                return false;       //kalau ada yg tidak, langsung return false
            }
        }
        return true;
    }

    public static String checksum(String temp) {
        String hasil = "";
        int checksum1 = 0;
        int checksum2 = 0;
        for (int m = 0; m < temp.length(); m++) {
            int code;       //code untuk ditambahkan ke checksum, kalau index lagi genap, akan ditambahkan ke checksum1, jika index lagi ganjil, maka checksum2 yang akan ditambahkan
            if (Character.isDigit(temp.charAt(m))) {
                code = Character.getNumericValue(temp.charAt(m));   //code akan tetap jika character memang sebuah digit
            }
            else {
                code = temp.charAt(m) - 'A' + 10;       //jika bukan digit, maka ascii code nya di kurang dengan 'A' dna ditambah 10
            }
            if (m % 2 == 0) {
                checksum1 += code;
            }
            else {
                checksum2 += code;
            }
        }
        int modChecksum1 = checksum1 % 36;      //hasil checksum 1 dan 2 di mod 36
        int modChecksum2 = checksum2 % 36;
        char charChecksum1;
        char charChecksum2;
        if (modChecksum1 > 9) {
            charChecksum1 = (char) (modChecksum1 - 10 + 'A');       //jika mod > 9, maka akan di convert sesuai dengan ascii code A keatas
            hasil += charChecksum1;
        }
        else {
            hasil += modChecksum1;
        }
        if (modChecksum2 > 9) {
            charChecksum2 = (char) (modChecksum2 - 10 + 'A');
            hasil += charChecksum2;
        }
        else {
            hasil += modChecksum2;
        }
        return hasil;
    }

    public static boolean correctID(String OrderID) {       //methood untuk mengecek apakah OrderID yang di input user valid dan bisa dipakai
        String validasi = OrderID.substring(0, OrderID.length() - 2);       //mengambil Order ID tanpa 2 character terakhir yaitu chcecksum 1 dan 2x
        validasi += checksum(validasi);
        return validasi.equals(OrderID);        //me return apakah ID validasi sama dengan input user
    }

    public static boolean dateValid (String tanggalOrder) {     //mengecek validasi tanggal dengan format DD/MM/YYYY
        DateTimeFormatter tanggal = DateTimeFormatter.ofPattern("DD/MM/YYYY");
        try {
            tanggal.parse(tanggalOrder);
            return true;
    } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        showMenu();
        ArrayList<String> listLokasi = new ArrayList<>();       //ArrayList untuk validasi apakah lokasi input user ada di jangkauan
        listLokasi.add("P");
        listLokasi.add("U");
        listLokasi.add("T");
        listLokasi.add("S");
        listLokasi.add("B");
        while (true) {
            System.out.print("Pilihan menu: ");
            int opsi = input.nextInt();         //mengambil input user dari pilihan menu
            if (opsi == 3) {                    //menutup program jika user memilih opsi 3
                System.out.println("Terima kasih telah menggunakan DepeFood!");
                break;
            }
            else if (opsi == 1) {               //Jika yang dipilih opsi 1, maka akan dijalankan method generateOrderBill
                String namaRestoran = "";
                String tanggalOrder = "";
                String noTelepon = "";
                input.nextLine();
                while (true) {
                    System.out.print("Nama Restoran: ");
                    namaRestoran = input.nextLine();        //nama restoran yang akan dipakai untuk membuat order ID
                    int count = 0;
                    for (int i = 0; i < namaRestoran.length(); i++) {
                        if (namaRestoran.charAt(i) == ' ') {        //character whitespace tidak akan masuk dinama
                            continue;
                        }
                        else {
                            count++;
                        }
                    }
                    if (count < 4) {
                        System.out.println("Nama Restoran tidak valid!\n");     //nama restoran tidak valid jika nama restoran tidak memiliki 4 atau lebih huruf
                        continue;
                    }
                    System.out.print("Tanggal Pemesanan: ");
                    tanggalOrder = input.nextLine();
                    if (!dateValid(tanggalOrder)) {
                        System.out.println("Tanggal pemesanan dalam format DD/MM/YYYY!\n");     //tanggal harus memenuhi format DD/MM/YYYY
                        continue;
                    }
                    System.out.print("No. Telepon: ");
                    noTelepon = input.nextLine();
                    if (noTelepon.startsWith("-") || !isInteger(noTelepon)) {       //nomor telepon harus berupa bilangan buklat yang positif
                        System.out.println("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.\n");
                        continue;
                    }
                    else {
                        break;  
                    }
                }
                System.out.println("Order ID " + generateOrderID(namaRestoran, tanggalOrder, noTelepon) + " diterima!");  //method generateOrderID dijalankan
                System.out.println("---------------------------------------------");
                altShowMenu();
            }
            else if (opsi == 2) {
                String validasiID = "";
                input.nextLine();
                while (true) {
                    System.out.print("Order ID: ");
                    validasiID = input.nextLine();
                    if (validasiID.length() != 16) {
                        System.out.println("Order ID minimal 16 karakter");     //Order ID harus 16 karakter
                        continue;
                    }
                    if (!correctID(validasiID)) {
                        System.out.println("Silahkan masukkan Order ID yang valid!");   //Order ID harus valid menurut method correctID
                        continue;
                    }
                    if (correctID(validasiID) && validasiID.length() == 16) {       //jika sudah benar maka akan keluar dari loop
                        break;
                    }
                }
                String lokasi = "";
                while (true) {
                    System.out.print("\nLokasi Pengiriman: ");
                    lokasi = input.nextLine();
                    if (!listLokasi.contains(lokasi.toUpperCase())) {    //lokasi harus pdada jangkauan , yang ada pada jangkauan ada di ArrayList listLokasi
                        System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");     
                    }
                    else {
                        break;
                    }
                }
                System.out.println(generateBill(validasiID, lokasi));       //print bill ketika semua valid
                System.out.println("---------------------------------------------");
                altShowMenu();
            }
        }
    } 
}
package assignments.assignment2;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList = new ArrayList<>();
    private static ArrayList<User> userList = new ArrayList<>();

    public static void main(String[] args) {
        initUser();
        boolean programRunning = true;
        while(programRunning){
            printHeader();
            startMenu();
            int command = input.nextInt();
            input.nextLine();

            if(command == 1){
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();

                User userLoggedIn = MainMenu.getUser(nama, noTelp); 
                boolean isLoggedIn = true;
                //sesuai ketentuan getUser, jika user tidak ada maka nomorTelepon di set -1 maka otomatis akan menjalankan ini
                if (userLoggedIn.getNomorTelepon().charAt(0) == '-') {      
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
                    continue;
                }
                //jika memang valid, kalau namanya "Admin" atau "Admin Baik" maka akan di set role "Admin"
                if (nama.equals("Admin") || nama.equals("Admin Baik")) {   
                    userList.add(userLoggedIn);
                    userLoggedIn.setRole("Admin");
                }
                else {      //selain itu akan di set role "Costumer"
                    userList.add(userLoggedIn);
                    userLoggedIn.setRole("Customer");
                }
                System.out.println("Selamat Datang " + userLoggedIn.getNama() + "!");
                //menu akan berbeda sesuai dengan role user
                if(userLoggedIn.getRole() == "Customer"){
                    while (isLoggedIn){
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        switch(commandCust){
                            case 1 -> handleBuatPesanan(userLoggedIn);
                            case 2 -> handleCetakBill(userLoggedIn);
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan(userLoggedIn);
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }else{
                    while (isLoggedIn){
                        menuAdmin();
                        int commandAdmin = input.nextInt();
                        input.nextLine();

                        switch(commandAdmin){
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
                            case 3 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }
            }else if(command == 2){
                programRunning = false;
            }else{
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
    }

    public static boolean dateValid (String tanggalPemesanan) {     //validasi tanggal yang di input user
        DateTimeFormatter tanggal = DateTimeFormatter.ofPattern("DD/MM/YYYY");      //di set pattern atau template tanggal DD/MM/YYYY
        try {
            tanggal.parse(tanggalPemesanan);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String checksum(String temp) {        //return checksum untuk membuat orderID
        String hasil = "";
        int checksum1 = 0;
        int checksum2 = 0;
        for (int m = 0; m < temp.length(); m++) {
            int code;       
            if (Character.isDigit(temp.charAt(m))) {
                code = Character.getNumericValue(temp.charAt(m));   
            }
            else {
                code = temp.charAt(m) - 'A' + 10;       
            }
            if (m % 2 == 0) {
                checksum1 += code;
            }
            else {
                checksum2 += code;
            }
        }
        int modChecksum1 = checksum1 % 36;      
        int modChecksum2 = checksum2 % 36;
        char charChecksum1;
        char charChecksum2;
        if (modChecksum1 > 9) {
            charChecksum1 = (char) (modChecksum1 - 10 + 'A');       
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

    public static User getUser(String nama, String nomorTelepon){
        for (User user : userList) {    //di cek satu-satu anggota userList apakah ada atau tidak sesuai dari input nama dan nomorTelepon
            if (user.getNama().equals(nama) && user.getNomorTelepon().equals(nomorTelepon)) {
                return user;        //jika nama dan nomorTelepon sesuai maka user akan di return    
            }
        }
        return new User(nama, "-1", nomorTelepon, nama, nomorTelepon);  //kalau tidak ada maka nomorTelepon dari user akan di set ke -1
    }

    public static String statusType(boolean getOrderFinished) {     //me-check value getOrderFinished, "Not Finished" jika false, "Selesai" jika true
        if (getOrderFinished == false) {
            return "Not Finished";
        }
        else {
            return "Selesai";
        }
    }

    public static void handleBuatPesanan(User user){        //jika buat pesanan di pilih di menu costumer
        System.out.println("--------------Buat Pesanan--------------");
        String idPesanan;
        String namaRestoran;
        String tanggalPemesanan;
        int jumlahPesanan;
        while (true) {
            int indexResto = 0;
            idPesanan = "";
            System.out.print("Nama Restoran: ");
            namaRestoran = input.nextLine();
            boolean ada = false;
            for (Restaurant resto : restoList) {   //cek tiap resto di restoList, jika ada, ada di set true dan program berjalan 
                if (resto.getNama().equalsIgnoreCase(namaRestoran)) {   
                    ada = true;
                }
                else {
                    indexResto++;       //indexResto di increment
                }
            }
            if (ada == false) {     //jika namaRestoran tidak ada di restoList, maka akan me-loop dan meminta namaRestoran lagi
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            Restaurant restoran = restoList.get(indexResto);    //restoran yang tadi di input adalah resto yang di index indexResto
            ArrayList<Menu> menuRestoran = restoran.getMenu();
            int count = 0;
            for (int i  = 0; i < namaRestoran.length(); i++) {  //cek apakah nama restoran tidak include whitespace mempunyai 4 char
                if (idPesanan.length() < 4) {
                    if (namaRestoran.charAt(i) != ' ') {    
                        idPesanan += Character.toUpperCase(namaRestoran.charAt(i));
                        count++;  
                    }
                }
                else {
                    break;
                }
            }
            if (count < 4) {
                System.out.println("Nama Restoran tidak valid!\n");     
                continue;
            }
            
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            tanggalPemesanan = input.nextLine();
            if (!dateValid(tanggalPemesanan)) {     //cek validasi tanggal sesuai dengan format
                System.out.println("Masukkan tanggal sesuai format DD/MM/YYYY!\n");   
                continue;
            }
            for (int i = 0; i < tanggalPemesanan.length(); i++) {       
                if (Character.isDigit(tanggalPemesanan.charAt(i))) {       
                    idPesanan += tanggalPemesanan.charAt(i);                    
                }
            }
            int sum = 0;      
            for (int l = 0; l < user.getNomorTelepon().length(); l++) {
                sum += Character.getNumericValue(user.getNomorTelepon().charAt(l));      
            }
            int mod = sum % 100;   
            if (mod < 10) {
                idPesanan += "0" + mod;   
            }
            else {
                idPesanan += mod;
            }
            System.out.print("Jumlah Pesanan: ");
            idPesanan += checksum(idPesanan);     

            ArrayList<String> pesananList = new ArrayList<>();

            jumlahPesanan = input.nextInt();
            System.out.println("Order:");
            input.nextLine();
            for (int i = 0; i < jumlahPesanan; i++) {       //di input dulu semua pesanan baru nanti di validasi
                String addList = input.nextLine();
                pesananList.add(addList);
            }
   
            ArrayList<Menu> pesananValid = new ArrayList<>();
            for (String pesanan : pesananList) {        //di cek tiap pesanan apakah ada di menu restoran, maka menu tersebut masuk ke ArrayList pesananValid
                for (Menu menu : menuRestoran) {
                    if (menu.getNamaMakanan().equals(pesanan)) {
                        pesananValid.add(new Menu(menu.getNamaMakanan(), menu.getHarga()));
                    }
                }
            }
            if (pesananValid.size() != pesananList.size()) {        //jika tidak semua pesanan valid, maka akan mengulang loop
                System.out.println("Mohon memesan menu yang tersedia di Restoran!\n");
                continue;
            }
            int biayaOngkir = 0;
            String lokasiPemesan = user.getLokasi();
            switch (lokasiPemesan) {        //memasang biayaOngkir berdasarkan lokasi sang user
                case "P": biayaOngkir = 10000;
                    break;
                case "U": biayaOngkir = 20000;
                    break;
                case "T": biayaOngkir = 35000;
                    break;
                case "S": biayaOngkir = 40000;
                    break; 
                case "B": biayaOngkir = 60000; 
                    break;
                default:
                    break;
            }
            Order newOrder = new Order(idPesanan, tanggalPemesanan, biayaOngkir, restoran, pesananValid);  //membuat order baru berdasarkan parameter yang telah dibuat tadi

            newOrder.setOrderFinished(false);       //order akan otomatis di set "Not Finished"
            System.out.println("Pesanan dengan ID " + idPesanan + " diterima!");
            user.setOrderHistory(newOrder);     //akan set orderHistory sang user dengan order yang barusan dibuat
            break;
        }
    }

    public static void handleCetakBill(User user){      //jika cetak bill di pilih di menu costumer
        System.out.println("--------------Cetak Bill--------------");
        while (true) {
            int biaya = 0;      //variabel total biaya yang perlu dibayar user
            System.out.print("Masukkan Order ID: ");
            String validasiID = input.nextLine();       //memasuki orderID dulu yang akan divalidasi terlebih dahulu
            Order rightOrder = null;
            String status;
            for (Order order : user.getOrderHistory()) {
                if (order.getOrderId().equals(validasiID)) {
                    rightOrder = order;                 //jika memang orderID nya ada di history user, maka akan di set ke rightOrder
                    break;
                }
            }
            if (rightOrder == null) {       //jika memang orderID tidak ada, maka akan mengulang loop lagi
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            status = statusType(rightOrder.getOrderFinished());     //status sesuai dengan statusType dari rightOrder
            biaya += rightOrder.getOngkir();    //biaya akan ditambahkan biaya ongkir di order tadi
            System.out.println("\nBill:\nOrder ID: " + validasiID + "\nTanggal Pemesanan: " + rightOrder.getTanggal() +
                               "\nRestaurant: " + rightOrder.getResto().getNama() + "\nLokasi Pengiriman: " + user.getLokasi() + 
                               "\nStatus Pengiriman: " + status + "\nPesanan:");
            for (Menu pesanan : rightOrder.getItems()) {       //setiap menu pesanan akan di print dengan nama dan harganya
                System.out.println("- " + pesanan.getNamaMakanan() + " " + (int) pesanan.getHarga());
                biaya += pesanan.getHarga();        //biaya akan ditambah setiap harga menu yang dipesan
            }
            System.out.println("Biaya Ongkos Kirim: Rp " + rightOrder.getOngkir() + "\nTotal Biaya: Rp " + biaya);
            break;
        } 
    }

    public static void handleLihatMenu(){   //jika lihat menu di pilih di menu costumer
        System.out.println("--------------Lihat Menu--------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String validasiResto = input.nextLine();
            Restaurant resto = null;
            for (Restaurant restaurant : restoList) {       //cek validasi nama restoran ada atau tidak di restoList
                if (restaurant.getNama().equalsIgnoreCase(validasiResto)) {
                    resto = restaurant;
                }
            }
            if (resto == null) {
                System.out.println("Restoran tidak terdaftar dalam sistem.\n");
            }
            ArrayList<Menu> menuList = resto.getMenu();
            for (int i = 0; i < menuList.size(); i++) {     //saya menggunakan bubble sort untuk sorting menu sesuai dengan harga dan namanya
                for (int j = 0; j < menuList.size() - i - 1; j++) {
                    if (menuList.get(j).getHarga() > menuList.get(j + 1).getHarga() || //jika harga di index j lebih dari index j+1 maka akan ditukar 
                    (menuList.get(j).getHarga() == menuList.get(j + 1).getHarga() &&   //jika harga sama, maka akan dibandingkan namanya sesuai urutan alfabet 
                    menuList.get(j).getNamaMakanan().compareToIgnoreCase(menuList.get(j + 1).getNamaMakanan()) > 0)) { //jika urutan alfabet index j lebih dari index j+1 mak aakan ditukar
                        Menu tempMenu =  menuList.get(j);       //menaruh value index j ke sini sementara untuk nanti dimasuki  
                        menuList.set(j, menuList.get(j + 1));   //menukar value index j dengan value j+1
                        menuList.set(j + 1, tempMenu);          //menaruh value temp tadi yang berisi value index j di index j+1
                    }
                }
            }
            System.out.println("Menu:");
            for (int i = 0; i < menuList.size(); i++) {
                System.out.println(i + 1 + ". " + menuList.get(i).getNamaMakanan() + " " + (int) menuList.get(i).getHarga());
            }
            break;
        }   
    }

    public static void handleUpdateStatusPesanan(User user){    //jika update status pesanan di pilih di menu costumer
        System.out.println("--------------Update Status Pesanan--------------");
        while (true) {
            System.out.print("Masukkan Order ID: ");
            String validasiID = input.nextLine();
            Order rightOrder = null;
            for (Order order : user.getOrderHistory()) {        //cek apakah order ada di orderHistory user
                if (order.getOrderId().equals(validasiID)) {
                    rightOrder = order;
                    break;
                }
            }
            if (rightOrder == null) {
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            String currentStatus = statusType(rightOrder.getOrderFinished());   //mengambil statysType order tadi
            System.out.print("Status: ");
            String newStatus = input.nextLine();
            if (newStatus.equals(currentStatus)) {      //jika status yang di input sama saja, maka status tadi tidak diubah
                System.out.println("Status pesanan dengan ID " + rightOrder.getOrderId() + " tidak berhasil diupdate!\n");
                continue;
            }
            rightOrder.setOrderFinished(!rightOrder.getOrderFinished());        //mengubah status sesuai input
            System.out.println("Status pesanan dengan ID " + rightOrder.getOrderId() + " berhasil diupdate!");
            break;
        }
    }

    public static void handleTambahRestoran(){  //jika tambah restoran di pilih di menu admin
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

    public static void handleHapusRestoran(){ //jika hapus restoran di pilih di menu admin
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

    public static void initUser(){
       userList = new ArrayList<User>();
       userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
       userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
       userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
       userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
       userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

       userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
       userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    public static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu(){
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }
}
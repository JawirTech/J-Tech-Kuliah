package assignments.assignment3.systemCLI;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import assignments.assignment3.MainMenu;
import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;

//TODO: Extends abstract class yang diberikan
public class CustomerSystemCLI extends UserSystemCLI{                           //class ini merupakan subclass dari class abstract UserSystemCLI
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList = AdminSystemCLI.restoList;
    private static User user;
    private static Restaurant restoran = null;

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    boolean handleMenu(int choice){
        switch(choice){
            case 1 -> handleBuatPesanan();
            case 2 -> handleCetakBill();
            case 3 -> handleLihatMenu();
            case 4 -> handleBayarBill();
            case 5 -> handleCekSaldo();
            case 6 -> {
                return false;
            }
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    void displayMenu() {
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Bayar Bill");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static Order getOrder(User user, String orderId) {
        for (Order order : user.getOrderHistory()) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    public static void cetakBill(String orderId) {      //method untuk mencetak bill sesuai dengan order ID
        while (true) {
            int biaya = 0;      //variabel total biaya yang perlu dibayar user
            Order rightOrder = null;
            String status;
            for (Order order : user.getOrderHistory()) {
                if (order.getOrderId().equals(orderId)) {
                    rightOrder = order;                 //jika memang orderID nya ada di history user, maka akan di set ke rightOrder
                    break;
                }
            }
            if (rightOrder == null) {       //jika memang orderID tidak ada, maka akan mengulang loop lagi
                System.out.println("Order ID tidak dapat ditemukan.\n");
                break;
            }
            status = statusType(rightOrder.getOrderFinished());     //status sesuai dengan statusType dari rightOrder
            biaya += rightOrder.getOngkir();    //biaya akan ditambahkan biaya ongkir di order tadi
            System.out.println("\nBill:\nOrder ID: " + orderId + "\nTanggal Pemesanan: " + rightOrder.getTanggal() +
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

    public static String statusType(boolean getOrderFinished) {     //me-check value getOrderFinished, "Not Finished" jika false, "Selesai" jika true
        if (getOrderFinished == false) {
            return "Not Finished";
        }
        else {
            return "Selesai";
        }
    }

    void handleBuatPesanan(){
        user = MainMenu.userLoggedIn;
        // TODO: Implementasi method untuk handle ketika customer membuat pesanan
        System.out.println("--------------Buat Pesanan--------------");
        String idPesanan;
        String namaRestoran;
        String tanggalPemesanan;
        int jumlahPesanan;
        while (true) {
            idPesanan = "";
            System.out.print("Nama Restoran: ");
            namaRestoran = input.nextLine();
            boolean ada = false;
            for (Restaurant resto : restoList) {   //cek tiap resto di restoList, jika ada, ada di set true dan program berjalan 
                if (resto.getNama().equalsIgnoreCase(namaRestoran)) {   
                    restoran = resto;
                    ada = true;
                }
            }
            if (ada == false) {     //jika namaRestoran tidak ada di restoList, maka akan me-loop dan meminta namaRestoran lagi
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
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

    void handleCetakBill(){
        // TODO: Implementasi method untuk handle ketika customer ingin cetak bill
        System.out.println("--------------Cetak Bill--------------");
        System.out.print("Masukkan Order ID: ");
        String validasiID = input.nextLine();       //memasuki orderID dulu yang akan divalidasi terlebih dahulu
        cetakBill(validasiID);
    }

    void handleLihatMenu(){
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
        System.out.println("--------------Lihat Menu--------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String validasiResto = input.nextLine();
            for (Restaurant resto : restoList) {       //cek validasi nama restoran ada atau tidak di restoList
                if (resto.getNama().equalsIgnoreCase(validasiResto)) {
                    restoran = resto;
                }
            }
            if (restoran == null) {
                System.out.println("restoranran tidak terdaftar dalam sistem.\n");
            }
            ArrayList<Menu> menuList = restoran.getMenu();
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

    void handleBayarBill(){     //method untuk membayar bill sesuai dengan order ID
        user = MainMenu.userLoggedIn;
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
        System.out.println("--------------Bayar Bill--------------");
        System.out.print("Masukkan Order ID: ");
        String validasiID = input.nextLine();       //memasuki orderID dulu yang akan divalidasi terlebih dahulu
        Order order = getOrder(user, validasiID);
        if (statusType(order.getOrderFinished()).equals("Selesai")) {       //kalau order sudah dibayar, maka order tidak bisa dibayar lagi
            System.out.println("Pesanan dengan ID ini sudah lunas!");
        }
        else {
            cetakBill(validasiID);
            System.out.print("\nOpsi Pembayaran:\n1. Credit Card\n2. Debit\nPilihan Metode Pembayaran: ");
            int pilihan = input.nextInt();
            if (pilihan == 1) {    //jika membayar dengan credit card
                if (user.getPayment() instanceof CreditCardPayment) {       //cek apakah user memiliki metode pembayaran credit card
                    CreditCardPayment creditCardPayment = (CreditCardPayment) user.getPayment();
                    long biayaTanpaBiayaTransaksi = (long) order.getTotalBiaya();       //biaya tanpa biaya transaksi untuk nanti di print
                    long biayaTransaksi = creditCardPayment.countTransactionFee((long) order.getTotalBiaya());  //biaya dengan biaya transaksi untuk nanti di print
                    if (user.getSaldo() > creditCardPayment.processPayment((long) order.getTotalBiaya())) {     //cek apakah saldo user cukup untuk membayar
                        System.out.println("Berhasil membayar bill sebesar Rp " + biayaTanpaBiayaTransaksi + " dengan biaya transaksi sebesar Rp " + biayaTransaksi);
                        user.setSaldo(user.getSaldo() - creditCardPayment.processPayment((long) order.getTotalBiaya()));  //set saldo user dengan mengurangi saldo awal dengan biaya yang sudah diproses
                        order.getResto().setSaldo(order.getResto().getSaldo() + creditCardPayment.processPayment((long) order.getTotalBiaya())); //set saldo restoran dengan menambah saldo awal dengan biaya yang sudah diproses
                        order.setOrderFinished(true);       //set kondisi pembayaran ke selesai
                    }
                    else {
                        System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain");
                    }
                }
                else {
                    System.out.println("User belum memiliki metode pembayaran ini!");     //jika user belum memiliki metode pembayaran credit card
                }
            }
            else if (pilihan == 2) {     //jika membayar dengan debit
                if ((user.getPayment() instanceof DebitPayment)) {        //cek apakah user memiliki metode pembayaran debit
                    DebitPayment debitPayment = (DebitPayment) user.getPayment();
                    long prosesPembayaran = debitPayment.processPayment((long) order.getTotalBiaya());
                    if (prosesPembayaran < 0) {     //jika prosesPembayaran tadi bernilai -1, tadi maka pembayaran gagal
                        System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain");
                    }
                    else {
                        if (order.getTotalBiaya() < debitPayment.getMINIMUM_TOTAL_PRICE()) {        //total biaya harus >= 50000
                            System.out.println("Jumlah pesanan < 50000 mohon menggunakan metode pembayaran yang lain");
                        }
                        else {
                            System.out.println("Berhasil Membayar Bill sebesar Rp " + prosesPembayaran);
                            user.setSaldo(user.getSaldo() - prosesPembayaran);
                            order.getResto().setSaldo(order.getResto().getSaldo() + prosesPembayaran);
                            order.setOrderFinished(true);
                        }
                    }
                }
                else {
                    System.out.println("User belum memiliki metode pembayaran ini!");
                }
            }
        }
    }

    void handleCekSaldo(){
        // TODO: Implementasi method untuk handle ketika customer ingin update status pesanan
        user = MainMenu.userLoggedIn;
        System.out.println("Sisa saldo sebesar Rp " + user.getSaldo());     //print sisa saldo
    }
}
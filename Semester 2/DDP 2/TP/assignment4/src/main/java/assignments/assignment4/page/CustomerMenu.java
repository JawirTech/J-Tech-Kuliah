package assignments.assignment4.page;

import java.util.ArrayList;

import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.BillPrinter;
import assignments.assignment4.components.form.LoginForm;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CustomerMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private BillPrinter billPrinter; // Instance of BillPrinter
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private static Label label = new Label();
    private MainApp mainApp;
    private User user;
    private boolean tanggalValid;
    private Restaurant restaurant;
    private ArrayList<Menu> listMenuDipesan;
    private ArrayList<String> namaMenuDipesan;
    private Order order;
    private static String orderId;
    private static int biayaOngkir;
    private boolean idExist;
    private int pilihanPembayaran;

    public CustomerMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addOrderScene = createTambahPesananForm();
        this.billPrinter = new BillPrinter(stage, mainApp, this.user); // Pass user to BillPrinter constructor
        this.printBillScene = createBillPrinter();
        this.payBillScene = createBayarBillForm();
        this.cekSaldoScene = createCekSaldoScene();
    }

    @Override
    protected void refresh() {  //implementasi refresh() di class ini untuk refresh data aplikasi
        this.addOrderScene = getAddOrderScene();
        this.printBillScene = getBillPrinterScene();
        this.payBillScene = getPayBillScene();
        this.cekSaldoScene = getCekSaldoScene();
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    public static String getOrderId() {
        return orderId;
    }

    public static int getBiayaOngkir() {
        return biayaOngkir;
    }

    @Override
    public Scene createBaseMenu() { //implementasi abstract method superclass untuk menampilkan tampilan utama dari CustomerMenu
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);    //set posisi vbox ke tengah2 scene

        Button buatPesananButton = new Button("Buat Pesanan");
        buatPesananButton.setOnAction(e -> {
            refresh();
            mainApp.setScene(createTambahPesananForm());    //tampilkan layar saat menambahkan pesanan
        });

        Button cetakBillButton = new Button("Cetak Bill");
        cetakBillButton.setOnAction(e -> {
            refresh();
            mainApp.setScene(createBillPrinter());  //tampilkan layar saat ingin nge print bill
        });

        Button bayarBillButton = new Button("Bayar Bill");
        bayarBillButton.setOnAction(e -> {
            refresh();
            mainApp.setScene(createBayarBillForm());         //tampilkan layar saat ingin bayar bill
        });

        Button cekSaldoButton = new Button("Cek Saldo");
        cekSaldoButton.setOnAction(e -> {
            refresh();
            mainApp.setScene(createCekSaldoScene());        //tampilkan layar saat ingin mengecek saldo user
        });

        Button logOutButton = new Button("Log Out");
        logOutButton.setOnAction(e -> {
            stage = (Stage) logOutButton.getScene().getWindow();
            LoginForm loginForm = new LoginForm(stage, mainApp);    //balik ke LoginForm ketika logout
            stage.setScene(loginForm.getScene());
            stage.show();
        });

        menuLayout.getChildren().addAll(buatPesananButton, cetakBillButton, bayarBillButton, cekSaldoButton, logOutButton);
        return new Scene(menuLayout, 400, 600);
    }

    private Scene createTambahPesananForm() {
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);    //set posisi vbox ke tengah2 scene
        
        Label restaurantLabel = new Label("Restaurant:");
        restaurantLabel.setTextFill(Color.BLACK);

        ArrayList<String> restaurantNameList = new ArrayList<>();
        for (Restaurant resto : MemberMenu.restoList) {
            restaurantNameList.add(resto.getNama());
        }
        restaurantComboBox = new ComboBox<>(FXCollections.observableArrayList(restaurantNameList)); //combobox yang hanya menampilkan restaurant yang ada
        restaurantComboBox.setPromptText("Select a restaurant");

        ListView<String> menuItemsView = new ListView<>();  //ListView untuk nanti menampilkan dan akan dipesan user
        menuItemsView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    
        Label dateLabel = new Label("Date (DD/MM/YYYY):");
        dateLabel.setTextFill(Color.BLACK);

        TextField dateField = new TextField();
        dateField.setPromptText("Enter date (DD/MM/YYYY)");

        Button menuButton = new Button("Menu");
        menuButton.setOnAction(e -> {
            if (dateField.getText().isEmpty()) {        //harus ada tanggal pemesanan
                showAlert("Warning!", "Warning!", "Please enter the date of the order", AlertType.WARNING);
            }
            else {
                for (Restaurant resto : MemberMenu.restoList) {
                    if (restaurantComboBox.getValue().equals(resto.getNama())) {
                        restaurant = resto; //set golbal variabel restaurant kepada nama restaurant yang di input untuk sementara
                        break;
                    }
                }
                if (CustomerSystemCLI.dateValid(dateField.getText()) == true) {
                    tanggalValid = true;
                }
                if (tanggalValid == true) {     //tampilkan menu2 saat tanggal valid
                    menuItemsView.getItems().clear();
                    for (Menu menu : restaurant.getMenu()) {    
                        menuItemsView.getItems().add(menu.getNamaMakanan());
                    }
                }
                else {
                    showAlert("Error!", "Error!", "Masukkan tanggal sesuai format (DD/MM/YYYY)", AlertType.ERROR);
                }
            }
        });
        
        Label menuItemsLabel = new Label("Menu Items:");
        menuItemsLabel.setTextFill(Color.BLACK);

        Button buatPesananButton = new Button("Buat Pesanan");
        buatPesananButton.setOnAction(e -> {
            namaMenuDipesan = new ArrayList<>();
            for (String menuDipesan : menuItemsView.getSelectionModel().getSelectedItems()) {   //dari menu2 tadi yang dipili user ditaruh ke namaMenuDipesan
                namaMenuDipesan.add(menuDipesan);
            }
            listMenuDipesan = new ArrayList<>();
            for (Menu menu : restaurant.getMenu()) {
                for (String namaMenu : namaMenuDipesan) {
                    if (menu.getNamaMakanan().equals(namaMenu)) {
                        listMenuDipesan.add(menu);
                    }
                }
            }
            handleBuatPesanan(restaurantComboBox.getValue(), dateField.getText(), listMenuDipesan);});

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setOnAction(e -> mainApp.setScene(createBaseMenu())); //balik lagi ke tampilan awal CustomerMenu

        menuLayout.getChildren().addAll(restaurantLabel, restaurantComboBox, dateLabel, dateField, menuButton, menuItemsLabel, menuItemsView, buatPesananButton, kembaliButton);
        return new Scene(menuLayout, 400, 600);
    }

    private Scene createBillPrinter(){
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);    //set posisi vbox ke tengah2 scene

        TextField idField = new TextField();
        idField.setPromptText("Enter your order ID");

        Button printBillButton = new Button("Print Bill");
        printBillButton.setOnAction(e -> { 
            if (idField.getText().isEmpty()) {  //harus input order ID yang ingin di print bill nya
                showAlert("Warning!", "Warning!", "Please enter the order ID", AlertType.WARNING);
            }
            else {
                for (Order orderan : user.getOrderHistory()) {  //validasi eksistensi orderId di history sang user
                    if (orderan.getOrderId().equals(idField.getText())) {
                        idExist = true;
                    }
                }
                if (idExist) {
                    mainApp.setScene(new BillPrinter(stage, mainApp, user).getScene());
                }
                else {
                    showAlert("ERROR!", "ERROR!", "Order ID tidak pernah dibuat!", AlertType.ERROR);
                }
            }
        });

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setOnAction(e -> mainApp.setScene(createBaseMenu())); //balik lagi ke tampilan awal CustomerMenu

        menuLayout.getChildren().addAll(idField, printBillButton, kembaliButton);
        
        return new Scene(menuLayout, 400, 600);
    }

    private Scene createBayarBillForm() {
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);    //set posisi vbox ke tengah2 scene

        String[] opsiPembayaran = {"Credit Card", "Debit"};

        TextField idField = new TextField();
        idField.setPromptText("Enter your order ID");

        ComboBox<String> opsiPembayaranCombo = new ComboBox<>(FXCollections.observableArrayList(opsiPembayaran));
        opsiPembayaranCombo.setPromptText("Pilih Opsi Pembayaran");

        Button bayarButton = new Button("Bayar");
        bayarButton.setOnAction(e -> {
            if (idField.getText().isEmpty()) {      //Order Id yang ingin dibayar harus ada
                showAlert("Warning!", "Warning!", "Please enter the order ID", AlertType.WARNING);
            }
            handleBayarBill(orderId, opsiPembayaranCombo.getValue());
        });

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setOnAction(e -> mainApp.setScene(createBaseMenu())); //balik lagi ke tampilan awal CustomerMenu

        menuLayout.getChildren().addAll(idField, opsiPembayaranCombo, bayarButton, kembaliButton);

        return new Scene(menuLayout, 400,600);
    }

    private Scene createCekSaldoScene() {
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);    //set posisi vbox ke tengah2 scene

        Label saldoLabel = new Label(user.getNama() + "\nSaldo: Rp " + user.getSaldo());
        saldoLabel.setTextFill(Color.BLACK);

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setOnAction(e -> mainApp.setScene(createBaseMenu())); //balik lagi ke tampilan awal CustomerMenu

        menuLayout.getChildren().addAll(saldoLabel, kembaliButton);
        return new Scene(menuLayout, 400,600);
    }

    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, ArrayList<Menu> menuItems) {
        //Bikin orderID dengan cara yang sama dengan tp sebelum2nya dan nantinya pesanan yang dibikin dimasukin ke historyOrderId user
        orderId = "";
        int sum = 0;
        biayaOngkir = 0;
        for (int i = 0; i < namaRestoran.length(); i++) {
            if (orderId.length() == 4) {
                break;
            }
            if (namaRestoran.charAt(i) != ' ') {
                orderId += Character.toUpperCase(namaRestoran.charAt(i));
            }
        }
        orderId += tanggalPemesanan.replaceAll("/", "");
        for (int i = 0; i < user.getNomorTelepon().length(); i++) {
            sum += Character.getNumericValue(user.getNomorTelepon().charAt(i));
        }
        int mod = sum % 100;
        if (mod < 10) {
            orderId += "0" + mod;
        }
        else {
            orderId += mod;
        }
        String csum = CustomerSystemCLI.checksum(orderId);
        orderId += csum;
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
        order = new Order(orderId, tanggalPemesanan, biayaOngkir, restaurant, menuItems);
        user.setOrderHistory(order);
        showAlert("Success", "Order dengan ID " + orderId + " berhasil ditambahkan", "", AlertType.INFORMATION);
    }

    private void handleBayarBill(String orderID, String pilihanPembayaran) {
        for (Order orderan : user.getOrderHistory()) {
            if (orderan.getOrderId().equals(orderID)) {
                order = orderan;     //set golbal variabel order kepada order yang tadi diketahui dari orderId
            }
        }
        if (pilihanPembayaran.equals("Credit Card")) {      //pembayaran dengan metode pembayaran Credit Card
            //Langkah-langkah validasi dan pembayaran sama persis dengan tp3
            if (user.getPayment() instanceof CreditCardPayment) {
                CreditCardPayment creditCardPayment = (CreditCardPayment) user.getPayment();
                long biayaTanpaBiayaTransaksi = (long) order.getTotalBiaya();       
                long biayaTransaksi = creditCardPayment.countTransactionFee((long) order.getTotalBiaya()); 
                if (user.getSaldo() >= creditCardPayment.processPayment(biayaTanpaBiayaTransaksi)) {
                    showAlert("Success", "Berhasil membayar bill sebesar Rp " + biayaTanpaBiayaTransaksi + " dengan biaya transaksi sebesar Rp " + biayaTransaksi, "", AlertType.INFORMATION);
                    user.setSaldo(user.getSaldo() - creditCardPayment.processPayment(biayaTanpaBiayaTransaksi));
                    order.setOrderFinished(true);  
                }
                else {
                    showAlert("Error!", "Error!", "Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain!", AlertType.ERROR);
                }
            }
            else {
                showAlert("Error!", "Error!", "User belum memiliki metode pembayaran ini!", AlertType.ERROR);
            }
        }
        else {  //pembayaran dengan metode pembayaran Debit
            //Langkah-langkah validasi dan pembayaran sama persis dengan tp3
            if (user.getPayment() instanceof DebitPayment) {
                DebitPayment debitPayment = (DebitPayment) user.getPayment();
                long prosesPembayaran = debitPayment.processPayment((long) order.getTotalBiaya());
                if (prosesPembayaran < 0) {
                    showAlert("ERROR!", "ERROR!", "Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain", AlertType.ERROR);
                }
                else {
                    if (order.getTotalBiaya() < debitPayment.getMINIMUM_TOTAL_PRICE()) {        //total biaya harus >= 50000
                        showAlert("ERROR!", "ERROR!", "Jumlah pesanan < 50000 mohon menggunakan metode pembayaran yang lain", AlertType.ERROR);
                    }
                    else {
                        showAlert("Message", "Berhasil Membayar Bill sebesar Rp " + prosesPembayaran, "", AlertType.INFORMATION);
                        user.setSaldo(user.getSaldo() - prosesPembayaran);
                        order.setOrderFinished(true);
                    }
                }
            }
            else {
                showAlert("Error!", "Error!", "User belum memiliki metode pembayaran ini!", AlertType.ERROR);
            }
        }
    }

    public Scene getAddOrderScene() {
        return this.createTambahPesananForm();
    }

    public Scene getBillPrinterScene() {
        return this.createBillPrinter();
    }

    public Scene getPayBillScene() {
        return this.createBayarBillForm();
    }

    public Scene getCekSaldoScene() {
        return this.createCekSaldoScene();
    }
}
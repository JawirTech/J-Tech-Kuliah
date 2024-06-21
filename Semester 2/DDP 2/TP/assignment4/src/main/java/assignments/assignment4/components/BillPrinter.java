package assignments.assignment4.components;

import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.CustomerMenu;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class BillPrinter {
    private Stage stage;
    private MainApp mainApp;
    private User user;
    private Order rightOrder;
    private String pesananString = "";
    private int hargaSum;
    private CustomerMenu customerMenu;
    private String tulisanStatusPesanan;

    public BillPrinter(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
    }

    private Scene createBillPrinterForm(){  //buat scene untuk menampilkan bill yang akan di print
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);    //set posisi vbox ke tengah2 scene

        printBill(CustomerMenu.getOrderId());  //validasi dulu orderId
        if (rightOrder.getOrderFinished() == false) {   //kalau belum selesai maka akan di set "Not Finished"
            tulisanStatusPesanan = "Not Finished";
        }
        else {  //kalau sudah selesai maka akan di set "Finished"
            tulisanStatusPesanan = "Finished";
        }
        Label billLabel = new Label("Bill\nOrder ID: " + rightOrder.getOrderId() + "\nTanggal Pemesanan: " + rightOrder.getTanggal()
                                    +"\nRestaurant: " + rightOrder.getResto().getNama() + "\nLokasi Pengiriman: " + user.getLokasi()
                                    + "\nStatus Pengiriman: " + tulisanStatusPesanan + "\nPesanan:");   //print bagian bill sementara
        billLabel.setTextFill(Color.BLACK);

        for (Menu menu : rightOrder.getItems()) {
            pesananString += "- " + menu.getNamaMakanan() + " Rp " + (int) menu.getHarga() + '\n';
            hargaSum += (int) menu.getHarga();
        }
        Label pesananLabel = new Label(pesananString);  //print semua pesanan yang di pesan user
        pesananLabel.setTextFill(Color.BLACK);

        Label biayaOngkosKirimLabel = new Label("Biaya Ongkos Kirim: Rp " + CustomerMenu.getBiayaOngkir()); //tampilkan biaya ongkos
        hargaSum += CustomerMenu.getBiayaOngkir();
        biayaOngkosKirimLabel.setTextFill(Color.BLACK);

        Label totalBiayaLabel = new Label("Total Biaya: Rp " + hargaSum);
        totalBiayaLabel.setTextFill(Color.BLACK);

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setOnAction(e -> mainApp.setScene(mainApp.getScene("CostumerMenu")));     //balik lagi ke CustomerMenu jika sudah

        layout.getChildren().addAll(billLabel, pesananLabel, biayaOngkosKirimLabel, totalBiayaLabel, kembaliButton);

        return new Scene(layout, 400, 600);
    }

    private void printBill(String orderId) {
        //validasi orderID apakah ada di history pemesanan user
        for (Order order : user.getOrderHistory()) {
            if (order.getOrderId().equals(orderId)) {
                rightOrder = order;
            }
        }
    }

    public Scene getScene() {
        return this.createBillPrinterForm();
    }

    // Class ini opsional
    public class MenuItem {
        private final StringProperty itemName;
        private final StringProperty price;

        public MenuItem(String itemName, String price) {
            this.itemName = new SimpleStringProperty(itemName);
            this.price = new SimpleStringProperty(price);
        }

        public StringProperty itemNameProperty() {
            return itemName;
        }

        public StringProperty priceProperty() {
            return price;
        }

        public String getItemName() {
            return itemName.get();
        }

        public String getPrice() {
            return price.get();
        }
    }
}

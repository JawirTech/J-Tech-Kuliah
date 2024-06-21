package assignments.assignment4.page;

import assignments.assignment3.Menu;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.form.LoginForm;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AdminMenu extends MemberMenu{
    private Restaurant restaurant;
    private Stage stage;
    private Scene scene;
    private User user;
    private Scene addRestaurantScene;
    private Scene addMenuScene;
    private Scene viewRestaurantsScene;
    private MainApp mainApp; // Reference to MainApp instance
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ListView<String> menuItemsListView = new ListView<>();

    public AdminMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addRestaurantScene = createAddRestaurantForm();
        this.addMenuScene = createAddMenuForm();
        this.viewRestaurantsScene = createViewRestaurantsForm();
    }

    @Override
    protected void refresh() {      //implementasi refresh() di class ini untuk refresh data aplikasi
        this.addRestaurantScene = getAddRestaurantScene();
        this.addMenuScene = getAddMenuScene();
        this.viewRestaurantsScene = getViewRestaurantScene();
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public Scene createBaseMenu() {     //implementasi abstract method superclass untuk menampilkan tampilan utama dari AdminMenu
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);       //set posisi vbox ke tengah2 scene

        Button addRestaurantButton = new Button("Tambah Restoran");
        addRestaurantButton.setOnAction(e -> { 
            refresh();      
            mainApp.setScene(createAddRestaurantForm());    //tampilkan layar saat menambahkan restaurant
        });

        Button addMenuButton = new Button("Tambah Menu Restoran");
        addMenuButton.setOnAction(e -> {
            refresh();
            mainApp.setScene(createAddMenuForm());          //tampilkan layar saat menambahkan menu dari restaurant yang diinginkan
        });

        Button viewRestaurantsButton = new Button("Lihat Daftar Restoran");
        viewRestaurantsButton.setOnAction(e -> {
            refresh();
            mainApp.setScene(createViewRestaurantsForm());  //tampilkan layar untuk melihat menu-menu di restaurant yang diinginkan
        });

        Button logOutButton = new Button("Log Out");
        logOutButton.setOnAction(e -> {
            stage = (Stage) logOutButton.getScene().getWindow();
            LoginForm loginForm = new LoginForm(stage, mainApp);    //balik ke LoginForm ketika logout
            stage.setScene(loginForm.getScene());
            stage.show();
        });

        menuLayout.getChildren().addAll(addRestaurantButton, addMenuButton, viewRestaurantsButton, logOutButton); 

        return new Scene(menuLayout, 400, 600);
    }

    private Scene createAddRestaurantForm() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);    //set posisi vbox ke tengah2 scene

        Label restaurantNameLabel = new Label("Restaurant Name:");
        restaurantNameLabel.setTextFill(Color.BLACK);

        TextField restaurantNameField = new TextField();
        restaurantNameField.setPromptText("Enter restaurant name");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            if (restaurantNameField.getText().isEmpty()) {  //user harus input nama restaurant yang ingin dibuat
                showAlert("Warning!", "Warning!", "Please enter a restaurant you want to make!", AlertType.WARNING);
            }
            else {
                handleTambahRestoran(restaurantNameField.getText());  //tambahkan restaurant dengan nama yang di input tadi
            }
        });

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setOnAction(e -> mainApp.setScene(createBaseMenu()));     //balik lagi ke tampilan awal AdminMenu

        layout.getChildren().addAll(restaurantNameLabel, restaurantNameField, submitButton, kembaliButton);

        return new Scene(layout, 400, 600);
    }

    private Scene createAddMenuForm() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);    //set posisi vbox ke tengah2 scene

        Label restaurantNameLabel = new Label("Restaurant Name:");
        restaurantNameLabel.setTextFill(Color.BLACK);

        TextField restaurantNameField = new TextField();
        restaurantNameField.setPromptText("Enter restaurant name");
    
        Label menuItemNameLabel = new Label("Menu Item Name:");
        menuItemNameLabel.setTextFill(Color.BLACK);

        TextField menuItemNameField = new TextField();
        menuItemNameField.setPromptText("Enter menu item name");

        Label priceLabel = new Label("Price:");
        priceLabel.setTextFill(Color.BLACK);

        TextField priceField = new TextField();
        priceField.setPromptText("Enter price");

        Button addMenuItemButton = new Button("Add Menu Item");
        addMenuItemButton.setOnAction(e -> {
            if (restaurantNameField.getText().isEmpty()) {      //harus ada nama restaurant yang di input
                showAlert("Warning!", "Warning!", "Please enter a restaurant!", AlertType.WARNING);
            }
            if (menuItemNameField.getText().isEmpty()) {        //harus ada nama menu yang di input yang ingin di buat
                showAlert("Warning!", "Warning!", "Please enter a menu item!", AlertType.WARNING);
            }
            if (priceField.getText().isEmpty()) {               //harus ada input harga dari menu tersebut
                showAlert("Warning!", "Warning!", "Please enter the price of the item!", AlertType.WARNING);
            }
            else {
                for (Restaurant resto : MemberMenu.restoList) {
                    if (resto.getNama().equalsIgnoreCase(restaurantNameField.getText())) {
                        restaurant = resto; //set golbal variabel restaurant kepada nama restaurant yang di input untuk sementara
                        break;
                    }
                    else {
                        restaurant = null;
                    }
                }
                handleTambahMenuRestoran(restaurant, menuItemNameField.getText(), Double.parseDouble(priceField.getText()));
            }
        });

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setOnAction(e -> mainApp.setScene(createBaseMenu())); //balik lagi ke tampilan awal AdminMenu

        layout.getChildren().addAll(restaurantNameLabel, restaurantNameField, menuItemNameLabel, menuItemNameField, priceLabel, priceField, addMenuItemButton, kembaliButton);

        return new Scene(layout, 400, 600);
    }
    
    
    private Scene createViewRestaurantsForm() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);        //set posisi vbox ke tengah2 scene

        ListView<String> menuView = new ListView<String>(); //untuk memperlihatkan menu2 dari restaurant yang diinginkan

        Label restoNameLabel = new Label("Restaurant Name:");
        restoNameLabel.setTextFill(Color.BLACK);

        TextField restoNameField = new TextField();
        restoNameField.setPromptText("Enter restaurant name to view menu");

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            if (restoNameField.getText().isEmpty()) {       //harus ada restaurant yang di input
                showAlert("Warning!", "Warning!", "Please enter a restaurant name!", AlertType.WARNING);
            }
            else {
                menuView.getItems().clear();

                for (Restaurant resto : MemberMenu.restoList) {
                    if (resto.getNama().equals(restoNameField.getText())) {
                        restaurant = resto; //set golbal variabel restaurant kepada nama restaurant yang di input untuk sementara
                        break;
                    }
                }

                // If restaurant found, display its menu
                if (restaurant != null) {   //jika restaurant yang di input ada, maka listview tadi akan diisi menu2 dari restaurant itu
                    for (Menu menu : restaurant.getMenu()) {
                        menuView.getItems().add(menu.getNamaMakanan() + " - Rp" + menu.getHarga());
                    }
                } else {    
                    showAlert("Error!", "Error!", "Restaurant not found!", AlertType.ERROR);
                }
            }
        });

        Label menuLabel = new Label("Menu:");
        menuLabel.setTextFill(Color.BLACK);

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setOnAction(e -> mainApp.setScene(createBaseMenu())); //balik lagi ke tampilan awal AdminMenu
    
        layout.getChildren().addAll(restoNameLabel, restoNameField, searchButton, menuLabel, menuView, kembaliButton);
        return new Scene(layout, 400, 600);
    }
    

    private void handleTambahRestoran(String nama) {
        Restaurant rest = new Restaurant(nama);
        boolean restoAda = false;
        int count = 0;
        //validasi eksistensi restaurant di restoList dan validasi namanya sesuai dengan tp sebelum2nya
        for (Restaurant resto : MemberMenu.restoList) {
            if (resto.getNama().equalsIgnoreCase(nama)) {
                restoAda = true;
            }
        }
        if (!restoAda) {
            for (int i = 0; i < nama.length(); i++) {
                if (nama.charAt(i) != ' ') {
                    count++;
                }
            }
            if (count < 4) {
                showAlert("Error!", "Error!", "Nama restoran tidak valid!", AlertType.ERROR);
            }
            else {  //tambahkan restaurant ke restoList kalo semuanya valid
                showAlert("Message", "Message", "Restaurant successfully registered!", AlertType.INFORMATION);
                MemberMenu.restoList.add(rest);
            }
        } 
        else {
            showAlert("Error!", "Error!", "Restoran dengan nama " + nama + " sudah pernah terdaftar. Mohon masukkan nama yang berbeda!", AlertType.ERROR);
        }
    }

    private void handleTambahMenuRestoran(Restaurant restaurant, String itemName, double price) {
        boolean menuAda = false;
        //validasi apakah restaurantnya ada, dan validasi apakah menu yang di input sudah apa atau tidak di restaurant yang dimaksud
        if (restaurant != null) {
            for (Menu menu : restaurant.getMenu()) {
                if (menu.getNamaMakanan().equalsIgnoreCase(itemName)) {
                    menuAda = true;
                    showAlert("Error!", "Error!", "Menu " + itemName + " sudah pernah terdaftar pada restaurant " + restaurant.getNama() + ". Mohon masukkan menu yang berbeda!", AlertType.ERROR);
                }
            }
            if (menuAda == false) {
                Menu menu = new Menu(itemName, price);
                restaurant.setMenu(menu);
                showAlert("Message", "Message", "Menu item added successfully!", AlertType.INFORMATION);
            }
        } 
        else {
            showAlert("Error!", "Error!", "Restaurant not found!", AlertType.ERROR);
        }
    }

    public Scene getAddRestaurantScene() {
        return this.createAddRestaurantForm();
    }

    public Scene getAddMenuScene() {
        return this.createAddMenuForm();
    }

    public Scene getViewRestaurantScene () {
        return this.createViewRestaurantsForm();
    }

    public User getUser() {
        return this.user;
    }
}

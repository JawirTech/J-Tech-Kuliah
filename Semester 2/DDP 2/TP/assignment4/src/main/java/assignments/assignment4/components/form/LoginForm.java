package assignments.assignment4.components.form;

import assignments.assignment3.MainMenu;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginForm {
    private Stage stage;
    private MainApp mainApp; // MainApp instance
    private TextField nameInput;
    private TextField phoneInput;
    private User neededUser;

    public LoginForm(Stage stage, MainApp mainApp) { // Pass MainApp instance to constructor
        this.stage = stage;
        this.mainApp = mainApp; // Store MainApp instance
        this.nameInput = new TextField();
        this.phoneInput = new TextField();
    }

    private Scene createLoginForm() {       //tampilan saat login awal user
        nameInput = new TextField();
        nameInput.setPromptText("Enter your name");

        phoneInput = new TextField();
        phoneInput.setPromptText("Enter your phone number");
        
        GridPane grid = new GridPane();

        nameInput.setMaxWidth(220);
        nameInput.setPrefWidth(220);
        
        phoneInput.setMaxWidth(220);
        phoneInput.setPrefWidth(220);

        Label welcomeLabel = new Label("Welcome to DepeFood"); 
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 31));
        grid.add(welcomeLabel, 0, 0);

        Label nameLabel = new Label("Name:");
        nameLabel.setTextFill(Color.BLACK);
        grid.add(nameLabel, 0, 1);
        grid.add(nameInput, 1, 1);

        Label phoneNumberLabel = new Label("Phone Number:");
        phoneNumberLabel.setTextFill(Color.BLACK);
        grid.add(phoneNumberLabel, 0, 2);
        grid.add(phoneInput, 1, 2);
        
        Button loginBtn = new Button("Login");
        grid.add(loginBtn, 1, 3);
        loginBtn.setOnAction(e ->{ 
        if (nameInput.getText().isEmpty() || phoneInput.getText().isEmpty()) {      //harus input nama dan nomor telefon user
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Input your name and your phone number!");
            alert.showAndWait();
        }
        else {
            handleLogin();
        }
        });

        Button exitButton = new Button("Exit");
        grid.add(exitButton, 1, 4);
        exitButton.setOnAction(e -> Platform.exit());   //keluar dari aplikasi

        grid.setPadding(new Insets(200, 0, 0, 30));                   
        grid.setHgap(-220);
        grid.setVgap(15);

        return new Scene(grid, 400, 600);
    }

    private void handleLogin() {
        //validasi dulu apakah user ada di list user, kalau tida, tidak akan bisa login, ini dilihat dari nama dan nomor telefonnya user
        boolean bisaLogin = false;
        for (User user : MainMenu.getUserList()) {
            if (user.getNama().equals(nameInput.getText()) && user.getNomorTelepon().equals(phoneInput.getText())) {
                bisaLogin = true;
                neededUser = user;
                break;
            }
        }
        if (bisaLogin) {
            if (neededUser.getRole().equals("Admin")) {     //ketika user berupa Admin, maka akan pindah ke scene nya untuk Admin
                mainApp.addScene("AdminMenu", new AdminMenu(stage, mainApp, neededUser).getScene());
                mainApp.setScene(new AdminMenu(stage, mainApp, neededUser).getScene());
            }
            else {  //ketika user berupa Customer, maka akan pindah ke scene nya untuk Customer
                mainApp.addScene("CostumerMenu", new CustomerMenu(stage, mainApp, neededUser).getScene());
                mainApp.setScene(new CustomerMenu(stage, mainApp, neededUser).getScene());
            }
        } 
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("User not found!");
            alert.showAndWait();
        }
    }

    public Scene getScene(){
        return this.createLoginForm();
    }

}

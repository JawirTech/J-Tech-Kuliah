package assignments.assignment4.page;

import java.util.ArrayList;

import assignments.assignment3.Restaurant;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public abstract class MemberMenu {
    private Scene scene;
    public static ArrayList<Restaurant> restoList = new ArrayList<>();

    abstract protected Scene createBaseMenu();
    //method yang bisa digunakan subclass untuk mempermudah pembuaatan alert
    protected void showAlert(String title, String header, String content, Alert.AlertType c){  
        Alert alert = new Alert(c);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Scene getScene(){
        return this.scene;
    }

    abstract protected void refresh();  //abstract method untuk subclass2 yang akan dipakai untuk refresh data yang dimiliki aplikasi
}
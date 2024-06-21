import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        primaryStage.setTitle("BMI Calculator");                //title
        primaryStage.setScene(new Scene(root, 350, 500));       //membuat window

        VBox isian = new VBox();                                //untuk button dan grid yang nanti dibuat

        Rectangle rect = new Rectangle(100, 100, 320, 470);     //membuat persegi panjang hitam
        rect.setFill(Color.BLACK);

        Label label = new Label("Welcome to BMI Calculator");   //tulisan diatas persegi panjang
        label.setTextFill(Color.BLACK);
        StackPane.setAlignment(label, Pos.TOP_LEFT);
        label.setPadding(new Insets(0, 0, 0, 15));

        GridPane gp = new GridPane();                               //init grid

        String[] genderChoice = {"Laki-Laki", "Perempuan"};         //arraylist berisi dua pilihan
        ChoiceBox<String> cb = new ChoiceBox<String>(FXCollections.observableArrayList(genderChoice));      //membuat choicebox

        TextField tfWeight = new TextField();                       //membuat text field untung berat dan panjang
        tfWeight.setMaxWidth(140);
        tfWeight.setPrefWidth(140);
        TextField tfHeight = new TextField();
        tfHeight.setMaxWidth(140);
        tfHeight.setPrefWidth(140);

        Label genderLabel  = new Label("Gender");               //buat label gender beserta choiceboxnya
        genderLabel.setTextFill(Color.WHITE);
        gp.addRow(0, genderLabel, cb);
        
        Label weightLabel = new Label("Berat Badan (kg)");      //buat label weight beserta textfieldnya
        weightLabel.setTextFill(Color.WHITE);
        gp.addRow(1, weightLabel, tfWeight);

        Label heightLabel = new Label("Tinggi Badan (cm)");     //buat label height beserta textfieldnya
        heightLabel.setTextFill(Color.WHITE);
        gp.addRow(2, heightLabel, tfHeight);

        Label hasilLabel = new Label("--- Hasil ---");
        hasilLabel.setTextFill(Color.WHITE);
        hasilLabel.setFont(new Font(20));
        gp.addRow(3, hasilLabel, new Label(""));

        Label idealLabel = new Label("Berat Badan Ideal");      //untuk hasil ideal berat dan isinya nanti
        idealLabel.setTextFill(Color.WHITE);
        Label ngisiIdeal = new Label("");
        gp.addRow(4, idealLabel, ngisiIdeal);
        ngisiIdeal.setTextFill(Color.WHITE);

        Label bmiLabel = new Label("Index Massa Tubuh (BMI)");  //untuk hasil bmi berat dan isinya nanti
        bmiLabel.setTextFill(Color.WHITE);
        Label ngisiBmi = new Label("");
        gp.addRow(5, bmiLabel, ngisiBmi);
        ngisiBmi.setTextFill(Color.WHITE);

        Label klasifikasiLabel = new Label("Klasifikasi");      //untuk hasil klasifikasi berat dan isinya nanti
        klasifikasiLabel.setTextFill(Color.WHITE);
        Label ngisiKlasifikasi = new Label("");
        gp.addRow(6, klasifikasiLabel, ngisiKlasifikasi);

        gp.setPadding(new Insets(100, 0, 0, 20));                   //padding lokasi gp
        gp.setHgap(30);
        gp.setVgap(15);
    
        HBox btnBox = new HBox();
        btnBox.setAlignment(Pos.BOTTOM_CENTER);
        Button computeBtn = new Button("Compute!");         //event yang saya tempel ketika compute! di klik
        computeBtn.setOnAction(event -> computeAction(cb.getValue(), tfWeight.getText(), tfHeight.getText(), ngisiIdeal, ngisiBmi, ngisiKlasifikasi)); 

        Button exitBtn = new Button("Exit");             //event yang saya tempel ketika Exit di klik
        exitBtn.setOnAction((ActionEvent event) -> {Platform.exit();});

        btnBox.setPadding(new Insets(110, 0, 0, 0));           //buat button bbox
        btnBox.getChildren().addAll(computeBtn, exitBtn);   

        isian.getChildren().addAll(gp, btnBox);

        root.getChildren().addAll(label, rect, isian);
        primaryStage.show();
    }

    public boolean validasiInput(String getWeight, String getHeight) {      //validasi input heught dan weight
        boolean res = false;
        for (int i = 0; i < getWeight.length(); i++) {
            if (Character.isDigit(getWeight.charAt(i))) {
                continue;
            }
            else if (getWeight.charAt(i) == '.') {
                continue;
            }
            else {
                return res;
            }
        }
        for (int i = 0; i < getHeight.length(); i++) {
            if (Character.isDigit(getHeight.charAt(i))) {
                continue;
            }
            else if (getWeight.charAt(i) == '.') {
                continue;
            }
            else {
                return res;
            }
        }
        res = true;
        return res;
    }

    public void computeAction(String gender, String weight, String height, Label idealLabel, Label bmiLabel, Label klasifikasiLabel) {
        if (!validasiInput(weight, height)) {
            idealLabel.setText("Invalid input!");
            bmiLabel.setText("Invalid input!");
            klasifikasiLabel.setText("Invalid input!");
            klasifikasiLabel.setTextFill(Color.WHITE);
        }
        else {
            idealLabel.setText(calculateIdealWeight(gender, height));
            bmiLabel.setText(calculateBMI(weight, height));
            klasifikasiLabel.setText(classification(calculateBMI(weight, height)));
            if (classification(calculateBMI(weight, height)).equalsIgnoreCase("Underweight")) {
                klasifikasiLabel.setTextFill(Color.BLUE);
            }
            else if (classification(calculateBMI(weight, height)).equalsIgnoreCase("Normal")) {
                klasifikasiLabel.setTextFill(Color.GREEN);
            }
            else if (classification(calculateBMI(weight, height)).equalsIgnoreCase("Overweight")) {
                klasifikasiLabel.setTextFill(Color.YELLOW);
            }
            else if (classification(calculateBMI(weight, height)).equalsIgnoreCase("Obese")) {
                klasifikasiLabel.setTextFill(Color.RED);
            }
        }
    }

    public String calculateBMI(String getWeight, String getHeight) {
        double weight = Double.parseDouble(getWeight);
        double height = Double.parseDouble(getHeight);
        return String.format("%.2f", weight / (height * height) * 10000);
    }

    public String calculateIdealWeight(String gender, String getHeight) {
        double height = Double.parseDouble(getHeight);
        if (gender.equals("Laki-Laki")) {
            return String.format("%.2f", (height - 100) - ((height - 100) * 0.1));
        }
        return String.format("%.2f", (height - 100) - ((height - 100) * 0.15));
    }
    
    public String classification(String bmi) {
        if (Double.parseDouble(bmi) < 18.5) {
            return "Underweight";
        }
        else if (Double.parseDouble(bmi) >= 18.5 && Double.parseDouble(bmi) < 25) {
            return "Normal";
        }
        else if (Double.parseDouble(bmi) >= 25 && Double.parseDouble(bmi) < 30) {
            return "Overweight";
        }
        else {
            return "Obese";
        } 
    }
}
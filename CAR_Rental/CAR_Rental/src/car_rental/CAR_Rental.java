package car_rental;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CAR_Rental extends Application {

    private static final String ADD_USER = "INSERT INTO USERS VALUES (?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE USERS SET  EMAIL=?, PASSWORD=? WHERE username_id=?";
      private static final String DELETE_USER="DELETE FROM USERS WHERE username_id=?";


    @Override
    public void start(Stage primaryStage) {
        // Create login
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button loginButton = new Button("Login");
        loginButton.setPrefSize(120, 40);
        loginButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Button CreateAccbutton = new Button("Create account");
        CreateAccbutton.setPrefSize(150, 40);
        CreateAccbutton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        //LOG IN LAYOUT
        GridPane loginPane = new GridPane();
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.setPadding(new Insets(25, 25, 25, 25));
        Label titleLabel = new Label("AutoFlex Rentals");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titleLabel.setAlignment(Pos.CENTER);
        loginPane.add(titleLabel, 0, 0, 2, 1);
        loginPane.add(usernameField, 0, 1, 2, 1);
        loginPane.add(passwordField, 0, 2, 2, 1);

        //BUTTONS ALIGMENT
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginButton, CreateAccbutton);
        loginPane.add(buttonBox, 0, 3, 2, 1);

        //PICTURE
        FlowPane picroot = new FlowPane();

        Image backgroundImage = new Image("file:///C:/Users/ibrah/OneDrive/Desktop/f90.jpg");
        picroot.setStyle("-fx-background-image: url('" + backgroundImage.getUrl() + "'); "
                + "-fx-background-size: cover;");
        picroot.getChildren().addAll(loginPane);

        Scene scene1 = new Scene(picroot, 800, 500);
        loginButton.requestFocus();

        // FORGET PASSWORD VIA DATABASE
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        ColumnConstraints columnTwoConstraints = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstraints.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstraints);

        Label createheaderLabel = new Label("Create an Account");
        createheaderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(createheaderLabel, 0, 0, 2, 1);
        GridPane.setHalignment(createheaderLabel, HPos.CENTER);
        GridPane.setMargin(createheaderLabel, new Insets(20, 0, 20, 0));

        TextField idField = new TextField();
        idField.setPromptText("Enter your ID");
        idField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        idField.setPrefHeight(40);
        gridPane.add(idField, 1, 1);

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your Email");
        emailField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        emailField.setPrefHeight(40);
        gridPane.add(emailField, 1, 3);

        PasswordField passwordField2 = new PasswordField();
        passwordField2.setPromptText("Enter your Password");
        passwordField2.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        passwordField2.setPrefHeight(40);
        gridPane.add(passwordField2, 1, 4);

        HBox buttonBox2 = new HBox(10);
        buttonBox2.setAlignment(Pos.CENTER);

        Button createButton = new Button("Create");
        createButton.setPrefSize(150, 40);
        createButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button updateButton = new Button("update");
        updateButton.setPrefSize(150, 40);
        updateButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        Button displayButton = new Button("display");
        displayButton.setPrefSize(150, 40);
        displayButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button deleteButton = new Button("Delete");
        deleteButton.setPrefSize(150, 40);
        deleteButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button backButton = new Button("Back");
        backButton.setPrefSize(150, 40);
        backButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        backButton.setOnAction(e -> {
            primaryStage.setScene(scene1);
        });

       buttonBox2.getChildren().addAll(createButton, updateButton,displayButton ,deleteButton, backButton);
        gridPane.add(buttonBox2, 0, 5, 2, 1);
        GridPane.setMargin(buttonBox2, new Insets(20, 0, 20, 0));


        Scene scene2 = new Scene(gridPane, 800, 500);
        CreateAccbutton.setOnAction(e -> {
            primaryStage.setScene(scene2);
            createButton.requestFocus();
        });
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (idField.getText().isEmpty() || emailField.getText().isEmpty() || passwordField2.getText().isEmpty()) {
                    Alert msg = new Alert(Alert.AlertType.ERROR, "Please fill all the text fields");
                    msg.show();
                } else {
                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/autoflex_users", "root", ""); 
                            PreparedStatement st = con.prepareStatement(ADD_USER)) {

                        // Parameters
                        st.setInt(1, Integer.parseInt(idField.getText()));
                        st.setString(2, emailField.getText());
                        st.setString(3, passwordField2.getText());

                        // Execute
                        st.execute();
                        Alert msg = new Alert(Alert.AlertType.INFORMATION, "One user added");
                        msg.show();
                    } catch (Exception e) {
                        Alert msg = new Alert(Alert.AlertType.ERROR, "Error " + e);
                        msg.show();
                    }
                }
            }
        });

        updateButton.setOnAction(event -> {
            if (idField.getText().isEmpty() || emailField.getText().isEmpty() || passwordField2.getText().isEmpty()) {
                Alert msg = new Alert(Alert.AlertType.ERROR, "Please fill all the text fields");
                msg.show();
            } else {
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/autoflex_users", "root", "");
                        PreparedStatement st = con.prepareStatement(UPDATE_USER)) {

                    st.setString(1, emailField.getText());
                    st.setString(2, passwordField2.getText());
                    st.setInt(3, Integer.parseInt(idField.getText()));

                    st.execute();

                    Alert msg = new Alert(Alert.AlertType.INFORMATION, "User updated successfully");
                    msg.show();
                } catch (Exception e) {
                    Alert msg = new Alert(Alert.AlertType.ERROR, "Error " + e);
                    msg.show();
                }
            }
        });
        deleteButton.setOnAction(event -> {
            if (idField.getText().isEmpty() ) {
                Alert msg = new Alert(Alert.AlertType.ERROR, "Please fill all the text fields");
                msg.show();
            } else {
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/autoflex_users", "root", ""); 
                        PreparedStatement st = con.prepareStatement(DELETE_USER)) {

                    st.setInt(1, Integer.parseInt(idField.getText()));

                    st.execute();

                    Alert msg = new Alert(Alert.AlertType.INFORMATION, "User deleted successfully");
                    msg.show();
                } catch (Exception e) {
                    Alert msg = new Alert(Alert.AlertType.ERROR, "Error " + e);
                    msg.show();
                }
            }
        });
        
        displayButton.setOnAction(event -> {
    if (idField.getText().isEmpty()) {
        Alert msg = new Alert(AlertType.ERROR, "Please fill all the text fields");
        msg.show();
    } else {
        try {
            //1-Connection
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/autoflex_users", "root", "");

            //2-Prepared Statement
            PreparedStatement st = con.prepareStatement("SELECT * FROM USERS WHERE username_id = ?");
            st.setString(1, idField.getText());
            ResultSet result = st.executeQuery();

          
            String userEmail=null;
            String userPassword=null;

            while (result.next()) {
                userEmail = result.getString("email");
                userPassword = result.getString("password");
            }

            emailField.setText(userEmail);
            passwordField2.setText(userPassword);

        } catch (Exception e) {
            Alert msg = new Alert(AlertType.ERROR, "Error: " + e.getMessage());
            msg.show();
        }
    }
});
        
        
        // INFO OF RENT PAGE
        GridPane rentGridPane = new GridPane();
        rentGridPane.setAlignment(Pos.CENTER);
        rentGridPane.setPadding(new Insets(40, 40, 40, 40));
        rentGridPane.setHgap(10);
        rentGridPane.setVgap(10);

        ColumnConstraints rentColumnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        rentColumnOneConstraints.setHalignment(HPos.RIGHT);

        ColumnConstraints rentColumnTwoConstraints = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        rentColumnTwoConstraints.setHgrow(Priority.ALWAYS);

        rentGridPane.getColumnConstraints().addAll(rentColumnOneConstraints, rentColumnTwoConstraints);

        Label rentHeaderLabel = new Label("Info of Rent");
        rentHeaderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        rentGridPane.add(rentHeaderLabel, 0, 0, 2, 1);
        GridPane.setHalignment(rentHeaderLabel, HPos.CENTER);
        GridPane.setMargin(rentHeaderLabel, new Insets(20, 0, 20, 0));

        // Add Customer Name Field
        TextField customerNameField = new TextField();
        customerNameField.setPromptText("Enter Customer Legal name");
        customerNameField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        customerNameField.setPrefHeight(40);
        rentGridPane.add(customerNameField, 1, 2);

        // Add Rent Duration Field
        TextField rentDurationField = new TextField();
        rentDurationField.setPromptText("Enter Rent Duration (days)");
        rentDurationField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        rentDurationField.setPrefHeight(40);
        rentGridPane.add(rentDurationField, 1, 4);

        // Add Location ComboBox
        ComboBox<String> locationComboBox = new ComboBox<>();
        locationComboBox.getItems().addAll("Amman", "As-Salt", "Madaba", "Kerak");
        locationComboBox.setPromptText("Select Rental Location");
        locationComboBox.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        rentGridPane.add(locationComboBox, 1, 5);

        // Create HBox for the buttons
        HBox rentButtonBox = new HBox(10);
        rentButtonBox.setAlignment(Pos.CENTER);

        Button submitRentButton = new Button("Submit");
        submitRentButton.setPrefSize(150, 40);
        submitRentButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        rentButtonBox.getChildren().addAll(submitRentButton);
        rentGridPane.add(rentButtonBox, 0, 6, 2, 1);
        GridPane.setMargin(rentButtonBox, new Insets(20, 0, 20, 0));

        Scene scene3 = new Scene(rentGridPane, 800, 500);
        submitRentButton.requestFocus();
        // Login button action
        loginButton.setOnAction(e -> {
            if (usernameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, loginPane.getScene().getWindow(), "Username Error!", "Please enter your username");
                return;
            }
            if (passwordField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, loginPane.getScene().getWindow(), "Password Error!", "Please enter your password");
                return;
            }
            primaryStage.setScene(scene3);
        });

        // Scene to show available cars
        GridPane availableCarsGridPane = new GridPane();

        availableCarsGridPane.setAlignment(Pos.CENTER);
        availableCarsGridPane.setPadding(new Insets(40, 40, 40, 40));
        availableCarsGridPane.setHgap(10);
        availableCarsGridPane.setVgap(10);

        ColumnConstraints availableCarsColumnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        availableCarsColumnOneConstraints.setHalignment(HPos.RIGHT);

        ColumnConstraints availableCarsColumnTwoConstraints = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        availableCarsColumnTwoConstraints.setHgrow(Priority.ALWAYS);

        availableCarsGridPane.getColumnConstraints().addAll(availableCarsColumnOneConstraints, availableCarsColumnTwoConstraints);

              Label availableCarsHeaderLabel = new Label("Available Cars");
        availableCarsHeaderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        availableCarsGridPane.add(availableCarsHeaderLabel, 0, 0, 2, 1);
        GridPane.setHalignment(availableCarsHeaderLabel, HPos.CENTER);
        GridPane.setMargin(availableCarsHeaderLabel, new Insets(20, 0, 20, 0));

        // ComboBox for car years
        ComboBox<String> carYearsComboBox = new ComboBox<>();
        carYearsComboBox.getItems().addAll("2022", "2023", "2024");
        carYearsComboBox.setPromptText("Select Year");
        carYearsComboBox.setPrefWidth(200);
        carYearsComboBox.setPrefHeight(40);
        carYearsComboBox.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial'; -fx-font-weight: bold;");
        availableCarsGridPane.add(carYearsComboBox, 0, 1, 2, 1);
        GridPane.setHalignment(carYearsComboBox, HPos.CENTER);

        // ComboBox for car models
        ComboBox<String> carModelsComboBox = new ComboBox<>();
        carModelsComboBox.getItems().addAll("Toyota Landcruiser", "Toyota Camry", "Nissan Patrol", "Nissan Sunny", "Range Rover Sport", "Mercedes-Benz E200", "BMW 5-Series");
        carModelsComboBox.setPromptText("Select Car Model");
        carModelsComboBox.setPrefWidth(200);
        carModelsComboBox.setPrefHeight(40);
        carModelsComboBox.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial'; -fx-font-weight: bold;");
        availableCarsGridPane.add(carModelsComboBox, 0, 2, 2, 1);
        GridPane.setHalignment(carModelsComboBox, HPos.CENTER);

        // Label to show selected car details
        Label availableCarsLabel = new Label();
        availableCarsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        availableCarsGridPane.add(availableCarsLabel, 0, 3, 2, 1);
        GridPane.setHalignment(availableCarsLabel, HPos.CENTER);

        // Confirm and Back buttons
        Button confirmButton = new Button("Confirm");
        confirmButton.setPrefSize(150, 40);
        confirmButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
         //GridPane.setHalignment(confirmButton, HPos.CENTER);
        Button backButton2 = new Button("Back");
        backButton2.setPrefSize(150, 40);
        backButton2.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        availableCarsGridPane.add(backButton2, 0,5, 2, 1);
         GridPane.setHalignment(backButton2, HPos.CENTER);
        
        
        
        
        backButton2.setOnAction(e -> primaryStage.setScene(scene3));

        Scene scene4 = new Scene(availableCarsGridPane, 800, 500);
        

        // PAYMENT SCENE
        GridPane paymentGridPane = new GridPane();
        paymentGridPane.setAlignment(Pos.CENTER);
        paymentGridPane.setPadding(new Insets(40, 40, 40, 40));
        paymentGridPane.setHgap(10);
        paymentGridPane.setVgap(10);

        Label paymentHeaderLabel = new Label("Payment Method");
        paymentHeaderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        paymentGridPane.add(paymentHeaderLabel, 0, 0, 3, 1);
        GridPane.setHalignment(paymentHeaderLabel, HPos.CENTER);
        GridPane.setMargin(paymentHeaderLabel, new Insets(20, 0, 20, 0));

        RadioButton creditCardRadioButton = new RadioButton("Credit Card");
        creditCardRadioButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        creditCardRadioButton.setUserData("Credit Card");

        TextField creditCardTextField = new TextField();
        creditCardTextField.setPromptText("Name on Card");
        creditCardTextField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        creditCardTextField.setPrefHeight(40);
        creditCardTextField.setVisible(false);

        TextField nameOnCardField = new TextField();
        nameOnCardField.setPromptText("Card Number");
        nameOnCardField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        nameOnCardField.setPrefHeight(40);
        nameOnCardField.setVisible(false);

        TextField cvvTextField = new TextField();
        cvvTextField.setPromptText("CVV");
        cvvTextField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        cvvTextField.setPrefHeight(40);
        cvvTextField.setVisible(false);

        creditCardRadioButton.setOnAction(e -> {
            boolean selected = creditCardRadioButton.isSelected();
            creditCardTextField.setVisible(selected);
            nameOnCardField.setVisible(selected);
            cvvTextField.setVisible(selected);
        });
        


        RadioButton cashRadioButton = new RadioButton("Cash");
        cashRadioButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        cashRadioButton.setUserData("Cash");
        cashRadioButton.setOnAction(e -> {
            boolean selected = creditCardRadioButton.isSelected();
            creditCardTextField.setVisible(false);
            nameOnCardField.setVisible(false);
            cvvTextField.setVisible(false);
        });
        ToggleGroup paymentToggleGroup = new ToggleGroup();
        creditCardRadioButton.setToggleGroup(paymentToggleGroup);
        cashRadioButton.setToggleGroup(paymentToggleGroup);

        VBox paymentMethodBox = new VBox(10);
        paymentMethodBox.getChildren().addAll(creditCardRadioButton, cashRadioButton);
        paymentGridPane.add(paymentMethodBox, 0, 1);

        // Add card details fields
        GridPane cardDetailsGridPane = new GridPane();
        cardDetailsGridPane.setHgap(10);
        cardDetailsGridPane.setVgap(10);
        cardDetailsGridPane.setAlignment(Pos.CENTER);

        cardDetailsGridPane.add(creditCardTextField, 0, 0);
        cardDetailsGridPane.add(nameOnCardField, 1, 0);
        cardDetailsGridPane.add(cvvTextField, 2, 0);
        paymentGridPane.add(cardDetailsGridPane, 0, 2);

        Button confirmPaymentButton = new Button("Confirm Payment");
        confirmPaymentButton.setPrefSize(200, 40);
        confirmPaymentButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        paymentGridPane.add(confirmPaymentButton, 0, 3);
        GridPane.setHalignment(confirmPaymentButton, HPos.CENTER);

        Scene scene5 = new Scene(paymentGridPane, 800, 500);
       //THANK YOU SCENE 
          GridPane thankyougrid = new GridPane();
        thankyougrid.setAlignment(Pos.CENTER);
        thankyougrid.setPadding(new Insets(40, 40, 40, 40));
        thankyougrid.setHgap(10);
        thankyougrid.setVgap(10);

        Label thankyoulabel = new Label("Thank you for booking with AUTO FLEX");
        thankyoulabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        thankyougrid.add(thankyoulabel, 0, 0);

        Label thankyoulabel2 = new Label("We have received your reservation");
        thankyoulabel2.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        thankyougrid.add(thankyoulabel2, 0, 1);

        Label thankyoulabel3 = new Label();
        thankyoulabel3.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        thankyougrid.add(thankyoulabel3, 0, 2);
        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-font-size: 15px;");
        thankyougrid.add(exitButton, 0, 3);
        exitButton.setOnAction(e -> {
        primaryStage.close();
});
 Scene scene6 = new Scene(thankyougrid, 800, 500);

        
       

 
       
        
        
confirmPaymentButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to confirm?");
            alert.showAndWait().ifPresent(response -> {
                
                if (response == ButtonType.OK) {
                     String message = "Reservation Details:\n" +
                    "Name: " + customerNameField.getText() + "\n" +
                    "Payment Method: " + (String) paymentToggleGroup.getSelectedToggle().getUserData()+ "\n"+
                    "Car Model: " + carModelsComboBox.getValue() + "\n" +
                    "Car Year: " + carYearsComboBox.getValue();
                    thankyoulabel3.setText(message);
                    primaryStage.setScene(scene6);
                }
            });
        });
        confirmButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to confirm?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    primaryStage.setScene(scene5);
                }
            });
        });

        availableCarsGridPane.add(confirmButton, 0, 4, 2, 1);
        GridPane.setHalignment(confirmButton, HPos.CENTER);

        // Submit rent button action
        submitRentButton.setOnAction(e -> {

            if (customerNameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, rentGridPane.getScene().getWindow(), "Input Error", "Please enter customer legal name");
                return;
            }
            if (locationComboBox.getValue().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, rentGridPane.getScene().getWindow(), "Input Error", "Please select rental location");
                return;
            }

            if (rentDurationField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, rentGridPane.getScene().getWindow(), "Input Error", "Please select rent duration ");
                return;
            }

            primaryStage.setScene(scene4);
        });

        //PRIMARY STAGE
        primaryStage.setTitle("AUTO FLEX");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    //ALERT
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

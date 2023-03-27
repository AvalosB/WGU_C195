package Controllers;

import DBConnection.DBConnection;
import DBConnection.DBQuery;
import Logger.Logger;
import TimeZone.TimeZone;
import User.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    public Button loginExitButton;
    public Label loginLabel;
    public Label timeZone1;
    public String errorMessage;
    public Label passwordLabel;
    public Label emailLabel;
    @FXML
    private Button SignInButton;
    @FXML
    private TextField LoginUsername;

    @FXML
    private TextField LoginPassword;
    @FXML
    private Label timeZone;
    public static User user;


    @Override
    public void initialize(URL url, ResourceBundle rb){

        try{
            Locale locale = Locale.getDefault();
            Locale.setDefault(locale);

            ZoneId zone = ZoneId.systemDefault();
            rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")){
                loginExitButton.setText(rb.getString("exit"));
                loginLabel.setText(rb.getString("login"));
                SignInButton.setText(rb.getString("SignIn"));
                timeZone1.setText(rb.getString("TimeZone"));
                passwordLabel.setText(rb.getString("Password"));
                emailLabel.setText(rb.getString("Username"));
                errorMessage = rb.getString("Failed");
            }
        } catch (MissingResourceException e){
            System.out.println("Missing Resource: " + e);
        }
        //System.out.println(rb.getString("Login"));
        timeZone.setText(TimeZone.getCurrentTimeZone());
    }

    @FXML
    protected void onLoginClick() throws SQLException, IOException {
        String un = LoginUsername.getText();
        String ps = LoginPassword.getText();
        int DBResult = DBQuery.loginQuery(un, ps);
        if(DBResult > 0){
            Instant now = Instant.now();
            String log = "Successful Log in - " + un + " - " + now;
            Logger.LogInLogger(log);
            this.setUser(DBResult);
            String failLog = "Failed Log in - " + un + " - " + now;
            Logger.LogInLogger(failLog);
            SceneManager.ChangeScene("Appointments.fxml", SignInButton, "Main Menu");
        } else {
            SceneManager.ErrorPopup(errorMessage);
        }
    }

    private void setUser(int userID){
        try {
            this.user = new User(userID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static User getUser(){
        return user;
    }

    @FXML
    protected void onExitClick() throws SQLException {
        DBConnection.closeConnection();
        System.exit(0);
    }
}
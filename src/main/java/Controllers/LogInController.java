package Controllers;

import DBConnection.DBConnection;
import DBConnection.DBQuery;
import Logger.Logger;
import TimeZone.TimeZone;
import User.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;

public class LogInController {
    public Button loginExitButton;
    @FXML
    private Button SignInButton;
    @FXML
    private TextField LoginUsername;

    @FXML
    private TextField LoginPassword;
    @FXML
    private Label timeZone;
    public static User user;


    @FXML
    protected void initialize(){

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
            SceneManager.ErrorPopup("Username/Password Wrong!");
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
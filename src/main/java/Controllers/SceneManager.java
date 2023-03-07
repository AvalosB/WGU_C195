package Controllers;

import DBConnection.DBConnection;
import com.example.c195.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class SceneManager {
    public static void CloseApplication() throws SQLException {
        DBConnection.closeConnection();
        System.exit(0);
    }

    public static void ChangeScene(String stageName, Button button, String ScreenName) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(stageName));
        Parent root = loader.load();
        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(ScreenName);
        stage.setScene(scene);
        stage.show();
    }

    public static void ErrorPopup(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    public static boolean AlertPopup(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        }
        else {
            return false;
        }
    }
}

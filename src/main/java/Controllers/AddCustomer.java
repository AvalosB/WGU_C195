package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AddCustomer {

    public Button addCustomerCancel;

    @FXML
    public void addCustomerCancel(){
        try {
            SceneManager.ChangeScene("Appointments.fxml", addCustomerCancel, "Main Menu");
        } catch (Exception e){
            e.getMessage();
        }
    }

    @FXML
    public void addCustomerSave(){
        try {
            SceneManager.ChangeScene("Appointments.fxml", addCustomerCancel, "Main Menu");
        } catch (Exception e){
            e.getMessage();
        }
    }


}

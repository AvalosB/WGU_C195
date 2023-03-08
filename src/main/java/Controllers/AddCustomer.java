package Controllers;

import DBConnection.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddCustomer {

    public ComboBox addCustomerCountryComboBox;
    public ComboBox addCustomerStateComboBox;
    public Button addCustomerCancel;
    public TextField addCustomerName;
    public TextField addCustomerPhone;
    public TextField addCustomerPostalCode;
    public TextField addCustomerAddress;
    private ObservableList<String> countryOL = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        setCountryComboBox();
        addCustomerCountryComboBox.getSelectionModel().selectFirst();
        updateDivisionComboBox();
        addCustomerStateComboBox.getSelectionModel().selectFirst();
    }

    private void setCountryComboBox() {
        try {
            ResultSet countryList = DBQuery.getCountries();
            while(countryList.next()){
                String country = countryList.getString("Country");
                countryOL.add(country);
            }
        } catch(Exception e){
            e.getMessage();
        }

        addCustomerCountryComboBox.setItems(countryOL);

    }

    @FXML
    private void updateDivisionComboBox(){
        ObservableList<String> stateOL = FXCollections.observableArrayList();
        String Country = (String) addCustomerCountryComboBox.getValue();
        System.out.println(Country);
        try {
            ResultSet stateList =  DBQuery.getDivisions(Country);
            while(stateList.next()){
                String state = stateList.getString("Division");
                stateOL.add(state);
            }
        }catch(Exception e){
            e.getMessage();
        }

        addCustomerStateComboBox.setItems(stateOL);
        addCustomerStateComboBox.getSelectionModel().selectFirst();
    }

    private void setDivisionComboBox(int countryID){

    }

    @FXML
    public void addCustomerCancel(){
        if(SceneManager.AlertPopup("Cancel", "Customer data will not be saved", "Are you sure?")){
            try {
                SceneManager.ChangeScene("Appointments.fxml", addCustomerCancel, "Main Menu");
            } catch (Exception e){
                e.getMessage();
            }
        }
    }

    @FXML
    public void addCustomerSave(){
        String customerName = addCustomerName.getText();
        String customerAddress = addCustomerAddress.getText();
        String customerPhone = addCustomerPhone.getText();
        String customerCountry = (String) addCustomerCountryComboBox.getValue().toString();
        String customerState = (String) addCustomerStateComboBox.getValue();
        String customerPostalCode = addCustomerPostalCode.getText();

        if(customerName == "" || customerAddress == "" || customerPhone == "" || customerCountry == "" || customerState == "" || customerPostalCode == ""){
            SceneManager.ErrorPopup("Please Fill out all Fields!");
            return;
        }
        DBQuery.addCustomerToDB(customerName, customerAddress, customerPostalCode, customerPhone, customerState);
        try {
            SceneManager.ChangeScene("Appointments.fxml", addCustomerCancel, "Main Menu");
        } catch (Exception e){
            e.getMessage();
        }
    }


}

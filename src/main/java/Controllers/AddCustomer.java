package Controllers;

import DBConnection.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddCustomer {

    public ComboBox addCustomerCountryComboBox;
    public ComboBox addCustomerStateComboBox;
    public Button addCustomerCancel;
    private ObservableList<String> countryOL = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        setCountryComboBox();
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
    }

    private void setDivisionComboBox(int countryID){

    }

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

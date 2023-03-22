package Controllers;

import DBConnection.DBQuery;
import User.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;

public class ModCustomer {
    public Button modCustomerCancel;
    public Button modCustomerSave;
    public TextField modCustomerAddress;
    public TextField modCustomerID;
    public TextField modCustomerName;
    public TextField modCustomerPhone;
    public TextField modCustomerPostalCode;
    public ComboBox modCustomerCountryComboBox;
    public ComboBox modCustomerStateComboBox;
    private Customer selectedCustomer;
    private int ID;
    private String Name;
    private String Address;
    private String PostalCode;
    private String State;
    private String Country;
    private String Create_Date;
    private String Phone;
    private String Created_By;
    private String Last_Update;
    private String Last_Updated_By;
    private int Division_ID;
    private ObservableList<String> countryOL = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        setCountryComboBox();

    }

    public void setSelectedCustomer(Customer customer){
        this.selectedCustomer = customer;
        this.ID = customer.getID();
        this.Name = customer.getName();
        this.Address = customer.getAddress();
        this.PostalCode = customer.getPostalCode();
        this.State = customer.getState();
        this.Phone = customer.getPhone();
        this.Division_ID = customer.getDivisionID();
        this.Country = DBQuery.getCustomerCountry(customer.getDivisionID());
        System.out.println("cust div " + customer.getDivisionID());

    }

    public void setTextFields(){
        String stringID = String.valueOf(this.ID);
        modCustomerID.setText(stringID);
        modCustomerName.setText(this.Name);
        modCustomerAddress.setText(this.Address);
        modCustomerPhone.setText(this.Phone);
        modCustomerCountryComboBox.setValue(this.Country);
        modCustomerStateComboBox.setValue(this.State);
        modCustomerPostalCode.setText(this.PostalCode);
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

        modCustomerCountryComboBox.setItems(countryOL);

    }

    @FXML
    private void updateDivisionComboBox(){
        ObservableList<String> stateOL = FXCollections.observableArrayList();
        String Country = (String) modCustomerCountryComboBox.getValue();
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

        modCustomerStateComboBox.setItems(stateOL);
        modCustomerStateComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void onCancelClick() {
        if (SceneManager.AlertPopup("Confirm", "These changes will Not be saved.", "Are you sure?")){
            try {
                SceneManager.ChangeScene("Appointments.fxml", modCustomerCancel, "Main Menu");
            } catch (IOException e) {
                System.out.println("Error Canceling Modded Appointment");
            }
        }
    }

    @FXML
    public void onSaveClick(){
        int custID = selectedCustomer.getID();
        String custName = modCustomerName.getText();
        String custAddress = modCustomerAddress.getText();
        String custPhone = modCustomerPhone.getText();
        int countryID = DBQuery.getCountryID(this.Country);
        int custDivision = DBQuery.getdivisionID(modCustomerStateComboBox.getSelectionModel().getSelectedItem().toString());
        String custPostalCode = modCustomerPostalCode.getText();

        try {
            DBQuery.updateCustomer(custName, custAddress, custPhone, custDivision, custPostalCode, custID);
        } catch (Exception e){
            System.out.println("Errir");
            e.getMessage();
        }

        LogInController.user.refreshCustomers();

        try {
            SceneManager.ChangeScene("Appointments.fxml", modCustomerSave, "Main Menu");
        } catch(Exception e){
            e.getMessage();
        }
    }


}

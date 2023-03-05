package Controllers;

import DBConnection.DBQuery;
import com.example.c195.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import User.User;
import User.Appointment;
import User.Customer;
import TimeZone.TimeZone;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class AppointmentsController {
    public Button ModAppointment;
    private User loggedInUser = LogInController.getUser();
    private static ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
    public TableView<Appointment> appointmentTable;
    public TableColumn appID;
    public TableColumn appTitle;
    public TableColumn appDesc;
    public TableColumn appLoc;
    public TableColumn appType;
    public TableColumn appStart;
    public TableColumn appEnd;
    public TableColumn appCustID;
    public TableColumn appUserID;
    public TableColumn appContID;

    public TableView<Customer> customerTable;
    public TableColumn customerID;
    public TableColumn customerName;
    public TableColumn customerAddress;
    public TableColumn customerPhone;
    public TableColumn customerState;
    public TableColumn customerPostalCode;
    public Button addCustomerButton;

    public Button removeAppointmentButton;
    @FXML
    public Button addAppointmentButton;
    @FXML
    public Button AppointmentsExitButton;

    User user = LogInController.user;
    @FXML
    protected void initialize(){
        appointmentTable.setItems(user.getAppointments());
        appID.setCellValueFactory(new PropertyValueFactory<>("iD"));
        appTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
        appType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        appCustID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appContID.setCellValueFactory(new PropertyValueFactory<>("contactID"));

        customerTable.setItems(user.getAssociatedCustomers());
        customerID.setCellValueFactory(new PropertyValueFactory<>("iD"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerState.setCellValueFactory(new PropertyValueFactory<>("state"));
        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

    }

    @FXML
    public void addCustomer(){
        try {
            SceneManager.ChangeScene("AddCustomer.fxml", addCustomerButton, "Add Customer");
        } catch (Exception e){
            e.getMessage();
        }
    }
    @FXML
    public void AppointmentExitClick() throws SQLException {
        SceneManager.CloseApplication();
    }

    @FXML
    public void currentMonthFilter(){
        filteredAppointments.clear();
        ObservableList<Appointment> appointments = user.getAppointments();
        appointments.forEach( (appointment) -> {
            LocalDate now = LocalDate.now();
            LocalDate appDate = TimeZone.changeStringToDate(appointment.Start.split(" ")[0]);
            long numberOfDays = (int) DAYS.between(now, appDate);
            System.out.println(numberOfDays);
            if(numberOfDays <= 31 && numberOfDays > 0){
                System.out.println("Month");
                filteredAppointments.add(appointment);
            }
        });
        appointmentTable.setItems(filteredAppointments);
        appointmentTable.refresh();
    }

    @FXML
    public void currentWeekFilter(){
        filteredAppointments.clear();

        ObservableList<Appointment> appointments = user.getAppointments();
        appointments.forEach( (appointment) -> {
            LocalDate now = LocalDate.now();
            LocalDate appDate = TimeZone.changeStringToDate(appointment.Start.split(" ")[0]);
            int numberOfDays = (int) DAYS.between(now, appDate);
            System.out.println(numberOfDays);
            if(numberOfDays <= 7 && numberOfDays > 0){
                filteredAppointments.add(appointment);
            }
        });
        appointmentTable.setItems(filteredAppointments);
        appointmentTable.refresh();
    }

    @FXML
    public void allAppointmentFilter(){
        appointmentTable.setItems(user.getAppointments());
        appointmentTable.refresh();
    }

    @FXML
    public void addAppointment() throws IOException{
            SceneManager.ChangeScene("AddAppointment.fxml", addAppointmentButton, "Add Appointment");
    }

    @FXML
    public void ModAppointment(){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("ModAppointment.fxml"));
            Parent root = loader.load();
            ModAppointment ma = loader.getController();
            System.out.println("ms Loaded");
            Appointment app = appointmentTable.getSelectionModel().getSelectedItem();
            System.out.println("Sending Application" + app);
            ma.setSelectedAppointment(app);
            ma.setTextFields();
            Stage stage = (Stage) ModAppointment.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Update Appointment");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.getMessage();
            SceneManager.ErrorPopup("Please Select An Appointment");
            System.out.println("Select Appointment");
        }
    }

    @FXML
    public void removeAppointment() throws SQLException {
        Appointment app = appointmentTable.getSelectionModel().getSelectedItem();
        int id = app.getID();
        DBQuery.deleteAppointment(id);
        User.removeAppointment(app);
        appointmentTable.refresh();
    }
}
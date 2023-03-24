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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicReference;

import static java.time.temporal.ChronoUnit.DAYS;

public class AppointmentsController {
    public Button ModAppointment;
    public Button modCustomer;
    public Button reportsButton;
    private User loggedInUser = LogInController.getUser();
    private static ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
    private ObservableList<Appointment> associatedAppointments = FXCollections.observableArrayList();
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
    private boolean appInFifteen = false;
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
        appContID.setCellValueFactory(new PropertyValueFactory<>("contactName"));

        customerTable.setItems(user.getAssociatedCustomers());
        customerID.setCellValueFactory(new PropertyValueFactory<>("iD"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerState.setCellValueFactory(new PropertyValueFactory<>("state"));
        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        associatedAppointments = user.getAppointments();

        String dateAndTime;
        associatedAppointments.forEach((app) -> {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime appStart = LocalDateTime.parse(app.getStart(), dtf);
            LocalDateTime now = LocalDateTime.now();

            if(appStart.isAfter(now) && appStart.isBefore(now.plusMinutes(15))){
                this.appInFifteen = true;
                String message;
                message = "Appointment: " + app.getID() + "   Start: " + app.getStart();
                SceneManager.AlertPopup("Alert", "Appointment in 15 minutes", message);
            }
        });

        if(!appInFifteen){
            SceneManager.ErrorPopup("You have no appointments withing the next 15 minutes");
        }
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
    public void deleteCustomer(){
        if(SceneManager.AlertPopup("Delete User", "Deleting this user will delete all appointments associated with this user.", "Are You Sure?")){
            System.out.println("Fake Delete");
            int customerID = customerTable.getSelectionModel().getSelectedItem().getID();
            System.out.println(customerID);
            DBQuery.deleteCustomer(customerID);
            user.refreshAppointments();
            user.refreshCustomers();
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
            //ma.setTextFields();
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

        if(SceneManager.AlertPopup("Confirm", "Permanently Delete this Appointment?", "Appointment ID: " + app.id + " Type: " + app.getType())){

            int id = app.getID();
            DBQuery.deleteAppointment(id);
            User.removeAppointment(app);
            appointmentTable.refresh();
            user.refreshCustomers();
            customerTable.refresh();
        }
    }

    @FXML
    public void ModCustomer(){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("ModCustomer.fxml"));
            Parent root = loader.load();
            ModCustomer ma = loader.getController();
            System.out.println("ms Loaded");
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            System.out.println("Sending Application" + customer);
            ma.setSelectedCustomer(customer);
            ma.setTextFields();
            Stage stage = (Stage) modCustomer.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Update Customer");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.getMessage();
            SceneManager.ErrorPopup("Please Select a Customer");
            System.out.println("Select Customer");
        }
    }

    @FXML
    public void onReportsButtonClick() throws IOException{
        SceneManager.ChangeScene("Reports.fxml", reportsButton, "Reports");
    }
}
package Controllers;

import DBConnection.DBQuery;
import Logger.CountryReport;
import Logger.MonthReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import User.User;
import User.Appointment;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import static Controllers.LogInController.user;
import static User.User.Appointments;

public class ReportsController {
    public Button reportsBack;
    public Button reportsLogout;
    public ComboBox reportsContactComboBox;
    public TableColumn appID;
    public TableColumn appTitle;
    public TableColumn appType;
    public TableColumn appDesc;
    public TableColumn appLoc;
    public TableColumn appStart;
    public TableColumn appEnd;
    public TableColumn appCustID;
    public TableView reportsContactTable;
    private static ObservableList<String> contactList = FXCollections.observableArrayList();
    public TableView reportsCountryTable;
    public TableColumn countryName;
    public TableColumn totalCustomers;
    public TableView reportsMonthTable;
    public TableColumn appointmentMonth;
    public TableColumn appointmentType;
    public TableColumn appointmentTotal;
    public ComboBox monthFilter;
    public ComboBox typeFilter;
    private ObservableList<Appointment> associatedAppointments = FXCollections.observableArrayList();
    public ObservableList<CountryReport> countryReportList = FXCollections.observableArrayList();
    public ObservableList<MonthReport> monthReportList = FXCollections.observableArrayList();


    @FXML
    protected void initialize(){
        setContactComboBox();

        CountryReport us = new CountryReport("U.S");
        CountryReport uk = new CountryReport("UK");
        CountryReport can = new CountryReport("Cananda");
        countryReportList.add(us);
        countryReportList.add(uk);
        countryReportList.add(can);

        setMonthTable();
        reportsMonthTable.setItems(monthReportList);
        appointmentMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentTotal.setCellValueFactory(new PropertyValueFactory<>("count"));

        setContactTable();
        reportsContactTable.setItems(associatedAppointments);
        appID.setCellValueFactory(new PropertyValueFactory<>("iD"));
        appTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
        appType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        appCustID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        setCountryTable();
        reportsCountryTable.setItems(countryReportList);
        countryName.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        totalCustomers.setCellValueFactory(new PropertyValueFactory<>("customerCount"));

        System.out.println("Country List Size: "+ countryReportList.size());
    }

    private void setContactComboBox(){
        try {
            ResultSet contactResult = DBQuery.contactQuery();
            while(contactResult.next()){
                String name = contactResult.getString("Contact_Name");
                System.out.println(name);
                contactList.add(name);
            }
        } catch (Exception e){
            e.getMessage();
        }

        reportsContactComboBox.setItems(contactList);
        reportsContactComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void onLogoutClick() throws SQLException {
        SceneManager.CloseApplication();
    }

    @FXML
    public void onBackClick() throws IOException{
        SceneManager.ChangeScene("Appointments.fxml", reportsBack, "Main Menu");
    }

    @FXML
    public void setContactTable(){
        associatedAppointments.clear();
        int userID = LogInController.user.getUserID();
        String selectedContact = (String) reportsContactComboBox.getSelectionModel().getSelectedItem();
        try {
            ResultSet rs = DBQuery.appointmentsQuery(userID);
            while(rs.next()){
                String ContactName = DBQuery.contactNameQuery(rs.getInt("Contact_ID"));
                if(ContactName.matches(selectedContact)){
                    int id = rs.getInt(1);
                    String title = rs.getString("Title");
                    String type = rs.getString("Type");
                    String desc = rs.getString("Description");
                    String loc = rs.getString("Location");
                    String start = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rs.getTimestamp("Start"));
                    String end = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rs.getTimestamp("End"));
                    int custID = rs.getInt("Customer_ID");
                    int uID = rs.getInt("User_ID");
                    int contID = rs.getInt("Contact_ID");
                    Appointment app = new Appointment(id, title, desc, loc, type, start, end, custID, uID, contID);
                    this.associatedAppointments.add(app);
                }
            }
        } catch (Exception e){
            e.getMessage();
        }
        reportsContactTable.refresh();
    }

    public void setCountryTable(){
        ObservableList <Appointment> userAppointments = FXCollections.observableArrayList();
        userAppointments = LogInController.user.getAppointments();

        userAppointments.forEach((app) -> {
            int customerID = app.getCustomerID();
            int divisionID = DBQuery.getCustomerDivisionID(customerID);
            String countryName = DBQuery.getCustomerCountry(divisionID);

            countryReportList.forEach((countryReport -> {
                String cn = countryReport.getCountryName();
                if(cn.matches(countryName)){
                    countryReport.addToCount();
                }
            }));
        });
    }

    public void setMonthTable(){
        ObservableList <Appointment> userAppointments = FXCollections.observableArrayList();
        ObservableList <Appointment> filteredAppointments = FXCollections.observableArrayList();
        userAppointments = LogInController.user.getAppointments();

        ObservableList<String> appointmentTypes = FXCollections.observableArrayList(
                "Planning",
                "Planning Session",
                "De-Briefing",
                "Decision-Making",
                "Problem-Solving",
                "Retrospective",
                "Other"
        );

        ObservableList<String> Months = FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June", "July","August", "September", "October", "November", "December"
        );
        typeFilter.setItems(appointmentTypes);
        monthFilter.setItems(Months);

        userAppointments.forEach(app -> {
            String unparsedMonth = app.getStart();
            String[] parsedMonth = unparsedMonth.split("");
            int start = Integer.parseInt(parsedMonth[5] + parsedMonth[6]);
            String type = app.getType();


        });

    }

}

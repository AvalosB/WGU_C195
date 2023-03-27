package Controllers;

import DBConnection.DBQuery;
import User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import TimeZone.TimeZone;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AddAppointment {
    public Button AddAppointmentCancel;
    public ComboBox Contact;
    public ComboBox UserID;
    public ComboBox CustomerID;
    public ComboBox StartTimeBox;
    public ComboBox EndTimeBox;
    public TextField Title;
    public ComboBox Type;
    public TextField Location;
    public TextField Description;
    public DatePicker StartDate;
    public DatePicker EndDate;
    public static Boolean overlapping;
    public ObservableList<Integer> ContactComboBoxArray = FXCollections.observableArrayList();
    public ObservableList<Integer> UserIDComboBoxArray = FXCollections.observableArrayList();
    public ObservableList<Integer> CustomerIDComboBoxArray = FXCollections.observableArrayList();

    public Button AppointmentSave;


    @FXML
    protected void initialize(){
        setContactComboBox();
        setUserIDBox();
        setCustomerIDBox();
        setAppointmentTimes();
        setAppointmentTypeComboBox();
        StartDate.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
            }
        });
        EndDate.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
            }
        });
    }

    private void setAppointmentTypeComboBox(){
        ObservableList<String> appointmentTypes = FXCollections.observableArrayList(
                "Planning",
                "Planning Session",
                "De-Briefing",
                "Decision-Making",
                "Problem-Solving",
                "Retrospective",
                "Other"
        );

        Type.setItems(appointmentTypes);
        Type.getSelectionModel().selectFirst();
    }

    private void setContactComboBox(){
        ResultSet rs = DBQuery.contactQuery();
        try {
            while (rs.next()) {
                ContactComboBoxArray.add(rs.getInt("Contact_ID"));
            }
        }catch (SQLException e ){
            System.out.println(e);
        }
        Contact.setItems(ContactComboBoxArray);
    }

    private void setUserIDBox(){
        ResultSet rs = DBQuery.UserIDQuery();
        try {
            while(rs.next()){
                UserIDComboBoxArray.add(rs.getInt("User_ID"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        UserID.setItems(UserIDComboBoxArray);
    }

    private void setCustomerIDBox(){
        ResultSet rs = DBQuery.CustomerIDQuery();
        try {
            while(rs.next()){
                CustomerIDComboBoxArray.add(rs.getInt("Customer_ID"));
            }
        } catch(SQLException e){
            System.out.println(e);
        }
        CustomerID.setItems(CustomerIDComboBoxArray);
    }

    @FXML
    private void OnSaveClick(){
        overlapping = false;
        String appointmentTitle = Title.getText();
        String appointmentDescription = Description.getText();
        String appointmentLocation = Location.getText();
        String appointmentType = (String) Type.getValue();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String appointmentStart = StartDate.getValue() + " " + StartTimeBox.getValue() + ":00";
        String appointmentEnd = EndDate.getValue() + " " + EndTimeBox.getValue() + ":00";
        LocalDateTime now = LocalDateTime.now();
        String createTime = (String) dtf.format(now);
        String appointmentCreatedBy = User.getUserName(User.getUserID());
        String appointmentLastUpdated = (String) dtf.format(now);
        int appointmentCustomerID = Integer.parseInt(CustomerID.getValue().toString());
        int appointmentUserID = Integer.parseInt(UserID.getValue().toString());
        int appointmentContactID = Integer.parseInt(Contact.getValue().toString());

        String convertedStart = TimeZone.convertToLocalDateTimeToUTC(appointmentStart);
        String convertedEnd = TimeZone.convertToLocalDateTimeToUTC(appointmentEnd);
        String convertedCreated = TimeZone.convertToLocalDateTimeToUTC(createTime);
        String convertedLastUpdate = TimeZone.convertToLocalDateTimeToUTC(appointmentLastUpdated);
        FormVerification.overlappingAppointments(convertedStart);
        if(FormVerification.verifyEndStartDates(convertedStart, convertedEnd) && FormVerification.verifyStartDate(convertedStart ) && !overlapping){
            DBQuery.addAppointment(appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, convertedStart, convertedEnd, convertedCreated, appointmentCreatedBy, convertedLastUpdate, appointmentCreatedBy, appointmentCustomerID, appointmentUserID, appointmentContactID);
            LogInController.user.refreshAppointments();
            LogInController.user.refreshCustomers();
            try {
                SceneManager.ChangeScene("Appointments.fxml", AppointmentSave, "Main Menu");
                LogInController.user.refreshAppointments();
                System.out.println(LogInController.user.getAppointments().size());
            } catch (Exception e){
                System.out.println("Change Scene Error");
                e.getMessage();
            }
        } else if(overlapping){
            SceneManager.ErrorPopup("You have an OverLapping Appointment");
        } else {
            SceneManager.ErrorPopup("Please Fix Errors!");
        }
    }

    private void checkDates(){
        if(StartDate.getValue() == null){
            SceneManager.ErrorPopup("Please Select a Date!");
            return;
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE", Locale.getDefault());
        String dayOfTheWeek = StartDate.getValue().format(format);
        if (dayOfTheWeek == "Saturday" || dayOfTheWeek == "Sunday"){
            SceneManager.ErrorPopup("Cannot set appointments on the weekend please choose another date");
            return;
        }

        if(StartDate.getValue().isAfter(EndDate.getValue())){
            SceneManager.ErrorPopup("");
        }
    }

    @FXML
    private void onCancelClick(){
        if(SceneManager.AlertPopup("Confirm", "This Appointment will NOT be saved.", "Are you sure?")){
            try {
                SceneManager.ChangeScene("Appointments.fxml", AddAppointmentCancel, "Main Menu");
            } catch (IOException e) {
                System.out.println("Error Canceling Add Appointment");
            }
        }

    }

    private void setAppointmentTimes(){
        StartTimeBox.getItems().addAll(
                "08:00",
                "08:15",
                "08:30",
                "08:45",
                "09:00",
                "09:15",
                "09:30",
                "09:45",
                "10:00",
                "10:15",
                "10:30",
                "10:45",
                "11:00",
                "11:15",
                "11:30",
                "11:45",
                "12:00",
                "12:15",
                "12:30",
                "12:45",
                "13:00",
                "13:15",
                "13:45",
                "14:00",
                "14:30",
                "14:45",
                "15:00",
                "15:30",
                "15:45",
                "16:00",
                "16:15",
                "16:30",
                "16:45",
                "17:00",
                "17:15",
                "17:30",
                "17:45",
                "18:00",
                "18:15",
                "18:30",
                "18:45",
                "19:00",
                "19:15",
                "19:30",
                "19:45",
                "20:00",
                "20:15",
                "20:30",
                "20:45",
                "21:00"
        );

        EndTimeBox.getItems().addAll(
                "09:00",
                "09:15",
                "09:30",
                "09:45",
                "10:00",
                "10:15",
                "10:30",
                "10:45",
                "11:00",
                "11:15",
                "11:30",
                "11:45",
                "12:00",
                "12:15",
                "12:30",
                "12:45",
                "13:00",
                "13:15",
                "13:45",
                "14:00",
                "14:30",
                "14:45",
                "15:00",
                "15:30",
                "15:45",
                "16:00",
                "16:15",
                "16:30",
                "16:45",
                "17:00",
                "17:15",
                "17:30",
                "17:45",
                "18:00",
                "18:15",
                "18:30",
                "18:45",
                "19:00",
                "19:15",
                "19:30",
                "19:45",
                "20:00",
                "20:15",
                "20:30",
                "20:45",
                "21:00",
                "21:15",
                "21:30",
                "21:45",
                "22:00"
        );
    }
}

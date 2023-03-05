package Controllers;

import DBConnection.DBQuery;
import User.Appointment;
import User.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import TimeZone.TimeZone;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

public class ModAppointment {
    public Button AppointmentSave;
    public Button AppointmentCancel;
    public TextField Title;
    public TextField Type;
    public TextField Location;
    public TextField Description;
    public DatePicker StartDate;
    public DatePicker EndDate;
    public ComboBox CustomerID;
    public ComboBox UserID;
    public ComboBox Contact;
    public ComboBox EndTimeBox;
    public ComboBox StartTimeBox;
    public TextField appointmentID;
    private Appointment selectedAppointment;

    @FXML
    public void initialize(){
        setAppointmentTimes();
    }

    @FXML
    public void onCancelClick(){

        try {
            SceneManager.ChangeScene("Appointments.fxml", AppointmentCancel, "Main Menu");
        } catch (IOException e) {
            System.out.println("Error Canceling Modded Appointment");
        }
    }

    @FXML
    public void OnSaveClick(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        int ID = this.selectedAppointment.getID();
        String Title = this.Title.getText();
        String appointmentDescription = this.Description.getText();
        String appointmentLocation = this.Location.getText();
        String appointmentType = this.Type.getText();
        String appointmentStart = StartDate.getValue() + " " + StartTimeBox.getValue();
        String appointmentEnd = EndDate.getValue() + " " + EndTimeBox.getValue();
        LocalDateTime now = LocalDateTime.now();
        String createTime = (String) dtf.format(now);
        String appointmentCreatedBy = User.getUserName(User.getUserID());
        String appointmentLastUpdated = (String) dtf.format(now);
        int appointmentCustomerID = Integer.parseInt(CustomerID.getValue().toString());
        int appointmentUserID = Integer.parseInt(UserID.getValue().toString());
        int appointmentContactID = Integer.parseInt(Contact.getValue().toString());
        if(FormVerification.verifyEndStartDates(appointmentStart, appointmentEnd) && FormVerification.verifyStartDate(appointmentStart)){
            DBQuery.modifyAppointment(Title, appointmentDescription, appointmentLocation, appointmentType, appointmentStart, appointmentEnd, createTime, appointmentCreatedBy, appointmentLastUpdated, appointmentCreatedBy, appointmentCustomerID, appointmentUserID, appointmentContactID, ID);
            LogInController.user.refreshAppointments();
            try {
                SceneManager.ChangeScene("Appointments.fxml", AppointmentCancel, "Main Menu");
            } catch (IOException e) {
                System.out.println("Error Canceling Modded Appointment");
            }
        } else {
            SceneManager.ErrorPopup("Fix The Errors");
        }
    }

    public void setSelectedAppointment(Appointment app){
        this.selectedAppointment = app;
        this.appointmentID.setText(Integer.toString(this.selectedAppointment.getID()));
        this.Title.setText(this.selectedAppointment.getTitle());
        this.Type.setText(this.selectedAppointment.getType());
        this.Location.setText(this.selectedAppointment.getLocation());
        this.Description.setText(this.selectedAppointment.getDescription());
        this.CustomerID.setValue(Integer.toString(this.selectedAppointment.getCustomerID()));
        this.UserID.setValue(Integer.toString(this.selectedAppointment.getUserID()));
        this.Contact.setValue(Integer.toString(this.selectedAppointment.getContactID()));
        String sd = this.selectedAppointment.Start.split(" ")[0];
        String ed = this.selectedAppointment.End.split(" ")[0];
        this.StartDate.setValue(TimeZone.changeStringToDate(sd));
        this.EndDate.setValue(TimeZone.changeStringToDate(ed));
        String st = this.selectedAppointment.Start.split(" ")[1];
        String et = this.selectedAppointment.End.split(" ")[1];
        this.StartTimeBox.setValue(st);
        this.EndTimeBox.setValue(et);
    }

    public void setTextFields(){

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

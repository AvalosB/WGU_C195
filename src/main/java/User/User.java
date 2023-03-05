package User;

import DBConnection.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class User {
    private static ObservableList<Appointment> Appointments = FXCollections.observableArrayList();
    private static ObservableList<Customer> associatedCustomers = FXCollections.observableArrayList();
    private static int UserID;
    private ArrayList<Integer> uniqueUserIDS = new ArrayList<Integer>();

    public User(int userID) throws SQLException {
        this.setUserID(userID);
        setAppointments();
        setCustomers();
    }

    private void setUserID(int userID){
        UserID = userID;
    }

    public static String getUserName(int userID){
        return DBQuery.UserNameQuery(userID);
    }

    public static int getUserID(){
        return UserID;
    }

    public void refreshAppointments(){
        Appointments.clear();
        try{
            this.setAppointments();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void setAppointments() throws SQLException {
        ResultSet rs = DBQuery.appointmentsQuery(UserID);
        while (rs.next()){
            int id = rs.getInt(1);
            String title = rs.getString("Title");
            String desc = rs.getString("Description");
            String loc = rs.getString("Location");
            String type = rs.getString("Type");
            String start = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rs.getTimestamp("Start"));
            String end = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rs.getTimestamp("End"));
            int custID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contID = rs.getInt("Contact_ID");
            Appointment app = new Appointment(id, title, desc, loc, type, start, end, custID, userID, contID);

            if(uniqueUserIDS.stream().count() < 1){
                uniqueUserIDS.add(custID);
            } else {
                for(int i : uniqueUserIDS){
                    if(custID != i){
                        uniqueUserIDS.add(i);
                    }
                }
            }

            Appointments.add(app);

            System.out.println("User " + getAssociatedCustomers().stream().count());
        }

        System.out.println(uniqueUserIDS);
    }

    public static ObservableList getAssociatedCustomers(){ return associatedCustomers; }

    private void setCustomers() throws SQLException {
        for(int i : uniqueUserIDS){
            Customer cust = new Customer(i);
            associatedCustomers.add(cust);
        }
    }

    public static void removeAssociatedCustomer(Customer cust){ associatedCustomers.remove(cust); }

    public static void removeAppointment(Appointment app){
        Appointments.remove(app);
    }

    public static ObservableList getAppointments(){
        return Appointments;
    }
}

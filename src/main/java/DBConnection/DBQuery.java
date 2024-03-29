package DBConnection;

import Controllers.LogInController;
import TimeZone.TimeZone;
import javafx.collections.ObservableList;
import User.User;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DBQuery {
    public static int loginQuery(String username, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        System.out.println(username + "," + password);
        if(rs.next()){
            System.out.println(rs.getInt("User_ID"));
            return rs.getInt("User_ID");
        }
        return 0;
    }

    public static ResultSet appointmentsQuery(int userID) {
    System.out.println("AQ: " + userID);
        try{
            String sql = "SELECT * FROM appointments WHERE User_ID = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    public static ResultSet customerQuery(int CustomerID) throws SQLException {
        String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setInt(1, CustomerID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public static ResultSet contactQuery(){
        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e){
            System.out.println("Error with Contact Query");
        }

        return null;
    }

    public static ResultSet UserIDQuery(){
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e ){
            return null;
        }
    }

    public static String UserNameQuery(int userID){
        try {
            String sql = "SELECT * FROM users WHERE User_ID = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String UserName = rs.getString("User_Name");
                return UserName;
            }
        } catch (SQLException e ){
            System.out.println(e);
        }
        return null;
    }

    public static ResultSet CustomerIDQuery() {
        try {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e ){
            return null;
        }
    }

    public static void deleteAppointment(int id) throws SQLException {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rs = ps.executeUpdate();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void addAppointment(
            String Title, String Description, String Location, String Type, String Start, String End, String CreateTime, String CreatedBy, String LastUpdated, String LastUpdatedBy, int CustomerID, int UserID, int ContactID
    ){
        try {
            String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setString(1, Title);
            ps.setString(2, Description);
            ps.setString(3, Location);
            ps.setString(4, Type);
            ps.setString(5, Start);
            ps.setString(6, End);
            ps.setString(7, CreateTime);
            ps.setString(8, CreatedBy);
            ps.setString(9, LastUpdated);
            ps.setString(10, LastUpdatedBy);
            ps.setInt(11, CustomerID);
            ps.setInt(12, UserID);
            ps.setInt(13, ContactID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            System.out.println(e.getMessage());
        }
    }

    public static String getCustomerState(int division_ID){

        try {
            System.out.println(division_ID);
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setInt(1, division_ID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getString("Division");
            }
        } catch (SQLException e ){
            e.getMessage();
        }
        return null;
    }

    public static void modifyAppointment(
            String Title, String Description, String Location, String Type, String Start, String End, String LastUpdated, String LastUpdatedBy, int CustomerID, int UserID, int ContactID, int appID
    ){
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?,Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setString(1, Title);
            ps.setString(2, Description);
            ps.setString(3, Location);
            ps.setString(4, Type);
            ps.setString(5, Start);
            ps.setString(6, End);
            ps.setString(7, LastUpdated);
            ps.setString(8, LastUpdatedBy);
            ps.setInt(9, CustomerID);
            ps.setInt(10, UserID);
            ps.setInt(11, ContactID);
            ps.setInt(12, appID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet getCountries(){
        try {
            String sql = "SELECT Country FROM countries";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    public static String getCustomerCountry(int divisionID){
        System.out.println("DB " + divisionID);
        int countryID = 0;
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ? ";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setInt(1, divisionID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                countryID = rs.getInt("Country_ID");
            }
        } catch (Exception e){
            e.getMessage();
        }
        System.out.println("Country Number " + countryID);
        try {
            String sql = "SELECT * FROM countries WHERE Country_ID = ? ";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getString("Country");
            }
        } catch (Exception e){
            e.getMessage();
        }

        return null;
    }

    public static ResultSet getDivisions(String countryName){
        int countryID = 0;
        try {
            String sql = "SELECT * FROM countries WHERE Country = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setString(1, countryName);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                countryID = rs.getInt("Country_ID");
            }
        } catch (SQLException e ){
            e.getMessage();
        }

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e ){
            e.getMessage();
        }

        return null;
    }

    public static void addCustomerToDB(String name, String address, String postalCode, String phone, String divisionName){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime oldDate = LocalDateTime.now();
        String dc = dtf.format(oldDate);
        String createDate = TimeZone.convertToLocalDateTimeToUTC(dc);

        int userID = LogInController.user.getUserID();
        String createdBy = UserNameQuery(userID);
        int divisionID = 0;
        try{
            String sql = "SELECT * FROM first_level_divisions WHERE Division = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setString(1, divisionName);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                divisionID = rs.getInt("Division_ID");
            }
        } catch (Exception e){
            e.getMessage();
        }

        try{
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, createDate.toString());
            ps.setString(6, createdBy);
            ps.setString(7, createDate.toString());
            ps.setString(8, createdBy);
            ps.setInt(9, divisionID);
            ps.executeUpdate();
        }catch(Exception e){
            e.getMessage();
        }
    }

    public static void deleteCustomer(int customerID){
        try {
            String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setInt(1, customerID);
            int result = ps.executeUpdate();
            System.out.println(result);
        } catch (Exception e){
            e.getMessage();
        }

        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setInt(1, customerID);
            ps.executeUpdate();

        } catch (Exception e){
            e.getMessage();
        }
    }

    public static int getdivisionID(String divisionName){
        try{
            String sql = "SELECT Division_ID from first_level_divisions WHERE Division = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setString(1, divisionName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return rs.getInt("Division_ID");
            }
        } catch (Exception e){
            e.getMessage();
        }
        return 0;
    }

    public static int getCountryID(String countryName){
        try{
            String sql = "SELECT Country_ID from countries WHERE Country = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setString(1, countryName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return rs.getInt("Country_ID");
            }
        } catch (Exception e){
            e.getMessage();
        }
        return 0;
    }

    public static void updateCustomer(String name, String address, String phone, int divisonID, String pc, int custID){
        int lastUpdateByID = LogInController.user.getUserID();
        String lastUpdateBy = getCustomerName(lastUpdateByID);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime oldDate = LocalDateTime.now();
        String dc = dtf.format(oldDate);
        String lastUpdate = TimeZone.convertToLocalDateTimeToUTC(dc);


        try{

            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ?, Postal_Code = ? WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, phone);
            ps.setString(4, lastUpdate);
            ps.setString(5, lastUpdateBy);
            ps.setInt(6, divisonID);
            ps.setString(7, pc);
            ps.setInt(8, custID);
            ps.executeUpdate();
            System.out.println("Updated");
        } catch(Exception e){
            System.out.println("Error");
            e.getMessage();
        }
    }

    private static String getCustomerName(int lastUpdateByID) {
        try {
            String sql = "SELECT * FROM users WHERE User_ID = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setInt(1, lastUpdateByID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String name = rs.getString("User_Name");
                return name;
            }

        } catch (Exception e){
            e.getMessage();
        }

        return null;
    }

    public static String contactNameQuery(int contactID){
        try {
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println("cnq " + rs.getInt("Contact_ID"));
                return rs.getString("Contact_Name");
            }
        } catch (SQLException e){
            System.out.println("Error with Contact Query");
        }
        return null;
    }

    public static int countDivision(int divisionID){
        try{
            String sql = "SELECT COUNT(Division_ID) AS DivisionCount FROM Customers";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt("DivisionCount");
            }
        } catch (Exception e){
            e.getMessage();
        }

        return 0;
    }

    public static String countryName(int cID){
        try{
            String sql = "SELECT Country_Name WHERE Country_ID = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setInt(1, cID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getString("Country_Name");
            }
        } catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    public static int getCustomerDivisionID(int customerID){
        try {
            String sql = "SELECT * FROM Customers WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt("Division_ID");
            }
        } catch (Exception e){
            e.getMessage();
        }
        return 0;
    }
}

package User;

import DBConnection.DBQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
    private final int ID;
    private String Name;
    private String Address;
    private String PostalCode;
    private String State;
    private String Create_Date;
    private String Phone;
    private String Created_By;
    private String Last_Update;
    private String Last_Updated_By;
    private int Division_ID;

    public Customer(int cid) throws SQLException {
         this.ID = cid;
         ResultSet rs = DBQuery.customerQuery(cid);
         while (rs.next()){
             this.Name = rs.getString("Customer_Name");
             this.Address = rs.getString("Address");
             this.PostalCode = rs.getString("Postal_Code");
             this.Phone = rs.getString("Phone");
             this.Create_Date = String.valueOf(rs.getDate("Create_Date"));
             this.Created_By = rs.getString("Created_By");
             this.Last_Update = String.valueOf(rs.getString("Last_Update"));
             this.Last_Updated_By = rs.getString("Last_Updated_By");
             this.Division_ID = rs.getInt("Division_ID");
             this.State = DBQuery.getCustomerState(this.Division_ID);
             System.out.println(this.State);
         }
    }

    public int getID() { return this.ID; }
    public String getName() { return this.Name; }
    public String getAddress(){ return this.Address; }
    public String getPostalCode(){ return this.PostalCode; }
    public String getPhone() { return this.Phone; }
    public String getCreateDate(){ return this.Create_Date; }
    public String getCreatedBy(){ return this.Created_By; }
    public String getLastUpdate(){ return this.Last_Update; }
    public String getLastUpdated_By(){ return this.Last_Updated_By; }
    public int getDivisionID(){ return this.Division_ID; }
    public String getState(){ return this.State; }

    private String customerStateDBQuery(){

        return null;
    }

}

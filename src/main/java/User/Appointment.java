package User;

import DBConnection.DBQuery;

import java.sql.SQLException;
import java.util.Date;

public class Appointment {
    public int id;
    public String Title;
    public String Description;
    public String Location;
    public String Type;
    public String Start;
    public String End;
    public int CustomerID;
    public int UserID;
    public int ContactID;

    public Appointment (int id, String title, String desc, String location, String type, String start, String end, int customerID,int userID, int contactID ){
        this.id = id;
        this.Title = title;
        this.Description = desc;
        this.Location = location;
        this.Type = type;
        this.Start = start;
        this.End = end;
        this.CustomerID = customerID;
        this.UserID = userID;
        this.ContactID = contactID;
    }

    public int getID(){
        return id;
    }

    public String getTitle(){ return Title; }
    public String getDescription(){ return Description; }
    public String getLocation(){ return Location;}
    public String getType(){ return Type; }
    public String getStart(){ return Start; }
    public String getEnd(){ return End; }
    public int getCustomerID(){ return CustomerID; }
    public int getUserID(){ return UserID; }
    public int getContactID(){ return ContactID; }
    public Date getStartDate(){
        return null;
    }

}

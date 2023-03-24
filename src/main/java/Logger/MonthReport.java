package Logger;

public class MonthReport {
    private String month;
    private String Type;
    private int count;

    public MonthReport(String month, String type, int count){
        this.month = month;
        this.Type = type;
        this.count = count;
    }

    public String getMonth(){ return this.month; }
    public String getType(){ return this.Type; }
    public int getCount(){ return this.count; }
}

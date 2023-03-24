package Logger;

public class CountryReport {
    public String countryName;
    public int customerCount;

    public CountryReport(String countryName){
        this.countryName = countryName;
        customerCount = 0;
    }

    public void addToCount(){
        this.customerCount = this.customerCount + 1;
    }

    public int getCustomerCount(){ return this.customerCount; }

    public String getCountryName(){ return this.countryName; }
}

package Util;
public class Data {
    private String companyname;
    private String number;
 
    public Data(){
        
    }
    
    public Data(String number, String companyname) {
        super();
        this.companyname = companyname;
        this.number = number;
    }
    
    public String getName() {
        return companyname;
    }
    public void setName(String name) {
        this.companyname = companyname;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    
}

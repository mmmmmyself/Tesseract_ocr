package Util;

public class test {
public static void main(String[] args) {
	String output = "编号:123456 企业名称:654321公司";
	int name_location = output.indexOf(58);
	int number_location = output.indexOf(58,name_location+1);
	int end_location = output.indexOf('司');
	String name = output.substring(number_location+1,end_location+1);
	String number = output.substring(name_location+1,number_location-5);
	System.out.println(number);
	System.out.println(name);
}
}

package Util;

public class test {
public static void main(String[] args) {
	String output = "���:123456 ��ҵ����:654321��˾";
	int name_location = output.indexOf(58);
	int number_location = output.indexOf(58,name_location+1);
	int end_location = output.indexOf('˾');
	String name = output.substring(number_location+1,end_location+1);
	String number = output.substring(name_location+1,number_location-5);
	System.out.println(number);
	System.out.println(name);
}
}

package sms;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class smsDone_new {

    public static void main(String[] args) 
    {        
    	String m="Banglore Computer Edu.: * Registration Open for DEC-2017 batches * Learn: ANDROID, JAVA, C Plus Plus.";
    	
    	String resp=SST_SMS.bceSunSoftSend("9465208070", m);
    	System.out.println(resp);
    	
    	if(resp.indexOf("Exception")!=-1)
    		System.out.println("Check Internet Connection");
    	
    	else
    		if(resp.indexOf("successfully")!=-1)
        		System.out.println("Sent");
    		else
    		System.out.println( "Invalid Number");
    }
}
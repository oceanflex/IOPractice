package lab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import static lab.MailingRecordFragment.*;

/**
 *
 * @author zsummers
 */
public class Class23Lab {
    public static void main(String[] args) {
        boolean append = true;
        File data = new File(File.separatorChar + "temp" + File.separatorChar + "lab" + File.separatorChar 
                        + "mailList.txt");
        
        BufferedReader in = null;
        List<String[]> records = new ArrayList<>();
        String[] record = new String[3];
        try {
           /*PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(data, append)));
           out.println("Gary Harrison");
           out.println("6123 S Bayview Dr");
           out.println("Green Bay, WI 53703");
           out.close();*///this block writes a new record to the document, provided the last line is empty
	   in = new BufferedReader(new FileReader(data));
	   String line = in.readLine();
           int recordBreak = 0;
           records.add(record);
	   while(line != null){
		  //System.out.println(line);
                  if(recordBreak < 3){
                      record[recordBreak] = line;
                      recordBreak++;
                  }else{
                      record = new String[3];
                      records.add(record);
                      record[0]=line;
                      recordBreak = 1;
                  }
		  line = in.readLine();  // strips out any carriage return chars
	   }
	 
        } catch(IOException ioe) {
            System.out.println("Houston, we have a problem! reading this file");
        } finally {
            try {
                in.close();
            } catch(Exception e) {
                
            }
        }
        System.out.println(printRecordFragment(records.get(2),FIRST_NAME)+ " "+
                printRecordFragment(records.get(2),LAST_NAME)+": "+
                printRecordFragment(records.get(2),STATE));
    }
    
    public static String printRecordFragment(String[] record, MailingRecordFragment type){
        String toPrint;
        int second = record[1].indexOf(' ',record[1].indexOf(" ") +1);
                
        switch(type){
            case FIRST_NAME:
                toPrint = record[0].substring(0, record[0].indexOf(' '));
                break;
            case LAST_NAME:
                toPrint = record[0].substring(record[0].lastIndexOf(' ')+1);
                break;
            case ADDRESS:
                toPrint = record[1].substring(0, second);
                break;
            case STREET:
                toPrint = record[1].substring(second+1,
                        record[1].length());
                break;
            case CITY:
                toPrint = record[2].substring(0, record[2].indexOf(", "));
                break;
            case STATE:
                toPrint = record[2].substring(record[2].indexOf(", ") +3, record[2].lastIndexOf(' ')-1);
                break;
            case ZIP:
                toPrint = record[2].substring(record[2].lastIndexOf(' ')+1);
                break;
            default:
                toPrint = "Unknown";
                break;
        }
        return toPrint;
    }
}

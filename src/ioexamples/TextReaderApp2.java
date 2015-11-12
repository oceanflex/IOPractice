package ioexamples;

import java.io.*;

public class TextReaderApp2{
	
   public static void main(String[] args) throws IOException{

       File data = new File("src/test.txt");

    // check if file exists, and if so, loop through and read each line of text
    if (data.exists()){
	   BufferedReader in = new BufferedReader(
						 new FileReader(data));

       char[] charBuffer = new char[20];
       int bytes = in.read(charBuffer);

       for(int i=0; i < bytes; i++) {
           if(charBuffer[i] == '\n') {
                System.out.println("CR: " + (0 + '\n'));
           } else {
                System.out.println(charBuffer[i]);
           }
       }
	   in.close();
    } else
	     System.out.println("File not found: test.txt");
    }
}

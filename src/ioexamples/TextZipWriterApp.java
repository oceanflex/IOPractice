package ioexamples;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * TextWriterApp.java	1.00 Nov 25, 2003
 *
 * A class that writes data to a text file, with buffering.
 */
public class TextZipWriterApp{
	
   public static void main(String[] args) throws IOException{
   	  boolean append = false;   // you can change this

	  // This references the file in the working directory
	  File data = new File("src/test.zip");
          FileOutputStream fout = new FileOutputStream(data);
	  		
      // This is where we setup our streams (append = false means overwrite)
      // new FileWriter() creates the file if doesn't exit
	  ZipOutputStream zout = new ZipOutputStream(fout);
          zout.setLevel(9); // maximum compression
          ZipEntry ze = new ZipEntry("my text file");
          zout.putNextEntry(ze);
	  
	  // print statements do actual work of writing data
	  // note that print statements work similar to Sytem.out.println,
	  // where data is converted to strings
          String s = "Ths is a string with numbers 12.25";
          byte[] ba = s.getBytes();
	  for (int i = 0; i < ba.length; i++) {
            zout.write(ba[i]);
          } 
          zout.close();  // be sure you close your streams when done!!
	
	  System.out.println("Wrote file to: " + data.getAbsolutePath());
   }
}

package ioexamples;

import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * TextWriterApp.java	1.00 Nov 25, 2003
 *
 * A class that writes data to a text file, with buffering.
 */
public class TextWriteGUIApp{
	private static final int ERROR = JFileChooser.ERROR_OPTION;
	private static final int APPROVE = JFileChooser.APPROVE_OPTION;
	private static final int CANCEL = JFileChooser.CANCEL_OPTION;
	
   public static void main(String[] args) throws IOException{
   	  boolean append = false;   // you can change this
   	 
 	  // Build a file chooser component
 	  JFileChooser fileChooser = new JFileChooser();
 	
 	  // check if user clicked Cancel button on dialog (or got an error), if so, exit
 	  int result = fileChooser.showSaveDialog( null );
 	  if( !(result == APPROVE) )
 		 System.exit(0);
     	
  	  // Display file output locations in FileChooser dialog.
  	  // Return a selected or entered file path.
  	  // filter class, and to learn more about JFileChooser,
  	  // see http://java.sun.com/j2se/1.4.2/docs/api/javax/swing/JFileChooser.html
  	  File data = fileChooser.getSelectedFile();
	  	
  	  // Make sure we have permission to write this file to this path
	  if ( data.canWrite() ) {
	  	  // Now write to that path...
	      // This is where we setup our streams (append = false means overwrite)
	      // new FileWriter() creates the file if doesn't exit
		  PrintWriter out = new PrintWriter(
							new BufferedWriter(
							new FileWriter(data, append)));
		  
		  // print statements do actual work of writing data
		  // note that print statements work similar to Sytem.out.println,
		  // where data is converted to strings
		  out.print(5.25);
		  out.print('c');
		  out.print(true);
		  out.println("Java");
		  out.println( new Date() );
		  out.print("End of file");
		  out.close();  // be sure you close your streams when done!!
		
		  System.out.println("Wrote file to: " + data.getAbsolutePath());
	  } else {
		  System.out.println("Sorry, you do not have write permission on the path: " + data.getCanonicalPath());
	  } // end if
	  
	  System.exit(0);
	  
   } // end main
   
} // end class

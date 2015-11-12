package ioexamples;

import java.io.*;

import javax.swing.JFileChooser;
/**
 * TextReaderApp.java	1.00 Nov 25, 2003
 *
 * A class that reads data from a text file, with buffering. Uses
 * the file "test.txt" which is in this project. Try reading your
 * own text file stored in a different directory.
 */
public class TextReaderGUIApp{
	private static final int ERROR = JFileChooser.ERROR_OPTION;
	private static final int APPROVE = JFileChooser.APPROVE_OPTION;
	private static final int CANCEL = JFileChooser.CANCEL_OPTION;
	
   public static void main(String[] args) throws IOException {
	  	 
	   	// Build a file chooser component that displays files only
	   	JFileChooser fileChooser = new JFileChooser();
	   	fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
	   	
	   	// check if user clicked Cancel button on dialog, if so, return
	   	int result = fileChooser.showOpenDialog( null );
	 	if( !(result == APPROVE) )
	 		System.exit(0);
	   	
		// Display all files available in FileChooser dialog
		// To filter for only ".txt" or other files you need to create a custom
		// filter class, and to learn more about JFileChooser,
		// see http://java.sun.com/j2se/1.4.2/docs/api/javax/swing/JFileChooser.html
	   	File data = fileChooser.getSelectedFile();
			
		// check if file exists, if we have read access rights and if the File object
	   	// is in fact a file (vs a Directory).
	   	// If so, loop through and read each line of text
	    if ( data.exists() && data.canRead() && data.isFile() ){
		   BufferedReader in = new BufferedReader(
							 new FileReader(data));
		   String line = in.readLine();
		   while(line != null){
			  System.out.println(line);
			  line = in.readLine();  // strips out any carriage return chars
		   }
		   in.close();
		   System.exit(0);
	    } else {
		     System.out.println("File not found: " + data.getCanonicalPath() );
	    	 System.exit(1);
	    } // end if
	    
   } // end main
   
} // end class

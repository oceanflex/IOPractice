package ioexamples;

/*
 * @(#)BinaryReaderFileChooser.java	1.00 Nov 25, 2003
 *
 * This class is identical to BinaryReaderApp.java, except that it uses
 * the JFileChooser component from the swing API for locate the file.
 */
import java.io.*;
import javax.swing.*;

public class BinaryReaderFileChooser{
   public static void main(String[] args) throws IOException{
  	 
   	// Build a file chooser component that displays files only
   	JFileChooser fileChooser = new JFileChooser();
   	fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
   	
   	// check if user clicked Cancel button on dialog, if so, return
   	int result = fileChooser.showOpenDialog( null );
   	if( result == JFileChooser.CANCEL_OPTION )
   		System.exit(0);
   	
	// Display all files available in FileChooser dialog
	// To filter for only ".dat" files you need to create a custom
	// filter class, and to learn more about JFileChooser,
	// see http://java.sun.com/j2se/1.4.2/docs/api/javax/swing/JFileChooser.html
	File data = fileChooser.getSelectedFile();
		  
	  if (data.exists()){
		 DataInputStream in = new DataInputStream(
							  new BufferedInputStream(
							  new FileInputStream(data)));

		 int number = in.readInt();
		 System.out.println(number);

		 char letter = in.readChar();
		 System.out.println(letter);

		 boolean value = in.readBoolean();
		 System.out.println(value);

		 String string = in.readUTF();
		 System.out.println(string);

		 in.readChar();

		 // each character is represented by 2 bytes, so we
		 // must divide available bytes by 2 to get each character
		 int numberOfChars = in.available()/2;
		 String characters = "";
		 for (int i = 0; i < numberOfChars; i++){
			char c = in.readChar();
			characters += c;
		 }
		 System.out.println(characters);

		 in.close();

	  }
   }
}

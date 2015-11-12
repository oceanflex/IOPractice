package ioexamples;

/*
 * @(#)TextFileHandler.java	1.00 May 5, 2004
 *
 * Copyright (c) 2003-2004 Waukesha County Technical College. All Rights Reserved.
 *
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF 
 * THE ACADEMIC FREE LICENSE V2.0 ("AGREEMENT"). ANY USE, REPRODUCTION 
 * OR DISTRIBUTION OF THE PROGRAM CONSTITUTES RECIPIENT'S ACCEPTANCE 
 * OF THIS AGREEMENT. A COPY OF THE AGREEMENT MUST BE ATTACHED TO ANY
 * AND ALL ORIGINALS OR DERIVITIVES.
 */
 
import java.io.*;
import java.util.*;

/**
 * An adapter class for handling text <--> file read/write operations.
 * This class can be used as a stand-alone, or as an adapter for another class.
 * 
 * @author Jim Lombardo
 */
public class TextFileHandler implements FileHandler {

	/**
	 * Writes an array of objects sequentially to a text file. Typically
	 * these objects will be Strings, but any object can be used, since
	 * the text of the object will be provided by the object's toString() method.
	 * 
	 * @param filePath - the absolute or relative path to the file.
	 * @param objects - an array of objects (typically strings).
	 * @param fileType - a flag indicating the type of file to be accessed. Must
	 * be FileHandler.TEXT_FILE for this method.
	 * are used to indicate the type of file to be created or read.
	 * @param append - true or false when appending text files only.
	 * @return null if successfull, an error message if not.
	 * 
	 * @see FileHandler#writeFile(String, Object[], int, boolean)
	 */
	public String writeFile(String filePath, Object[] objects, int fileType, boolean append) {
		String errorMessage = null; // if unchanged, method was successfull.
		File file = new File(filePath);
		
		// write to file of type typeCode
		switch(fileType) {
			case FileHandler.TEXT_FILE:
				try {
					PrintWriter out = new PrintWriter(
									  new BufferedWriter(
									  new FileWriter(file, append)));
					
					/* Combine individual String objects into one large String.
					 * Use StringBuffer for speed
					 */
					StringBuffer sbuf = new StringBuffer();
					for( int i=0; i<objects.length; i++) {
						sbuf.append(objects[i]).append("\n");  // String cast unnecessary due to toString()
					}
					out.println( sbuf.toString() ); // why is toString() needed? Because println() is overloaded.
					out.close();
				} catch(IOException ioe) {
					errorMessage = ioe.getMessage();
				}
				break;
			
			default:
				return "fileType flag set to non-text code. " +
					   "TextFileHandler#writeFile operation is aborted.";
		}
		return errorMessage; // if null, method was successfull.
	}

	/**
	 * Reads String objects from a text file.
	 * 
	 * @param filePath - the absolute or relative path to the file.
	 * @param fileType - a flag indicating the type of file to be accessed. Must
	 * be FileHandler.TEXT_FILE for this method.
	 * @return an array of objects, where the first object element is null
	 * and the second object element is a Vector of Strings retrieved. Strings
	 * are delimited by a return character. If the first
	 * element is not null, that element will contain an error
	 * message as a String.
	 * @see FileHandler#readFile(String, int)
	 */
	public Object[] readFile(String filePath, int fileType) {
		Object[] objects = { null, null };
		Vector strings = new Vector();
		File file = new File(filePath);
		
		
		// write to file of type typeCode
		switch(fileType) {
			case FileHandler.TEXT_FILE:
				try {
					BufferedReader in = new BufferedReader(
										new FileReader(file));
					String line = in.readLine();
					while(line != null){
					   strings.add(line);
					   line = in.readLine();
					}
					in.close();
					objects[1] = strings; // put Vector in object array
				} catch(IOException ioe) {
					objects[0] = ioe.getMessage();
				}
				break;
			
			default:
				objects[0] = "fileType flag set to non-text code. " +
					   "TextFileHandler#readFile operation is aborted.";
		}
				
		return objects;
	}

	/**
	 * For unit testing only. Typically this will not be included in
	 * production versions of this class.
	 * @param args -- not used.
	 */
	public static void main(String[] args) {
		TextFileHandler handler = new TextFileHandler();
		
		// Try to read a file that doesn't exist
		Object[] objects = handler.readFile("src/test.txt", FileHandler.TEXT_FILE);
		// Print error message
		if( objects[0] != null ) System.out.println(objects[0]);
		
		//Next write to a file
		String[] strings = {"Sentence 1", "Sentence 2"};
		String errorMessage = handler.writeFile("src/test.txt",
							  strings,FileHandler.TEXT_FILE,false);
		if( errorMessage != null ) System.out.println(errorMessage);
		
		//Now read the text back in
		objects = handler.readFile("src/test.txt", FileHandler.TEXT_FILE);
		// Print error message
		if( objects[0] == null ) {
			Vector v = (Vector)objects[1];
			for(int i=0; i<v.size(); i++) {
				System.out.println(v.get(i));
			}
		} else {
			System.out.println(objects[0]);
		}
	}
}
